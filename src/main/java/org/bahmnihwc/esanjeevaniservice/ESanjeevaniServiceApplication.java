package org.bahmnihwc.esanjeevaniservice;

import jakarta.annotation.PostConstruct;
import org.bahmnihwc.esanjeevaniservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class ESanjeevaniServiceApplication {

    @Autowired
    private AddressService addressService;

    public static void main(String[] args) {
        SpringApplication.run(ESanjeevaniServiceApplication.class, args);
    }

    @PostConstruct
    public void run() {
        try {
            addressService.importCsvData();
        } catch (IOException e) {
            throw new RuntimeException("Error while importing CSV data: " + e.getMessage());
        }
    }

}
