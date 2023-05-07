# Integration Tests
### M2 in-memory DB and JPA
The integration test suit is currently running on the M2 in-memory database.
This works because JPA is being used as an ORM tool and therefore, it can create the
desired structure in the M2 database during the integration tests. Because this
project does not use native queries, JPA always maps the created queries to the underlying
database and therefore, although the M2 database is different from the actual MySQL/ MariaDB
database, it can be used just fine during the integration tests.

PS: This method was used due to the lack of resources and time to start up a dedicated docker
image with the exact database, which would be the preferred method for unit tests.