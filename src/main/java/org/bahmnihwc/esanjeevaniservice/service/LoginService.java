package org.bahmnihwc.esanjeevaniservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bahmnihwc.esanjeevaniservice.client.ESanjeevaniHttpClient;
import org.bahmnihwc.esanjeevaniservice.constants.APIEndpoints;
import org.bahmnihwc.esanjeevaniservice.contract.EsanjeevaniCredentials;
import org.bahmnihwc.esanjeevaniservice.contract.EsanjeevaniLoginRequest;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniLoginException;
import org.bahmnihwc.esanjeevaniservice.util.ESanjeevaniResponseUtil;
import org.bahmnihwc.esanjeevaniservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    @Value("${esanjeevani.source}")
    private String esanjeevaniSource;

    @Value("${esanjeevani.ssologin.baseUrl}")
    private String esanjeevaniSSOLoginBaseUrl;

    @Autowired
    private ESanjeevaniHttpClient eSanjeevaniHttpClient;


    public String getAccessToken(EsanjeevaniCredentials eSanjeevaniCredentials) throws Exception {
        ResponseEntity<String> loginResponse = eSanjeevaniHttpClient.post(APIEndpoints.PROVIDER_LOGIN, getLoginRequestJson(eSanjeevaniCredentials));
        if (loginResponse.getStatusCode().is2xxSuccessful() && ESanjeevaniResponseUtil.isSuccessResponse(loginResponse.getBody()))
            return extractAccessToken(loginResponse.getBody());

        throw new ESanjeevaniLoginException(ESanjeevaniResponseUtil.getMessageField(loginResponse.getBody()));
    }


    public String getSSORedirectURL(EsanjeevaniCredentials eSanjeevaniCredentials) throws Exception {

        ResponseEntity<String> loginResponse = eSanjeevaniHttpClient.post(APIEndpoints.SSO_LOGIN, getLoginRequestJson(eSanjeevaniCredentials));
        if (loginResponse.getStatusCode().is2xxSuccessful() && ESanjeevaniResponseUtil.isSuccessResponse(loginResponse.getBody())) {
            String referenceID = extractReferenceId(loginResponse.getBody());
            return consturctSSOLoginURL(referenceID);
        }

        throw new ESanjeevaniLoginException(ESanjeevaniResponseUtil.getMessageField(loginResponse.getBody()));
    }

    private String getSalt() {
        int randomNumber = (int) (Math.random() * 900000) + 100000;
        return Integer.toString(randomNumber);
    }

    private String getLoginRequestJson(EsanjeevaniCredentials eSanjeevaniCredentials) {
        String salt = getSalt();
        EsanjeevaniLoginRequest loginRequest = new EsanjeevaniLoginRequest(eSanjeevaniCredentials.getUsername(), PasswordUtil.getEncryptedPassword(eSanjeevaniCredentials.getPassword(), salt), salt, esanjeevaniSource);
        return loginRequest.toString();
    }

    private String extractAccessToken(String response) {
        try {
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(response, Map.class);
            Map<String, Object> modelObject = (Map<String, Object>) jsonResponse.get("model");
            return (String) modelObject.get("access_token");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractReferenceId(String response) {
        try {
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(response, Map.class);
            Map<String, Object> modelObject = (Map<String, Object>) jsonResponse.get("model");
            return (String) modelObject.get("referenceId");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String consturctSSOLoginURL(String referenceId) {
        if (esanjeevaniSSOLoginBaseUrl.endsWith("/"))
            return esanjeevaniSSOLoginBaseUrl + referenceId;
        else
            return esanjeevaniSSOLoginBaseUrl + "/" + referenceId;
    }
}
