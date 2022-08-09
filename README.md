# HabitYouHeard
This repo consists of our back-end logic, including our API and allows interaction with the database via Spring Data JPA and Hibernate.

[Link to front end repo](https://github.com/Jul-2022-LC-LiftOff/Habit-You-Heard-Frontend)

Getting Started with HabitYouHeard

## Install

This project uses Java. [Here](https://www.freecodecamp.org/news/how-to-install-java-on-windows/) are general instructions to download what you need to your local machine.

This project uses Java version 17.

Download IntelliJ or similar IDE if not already downloaded.

Download MySQL Workbench This project uses version 8.

## MySql Set Up

Navigate to MySQL Workbench

-Open up Local instance 3306 connection or add a new one. 

Each step here is very important that it is followed to the letter. If there is any issue with any of these steps, please let us know immediately so that we can fix them.

Note: When quotation marks are used (like around “habityouheard”) the quotation marks should not be included in what you type in.

1. Create new Schema called “habityouheard”
2. In MySQL, navigate to Administration -> Management -> Users and Privileges 
-Click add account. Make sure that you take note of the login name and password

3. Navigate to Schema Privileges. 

-Click Add Entry Select habityouheard

-Click the Select “ALL” button

4. In IntelliJ :
-Before bootRun, add the previously mentioned login name and password from your Add Account step and create [Environmental Variables](https://education.launchcode.org/gis-devops/configurations/02-environment-variables-intellij/index.html) in IntelliJ named : “DB_LOGIN” And “DB_PASSWORD” respectively.

## Usage

This project is still in progress, so some of the instructions may not be functioning at this point in development!

Since IntelliJ uses the Gradle Wrapper, the easiest way to run the program is opening the Gradle tab, navigate to habityouheard -> Tasks -> application -> bootrun.

The API can then be accessed at [http://localhost:8080](http://localhost:8080) via Postman or another tool to access endpoints. Specific endpoint routes can be found by inspecting the Controllers. 

