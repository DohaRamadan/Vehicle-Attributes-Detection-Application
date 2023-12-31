package com.example.figma;

import com.example.figma.entities.Vehicle;
import javafx.util.Pair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class APIController {


    public static String checkProcessingStatus(String apiHttp) throws IOException {

        URL url = new URL(apiHttp + "/get_processing_status");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        String responseBody = response.toString();
        System.out.println("Response: " + responseBody);
        return responseBody;
    }

    public static void waitForProcessingCompletion() throws InterruptedException {
        long delay = 80000;
        Thread.sleep(delay);
    }


    public static boolean uploadVideo(String videoLocation, String apiHttp) throws IOException {
        System.out.println("---------------------------- Uploading video ----------------------------");
        URL url = new URL(apiHttp + "/Vechicle_Attribute_Detection");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        // Set the request content type
        String boundary = "---------------------------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        // Open the output stream for writing the request body
        OutputStream outputStream = connection.getOutputStream();
        // Write the file part
        File videoFile = new File(videoLocation);
        String fileName = videoFile.getName();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        String filePartHeader = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"video\"; filename=\"" + fileName + "\"\r\n" +
                "Content-Type: video/mp4\r\n\r\n";
        outputStream.write(filePartHeader.getBytes());
        FileInputStream fileInputStream = new FileInputStream(videoFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Write the closing boundary
        String closingBoundary = "\r\n--" + boundary + "--\r\n";
        outputStream.write(closingBoundary.getBytes());
        // Close the streams
        fileInputStream.close();
        outputStream.close();
        // Get the response code

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);


        return responseCode == 200;
    }

    public static void sendSpeedAttr(String apiHttp, ArrayList<Pair<Integer, Integer>> mouseClicks, float distance) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(mouseClicks.get(mouseClicks.size() - 4).getKey());
        numbers.add(mouseClicks.get(mouseClicks.size() - 4).getValue());

        numbers.add(mouseClicks.get(mouseClicks.size() - 3).getKey());
        numbers.add(mouseClicks.get(mouseClicks.size() - 3).getValue());

        numbers.add(mouseClicks.get(mouseClicks.size() - 2).getKey());
        numbers.add(mouseClicks.get(mouseClicks.size() - 2).getValue());

        numbers.add(mouseClicks.get(mouseClicks.size() - 1).getKey());
        numbers.add(mouseClicks.get(mouseClicks.size() - 1).getValue());
        // Create a JSON string payload
        String payload = "{\"numbers_int\":" + numbers + ", \"number_float\":" + distance + "}";
        // Make a POST request to the Flask endpoint
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiHttp + "/send_speed_attr"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());  // Print the response from the Flask server
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String processingVideo(String apiHttp) throws IOException, InterruptedException {
        System.out.println("---------------------------- Processing ----------------------------");
        URL url = new URL(apiHttp + "/process_video");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // Get the response code
        int responseCode;
        try {
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            int retries = 1;
            int maxRetries = 5000;
            System.out.println("delay");
            String status = checkProcessingStatus(apiHttp);
            while (status.equals("in_progress") && retries < maxRetries) {
                waitForProcessingCompletion();
                status = checkProcessingStatus(apiHttp);
                retries++;
            }
            return "completed";
        }
        if (responseCode == 200)
            return "completed";
        int retries = 1;
        int maxRetries = 5000;
        System.out.println("delay");
        String status = checkProcessingStatus(apiHttp);
        while (status.equals("in_progress") && retries < maxRetries) {
            waitForProcessingCompletion();
            status = checkProcessingStatus(apiHttp);
            retries++;
        }
        return "completed";


    }


    public static ArrayList<Vehicle> getTextData(String apiHttp) throws IOException {
        String rawData = null;
        URL url = new URL(apiHttp + "/getdata");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // Get the response code
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            // Parse the JSON response
            String jsonResponse = response.toString();
            // Process the jsonResponse as needed
            rawData = jsonResponse;
        } else {
            System.out.println("Request failed. Response Code: " + responseCode);
        }
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        System.out.println(rawData);
        String[] parts = rawData.split("ss");
        for (String part : parts) {
            String trimmedPart = part.trim();
            if (!trimmedPart.isEmpty()) {
                strings.add(trimmedPart);
            }
        }
        for (String string : strings) {
            String[] partss = string.split("\\s+");
            int trackerID = Integer.parseInt(partss[0]);
            String type = partss[1];
            float typeConf = Float.parseFloat(partss[2]);
            String make = partss[3];
            String model = partss[4];
            float makeModelConf = Float.parseFloat(partss[5]);
            String lpText = partss[6].replaceAll("[()',]", "");
            String lpConf = partss[7].replaceAll("[()']", "");  // Remove parentheses and single quotes
            String color = partss[9].replaceAll("[',]", "");
            float speed = Float.parseFloat(partss[20]);
            Vehicle vehicle = new Vehicle(type, trackerID, model, make, color, speed, "", lpText, "");
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    public static void getImageData(String savePath, String apiHttp) throws IOException {
        URL url = new URL(apiHttp + "/get_image");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");


        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream(savePath);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                fileOutputStream.close();
                inputStream.close();
                System.out.println("Images downloaded successfully.");
            } else {
                System.out.println("Failed to download images. Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<Vehicle> callApi(String videoLocation, String apiHttp, String formattedTime, ArrayList<Pair<Integer, Integer>> mouseClicks, float distance) throws IOException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try {
            if (uploadVideo(videoLocation, apiHttp)) {
                System.out.println("==========Uploaded Successfully==========");
                sendSpeedAttr(apiHttp, mouseClicks, distance);
                if (processingVideo(apiHttp).equals("completed")) {
                    vehicles = getTextData(apiHttp);
                    getImageData("images.zip", apiHttp);
                    formattedTime = formattedTime.replace(":", "").replaceAll(" ", "").replaceAll("/", "");
                    String folderName = formattedTime;

                    System.out.println(folderName);

                    try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Path.of("images.zip")))) {
                        byte[] buffer = new byte[1024];
                        ZipEntry entry;
                        String destinationFolderName = "C:\\Users\\DOHA\\IdeaProjects\\Figma\\src\\main\\resources\\com\\example\\figma\\" + formattedTime;
                        while ((entry = zipInputStream.getNextEntry()) != null) {
                            String entryName = entry.getName();
                            File entryFile = new File(destinationFolderName, entryName);
                            if (entry.isDirectory()) {
                                entryFile.mkdirs();
                            } else {
                                new File(entryFile.getParent()).mkdirs();
                                FileOutputStream fos = new FileOutputStream(entryFile);
                                int length;
                                while ((length = zipInputStream.read(buffer)) > 0) {
                                    fos.write(buffer, 0, length);
                                }
                                fos.close();
                            }
                            zipInputStream.closeEntry();
                        }
                        System.out.println("ZIP file extracted successfully.");
                        vehicles = getTextData(apiHttp);
                        getImageData("images.zip", apiHttp);
                        for (int i = 0; i < vehicles.size(); i++) {
                            vehicles.get(i).setImageSrc(destinationFolderName + "\\content\\" + vehicles.get(i).getID() + ".jpg");

                        }
                        for (int i = 0; i < vehicles.size(); i++) {
                            System.out.println("car " + (i + 1) + " : ");
                            System.out.println("Tracker ID: " + vehicles.get(i).getID());
                            System.out.println("Type: " + vehicles.get(i).getType());
                            System.out.println("Make: " + vehicles.get(i).getMake());
                            System.out.println("Model: " + vehicles.get(i).getModel());
                            System.out.println("License Plate Text: " + vehicles.get(i).getLicencePlateString());
                            System.out.println("speed: " + vehicles.get(i).getSpeed());
                            System.out.println("image path: " + vehicles.get(i).getImageSrc());
                        }
                        String status = checkProcessingStatus(apiHttp);
                        System.out.println(status);
                    }

                }
                else {
                    return null;
                }
            }
        else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return vehicles;
    }

}
