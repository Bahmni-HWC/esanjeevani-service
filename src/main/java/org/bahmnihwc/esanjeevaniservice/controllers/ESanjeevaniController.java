package org.bahmnihwc.esanjeevaniservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmnihwc.esanjeevaniservice.constants.AuditMethods;
import org.bahmnihwc.esanjeevaniservice.contract.RegisterAndLaunchRequest;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniLoginException;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniRegisterPatientException;
import org.bahmnihwc.esanjeevaniservice.exceptions.LGDCodeNotFoundException;
import org.bahmnihwc.esanjeevaniservice.service.AuditService;
import org.bahmnihwc.esanjeevaniservice.service.LoginService;
import org.bahmnihwc.esanjeevaniservice.service.PatientService;
import org.bahmnihwc.esanjeevaniservice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/esanjeevani-bridge")
public class ESanjeevaniController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuditService auditService;

    @Value("${esanjeevani.audit.method}")
    private String auditMethod;

    private static final Logger logger = LogManager.getLogger(ESanjeevaniController.class);

    @RequestMapping(value = "/registerAndLaunch", method = RequestMethod.POST)
    public ResponseEntity<String> registerAndLaunch(@Valid @RequestBody RegisterAndLaunchRequest registerAndLaunchRequest) {
        try {
            String accessToken = loginService.getAccessToken(registerAndLaunchRequest.getEsanjeevaniCredentials());
            patientService.registerPatient(registerAndLaunchRequest.getPatient(), accessToken);
            String ssoRedirectURL = loginService.getSSORedirectURL(registerAndLaunchRequest.getEsanjeevaniCredentials());
            auditService.logAudit(registerAndLaunchRequest.getEsanjeevaniCredentials().getUsername(), registerAndLaunchRequest.getPatient().getPatientName().getFullName());
            return new ResponseEntity<>(ssoRedirectURL, HttpStatus.OK);

        } catch (ESanjeevaniLoginException | ESanjeevaniRegisterPatientException | LGDCodeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity<String> getReport(@RequestParam String fromDate, @RequestParam String toDate) {
        logger.info("Received request for report for date range: " + fromDate + " to " + toDate);
        if (auditMethod.equals(AuditMethods.OPENMRS_AUDIT.name()))
            return new ResponseEntity<>("Audit method is set to OpenMRS audit. Please use bahmni reports", HttpStatus.UNPROCESSABLE_ENTITY);
        try {
            if (DateTimeUtil.isValidDate(fromDate) && DateTimeUtil.isValidDate(toDate)) {
                Map<String, Long> auditCountByUsername = auditService.getAuditCountByUsername(fromDate, toDate);
                return new ResponseEntity<>(new ObjectMapper().writeValueAsString(auditCountByUsername), HttpStatus.OK);
            } else
                return new ResponseEntity<>("Invalid date format. Use yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
