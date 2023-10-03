package org.bahmnihwc.esanjeevaniservice.mapper;

import org.bahmnihwc.esanjeevaniservice.contract.ESanjeevaniRegisterPatientRequest;
import org.bahmnihwc.esanjeevaniservice.contract.EsanjeevaniContactDetails;
import org.bahmnihwc.esanjeevaniservice.contract.EsanjeevaniPatientAddress;
import org.bahmnihwc.esanjeevaniservice.contract.Patient;
import org.bahmnihwc.esanjeevaniservice.contract.PatientAddress;
import org.bahmnihwc.esanjeevaniservice.exceptions.LGDCodeNotFoundException;
import org.bahmnihwc.esanjeevaniservice.model.Address;
import org.bahmnihwc.esanjeevaniservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EsanjeevaniPatientMapper {

    @Value("${esanjeevani.source}")
    private String esanjeevaniSource;

    @Autowired
    private AddressService addressService;

    public ESanjeevaniRegisterPatientRequest getRegisterPatientRequest(Patient patient) throws Exception {
        ESanjeevaniRegisterPatientRequest createPatientRequest = new ESanjeevaniRegisterPatientRequest();
        createPatientRequest.setAge(patient.getAge());
        createPatientRequest.setBirthdate(patient.getDateOfBirth());
        createPatientRequest.setDisplayName(patient.getPatientName().getFullName());
        createPatientRequest.setFirstName(patient.getPatientName().getFirstName());
        createPatientRequest.setMiddleName(patient.getPatientName().getMiddleName());
        createPatientRequest.setLastName(patient.getPatientName().getLastName());
        createPatientRequest.setGenderDisplay(patient.getGender().name());
        createPatientRequest.setGenderCode(mapGenderDisplayToCode(patient.getGender().name()));
        if (patient.getAbhaAddress() != null && !patient.getAbhaAddress().isBlank()) {
            createPatientRequest.setAbhaAddress(patient.getAbhaAddress());
        }
        if (patient.getAbhaNumber() != null && !patient.getAbhaNumber().isBlank()) {
            createPatientRequest.setAbhaNumber(patient.getAbhaNumber());
        }
        createPatientRequest.setLstPatientAddress(mapPatientAddress(patient));
        createPatientRequest.setLstPatientContactDetail(mapPatientContactDetails(patient));
        createPatientRequest.setIsBlock(false);
        createPatientRequest.setSource(esanjeevaniSource);

        return createPatientRequest;
    }


    private List<EsanjeevaniPatientAddress> mapPatientAddress(Patient patient) throws Exception {
        PatientAddress patientAddress = patient.getPatientAddress();

        isCompletePatientAddress(patientAddress);
        Address addressWithLgdCodes = getLGDCOdeForPersonAddress(patientAddress);

        EsanjeevaniPatientAddress esanjeevaniPatientAddress = new EsanjeevaniPatientAddress();
        esanjeevaniPatientAddress.setAddressLine1(patientAddress.getVillage());
        esanjeevaniPatientAddress.setAddressType("Physical");
        esanjeevaniPatientAddress.setAddressUse("Work");
        esanjeevaniPatientAddress.setPostalCode(patientAddress.getPostalCode());
        esanjeevaniPatientAddress.setCityCode(Integer.parseInt(addressWithLgdCodes.getSubDistrictCode()));
        esanjeevaniPatientAddress.setCityDisplay(patientAddress.getSubDistrict());
        esanjeevaniPatientAddress.setDistrictCode(Integer.parseInt(addressWithLgdCodes.getDistrictCode()));
        esanjeevaniPatientAddress.setDistrictDisplay(patientAddress.getDistrict());
        esanjeevaniPatientAddress.setStateCode(Integer.parseInt(addressWithLgdCodes.getStateCode()));
        esanjeevaniPatientAddress.setStateDisplay(patientAddress.getState());
        esanjeevaniPatientAddress.setCountryCode("1");
        esanjeevaniPatientAddress.setCountryDisplay("India");
        return Arrays.asList(esanjeevaniPatientAddress);
    }

    private static void isCompletePatientAddress(PatientAddress patientAddress) {
        if (patientAddress.getState() == null || patientAddress.getDistrict() == null || patientAddress.getSubDistrict() == null) {
            throw new NullPointerException("State or District or Sub-district cannot be null");
        }
    }

    private Address getLGDCOdeForPersonAddress(PatientAddress patientAddress) throws LGDCodeNotFoundException {
        Address address = addressService.findAddressByStateDistrictAndSubDistrict(patientAddress.getState(), patientAddress.getDistrict(), patientAddress.getSubDistrict());
        if (address != null) {
            return address;
        }
        throw new LGDCodeNotFoundException("LGD Code not found for " + patientAddress.getSubDistrict() + ", " + patientAddress.getDistrict() + ", " + patientAddress.getState());
    }

    private List<EsanjeevaniContactDetails> mapPatientContactDetails(Patient patient) {
        EsanjeevaniContactDetails esanjeevaniContactDetails = new EsanjeevaniContactDetails();
        esanjeevaniContactDetails.setContactPointStatus(true);
        esanjeevaniContactDetails.setContactPointType("Phone");
        esanjeevaniContactDetails.setContactPointUse("Work");
        esanjeevaniContactDetails.setContactPointValue(patient.getPhoneNumber());
        return Arrays.asList(esanjeevaniContactDetails);
    }

    private int mapGenderDisplayToCode(String genderdisplay) {
        if (genderdisplay.equals("Male")) {
            return 1;
        } else if (genderdisplay.equals("Female")) {
            return 2;
        } else {
            return 3;
        }
    }

}
