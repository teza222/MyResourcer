package com.myresourcer.MyResourcer.Controllers;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
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

    /// PUT REQUEST -------------------------------------->
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

    @PutMapping("/assets/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Integer id, @RequestBody DTO_Assets asset) {
        logger.info("Updating asset with ID: {}", id);

        try {
            boolean isAssetUpdated = serviceManager.updateAsset(id, asset);
            if (isAssetUpdated) {
                logger.debug("Asset {} successfully updated.",id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Asset Successfully Updated");
            } else {
                logger.warn("Asset {} could not be found or updated.",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Asset not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update asset with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Categories category) {
        logger.info("Updating category with ID: {}", id);

        try {
            boolean isCategoryUpdated = serviceManager.updateCategory(id, category);
            if (isCategoryUpdated) {
                logger.debug("Category {} successfully updated.",id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Category Successfully Updated");
            } else {
                logger.warn("Category {} could not be found or updated.",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Category not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }

    @PutMapping("/conditions/{id}")
    public ResponseEntity<?> updateCondition(@PathVariable Integer id, @RequestBody Condition condition) {
        logger.info("Updating condition with ID: {}", id);

        try {
            boolean isConditionUpdated = serviceManager.updateCondition(id, condition);
            if (isConditionUpdated) {
                logger.debug("Condition {} successfully updated.",id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Condition Successfully Updated");
            } else {
                logger.warn("Conditio {} could not be found or updated.",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Condition not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update condition with ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
        }

        @PutMapping("/departments/{id}")
        public ResponseEntity<?> updateDepartment(@PathVariable Integer id, @RequestBody Departments department) {
            logger.info("Updating department with ID: {}", id);

            try {
                boolean isDepartmentUpdated = serviceManager.updateDepartment(id, department);
                if (isDepartmentUpdated) {
                    logger.debug("Department {} successfully updated.", id);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body("Department Successfully Updated");
                } else {
                    logger.warn("Department {} could not be found or updated.", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Unable to process request. Department not found.");
                }
            } catch (Exception e) {
                logger.error("Failed to update department with ID: {}", id, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while processing your request");
            }
        }

        @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Users user) {
        logger.info("Updating user with ID: {}", id);

        try {
            boolean isUserUpdated = serviceManager.updateUser(id, user);
            if (isUserUpdated) {
                logger.debug("User {} successfully updated.", id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User Successfully Updated");
            } else {
                logger.warn("User {} could not be found or updated.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. User not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody Roles role) {
        logger.info("Updating role with ID: {}", id);

        try {
            boolean isRoleUpdated = serviceManager.updateRole(id, role);
            if (isRoleUpdated) {
                logger.debug("Role {} successfully updated.", id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Role Successfully Updated");
            } else {
                logger.warn("Role {} could not be found or updated.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Role not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update role with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }

    @PutMapping("/statuses/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Status status) {
        logger.info("Updating status with ID: {}", id);

        try {
            boolean isStatusUpdated = serviceManager.updateStatus(id, status);
            if (isStatusUpdated) {
                logger.debug("Status {} successfully updated.", id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Status Successfully Updated");
            } else {
                logger.warn("Status {} could not be found or updated.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to process request. Status not found.");
            }
        } catch (Exception e) {
            logger.error("Failed to update status with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }



}