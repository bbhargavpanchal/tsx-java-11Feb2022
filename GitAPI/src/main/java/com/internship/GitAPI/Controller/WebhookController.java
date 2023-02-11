//package com.internship.GitAPI.Controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/webhook")
//public class WebhookController {
//
//    @PostMapping // http://localhost:5040/api/webhook
//    public ResponseEntity<String> print(@RequestBody String requestBody) {
//        System.out.println("###### Webhook #####" + requestBody);
//        return new ResponseEntity<String >(requestBody, HttpStatus.OK);
//    }
//}
package com.internship.GitAPI.Controller;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.*;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody String requestBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(requestBody);
            String event = root.get("event").asText();
            switch (event) {
                case "push":
                    LOGGER.info("Push Event Triggered: " + root.get("payload").toString());
                    break;
                case "pull_request":
                    LOGGER.info("Pull Request Event Triggered: " + root.get("payload").toString());
                    break;
                case "pull_request_review":
                    LOGGER.info("Pull Request Review Event Triggered: " + root.get("payload").toString());
                    break;
                case "merge":
                    LOGGER.info("Merge Event Triggered: " + root.get("payload").toString());
                    break;
                default:
                    LOGGER.info("Unknown Event Triggered: " + requestBody);
            }
            return new ResponseEntity<>("Webhook event handled successfully", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error handling webhook event", e);
            return new ResponseEntity<>("Error handling webhook event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
