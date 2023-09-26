package org.bahmnihwc.esanjeevaniservice.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.bahmnihwc.esanjeevaniservice.model.Address;
import org.bahmnihwc.esanjeevaniservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void importCsvData() throws IOException {

        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/addresshierarchy.csv"))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
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
