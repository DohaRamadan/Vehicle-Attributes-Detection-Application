# Vehicle-Attributes-Detection-Application
## Description 
- An application where the user can upload traffic camera footage videos and the videos get uploaded to the server(currently not working)
- and the server runs Vehicle Detection models, Vehicle Color Detection models, Vehicle Model and Make Detection models, Licence Plate Recognition models and Vehicle Speed Estimation models on the uploaded video.
- The server returns all the vehicles detected in the video each with their detected color, make, model, license plate, and estimated speed.
- This data gets saved in the client database and the user can browse through all the detected vehicles in each uploaded video.

## Tools Used for the Application Part 
- Java
- SQLite
- JavaFX

## How to Run
- Make sure you have all the dependencies installed, which you can find in the pom.xml file.
- Make sure to add the sqlite-jdbc-3.36.0.3.jar as a library in your project.
- Open the project in your IDE.
- navigate to src\main\java\com\example\figma\Application.java and run it.
- Due to the unavailability of a server, the video submission will always fail, but you can browse the history and check out application_demo.mp4 to see the application in its full functionality.



