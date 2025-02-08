package com.tus.nms.data_import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tus.nms.model.EventCause;
import com.tus.nms.model.EventRecord;
import com.tus.nms.model.FailureClass;
import com.tus.nms.model.MccMncDetails;
import com.tus.nms.model.UEDetails;
import com.tus.nms.repos.EventCauseRepo;
import com.tus.nms.repos.EventRecordRepo;
import com.tus.nms.repos.FailureClassRepo;
import com.tus.nms.repos.MccMncRepo;
import com.tus.nms.repos.UEDetailsRepo;

@ExtendWith(MockitoExtension.class)
public class DataImportServiceTest {

    // Use a relative file path within the project folder, e.g., under src/test/resources
    private static final String FILE_PATH = "src/test/resources/event_records.xlsx";

    @Mock
    private EventRecordRepo eventRecordRepo;

    @Mock
    private EventCauseRepo eventCauseRepo;

    @Mock
    private FailureClassRepo failureClassRepo;

    @Mock
    private UEDetailsRepo ueDetailsRepo;

    @Mock
    private MccMncRepo mccMncRepo;
    
    @InjectMocks
    private DataImportService dataImportService;
    
    // Test the complete import process when the Excel file contains all expected sheets.
    @Test
    public void testImportEventDetailsIntoDB_AllSheets() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // --- "Base Data" Sheet (Event Records) ---
        Sheet baseDataSheet = workbook.createSheet("Base Data");
        Row header = baseDataSheet.createRow(0);
        header.createCell(0).setCellValue("DateTime");
        header.createCell(1).setCellValue("EventId");
        header.createCell(2).setCellValue("FailureClass");
        header.createCell(3).setCellValue("UeType");
        header.createCell(4).setCellValue("Market");
        header.createCell(5).setCellValue("Operator");
        header.createCell(6).setCellValue("CellId");
        header.createCell(7).setCellValue("Duration");
        header.createCell(8).setCellValue("CauseCode");
        header.createCell(9).setCellValue("NeVersion");
        header.createCell(10).setCellValue("IMSI");
        header.createCell(11).setCellValue("Hier3Id");
        header.createCell(12).setCellValue("Hier32Id");
        header.createCell(13).setCellValue("Hier321Id");
        
        Row row1 = baseDataSheet.createRow(1);
        row1.createCell(0).setCellValue("2025-02-07 10:00:00");
        row1.createCell(1).setCellValue("1");
        row1.createCell(2).setCellValue("ClassA");
        row1.createCell(3).setCellValue("10");
        row1.createCell(4).setCellValue("20");
        row1.createCell(5).setCellValue("30");
        row1.createCell(6).setCellValue("40");
        row1.createCell(7).setCellValue("50");
        row1.createCell(8).setCellValue("Cause001");
        row1.createCell(9).setCellValue("v1");
        row1.createCell(10).setCellValue("imsi1");
        row1.createCell(11).setCellValue("hier3");
        row1.createCell(12).setCellValue("hier32");
        row1.createCell(13).setCellValue("hier321");
        
        // "Event-Cause Table" Sheet
        Sheet eventCauseSheet = workbook.createSheet("Event-Cause Table");
        Row headerEC = eventCauseSheet.createRow(0);
        headerEC.createCell(0).setCellValue("CauseCode");
        headerEC.createCell(1).setCellValue("EventId");
        headerEC.createCell(2).setCellValue("Description");
        Row rowEC = eventCauseSheet.createRow(1);
        rowEC.createCell(0).setCellValue("100");
        rowEC.createCell(1).setCellValue("1");
        rowEC.createCell(2).setCellValue("Test Event Cause");
        
        // "Failure Class Table" Sheet
        Sheet failureSheet = workbook.createSheet("Failure Class Table");
        Row headerFC = failureSheet.createRow(0);
        headerFC.createCell(0).setCellValue("FailureClass");
        headerFC.createCell(1).setCellValue("Description");
        Row rowFC = failureSheet.createRow(1);
        rowFC.createCell(0).setCellValue("200");
        rowFC.createCell(1).setCellValue("Test Failure Class");
        
