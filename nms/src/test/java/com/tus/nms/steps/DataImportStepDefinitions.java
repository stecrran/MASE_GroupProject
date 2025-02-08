package com.tus.nms.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DataImportStepDefinitions {

    // Use HttpResponse with type String
    private HttpResponse<String> response;

    @Given("the file {string} exists with valid data")
    public void the_file_exists_with_valid_data(String filePath) {
        File file = new File(filePath);
        assertTrue(file.exists(), "File " + filePath + " does not exist");
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) throws Exception {
        // Create a new HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the POST HttpRequest. Adjust the URI
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9091" + endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request and store response
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatusCode) {
        // Compare the expected status code with actual status code
        assertEquals(expectedStatusCode.intValue(), response.statusCode(), "Unexpected status code");
    }

    @Then("the response body should contain {string}")
    public void the_response_body_should_contain(String expectedContent) {
        String body = response.body();
        // Assert that the response body contains the expected content
        assertTrue(body.contains(expectedContent), "Response body did not contain expected content: " + expectedContent);
    }
}
