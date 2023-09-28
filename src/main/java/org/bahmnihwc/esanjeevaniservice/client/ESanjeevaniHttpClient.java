package org.bahmnihwc.esanjeevaniservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;

@Component
public class ESanjeevaniHttpClient {
    @Value("${esanjeevani.api.baseUrl}")
    private String esanjeevaniApiBaseUrl;

    @Value("${esanjeevani.enable.debugLog}")
    private boolean enableDebugLog;

    private static final Logger logger = LogManager.getLogger(ESanjeevaniHttpClient.class);


    public ResponseEntity<String> post(String apiEndpoint, String json) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = getFullURI(apiEndpoint);
        logRequest(uri.toString(), json);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, getRequestEntity(json, null), String.class);
        logResponse(uri.toString(), responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> post(String apiEndpoint, String json, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = getFullURI(apiEndpoint);
        logRequest(uri.toString(), json);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, getRequestEntity(json, accessToken), String.class);
        logResponse(uri.toString(), responseEntity);
        return responseEntity;
    }

    private URI getFullURI(String apiEndpoint) {
        if (esanjeevaniApiBaseUrl.endsWith("/"))
            return UriComponentsBuilder.fromHttpUrl(esanjeevaniApiBaseUrl.substring(0, esanjeevaniApiBaseUrl.length() - 1) + apiEndpoint).build().toUri();
        else
            return UriComponentsBuilder.fromHttpUrl(esanjeevaniApiBaseUrl + apiEndpoint).build().toUri();
    }

    private HttpEntity<String> getRequestEntity(String json, String accessToken) {
        HttpHeaders headers = getDefaultHeaders();
        if (accessToken != null) {
            headers.add("Authorization", "Bearer " + accessToken);
        }
        return json == null ? new HttpEntity<>(headers) : new HttpEntity<>(json, headers);
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private void logResponse(String url, ResponseEntity<String> responseEntity) {
        logger.info(String.format("Response for: %s with status code: %s", url, responseEntity.getStatusCode()));
        if (enableDebugLog)
            logger.info("Response Body: " + responseEntity.getBody());
    }

    private void logRequest(String url, String json) {
        logger.info(String.format("Intiating Request for: %s ", url));
        if (enableDebugLog)
            logger.info("Request Body: " + json);
    }
}