        // "UE Table" Sheet
        Sheet ueSheet = workbook.createSheet("UE Table");
        Row headerUE = ueSheet.createRow(0);
        headerUE.createCell(0).setCellValue("TAC");
        headerUE.createCell(1).setCellValue("MarketingName");
        headerUE.createCell(2).setCellValue("Manufacturer");
        headerUE.createCell(3).setCellValue("AccessCapability");
        Row rowUE = ueSheet.createRow(1);
        rowUE.createCell(0).setCellValue("300");
        rowUE.createCell(1).setCellValue("Test UE Marketing");
        rowUE.createCell(2).setCellValue("Test Manufacturer");
        rowUE.createCell(3).setCellValue("Capability");
        
        // "MCC - MNC Table" Sheet 
        Sheet mccMncSheet = workbook.createSheet("MCC - MNC Table");
        Row headerMM = mccMncSheet.createRow(0);
        headerMM.createCell(0).setCellValue("MCC");
        headerMM.createCell(1).setCellValue("MNC");
        headerMM.createCell(2).setCellValue("Country");
        headerMM.createCell(3).setCellValue("Operator");
        Row rowMM = mccMncSheet.createRow(1);
        rowMM.createCell(0).setCellValue("400");
        rowMM.createCell(1).setCellValue("500");
        rowMM.createCell(2).setCellValue("Test Country");
        rowMM.createCell(3).setCellValue("Test Operator");
        
        // Write the workbook to the file location in the project folder
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        // Execute the import process using the file at FILE_PATH
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        // Verify EventRecordRepo (Base Data) 
        verify(eventRecordRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<EventRecord>> eventRecordCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventRecordRepo, times(1)).saveAll(eventRecordCaptor.capture());
        List<EventRecord> eventRecords = eventRecordCaptor.getValue();
        assertEquals(1, eventRecords.size());
        EventRecord record = eventRecords.get(0);
        assertEquals(1, record.getEventId());
        assertEquals("2025-02-07 10:00:00", record.getDateTime());
        assertEquals("ClassA", record.getFailureClass());
        assertEquals(10, record.getUeType());
        assertEquals(20, record.getMarket());
        assertEquals(30, record.getOperator());
        assertEquals(40, record.getCellId());
        assertEquals(50, record.getDuration());
        assertEquals("Cause001", record.getCauseCode());
        assertEquals("v1", record.getNeVersion());
        assertEquals("imsi1", record.getImsi());
        assertEquals("hier3", record.getHier3Id());
        assertEquals("hier32", record.getHier32Id());
        assertEquals("hier321", record.getHier321Id());
        
        // Verify EventCauseRepo 
        verify(eventCauseRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<EventCause>> eventCauseCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventCauseRepo, times(1)).saveAll(eventCauseCaptor.capture());
        List<EventCause> eventCauses = eventCauseCaptor.getValue();
        assertEquals(1, eventCauses.size());
        EventCause eventCause = eventCauses.get(0);
        assertEquals(100, eventCause.getCauseCode());
        assertEquals(1, eventCause.getEventId());
        assertEquals("Test Event Cause", eventCause.getDescription());
        
