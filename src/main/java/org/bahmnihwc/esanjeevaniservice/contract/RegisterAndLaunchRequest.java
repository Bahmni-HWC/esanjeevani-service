package org.bahmnihwc.esanjeevaniservice.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

public class RegisterAndLaunchRequest {

    @JsonProperty("credentials")
    @Valid
    private EsanjeevaniCredentials eSanjeevaniCredentials;

    @JsonProperty("patient")
    @Valid
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public EsanjeevaniCredentials getEsanjeevaniCredentials() {
        return eSanjeevaniCredentials;
    }

    public void setEsanjeevaniCredentials(EsanjeevaniCredentials eSanjeevaniCredentials) {
        this.eSanjeevaniCredentials = eSanjeevaniCredentials;
    }
}
