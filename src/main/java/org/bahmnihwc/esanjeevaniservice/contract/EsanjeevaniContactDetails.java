package org.bahmnihwc.esanjeevaniservice.contract;

public class EsanjeevaniContactDetails {
    private boolean contactPointStatus;
    private String contactPointType;
    private String contactPointUse;
    private String contactPointValue;

    public boolean getContactPointStatus() {
        return contactPointStatus;
    }

    public void setContactPointStatus(boolean contactPointStatus) {
        this.contactPointStatus = contactPointStatus;
    }

    public String getContactPointType() {
        return contactPointType;
    }

    public void setContactPointType(String contactPointType) {
        this.contactPointType = contactPointType;
    }

    public String getContactPointUse() {
        return contactPointUse;
    }

    public void setContactPointUse(String contactPointUse) {
        this.contactPointUse = contactPointUse;
    }

    public String getContactPointValue() {
        return contactPointValue;
    }

    public void setContactPointValue(String contactPointValue) {
        this.contactPointValue = contactPointValue;
    }
}
