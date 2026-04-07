package com.myresourcer.MyResourcer.Controllers;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.DTOs.DTO_Users;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Services.POST_ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class POST_API_Controller {

    @Autowired
    private POST_ServiceManager serviceManager;


    public POST_API_Controller(){}

    private static final Logger logger = LogManager.getLogger(POST_API_Controller.class);



    ///POST REQUEST -------------------------------------->
    @PostMapping("/requests")
    public ResponseEntity<?> addRequest(@RequestBody DTO_Request request) {
        logger.info("Asset Requested with ID: {}, by user with Id: {}", request.getAssetId(), request.getUserId());

        try {
            boolean isRequestAdded = serviceManager.addRequest(request);

            if (isRequestAdded) {
                logger.debug("Request Successfully Sent, with asset ID: {}, by user with Id: {}", request.getAssetId(), request.getUserId());
                return ResponseEntity.status(HttpStatus.CREATED) // 201 Created
                        .body("Request Successfully Added");
            } else {
                logger.warn("Request could not be processed for asset ID: {}, by user with Id: {}", request.getAssetId(), request.getUserId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                        .body("Unable to process request");
            }
        } catch (Exception e) {
            logger.error("Failed to Send Request for Asset with ID: {}, by user with Id: {}", request.getAssetId(), request.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
                    .body("An error occurred while processing your request");
        }
    }

    @PostMapping("/assets")
    public ResponseEntity<String> addAsset(@RequestBody DTO_Assets assets) {
        logger.info("Adding new asset: {}", assets.getItem());

        try {
            boolean isAssetAdded = serviceManager.addAsset(assets);

            if (isAssetAdded) {
                logger.debug("Asset added successfully: {}", assets.getItem());
                return ResponseEntity.status(HttpStatus.CREATED).body("Asset Successfully Added");
            } else {
                logger.warn("Asset not added: {}", assets.getItem());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Asset Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding asset: {}", assets.getItem(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding asset");
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<String> addCategories(@RequestBody Categories categories) {
        logger.info("Adding new category: {}", categories.getCategoryName());

        try {
            boolean isCategoryAdded = serviceManager.addCategory(categories);

            if (isCategoryAdded) {
                logger.debug("Category added successfully: {}", categories.getCategoryName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Category Successfully Added");
            } else {
                logger.warn("Category not added: {}", categories.getCategoryName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding category: {}", categories.getCategoryName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding category");
        }
    }

    @PostMapping("/conditions")
    public ResponseEntity<String> addCondition(@RequestBody Condition condition) {
        logger.info("Adding new condition: {}", condition.getConditionName());

        try {
            boolean isConditionAdded = serviceManager.addCondition(condition);

            if (isConditionAdded) {
                logger.debug("Condition added successfully: {}", condition.getConditionName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Condition Successfully Added");
            } else {
                logger.warn("Condition not added: {}", condition.getConditionName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Condition Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding condition: {}", condition.getConditionName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding condition");
        }
    }

    @PostMapping("/departments")
    public ResponseEntity<String> addDepartment(@RequestBody Departments department) {
        logger.info("Adding new department: {}", department.getDepartmentName());

        try {
            boolean isDepartmentAdded = serviceManager.addDepartment(department);

            if (isDepartmentAdded) {
                logger.debug("Department added successfully: {}", department.getDepartmentName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Department Successfully Added");
            } else {
                logger.warn("Department not added: {}", department.getDepartmentName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding department: {}", department.getDepartmentName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding department");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody DTO_Users users) {
        logger.info("Adding new user: {}", users.getUsername());

        try {
            boolean isUserAdded = serviceManager.addUser(users);

            if (isUserAdded) {
                logger.debug("User added successfully: {}", users.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body("User Successfully Added");
            } else {
                logger.warn("User not added: {}", users.getUsername());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding user: {}", users.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding user");
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<String> addRole(@RequestBody Roles roles) {
        logger.info("Adding new role: {}", roles.getRoleName());

        try {
            boolean isRoleAdded = serviceManager.addRole(roles);

            if (isRoleAdded) {
                logger.debug("Role added successfully: {}", roles.getRoleName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Role Successfully Added");
            } else {
                logger.warn("Role not added: {}", roles.getRoleName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding role: {}", roles.getRoleName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding role");
        }
    }

    @PostMapping("/statuses")
    public ResponseEntity<String> addStatus(@RequestBody Status status) {
        logger.info("Adding new status: {}", status.getStatusName());

        try {
            boolean isStatusAdded = serviceManager.addStatus(status);

            if (isStatusAdded) {
                logger.debug("Status added successfully: {}", status.getStatusName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Status Successfully Added");
            } else {
                logger.warn("Status not added: {}", status.getStatusName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding status: {}", status.getStatusName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding status");
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<String> addComment(@RequestBody Comments comments) {
        logger.info("Adding new comment: {}", comments.getComment());
        try {boolean isCommentAdded = serviceManager.addComment(comments);

            if (isCommentAdded) {
                logger.debug("Comment added successfully: {}", comments.getComment());
                return ResponseEntity.status(HttpStatus.CREATED).body("Comment Successfully Added");
            } else {
                logger.warn("Comment not added: {}", comments.getComment());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment Not Added");
            }
        } catch (Exception e) {
            logger.error("Error adding comment: {}", comments.getComment(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error while adding comment");
        }
    }

}