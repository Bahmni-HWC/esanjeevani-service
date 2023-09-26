package org.bahmnihwc.esanjeevaniservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Address {

    private String state;
    private String stateCode;
    private String district;
    private String districtCode;
    private String subDistrict;
    private String subDistrictCode;

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getSubDistrictCode() {
        return subDistrictCode;
    }

    public void setSubDistrictCode(String subDistrictCode) {
        this.subDistrictCode = subDistrictCode;
    }
}