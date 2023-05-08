![Logo](ui/src/main/resources/images/weingutmerlot.png)
## Overview
Welcome to our small project for the ATIT course. The following guide outlines how to get started with developing
the project. To find out more about the project please refer to our Wiki:

[Weingut Merlot Wiki](https://github.com/ItsZiroy/atit-weingut-merlot/wiki)
## Prerequisits

### Java SDK 19
This project runs on java Version 19.0.2. Make sure you either install it system-wide or
make sure your IDE uses it for executing all maven goals.

To run the javafx application you need to have the right java version configured in the path.
You can check this with the following command:

```shell
java --version
# which should result in something similar to this
openjdk 19.0.2 2023-01-17
OpenJDK Runtime Environment (build 19.0.2+7-44)
OpenJDK 64-Bit Server VM (build 19.0.2+7-44, mixed mode, sharing)
```
### MariaDB/ Mysql
Download the latest version of either MariaDB or Mysql and install the database
to your system. Make sure to remember the root password that you set during
installation!

### Maven
Maven is the package manager that is used for building the project. If you use
the IntelliJ IDEA this is usually installed automatically. If not, you can download
the latest version from their [Website](https://maven.apache.org)

## Installation
Clone the repository and install maven dependencies
```shell
git clone https://github.com/ItsZiroy/atit-weingut-merlot.git
cd atit-weingut-merlot

mvn clean install
# If you don't want or don't need to run dependency vulnerability checks you
# can skip them:
mvn clean install --Ddependency-check.skip=true
```

### Initializing Database
From the root directory of the project run the following two commands in the terminal
and change your username to your database user:

On macos/linux:
```shell
mysql -u <<user>> -p < datamodel/sql/initialize.sql

mysql -u <<user>> -p < datamodel/sql/data.sql
```

On windows:

```shell
## Open mysql Window
mysql

# Initialize db
mysql> source datamodel/sql/initialize.sql;
mysql> source datamodel/sql/data.sql;
```


### Persistence.xml
Copy content from [persistence.template.xml](/backend/src/main/resources/META-INF/persistence.template.xml)
and create a new file in the same folder called `persistence.xml` and change the following content
to your database configuration

For Mysql:
```xml
        <properties>
            <property name="javax.persistence.jdbc.driver" value="mysql.mysql-connector-java.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://127.0.0.1:3306/weingutmerlot" />
            <property name="javax.persistence.jdbc.user" value="{{your_user}}" />
            <property name="javax.persistence.jdbc.password" value="{{your_password}}" />
        </properties>
```

For MariaDB:

```xml
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mariadb://127.0.0.1:3306/weingutmerlot" />
            <property name="javax.persistence.jdbc.user" value="{{your_user}}" />
            <property name="javax.persistence.jdbc.password" value="{{your_password}}" />
        </properties>
```

After changing the persistence.xml you need to run another clean install so the ui has the
updated backend version:

```shell
mvn clean install
```

### Running the Application
Having everything in place you just need to run the `javafx:run` maven goal in the ui module to get 
the application going either through the editor or through the command line:

```shell
mvn -pl ui javafx:run
```

## Migration Guide

In order to install the latest packages and have the current data model version, when developing please follow
these two steps to upgrade your project on changes:

### Updating dependencies
Either run 
```shell
mvn clean install -Ddependency-check.skip=true -Djacoco.skip=true -Dmaven.javadoc.skip=true
```
or run the included maven run configuration, which ultimately does the same. 
See [here](/.run/Update%20Backend%20For%20Frontend.run.xml) for more.

### Updating the Database
Currently, there are no migration files included, so in order to upgrade the database, either manually map the changes
to the current instance or drop the database and follow the guide under [Initializing Database](#initializing-database)

## Multi Modular Project
This project consists of multiple modules:
* [/backend](/backend) For Data related functionality
* [/ui](/ui) JavaFX Ui
* ([/datamodel](/datamodel)) Not a maven module but this is where all the information
    regarding the data model lies.

## Contact
If you have further questions, you can contact us directly at 
[maximilian.leonhard@studmail.hwg-lu.de](mailto:maximilian.leonhard@studmail.hwg-lu.de), 
[dennis.luedke@studmail.hwg-lu.de](mailto:dennis.luedke@studmail.hwg-lu.de), 
[laura.goedde@studmail.hwg-lu.de](mailto:laura.goedde@studmail.hwg-lu.de) 
or [yannik.hahn@studmail.hwg-lu.de](mailto:yannik.hahn@studmail.hwg-lu.de)
