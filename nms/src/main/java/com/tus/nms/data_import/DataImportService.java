package com.tus.nms.data_import;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


@Service
public class DataImportService {

	@Autowired
	EventRecordRepo eventRecordRepo;

	@Autowired
	EventCauseRepo eventCauseRepo;

	@Autowired
	FailureClassRepo failureClassRepo;

	@Autowired
	UEDetailsRepo ueDetailsRepo;

	@Autowired
	MccMncRepo mccMncRepo;
	
	public void importEventDetailsIntoDB(String fileLocation ) {
		Workbook workbook = null;
		try {
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			workbook = WorkbookFactory.create(new File(fileLocation));
			// Retrieving the number of sheets in the Workbook
			System.out.println("Number of sheets: "+ workbook.getNumberOfSheets());
			// Iterate through all sheets
			for (Sheet sheet : workbook) {
            	String sheetName = sheet.getSheetName();
				switch(sheetName)
				{
				case "Base Data": processEventRecords(sheet);
				break;
				case "Event-Cause Table": processEventCauseDetails(sheet);
				break;
				case "Failure Class Table": processFailureCauseDetails(sheet);
				break;
				case "UE Table": processUEDetails(sheet);
				break;
				case "MCC - MNC Table": processMccMncDetails(sheet);
				break;
				}
            }
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(workbook != null) workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void processEventRecords(Sheet sheet) {
		eventRecordRepo.deleteAllEntities();
		List<EventRecord> eventRecordList = StreamSupport.stream(sheet.spliterator(), false)
			.skip(1) // Skip header
			.map(this::createEventRecord)
			.toList();
		
		eventRecordRepo.saveAll(eventRecordList);
	}
	
	private void processEventCauseDetails(Sheet sheet) {
		eventCauseRepo.deleteAllEntities();
		List<EventCause> eventCauseList = StreamSupport.stream(sheet.spliterator(), false)
			.skip(1)
			.map(this::createEventCause)
			.toList();
		
		eventCauseRepo.saveAll(eventCauseList);
	}
	
	private void processFailureCauseDetails(Sheet sheet) {
		failureClassRepo.deleteAllEntities();
		List<FailureClass> failureClassList = StreamSupport.stream(sheet.spliterator(), false)
			.skip(1)
			.map(this::createFailureClass)
			.toList();
		
		failureClassRepo.saveAll(failureClassList);
	}
	
	private void processUEDetails(Sheet sheet) {
		ueDetailsRepo.deleteAllEntities();
		List<UEDetails> ueDetailsList = StreamSupport.stream(sheet.spliterator(), false)
			.skip(1)
			.map(this::createUEDetail)
			.toList();
		
		ueDetailsRepo.saveAll(ueDetailsList);
	}
	
	private void processMccMncDetails(Sheet sheet) {
		mccMncRepo.deleteAllEntities();
		List<MccMncDetails> mccMncDetailsList = StreamSupport.stream(sheet.spliterator(), false)
			.skip(1)
			.map(this::createMccMncDetails)
			.toList();
		
		mccMncRepo.saveAll(mccMncDetailsList);
	}
	

	private EventRecord createEventRecord(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		EventRecord event = new EventRecord();
		event.setEventId(convertToInteger(row.getCell(1))); // event id 
		event.setDateTime(dataFormatter.formatCellValue(row.getCell(0))); // date time
		event.setFailureClass(dataFormatter.formatCellValue(row.getCell(2))); // failure class
		event.setUeType(convertToInteger(row.getCell(3))); // ue type
		event.setMarket(convertToInteger(row.getCell(4))); // market
		event.setOperator(convertToInteger(row.getCell(5))); // operator

		event.setCellId(convertToInteger(row.getCell(6))); // cellId
		event.setDuration(convertToInteger(row.getCell(7))); // duration
		event.setCauseCode(dataFormatter.formatCellValue(row.getCell(8))); // causecode

		event.setNeVersion(dataFormatter.formatCellValue(row.getCell(9))); // neVersion
		event.setImsi(dataFormatter.formatCellValue(row.getCell(10))); // imsi
		event.setHier3Id(dataFormatter.formatCellValue(row.getCell(11))); // hier3Id
		event.setHier32Id(dataFormatter.formatCellValue(row.getCell(12))); // hier32Id
		event.setHier321Id(dataFormatter.formatCellValue(row.getCell(13))); // hier321Id
		return event;
	}

	private EventCause createEventCause(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		EventCause eventCause = new EventCause();
		eventCause.setCauseCode(convertToInteger(row.getCell(0)));
		eventCause.setEventId(convertToInteger(row.getCell(1)));
		eventCause.setDescription(dataFormatter.formatCellValue(row.getCell(2)));
		return eventCause;
	}

	private FailureClass createFailureClass(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		FailureClass failureClass = new FailureClass();
		failureClass.setFailureClass(convertToInteger(row.getCell(0)));
		failureClass.setDescription(dataFormatter.formatCellValue(row.getCell(1)));
		return failureClass;
	}

	private UEDetails createUEDetail(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		UEDetails ueDetails = new UEDetails();
		ueDetails.setTac(convertToInteger(row.getCell(0)));
		ueDetails.setMarketingName(dataFormatter.formatCellValue(row.getCell(1)));
		ueDetails.setManufacturer(dataFormatter.formatCellValue(row.getCell(2)));
		ueDetails.setAccessCapability(dataFormatter.formatCellValue(row.getCell(3)));
		return ueDetails;
	}

	private MccMncDetails createMccMncDetails(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		MccMncDetails mccMncDetails = new MccMncDetails();
		mccMncDetails.setMcc(convertToInteger(row.getCell(0)));
		mccMncDetails.setMnc(convertToInteger(row.getCell(1)));
		mccMncDetails.setCountry(dataFormatter.formatCellValue(row.getCell(2)));
		mccMncDetails.setOperator(dataFormatter.formatCellValue(row.getCell(3)));
		return mccMncDetails;
	}
	
	private Integer convertToInteger(Cell cell) {
		DataFormatter dataFormatter = new DataFormatter();
		String input = dataFormatter.formatCellValue(cell);
		Integer result = null;
		try {
			if(input != null)
			{
				result = Integer.parseInt(input);
			}
		} catch (NumberFormatException e) {
			// Handle the case where the cell value is not a valid number
			System.err.println("Invalid number format in cell: " + cell);
		}
		return result;
	}
}
