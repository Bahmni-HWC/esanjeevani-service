package org.bahmnihwc.esanjeevaniservice.mapper;

import org.bahmnihwc.esanjeevaniservice.contract.ESanjeevaniRegisterPatientRequest;
import org.bahmnihwc.esanjeevaniservice.contract.EsanjeevaniContactDetails;
import org.bahmnihwc.esanjeevaniservice.contract.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EsanjeevaniPatientMapper {

    @Value("${esanjeevani.source}")
    private String esanjeevaniSource;


    public ESanjeevaniRegisterPatientRequest getRegisterPatientRequest(Patient patient) {
        ESanjeevaniRegisterPatientRequest createPatientRequest = new ESanjeevaniRegisterPatientRequest();
        createPatientRequest.setAge(patient.getAge());
        createPatientRequest.setBirthdate(patient.getDateOfBirth());
        createPatientRequest.setDisplayName(patient.getPatientName().getFullName());
        createPatientRequest.setFirstName(patient.getPatientName().getFirstName());
        createPatientRequest.setMiddleName(patient.getPatientName().getMiddleName());
        createPatientRequest.setLastName(patient.getPatientName().getLastName());
        createPatientRequest.setGenderDisplay(patient.getGender().name());
        createPatientRequest.setGenderCode(mapGenderDisplayToCode(patient.getGender().name()));
        //createPatientRequest.setLstPatientAddress(mapPatientAddress(patient));
        createPatientRequest.setLstPatientContactDetail(mapPatientContactDetails(patient));
        createPatientRequest.setIsBlock(false);
        createPatientRequest.setSource(esanjeevaniSource);

        return createPatientRequest;
    }


//    private List<EsanjeevaniPatientAddress> mapPatientAddress(Patient patient) throws Exception {
//        PersonAddress personAddress = patient.getPersonAddress();
//        isCompletePatientAddress(personAddress);
//        Map<String, Integer> lgdCodeForPersonAddress = getLGDCOdeForPersonAddress(personAddress);
//        EsanjeevaniPatientAddress esanjeevaniPatientAddress = new EsanjeevaniPatientAddress();
//        esanjeevaniPatientAddress.setAddressLine1(personAddress.getCityVillage());
//        esanjeevaniPatientAddress.setAddressType("Physical");
//        esanjeevaniPatientAddress.setAddressUse("Work");
//        esanjeevaniPatientAddress.setPostalCode(personAddress.getPostalCode());
//        esanjeevaniPatientAddress.setCityCode(lgdCodeForPersonAddress.get(personAddress.getAddress4()));
//        esanjeevaniPatientAddress.setCityDisplay(personAddress.getAddress4());
//        esanjeevaniPatientAddress.setDistrictCode(lgdCodeForPersonAddress.get(personAddress.getCountyDistrict()));
//        esanjeevaniPatientAddress.setDistrictDisplay(personAddress.getCountyDistrict());
//        esanjeevaniPatientAddress.setStateCode(lgdCodeForPersonAddress.get(personAddress.getStateProvince()));
//        esanjeevaniPatientAddress.setStateDisplay(personAddress.getStateProvince());
//        esanjeevaniPatientAddress.setCountryCode("1");
//        esanjeevaniPatientAddress.setCountryDisplay("India");
//        return Arrays.asList(esanjeevaniPatientAddress);
//    }
//
//    private static void isCompletePatientAddress(PersonAddress personAddress) {
//        if(personAddress.getAddress4()== null || personAddress.getCountyDistrict() == null || personAddress.getStateProvince() == null){
//            throw new NullPointerException("State or District or Sub-district cannot be null");
//        }
//    }
//
//    private Map<String,Integer> getLGDCOdeForPersonAddress(PersonAddress personAddress) throws LGDCodeNotFoundException {
//        Map<String,Integer> lgdCodeMap = new HashMap<>();
//        AddressHierarchyService addressHierarchyService = Context.getService(AddressHierarchyService.class);
//        AddressHierarchyLevel addressHierarchyLevel = addressHierarchyService.getAddressHierarchyLevelByAddressField(AddressField.ADDRESS_4);
//        List<AddressHierarchyEntry> matchingSubDistrictEntries = addressHierarchyService.getAddressHierarchyEntriesByLevelAndName(addressHierarchyLevel,personAddress.getAddress4());
//
//        for (AddressHierarchyEntry entry : matchingSubDistrictEntries) {
//            AddressHierarchyEntry districtEntry = entry.getParent();
//            AddressHierarchyEntry stateEntry = entry.getParent().getParent();
//
//            if (districtEntry.getName().equalsIgnoreCase(personAddress.getCountyDistrict()) && stateEntry.getName().equalsIgnoreCase(personAddress.getStateProvince())) {
//                lgdCodeMap.put(personAddress.getStateProvince(), Integer.parseInt(stateEntry.getUserGeneratedId()));
//                lgdCodeMap.put(personAddress.getCountyDistrict(), Integer.parseInt(districtEntry.getUserGeneratedId()));
//                lgdCodeMap.put(personAddress.getAddress4(), Integer.parseInt(entry.getUserGeneratedId()));
//                return lgdCodeMap;
//            }
//        }
//
//        throw new LGDCodeNotFoundException("LGD Code not found for " + personAddress.getAddress4() + ", " + personAddress.getCountyDistrict() + ", " + personAddress.getStateProvince());
//    }

    private  List<EsanjeevaniContactDetails> mapPatientContactDetails(Patient patient){
        EsanjeevaniContactDetails esanjeevaniContactDetails = new EsanjeevaniContactDetails();
        esanjeevaniContactDetails.setContactPointStatus(true);
        esanjeevaniContactDetails.setContactPointType("Phone");
        esanjeevaniContactDetails.setContactPointUse("Work");
        esanjeevaniContactDetails.setContactPointValue(patient.getPhoneNumber());
        return Arrays.asList(esanjeevaniContactDetails);
    }

    private int mapGenderDisplayToCode(String genderdisplay){
        if(genderdisplay.equals("Male")){
            return 1;
        }else if(genderdisplay.equals("Female")){
            return 2;
        }else{
            return 3;
        }
    }

}
