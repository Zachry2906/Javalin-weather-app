# Java Web Application with Javalin

This repository contains a Java web application built using the Javalin framework. The project demonstrates the integration of various web technologies to create a robust and responsive web application.

This program is a website that displays weather, temperature, and time information for certain locations around the world. 

You can read about the program ideas and detail program documentation 
[here](https://docs.google.com/document/d/1FYWJrXB408GinDQZMd4ar14Gj0jsDXasvONAo-KuPPo/edit?usp=sharing). Unfortunately I only provide documentation in Indonesian, next time I will try to make it in English

## Technologies Used

- Java
- Javalin (Web Framework)
- jQuery
- AJAX
- Bootstrap
- Maven (Dependency Management)

## Features
- Showing temperature, time, date and weather according to the place we choose
- Add city or select city, you can input it with name of the city, latitude and longitude, or you can select city with map that has been provided inside
- Every city that has been added will appear in select city menu in navbar
- In select city menu, there's search features to search city that has benn added
- The background image will change according to what time is it, there's 6 condition. Dawn, morning, noon, dusk, evening, and midnight.

## Setup and Running

### Running App

if you just want to see and run the app, follow this step : 
-  Download file *weather-app-1.0-SNAPSHOT-jar-with-dependencies.jar* from this repository
- Inside folder where you download the file, open terminal then run
``` bash
java -jar weather-app-1.0-SNAPSHOT-jar-with-dependencies.jar
```
- Make sure you have Java JDK 22 or above installed in your local to prevent error while running the program
- project will run in *http://localhost:1234/*

**Or if you want to clone this repo, edit, and run the program, you can follow step below**

### Prerequisites

- Java JDK 22 or higher
- Maven
- IntelliJ IDEA (strongly recommended)

### Steps to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Refresh Maven dependencies:
   - Right-click on the `pom.xml` file
   - Select "Maven" > "Reload project"
4. Run the main application file

**Important Note:** Always refresh your Maven dependencies before running the project to ensure all required libraries are up to date.

## IDE Recommendation

While this project can be run in various IDEs, i strongly recommend using IntelliJ IDEA for the best development experience. IntelliJ IDEA provides excellent support for Java development and offers seamless integration with Maven and Javalin.

## Contributing

Feel free to fork this project and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## Acknowledgements

- Javalin Framework: [https://javalin.io/](https://javalin.io/)
- jQuery: [https://jquery.com/](https://jquery.com/)
- Bootstrap: [https://getbootstrap.com/](https://getbootstrap.com/)
