package org.bahmnihwc.esanjeevaniservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esanjeevani-bridge")
public class ESanjeevaniController {

    @RequestMapping("/registerAndLaunch")
    public String registerAndLaunch() {
        return "API used to register patient and launch eSanjeevani app";
    }
}
