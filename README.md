# eSanjeevani Service
This is a micro-service which is used to integrate with eSanjeevani application.

### Tech Stack
- Java 17
- Spring Boot
- H2 Database for Address Hierarchies with LGD Code

### How to build and run
- Run `./mvnw clean install` to build the project.
- Run `docker build -t bahmnihwc/esanjeevani-service -f package/docker/Dockerfile .` to build the docker image.
- The service is added with proxy configurations in Bahmni-HWC docker compose. So do a `docker compose up -d` from Bahmni-HWC/bahmni-india-package repository.

### API Information
This service expose one API on the path `/esanjeevani-bridge/registerAndLaunch` which is used to register a patient and launch the eSanjeevani application. The request body of the API is below in which abhaAddress and abhaNumber is optional.
```json
{
  "patient": {
    "name": {
      "firstName": "Test",
      "lastName": "Sample",
      "middleName": "Test"
    },
    "dateOfBirth": "2022-10-10",
    "age":24,
    "phoneNumber": "9876543219",
    "gender": "Male",
    "address": {
      "state": "KARNATAKA",
      "district": "BENGALURU RURAL",
      "subDistrict": "HOSAKOTE",
      "village": "ANUPAHALLI",
      "postalCode": "560001"
    },
    "abhaAddress": "",
    "abhaNumber": ""
  },
  "credentials": {
    "username": "dummy",
    "password": "duumy"
  }
}
```
In the address hierarchy, state, district, subDistrict should have a matching entry with LGD code in the database. If the entry is not found, then the patient registration will fail. The hierarchy can be found in the [addresshierarchy.csv](./src/main/resources/addresshierarchy.csv) file. The contents from the CSV are read and stored into in-memory H2 database on startup of the application.

The API will return a 200 response with the SSO login URL in the response text. The URL can be used to launch the eSanjeevani application. Any failure in login or registration will return a 400 BAD REQUEST with the appropriate error message from e-Sanjeevani. 

### Audit Logging
In order to log successful redirects to e-sanjeevani, an Audit service is introduced. There are two different audit logging possible. It can be configured by setting the `ESANJEEVANI_AUDIT_METHOD` environment variable to either OPENMRS_AUDIT or CSV_AUDIT

#### OpenMRS Audit Logging
When this method is used, audit entries are added to audit_log table in Bahmni OpenMRS database. This is done using the audit_log API. The count can be found by using the Bahmni reporting module.

#### CSV Based Auditing
When this method is used, logs are added to a CSV file in the container under /opt/esanjeevani/audit which is volume mounted. The CSV file name is of the format `audit_<date>.csv`. 

For retrieving logs from the CSV log files, an API endpoint is exposed. Below are the details:

Path: `/esanjeevani-bridge/report`
Query Params: fromDate, toDate (Format: yyyy-MM-dd)

Response Schema:

```json
{
  "<esanjeevaniusername>": "<count>"
}
```
