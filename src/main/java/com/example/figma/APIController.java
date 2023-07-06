package com.example.figma;

import com.example.figma.entities.Vehicle;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class APIController {

//    //////// TODO
//    Vehicle vehicle = new Vehicle(type, trackerID, model, make, "RED", 67, "images/car1.jpg", lpText , "");
//
//    // Print the car details
//                System.out.println("Tracker ID: " + vehicle.getID());
//                System.out.println("Type: " + vehicle.getType());
//                System.out.println("Make: " + vehicle.getMake());
//                System.out.println("Model: " + vehicle.getModel());
//                System.out.println("License Plate Text: " + vehicle.getLicencePlateString());
//                result.add(vehicle);

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

    public static void waitForProcessingCompletion() throws IOException, InterruptedException {
        long delay = 80000;
        Thread.sleep(delay);
    }


    public static boolean uploadVideo(String videoLocation, String apiHttp) throws IOException, InterruptedException {
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

        System.out.println("---------------------------- Processing ----------------------------");
        // Close the streams
        fileInputStream.close();
        outputStream.close();
        // Get the response code

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);


        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            return true;
        }
        if (responseCode == 503) {
            int retries = 1;
            int maxRetries = 10;
            System.out.println("hi");
            String status = checkProcessingStatus(apiHttp);
            while (status.equals("in_progress") && retries < maxRetries) {
                waitForProcessingCompletion();
                status = checkProcessingStatus(apiHttp);
                retries++;
            }
            return true;
        }
        return false;
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
        String[] parts = rawData.split("ss");
        for (String part : parts) {
            String trimmedPart = part.trim();
            if (!trimmedPart.isEmpty()) {
                strings.add(trimmedPart);
            }
        }
        for (int i = 0; i < strings.size(); i++) {
            String[] partss = strings.get(i).split("\\s+");
            int trackerID = Integer.parseInt(partss[0]);
            String type = partss[1];
            float typeConf = Float.parseFloat(partss[2]);
            String make = partss[3];
            String model = partss[4];
            float makeModelConf = Float.parseFloat(partss[5]);
            String lpText = partss[6].replaceAll("[()',]", "");
            String lpConf = partss[7].replaceAll("[()']", "");  // Remove parentheses and single quotes
            float speed = Float.parseFloat(partss[8]);
            Vehicle vehicle = new Vehicle(type, trackerID, model, make, "RED", speed, "", lpText, "");
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
                System.out.println("hi");
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


    public static ArrayList<Vehicle> callApi(String videoLocation, String apiHttp) throws IOException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        try {

            if (uploadVideo(videoLocation, apiHttp)) {
                ArrayList<Vehicle> vehicle = new ArrayList<>();
                vehicle = getTextData(apiHttp);
                getImageData("images.zip", apiHttp);
                Path currentPath = Paths.get("");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String folderName = LocalDateTime.now().format(formatter);
                String formattedTimestamp = folderName.replace(":", "");
                System.out.println(folderName);

                try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Path.of("images.zip")))) {
                    byte[] buffer = new byte[1024];
                    ZipEntry entry;
                    while ((entry = zipInputStream.getNextEntry()) != null) {
                        String entryName = entry.getName();
                        String destinationFolderName = "C:\\Users\\DOHA\\IdeaProjects\\Figma\\src\\main\\resources\\com\\example\\figma\\"+formattedTimestamp;
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
//                    Path currentPath = Paths.get("");
                    for (int i = 0; i < vehicles.size(); i++) {
//                vehicles.get(i).setImageSrc(String.valueOf("content/"+vehicles.get(i).getID()+".jpg"));
                        vehicles.get(i).setImageSrc(formattedTimestamp + "/content/" + vehicles.get(i).getID() + ".png");
                    }
                    for (int i = 0; i < vehicles.size(); i++) {
                        System.out.println("car " + (i + 1) + " : ");
                        System.out.println("Tracker ID: " + vehicles.get(i).getID());
                        System.out.println("Type: " + vehicles.get(i).getType());
//                System.out.println("Type Confidence: " + vehicles.get(i).typeConf);
                        System.out.println("Make: " + vehicles.get(i).getMake());
                        System.out.println("Model: " + vehicles.get(i).getModel());
//                System.out.println("Make-Model Confidence: " + vehicles.get(i).makeModelConf);
                        System.out.println("License Plate Text: " + vehicles.get(i).getLicencePlateString());
//                System.out.println("License Plate Confidence: " + vehicles.get(i).lpConf);
                        System.out.println("speed: " + vehicles.get(i).getSpeed());
                        System.out.println("image path: " + vehicles.get(i).getImageSrc());
                    }
                    System.out.println("hi");
                    String status = checkProcessingStatus(apiHttp);
                    System.out.println(status);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicles;
    }

//    public static void main(String[] args) throws IOException {
//        callApi("D:\\grad\\vid.mp4", "http://b6c8-34-125-243-125.ngrok-free.app");
//
//    }=
}
