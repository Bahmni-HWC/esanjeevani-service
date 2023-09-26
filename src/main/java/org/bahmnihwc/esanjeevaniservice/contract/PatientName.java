package org.bahmnihwc.esanjeevaniservice.contract;

public class PatientName {
    private String firstName;
    private String lastName;
    private String middleName;

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        if (middleName == null || middleName.isEmpty()) {
            return (firstName + " " + lastName).trim();
        } else {
            return (firstName + " " + middleName + " " + lastName).trim();
        }
    }

}
