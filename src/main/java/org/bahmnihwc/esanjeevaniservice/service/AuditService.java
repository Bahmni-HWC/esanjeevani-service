package org.bahmnihwc.esanjeevaniservice.service;


import org.bahmnihwc.esanjeevaniservice.constants.AuditMethods;
import org.bahmnihwc.esanjeevaniservice.contract.AuditEntry;
import org.bahmnihwc.esanjeevaniservice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditService {

    @Value("${esanjeevani.audit.method}")
    private String auditMethod;

    @Autowired
    private AuditCSVFileService auditCSVFileService;

    @Autowired
    private OpenMRSAuditService openMRSAuditService;

    public void logAudit(String username, String patientName) {
        AuditEntry auditEntry = new AuditEntry(DateTimeUtil.getCurrentDateTimeString(), username, patientName);
        if (auditMethod.equals(AuditMethods.CSV_AUDIT.name()))
            auditCSVFileService.writeToCSV(auditEntry.toString());
        if (auditMethod.equals(AuditMethods.OPENMRS_AUDIT.name()))
            openMRSAuditService.logAudit(auditEntry);
    }

    public Map<String, Long> getAuditCountByUsername(String fromDate, String toDate) {
        List<AuditEntry> auditEntries = getAuditEntriesForDateRange(fromDate, toDate);
        return auditEntries.stream().collect(Collectors.groupingBy(AuditEntry::getUsername, Collectors.counting()));
    }

    private List<AuditEntry> getAuditEntriesForDateRange(String fromDate, String toDate) {
        LocalDate startDate = DateTimeUtil.parseDate(fromDate);
        LocalDate endDate = DateTimeUtil.parseDate(toDate);
        return auditCSVFileService.getAuditEntries(startDate, endDate);
    }


}
