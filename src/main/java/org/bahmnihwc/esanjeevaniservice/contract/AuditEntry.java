package org.bahmnihwc.esanjeevaniservice.contract;

public class AuditEntry {
    private String timestamp;
    private String username;
    private String patientName;

    public AuditEntry(String timestamp, String username, String patientName) {
        this.timestamp = timestamp;
        this.username = username;
        this.patientName = patientName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getPatientName() {
        return patientName;
    }

    @Override
    public String toString() {
        return timestamp + "," + username + "," + patientName;
    }
}
