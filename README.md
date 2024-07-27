# WeatherForecast

Steps to Run the project

1. Maven setup
* Download maven zip for windows - `apache-maven-3.9.8-bin.zip` - https://maven.apache.org/download.cgi
* Unzip the maven folder and keep in any drive.
* Environment Variable setup - https://phoenixnap.com/kb/install-maven-windows
* Restart the System.
* run maven command to verify that maven has been setup successfully or not.
  - `mvn -version`
2. Open This project in Intellij
3. Setup JDK In Intellij
4. Run mvn build command to build your project.
   - `mvn clean install`
5. Once the maven build successfully completed then run your application from below command
   - `mvn jetty:run`