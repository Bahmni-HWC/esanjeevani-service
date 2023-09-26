package org.bahmnihwc.esanjeevaniservice.contract;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ESanjeevaniRegisterPatientRequest {
    private String abhaAddress;
    private String abhaNumber;
    private int age;
    private String birthdate;
    private String displayName;
    private String firstName;
    private String middleName;
    private String lastName;
    private int genderCode;
    private String genderDisplay;
    private boolean isBlock;
    private List<EsanjeevaniPatientAddress> lstPatientAddress;
    private List<EsanjeevaniContactDetails> lstPatientContactDetail;
    private String source;

    public String getAbhaAddress() {
        return abhaAddress;
    }

    public void setAbhaAddress(String abhaAddress) {
        this.abhaAddress = abhaAddress;
    }

    public String getAbhaNumber() {
        return abhaNumber;
    }

    public void setAbhaNumber(String abhaNumber) {
        this.abhaNumber = abhaNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(int genderCode) {
        this.genderCode = genderCode;
    }

    public String getGenderDisplay() {
        return genderDisplay;
    }

    public void setGenderDisplay(String genderDisplay) {
        this.genderDisplay = genderDisplay;
    }

    public boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean block) {
        isBlock = block;
    }

    public List<EsanjeevaniPatientAddress> getLstPatientAddress() {
        return lstPatientAddress;
    }

    public void setLstPatientAddress(List<EsanjeevaniPatientAddress> lstPatientAddress) {
        this.lstPatientAddress = lstPatientAddress;
    }

    public List<EsanjeevaniContactDetails> getLstPatientContactDetail() {
        return lstPatientContactDetail;
    }

    public void setLstPatientContactDetail(List<EsanjeevaniContactDetails> lstPatientContactDetail) {
        this.lstPatientContactDetail = lstPatientContactDetail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