        // Verify FailureClassRepo 
        verify(failureClassRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<FailureClass>> failureClassCaptor = ArgumentCaptor.forClass(List.class);
        verify(failureClassRepo, times(1)).saveAll(failureClassCaptor.capture());
        List<FailureClass> failureClasses = failureClassCaptor.getValue();
        assertEquals(1, failureClasses.size());
        FailureClass failureClass = failureClasses.get(0);
        assertEquals(200, failureClass.getFailureClass());
        assertEquals("Test Failure Class", failureClass.getDescription());
        
        // Verify UEDetailsRepo 
        verify(ueDetailsRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<UEDetails>> ueDetailsCaptor = ArgumentCaptor.forClass(List.class);
        verify(ueDetailsRepo, times(1)).saveAll(ueDetailsCaptor.capture());
        List<UEDetails> ueDetails = ueDetailsCaptor.getValue();
        assertEquals(1, ueDetails.size());
        UEDetails ueDetail = ueDetails.get(0);
        assertEquals(300, ueDetail.getTac());
        assertEquals("Test UE Marketing", ueDetail.getMarketingName());
        assertEquals("Test Manufacturer", ueDetail.getManufacturer());
        assertEquals("Capability", ueDetail.getAccessCapability());
        
        // Verify MccMncRepo 
        verify(mccMncRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<MccMncDetails>> mccMncCaptor = ArgumentCaptor.forClass(List.class);
        verify(mccMncRepo, times(1)).saveAll(mccMncCaptor.capture());
        List<MccMncDetails> mccMncDetailsList = mccMncCaptor.getValue();
        assertEquals(1, mccMncDetailsList.size());
        MccMncDetails mccMncDetails = mccMncDetailsList.get(0);
        assertEquals(400, mccMncDetails.getMcc());
        assertEquals(500, mccMncDetails.getMnc());
        assertEquals("Test Country", mccMncDetails.getCountry());
        assertEquals("Test Operator", mccMncDetails.getOperator());
    }
    
    // Test that when only the "Base Data" sheet is present, only the EventRecordRepo is used
    @Test
    public void testProcessEventRecords() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet baseDataSheet = workbook.createSheet("Base Data");
        Row header = baseDataSheet.createRow(0);
        header.createCell(0).setCellValue("DateTime");
        header.createCell(1).setCellValue("EventId");
        header.createCell(2).setCellValue("FailureClass");
        header.createCell(3).setCellValue("UeType");
        header.createCell(4).setCellValue("Market");
        header.createCell(5).setCellValue("Operator");
        header.createCell(6).setCellValue("CellId");
        header.createCell(7).setCellValue("Duration");
        header.createCell(8).setCellValue("CauseCode");
        header.createCell(9).setCellValue("NeVersion");
        header.createCell(10).setCellValue("IMSI");
        header.createCell(11).setCellValue("Hier3Id");
        header.createCell(12).setCellValue("Hier32Id");
        header.createCell(13).setCellValue("Hier321Id");
        
        Row row = baseDataSheet.createRow(1);
        row.createCell(0).setCellValue("2025-02-07 10:00:00");
        row.createCell(1).setCellValue("1");
        row.createCell(2).setCellValue("ClassA");
        row.createCell(3).setCellValue("10");
        row.createCell(4).setCellValue("20");
        row.createCell(5).setCellValue("30");
        row.createCell(6).setCellValue("40");
        row.createCell(7).setCellValue("50");
        row.createCell(8).setCellValue("Cause001");
        row.createCell(9).setCellValue("v1");
        row.createCell(10).setCellValue("imsi1");
        row.createCell(11).setCellValue("hier3");
        row.createCell(12).setCellValue("hier32");
        row.createCell(13).setCellValue("hier321");
        
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        // Verify that only eventRecordRepo is invoked
        verify(eventRecordRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<EventRecord>> eventRecordCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventRecordRepo, times(1)).saveAll(eventRecordCaptor.capture());
        List<EventRecord> eventRecords = eventRecordCaptor.getValue();
        assertEquals(1, eventRecords.size());
        
        verifyNoInteractions(eventCauseRepo, failureClassRepo, ueDetailsRepo, mccMncRepo);
    }
    
    // Test that when only the "Event-Cause Table" sheet is present, only the EventCauseRepo is used
    @Test
    public void testProcessEventCauseDetails() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet eventCauseSheet = workbook.createSheet("Event-Cause Table");
        Row headerEC = eventCauseSheet.createRow(0);
        headerEC.createCell(0).setCellValue("CauseCode");
        headerEC.createCell(1).setCellValue("EventId");
        headerEC.createCell(2).setCellValue("Description");
        
        Row rowEC = eventCauseSheet.createRow(1);
        rowEC.createCell(0).setCellValue("100");
        rowEC.createCell(1).setCellValue("1");
        rowEC.createCell(2).setCellValue("Test Event Cause");
        
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        verify(eventCauseRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<EventCause>> eventCauseCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventCauseRepo, times(1)).saveAll(eventCauseCaptor.capture());
        List<EventCause> eventCauses = eventCauseCaptor.getValue();
        assertEquals(1, eventCauses.size());
        
        verifyNoInteractions(eventRecordRepo, failureClassRepo, ueDetailsRepo, mccMncRepo);
    }
    
