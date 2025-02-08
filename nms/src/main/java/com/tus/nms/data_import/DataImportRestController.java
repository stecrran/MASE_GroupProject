package com.tus.nms.data_import;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class DataImportRestController {
	
	@Value("${file.import.path}")
	private String filePath;
	
	@Autowired
	DataImportService dataImportService;

	Workbook workbook=null;


	@PostMapping(value = "/import")
	public ResponseEntity<String> handleFileUpload() {
	    // Create a File object from the configured file path.
	    File file = new File(filePath);
	    
	    // If the file does not exist, return a BAD_REQUEST response.
	    if (!file.exists()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("File not found in path " + filePath);
	    }
	    
	    // Otherwise, attempt to process the file.
	    try {
	        dataImportService.importEventDetailsIntoDB(filePath);
	        return ResponseEntity.ok("File uploaded and data saved successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error: " + e.getMessage());
	    }
	}

	
    // Add this setter to allow tests to override the filePath
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}