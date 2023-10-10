package org.bahmnihwc.esanjeevaniservice.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmnihwc.esanjeevaniservice.contract.AuditEntry;
import org.bahmnihwc.esanjeevaniservice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditCSVFileService {
    private static final Logger logger = LogManager.getLogger(AuditCSVFileService.class);

    @Value("${esanjeevani.audit.directory}")
    private String auditDirectory;

    public void writeToCSV(String lineEntry) {
        String filePath = getFilePath(DateTimeUtil.getCurrentDateString());

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(lineEntry);
            printWriter.close();
            fileWriter.close();
            logger.info("Audit data has been written to " + filePath);
        } catch (IOException e) {
            logger.error("Error writing to the CSV file: " + e.getMessage());
        }
    }

    private List<AuditEntry> getAuditEntries(String date) {
        String filePath = getFilePath(date);
        List<AuditEntry> auditEntries = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> data = csvReader.readAll();
            for (String[] row : data) {
                AuditEntry auditEntry = new AuditEntry(row[0], row[1], row[2]);
                auditEntries.add(auditEntry);
            }
            return auditEntries;
        } catch (IOException e) {
            System.err.println("Error reading from the CSV file: " + e.getMessage());
            return null;
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AuditEntry> getAuditEntries(LocalDate startDate, LocalDate endDate) {
        List<AuditEntry> auditEntries = new ArrayList<>();
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            List<AuditEntry> auditEntriesForDate = getAuditEntries(startDate.toString());
            if (auditEntriesForDate != null) {
                auditEntries.addAll(auditEntriesForDate);
            }
            startDate = startDate.plusDays(1);
        }
        return auditEntries;
    }

    private String getFilePath(String date) {
        String FILE_PREFIX = "audit_";
        return auditDirectory + "/" + FILE_PREFIX + date + ".csv";
    }
}
