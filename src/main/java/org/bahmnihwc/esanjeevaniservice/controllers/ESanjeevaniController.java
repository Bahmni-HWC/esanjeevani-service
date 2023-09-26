package org.bahmnihwc.esanjeevaniservice.controllers;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmnihwc.esanjeevaniservice.contract.RegisterAndLaunchRequest;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniLoginException;
import org.bahmnihwc.esanjeevaniservice.exceptions.ESanjeevaniRegisterPatientException;
import org.bahmnihwc.esanjeevaniservice.service.LoginService;
import org.bahmnihwc.esanjeevaniservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esanjeevani-bridge")
public class ESanjeevaniController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PatientService patientService;

    private static final Logger logger = LogManager.getLogger(ESanjeevaniController.class);

    @RequestMapping(value = "/registerAndLaunch", method = RequestMethod.POST)
    public ResponseEntity<String> registerAndLaunch(@Valid @RequestBody RegisterAndLaunchRequest registerAndLaunchRequest) {
        try {
            String accessToken = loginService.getAccessToken(registerAndLaunchRequest.getEsanjeevaniCredentials());
            patientService.registerPatient(registerAndLaunchRequest.getPatient(), accessToken);
            String ssoRedirectURL = loginService.getSSORedirectURL(registerAndLaunchRequest.getEsanjeevaniCredentials());
            return new ResponseEntity<>(ssoRedirectURL, HttpStatus.OK);

        } catch (ESanjeevaniLoginException | ESanjeevaniRegisterPatientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
