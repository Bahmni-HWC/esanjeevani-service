package org.bahmnihwc.esanjeevaniservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmnihwc.esanjeevaniservice.client.OpenMRSHttpClient;
import org.bahmnihwc.esanjeevaniservice.constants.APIEndpoints;
import org.bahmnihwc.esanjeevaniservice.contract.AuditEntry;
import org.bahmnihwc.esanjeevaniservice.contract.OpenMRSAuditPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenMRSAuditService {

    @Autowired
    private OpenMRSHttpClient openMRSHttpClient;

    private static final String AUDIT_MESSAGE_FORMAT = "User %s accessed e-Sanjeevani for patient %s";

    private static final Logger logger = LogManager.getLogger(OpenMRSAuditService.class);

    public void logAudit(AuditEntry auditEntry) {
        try {
            String message = String.format(AUDIT_MESSAGE_FORMAT, auditEntry.getUsername(), auditEntry.getPatientName());
            OpenMRSAuditPayload openMRSAuditPayload = new OpenMRSAuditPayload(message);
            openMRSHttpClient.post(APIEndpoints.OPENMRS_AUDIT_URL, new ObjectMapper().writeValueAsString(openMRSAuditPayload));
            logger.info("Successfully logged audit to OpenMRS");
        } catch (Exception e) {
            logger.error("Error while logging audit to OpenMRS", e);
        }

    }


}
