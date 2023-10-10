package org.bahmnihwc.esanjeevaniservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class OpenMRSHttpClient {
    @Value("${openmrs.baseUrl}")
    private String openmrsBaseURL;

    public ResponseEntity<String> post(String apiEndpoint, String json) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = getFullURI(apiEndpoint);
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(json, getDefaultHeaders()), String.class);
    }

    private URI getFullURI(String apiEndpoint) {
        if (openmrsBaseURL.endsWith("/"))
            return UriComponentsBuilder.fromHttpUrl(openmrsBaseURL.substring(0, openmrsBaseURL.length() - 1) + apiEndpoint).build().toUri();
        else
            return UriComponentsBuilder.fromHttpUrl(openmrsBaseURL + apiEndpoint).build().toUri();
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

}