    // Test that when only the "Failure Class Table" sheet is present, only the FailureClassRepo is used
    @Test
    public void testProcessFailureCauseDetails() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet failureSheet = workbook.createSheet("Failure Class Table");
        Row headerFC = failureSheet.createRow(0);
        headerFC.createCell(0).setCellValue("FailureClass");
        headerFC.createCell(1).setCellValue("Description");
        
        Row rowFC = failureSheet.createRow(1);
        rowFC.createCell(0).setCellValue("200");
        rowFC.createCell(1).setCellValue("Test Failure Class");
        
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        verify(failureClassRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<FailureClass>> failureClassCaptor = ArgumentCaptor.forClass(List.class);
        verify(failureClassRepo, times(1)).saveAll(failureClassCaptor.capture());
        List<FailureClass> failureClasses = failureClassCaptor.getValue();
        assertEquals(1, failureClasses.size());
        
        verifyNoInteractions(eventRecordRepo, eventCauseRepo, ueDetailsRepo, mccMncRepo);
    }
    
    // Test that when only the "UE Table" sheet is present, only the UEDetailsRepo is used
    @Test
    public void testProcessUEDetails() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet ueSheet = workbook.createSheet("UE Table");
        Row headerUE = ueSheet.createRow(0);
        headerUE.createCell(0).setCellValue("TAC");
        headerUE.createCell(1).setCellValue("MarketingName");
        headerUE.createCell(2).setCellValue("Manufacturer");
        headerUE.createCell(3).setCellValue("AccessCapability");
        
        Row rowUE = ueSheet.createRow(1);
        rowUE.createCell(0).setCellValue("300");
        rowUE.createCell(1).setCellValue("Test UE Marketing");
        rowUE.createCell(2).setCellValue("Test Manufacturer");
        rowUE.createCell(3).setCellValue("Capability");
        
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        verify(ueDetailsRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<UEDetails>> ueDetailsCaptor = ArgumentCaptor.forClass(List.class);
        verify(ueDetailsRepo, times(1)).saveAll(ueDetailsCaptor.capture());
        List<UEDetails> ueDetails = ueDetailsCaptor.getValue();
        assertEquals(1, ueDetails.size());
        
        verifyNoInteractions(eventRecordRepo, eventCauseRepo, failureClassRepo, mccMncRepo);
    }
    

     // Test that when only the "MCC - MNC Table" sheet is present, only the MccMncRepo is used
    @Test
    public void testProcessMccMncDetails() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet mccMncSheet = workbook.createSheet("MCC - MNC Table");
        Row headerMM = mccMncSheet.createRow(0);
        headerMM.createCell(0).setCellValue("MCC");
        headerMM.createCell(1).setCellValue("MNC");
        headerMM.createCell(2).setCellValue("Country");
        headerMM.createCell(3).setCellValue("Operator");
        
        Row rowMM = mccMncSheet.createRow(1);
        rowMM.createCell(0).setCellValue("400");
        rowMM.createCell(1).setCellValue("500");
        rowMM.createCell(2).setCellValue("Test Country");
        rowMM.createCell(3).setCellValue("Test Operator");
        
        File file = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();
        
        dataImportService.importEventDetailsIntoDB(FILE_PATH);
        
        verify(mccMncRepo, times(1)).deleteAllEntities();
        ArgumentCaptor<List<MccMncDetails>> mccMncCaptor = ArgumentCaptor.forClass(List.class);
        verify(mccMncRepo, times(1)).saveAll(mccMncCaptor.capture());
        List<MccMncDetails> mccMncDetailsList = mccMncCaptor.getValue();
        assertEquals(1, mccMncDetailsList.size());
    }
}
