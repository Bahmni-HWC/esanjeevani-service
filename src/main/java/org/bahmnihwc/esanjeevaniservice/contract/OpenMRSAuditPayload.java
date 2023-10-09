package org.bahmnihwc.esanjeevaniservice.contract;

public class OpenMRSAuditPayload {
    private String eventType;
    private String message;
    private String module;

    public OpenMRSAuditPayload(String message) {
        this.eventType = "ACCESSED_E_SANJEEVANI";
        this.message = message;
        this.module = "E-Sanjeevani";
    }

    public String getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

    public String getModule() {
        return module;
    }
}
