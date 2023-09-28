package org.bahmnihwc.esanjeevaniservice.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.bahmnihwc.esanjeevaniservice.model.Address;
import org.bahmnihwc.esanjeevaniservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Value("classpath:addresshierarchy.csv")
    private Resource csvResource;

    public void importCsvData() throws IOException {

        try (InputStream inputStream = csvResource.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVReader csvReader = new CSVReader(reader)) {

                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    Address address = new Address();
                    String[] str;

                    str = line[0].split("%");

                    address.setState(str[0].trim());
                    address.setStateCode(str[1].trim());

                    str = line[1].split("%");

                    address.setDistrict(str[0].trim());
                    address.setDistrictCode(str[1].trim());

                    str = line[2].split("%");

                    address.setSubDistrict(str[0].trim());
                    address.setSubDistrictCode(str[1].trim());

                    addressRepository.save(address);
                }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public Address findAddressByStateDistrictAndSubDistrict(String state, String district, String subDistrict) {
        Optional<Address> address = addressRepository.findTopByStateAndDistrictAndSubDistrict(state, district, subDistrict);
        return address.orElse(null);
    }
}
