package com.example.PincodeFinder1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

@RestController
public class PincodeController {
    private static final Logger logger = LoggerFactory.getLogger(PincodeController.class);
    private static final String API_URL = "https://india-pincode-with-latitude-and-longitude.p.rapidapi.com/api/v1/pincode/%s";
    private static final String API_ALL_URL = "https://india-pincode-with-latitude-and-longitude.p.rapidapi.com/api/v1/allPincodes";
    private static final String API_KEY = "39cf8a3f3bmsh721b729bb85aab8p1dbc5bjsn54fa628ecf0d";
    private static final String API_HOST = "india-pincode-with-latitude-and-longitude.p.rapidapi.com";

    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<List<Pincode>> getPincodeDetails(@PathVariable String pincode) {
        logger.info("Received request for pincode: {}", pincode);
        try {
            String apiUrlWithPincode = String.format(API_URL, pincode);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrlWithPincode, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                Pincode[] pincodes = objectMapper.readValue(response.getBody(), Pincode[].class);
                return ResponseEntity.ok().body(List.of(pincodes));
            } else {
                logger.error("Error response from API: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(Collections.emptyList());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while fetching pincode details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/pincode")
    public ResponseEntity<List<Pincode>> getAllPincodeDetails() {
        logger.info("Received request for all pincodes");
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(API_ALL_URL, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                Pincode[] pincodes = objectMapper.readValue(response.getBody(), Pincode[].class);
                return ResponseEntity.ok().body(List.of(pincodes));
            } else {
                logger.error("Error response from API: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(Collections.emptyList());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while fetching all pincode details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", API_KEY);
        headers.set("x-rapidapi-host", API_HOST);
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Cache-Control", "no-cache");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        return headers;
    }
}
