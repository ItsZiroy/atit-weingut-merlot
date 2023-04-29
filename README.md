# ATIT Weingut Merlot
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

### Intializing Database
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

## Multi Modular Project
This project consists of multiple modules:
* [/backend](/backend) For Data related functionality
* [/ui](/ui) JavaFX Ui
* ([/datamodel](/datamodel)) Not a maven module but this is where all the information
    regarding the data model lies.


