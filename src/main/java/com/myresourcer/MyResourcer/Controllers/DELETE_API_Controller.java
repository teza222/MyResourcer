package com.myresourcer.MyResourcer.Controllers;

import com.myresourcer.MyResourcer.Services.DELETE_ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DELETE_API_Controller {
    @Autowired
    private DELETE_ServiceManager serviceManager;

    public DELETE_API_Controller() {
    }

    private static final Logger logger = LogManager.getLogger(DELETE_API_Controller.class);

    //DELETE REQUEST -------------------------------------->

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        logger.info("Deleting request with ID: {}", id);
        try {
            boolean isRequestDeleted = serviceManager.deleteRequest(id);
            if (isRequestDeleted) {
                logger.debug("Request {} successfully deleted.", id);
                return ResponseEntity.ok("Request Successfully Deleted");
            } else {
                logger.warn("Request {} could not be found or deleted.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete request with ID: {}", id, e);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
            }
    }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Integer id) {
        logger.info("Deleting asset with ID: {}", id);
        try {
            boolean isAssetDeleted = serviceManager.softDeleteAsset(id);
            if (isAssetDeleted) {
                logger.debug("Asset {} successfully deleted.", id);
                return ResponseEntity.ok("Asset Successfully Deleted");
            } else {
                logger.warn("Asset {} could not be found or deleted.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete asset with ID: {}", id, e);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        logger.info("Deleting category with ID: {}", id);
        try {
            boolean isCategoryDeleted = serviceManager.deleteCategory(id);
            if (isCategoryDeleted) {
                logger.debug("Category {} succesfully deleted.", id);
                return ResponseEntity.ok("Category Successfully Deleted");
            } else {
                logger.warn("Category {} could not be found or deleted.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete category with ID: {}", id, e);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/conditions/{id}")
    public ResponseEntity<?> deleteCondition(@PathVariable Integer id) {
        logger.info("Deleting condition with ID: {}", id);
        try {
            boolean isConditionDeleted = serviceManager.deleteCondition(id);
            if (isConditionDeleted) {
                logger.debug("Condition {} successfully deleted.", id);
                return ResponseEntity.ok("Condition Successfully Deleted");
            } else {
                logger.warn("Condition {} could not be found or deleted", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete condition with ID: {}", id, e);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        logger.info("Deleting department with ID: {}", id);
        try {
            boolean isDepartmentDeleted = serviceManager.deleteDepartment(id); {
                if (isDepartmentDeleted) {
                    logger.debug("Department {} successfully deleted.", id);
                    return ResponseEntity.ok("Department Successfully Deleted");
                } else {
                    logger.warn("Department {} could not be found or deleted", id);
                    return ResponseEntity.notFound().build();
                }
            }
        }catch(Exception e){
            logger.error("Failed to delete department with ID: {}", id);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            boolean isUserDeleted = serviceManager.deleteUser(id);
            if (isUserDeleted) {
                logger.debug("User {} successfully deleted.",id);
                return ResponseEntity.ok("User Successfully Deleted");
            } else {
                logger.warn("User {} could not be found or deleted",id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete user with ID: {}", id);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        logger.info("Deleting role with ID: {}", id);
        try {
            boolean isRoleDeleted = serviceManager.deleteRole(id);
            if (isRoleDeleted) {
                logger.debug("Role {} successfully deleted.",id);
                return ResponseEntity.ok("Role Successfully Deleted");
            } else {
                logger.warn("Role {} could not be found or deleted",id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete role user with ID: {}",id);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Integer id) {
        logger.info("Deleting status with ID: {}", id);
        try {
            boolean isStatusDeleted = serviceManager.deleteStatus(id);
            if (isStatusDeleted) {
                logger.debug("Status {} successfully deleted", id);
                return ResponseEntity.ok("Status Successfully Deleted");
            } else {
                logger.warn("Status {} could not be found or deleted", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to delete status with ID: {}", id);
            return ResponseEntity.status(500).body("An error occurred while processing your request");
        }
    }


}
