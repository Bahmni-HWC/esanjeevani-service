package org.bahmnihwc.esanjeevaniservice.service;

import org.bahmnihwc.esanjeevaniservice.client.ESanjeevaniHttpClient;
import org.bahmnihwc.esanjeevaniservice.constants.APIEndpoints;
import org.bahmnihwc.esanjeevaniservice.constants.MessageCodes;
import org.bahmnihwc.esanjeevaniservice.contract.ESanjeevaniRegisterPatientRequest;
import org.bahmnihwc.esanjeevaniservice.contract.Patient;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniRegisterPatientException;
import org.bahmnihwc.esanjeevaniservice.mapper.EsanjeevaniPatientMapper;
import org.bahmnihwc.esanjeevaniservice.util.ESanjeevaniResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private ESanjeevaniHttpClient eSanjeevaniHttpClient;

    @Autowired
    private EsanjeevaniPatientMapper esanjeevaniPatientMapper;

    public String registerPatient(Patient patient, String accessToken) throws Exception {
        ESanjeevaniRegisterPatientRequest eSanjeevaniRegisterPatientRequest = esanjeevaniPatientMapper.getRegisterPatientRequest(patient);
        ResponseEntity<String> registerPatientResponse = eSanjeevaniHttpClient.post(APIEndpoints.REGISTER_PATIENT, eSanjeevaniRegisterPatientRequest.toString(), accessToken);
        if (registerPatientResponse.getStatusCode().is2xxSuccessful()) {
            if (ESanjeevaniResponseUtil.isSuccessResponse(registerPatientResponse.getBody()) || isSamePatientAlreadyRegistered(registerPatientResponse.getBody())) {
                return "Patient registered sucessfully";
            }
        }
        throw new ESanjeevaniRegisterPatientException(ESanjeevaniResponseUtil.getMessageField(registerPatientResponse.getBody()));
    }

    private boolean isSamePatientAlreadyRegistered(String response) throws Exception {
        String messageCode = ESanjeevaniResponseUtil.getMessageCodeField(response);
        return messageCode.equals(MessageCodes.SAME_PROFILE_EXISTS);
    }
}
