package com.tus.nms.data_import;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DataImportRestControllerTest {

    @Mock
    private DataImportService dataImportService;

    @InjectMocks
    private DataImportRestController controller;

    private String filePath;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        // Default filePath (won't be used in tests that require a file to exist)
        filePath = "src/test/resources/event_records.xlsx";
        controller.setFilePath(filePath);
    }

    @Test
    public void testHandleFileUpload_FileNotFound() throws Exception {
        // Define a file path that should simulate a "file not found" scenario.
        String nonExistentFilePath = "src/test/resources/nonExistentFile.xlsx";
        controller.setFilePath(nonExistentFilePath);

        // Ensure the file does not exist.
        File file = new File(nonExistentFilePath);
        if (file.exists()) {
            file.delete();
        }

        // Call the controller method.
        ResponseEntity<String> response = controller.handleFileUpload();

        // Expect a BAD_REQUEST status with the appropriate error message.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File not found in path " + nonExistentFilePath, response.getBody());

        // Verify that the service method is never invoked.
        verify(dataImportService, never()).importEventDetailsIntoDB(anyString());
    }

    @Test
    public void testHandleFileUpload_Success() throws Exception {
        // Define the actual file path.
        String actualFilePath = "src/test/resources/event_records.xlsx";
        File actualFile = new File(actualFilePath);
        
        
        // Set the file path in the controller.
        controller.setFilePath(actualFilePath);
        
        // Call the controller's method.
        ResponseEntity<String> response = controller.handleFileUpload();
        
        // Verify that the service method was called exactly once with the actual file path.
        verify(dataImportService, times(1)).importEventDetailsIntoDB(actualFilePath);
        
        // Verify that the response is OK with the expected success message.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded and data saved successfully.", response.getBody());
        
    }


    @Test
    public void testHandleFileUpload_ExceptionThrown() throws Exception {
        // Create a temporary file so that the existence check passes.
        File tempFile = File.createTempFile("event_records", ".xlsx");
        tempFile.deleteOnExit();
        String tempFilePath = tempFile.getAbsolutePath();
        controller.setFilePath(tempFilePath);

        // Simulate a runtime exception (e.g., a database error) from the service.
        doThrow(new RuntimeException("Database error"))
            .when(dataImportService).importEventDetailsIntoDB(tempFilePath);

        // Call the controller method.
        ResponseEntity<String> response = controller.handleFileUpload();

        // Verify that an INTERNAL_SERVER_ERROR is returned with the expected error message.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: Database error", response.getBody());
    }
}
