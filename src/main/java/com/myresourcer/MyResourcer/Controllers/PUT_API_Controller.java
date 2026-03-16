package com.myresourcer.MyResourcer.Controllers;

import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Services.PUT_ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PUT_API_Controller {

    @Autowired
    private PUT_ServiceManager serviceManager;

    public PUT_API_Controller() {
    }

    private static final Logger logger = LogManager.getLogger(PUT_API_Controller.class);

    ///PUT REQUEST -------------------------------------->
    @PutMapping("/requests/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Integer id, @RequestBody DTO_Request request) {
        logger.info("Updating request with ID: {}", id);

        try {
            boolean isRequestUpdated = serviceManager.updateRequest(id, request);

            if (isRequestUpdated) {
                logger.debug("Request {} successfully updated.", id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Request Successfully Updated");
            } else {
                logger.warn("Request {} could not be found or updated.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Request not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update request with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }
}
