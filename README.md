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
This service expose one API on the path `/esanjeevani-bridge/registerAndLaunch` which is used to register a patient and launch the eSanjeevani application. The request body of the API is below
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
    }
  },
  "credentials": {
    "username": "dummy",
    "password": "duumy"
  }
}
```
In the address hierarchy, state, district, subDistrict should have a matching entry with LGD code in the database. If the entry is not found, then the patient registration will fail. The hierarchy can be found in the [addresshierarchy.csv](./src/main/resources/addresshierarchy.csv) file. The contents from the CSV are read and stored into in-memory H2 database on startup of the application.

The API will return a 200 response with the SSO login URL in the response text. The URL can be used to launch the eSanjeevani application. Any failure in login or registration will return a 400 BAD REQUEST with the appropriate error message from e-Sanjeevani. 
