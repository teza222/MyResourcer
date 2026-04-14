package com.myresourcer.MyResourcer.WEB_Controllers;

import com.myresourcer.MyResourcer.DTOs.DTO_Comments;
import com.myresourcer.MyResourcer.DTOs.DTO_Users;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import com.myresourcer.MyResourcer.Services.EMAIL_ServiceManager;
import com.myresourcer.MyResourcer.Services.GET_ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    private GET_ServiceManager serviceManager;

    @Autowired
    private User_Repository userRepository;

    @Autowired
    private Role_Repository roleRepository;

    @Autowired
    private Department_Repository departmentRepository;

    @Autowired
    private Request_Repository requestRepository;

    @Autowired
    private Asset_Repository assetRepository;

    @Autowired
    private Category_Repository categoryRepository;

    @Autowired
    private Status_Repository statusRepository;

    @Autowired
    private Condition_Repository conditionRepository;

    @Autowired
    private Comment_Repository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EMAIL_ServiceManager emailService;

    private static final Logger logger = LogManager.getLogger(WebController.class);

    // Passing the user object to the model
    @ModelAttribute
    public void addAuthenticatedUserToModel(Model model, Principal principal) {
        if (principal != null) {
            userRepository.findByUsername(principal.getName())
                    .ifPresent(user -> model.addAttribute("user", user));
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        // Filter out removed assets for the count
        model.addAttribute("assetCount", serviceManager.getAllAssets().stream().filter(a -> a.getRemoved() == null || !a.getRemoved()).count());
        model.addAttribute("userCount", serviceManager.getAllUsers().size());
        model.addAttribute("requestCount", serviceManager.getAllRequests().size());
        
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("hideNavbar", false);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("hideNavbar", false);
        model.addAttribute("roles", serviceManager.getAllRoles().stream().filter(r -> !"Administrator".equalsIgnoreCase(r.getRoleName())).collect(Collectors.toList()));
        model.addAttribute("departments", serviceManager.getAllDepartments());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute DTO_Users user) {
        Roles role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        Departments department = departmentRepository.findById(user.getDepartmentId()).orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // Create a new user from DTO user object
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setEmail(user.getEmail());
        newUser.setRoleId(role);
        newUser.setDepartmentId(department);
        newUser.setFlag(0); // Initialize flag at 0

        userRepository.save(newUser);
        logger.info("New user registered: {}", newUser.getUsername());
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        // Check if user is logged in if not redirect to login page
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = (Users) model.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Check if user is an Administrator
        if ("Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            logger.info("Administrator accessing dashboard {} at {}", user.getUsername(), LocalDateTime.now());
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "home");

        // Sort requests by request ID in descending order and filter by user ID
        List<Request> sortedRequests = requestRepository.findAll().stream()
                .filter(r -> r.getUserId().getId().equals(user.getId()))
                .sorted(Comparator.comparing(Request::getRequestId).reversed())
                .collect(Collectors.toList());
        model.addAttribute("requests", sortedRequests);

        return "dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = (Users) model.getAttribute("user");
        if (user == null || !"Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "admin-home");

        // Filter out removed assets for the count
        model.addAttribute("assetCount", assetRepository.findAll().stream().filter(a -> a.getRemoved() == null || !a.getRemoved()).count());
        model.addAttribute("userCount", serviceManager.getAllUsers().size());
        model.addAttribute("requestCount", serviceManager.getAllRequests().size());
        model.addAttribute("categoryCount", serviceManager.getAllCategories().size());
        
        model.addAttribute("pendingRequestsCount", requestRepository.findAll().stream()
                .filter(r -> "Pending".equalsIgnoreCase(r.getStatusId().getStatusName()))
                .count());
        model.addAttribute("approvedRequestsCount", requestRepository.findAll().stream()
                .filter(r -> "Approved".equalsIgnoreCase(r.getStatusId().getStatusName()))
                .count());
        model.addAttribute("canceledRequestsCount", requestRepository.findAll().stream()
                .filter(r -> "Canceled".equalsIgnoreCase(r.getStatusId().getStatusName()))
                .count());

        return "admin_dashboard";
    }

    @GetMapping("/admin/manage-assets")
    public String manageAssets(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Users user = (Users) model.getAttribute("user");
        if (user == null || !"Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "manage-assets");

        // Filter out removed assets
        List<Assets> activeAssets = assetRepository.findAll().stream()
                .filter(a -> a.getRemoved() == null || !a.getRemoved())
                .collect(Collectors.toList());
        model.addAttribute("assets", activeAssets);
        model.addAttribute("categories", serviceManager.getAllCategories());
        model.addAttribute("conditions", conditionRepository.findAll());

        return "manage_assets";
    }

    @PostMapping("/admin/add-asset")
    public String addAsset(@RequestParam String item,
                           @RequestParam String serialNumber,
                           @RequestParam(required = false) String specifications,
                           @RequestParam Integer categoryId,
                           @RequestParam(defaultValue = "false") Boolean isMobile,
                           RedirectAttributes redirectAttributes) {

        Categories category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid category selected.");
            return "redirect:/admin/manage-assets";
        }

        // Creating a new asset
        Assets asset = new Assets();
        asset.setItem(item);
        asset.setSerialNumber(serialNumber);
        asset.setSpecifications(specifications);
        asset.setCategoryId(category);
        asset.setMobile(isMobile);
        asset.setRemoved(false);
        
        // Set default condition to Working
        conditionRepository.findAll().stream()
                .filter(c -> "Working".equalsIgnoreCase(c.getConditionName()))
                .findFirst()
                .ifPresent(asset::setConditionId);

        assetRepository.save(asset);
        logger.info("Asset added: {}", asset.getSerialNumber());
        redirectAttributes.addFlashAttribute("success", "Asset '" + item + "' added successfully!");
        return "redirect:/admin/manage-assets";
    }

    @PostMapping("/admin/edit-asset-condition")
    public String editAssetCondition(@RequestParam Integer assetId, @RequestParam Integer conditionId, RedirectAttributes redirectAttributes) {
        Assets asset = assetRepository.findById(assetId).orElse(null);
        Condition condition = conditionRepository.findById(conditionId).orElse(null);
        
        if (asset != null && condition != null) {
            asset.setConditionId(condition);
            assetRepository.save(asset);
            logger.info("Condition updated for asset: {} to: {}", asset.getItem(), condition.getConditionName());
            redirectAttributes.addFlashAttribute("success", "Condition updated for asset: " + asset.getItem());
        } else {
            redirectAttributes.addFlashAttribute("error", "Asset or Condition not found.");
        }
        return "redirect:/admin/manage-assets";
    }

    @PostMapping("/admin/delete-asset")
    public String deleteAsset(@ModelAttribute DTO_Comments commentDTO,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        Assets asset = assetRepository.findById(commentDTO.getAssetId()).orElse(null);
        if (asset != null) {
            asset.setRemoved(true); // Soft delete set isRemoved to true
            logger.info("Asset removed: {} by: {} at: {}", asset.getSerialNumber(), principal.getName(), LocalDateTime.now());
            assetRepository.save(asset);

            // User must provide a comment before deletion
            Users currentUser = userRepository.findByUsername(principal.getName()).orElse(null);
            if (currentUser != null) {
                Comments deletionComment = new Comments();
                deletionComment.setType("Asset Deletion");
                deletionComment.setComment("Reason: " + commentDTO.getComment() + " | Asset ID: " + commentDTO.getAssetId() + " (" + asset.getItem() + ")");
                deletionComment.setDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                deletionComment.setUser(currentUser);
                deletionComment.setAsset(asset);
                commentRepository.save(deletionComment);
            }

            redirectAttributes.addFlashAttribute("success", "Asset '" + asset.getItem() + "' has been removed.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Asset not found.");
        }
        return "redirect:/admin/manage-assets";
    }

    @GetMapping("/admin/manage-requests")
    public String manageRequests(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Users user = (Users) model.getAttribute("user");
        if (user == null || !"Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "manage-requests");

        List<Request> allRequests = requestRepository.findAll();
        // Sort requests by pending first, then by dateOut descending
        allRequests.sort(Comparator
                .comparing((Request r) -> "Pending".equalsIgnoreCase(r.getStatusId().getStatusName()) ? 0 : 1) // Pending first
                .thenComparing(Request::getDateOut, Comparator.nullsLast(Comparator.reverseOrder())) // Then by dateOut descending
                .thenComparing(Request::getTimeOut, Comparator.nullsLast(Comparator.reverseOrder()))); // Then by timeOut descending
        
        model.addAttribute("requests", allRequests);
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("conditions", conditionRepository.findAll());

        return "manage_requests";
    }

    @PostMapping("/admin/approve-request")
    public String approveRequest(@RequestParam Integer requestId, RedirectAttributes redirectAttributes) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
            return "redirect:/admin/manage-requests";
        }

        Status approvedStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Approved"))
                .findFirst()
                .orElse(null); // Fallback if status not found

        if (approvedStatus == null) {
            approvedStatus = new Status();
            approvedStatus.setStatusName("Approved");
            statusRepository.save(approvedStatus);
        }
        
        request.setStatusId(approvedStatus);
        // Set dateOut and timeOut when the asset is approved
        request.setDateOut(LocalDate.now().toString());
        request.setTimeOut(LocalTime.now().withNano(0).toString());
        
        requestRepository.save(request);
        logger.info("Approved request with ID: {}, by user: {}", requestId, request.getUserId().getUsername());

        // Send Email to user
        String subject = "Asset Request Approved";
        String body = "Hello " + request.getUserId().getFname() + ",\n\n" +
                "Your request for the asset '" + request.getAssetId().getItem() + "' (Serial: " + request.getAssetId().getSerialNumber() + ") has been APPROVED.\n\n" +
                "You can now proceed to collect the asset from the administration office.\n\n" +
                "Thank you.";
        try {
            emailService.sendEmail(request.getUserId().getEmail(), subject, body);
        } catch (Exception e) {
            logger.error("Failed to send approval email to: {}", request.getUserId().getEmail(), e);
        }

        redirectAttributes.addFlashAttribute("success", "Request for " + request.getAssetId().getItem() + " approved. Awaiting collection.");
        return "redirect:/admin/manage-requests";
    }

    @PostMapping("/admin/mark-collected")
    public String markCollected(@RequestParam Integer requestId, RedirectAttributes redirectAttributes) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
            return "redirect:/admin/manage-requests";
        }

        Status collectedStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Collected"))
                .findFirst()
                .orElse(null);

        if (collectedStatus == null) {
            collectedStatus = new Status();
            collectedStatus.setStatusName("Collected");
            statusRepository.save(collectedStatus);
        }

        request.setStatusId(collectedStatus);
        // Set dateOut and timeOut when the asset is collected
        request.setDateOut(LocalDate.now().toString());
        request.setTimeOut(LocalTime.now().withNano(0).toString());
        
        requestRepository.save(request);
        logger.info("Request with ID: {} marked as collected. Asset: {}", requestId, request.getAssetId().getItem());
        redirectAttributes.addFlashAttribute("success", "Asset '" + request.getAssetId().getItem() + "' marked as collected.");
        return "redirect:/admin/manage-requests";
    }

    @PostMapping("/admin/reject-request")
    public String rejectRequest(@RequestParam Integer requestId, @RequestParam String reason, RedirectAttributes redirectAttributes) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
            return "redirect:/admin/manage-requests";
        }

        Status rejectedStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Rejected"))
                .findFirst()
                .orElse(null); // Fallback if status not found

        if (rejectedStatus == null) {
            rejectedStatus = new Status();
            rejectedStatus.setStatusName("Rejected");
            statusRepository.save(rejectedStatus);
        }
        
        request.setStatusId(rejectedStatus);
        // Set dateIn and timeIn when the request is rejected/cancelled
        request.setDateIn(LocalDate.now().toString());
        request.setTimeIn(LocalTime.now().withNano(0).toString());
        requestRepository.save(request);
        logger.info("Rejected request with ID: {}, by user: {}", requestId, request.getUserId());

        // Send Email to user with reason
        String subject = "Asset Request Rejected";
        String body = "Hello " + request.getUserId().getFname() + ",\n\n" +
                "Your request for the asset '" + request.getAssetId().getItem() + "' has been REJECTED / CANCELED.\n\n" +
                "Reason provided: " + reason + "\n\n" +
                "If you have any questions, please contact the administrator.\n\n" +
                "Thank you.";
        try {
            emailService.sendEmail(request.getUserId().getEmail(), subject, body);
        } catch (Exception e) {
            logger.error("Failed to send rejection email to: {}", request.getUserId().getEmail(), e);
        }

        redirectAttributes.addFlashAttribute("success", "Request for " + request.getAssetId().getItem() + " rejected.");
        return "redirect:/admin/manage-requests";
    }

    @PostMapping("/admin/return-request")
    public String returnRequest(@RequestParam Integer requestId, RedirectAttributes redirectAttributes) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
            return "redirect:/admin/manage-requests";
        }

        LocalDate today = LocalDate.now();
        LocalDate dateOut = LocalDate.parse(request.getDateOut());
        boolean isLate = today.isAfter(dateOut);
        String statusName = isLate ? "Late" : "Returned";

        Status returnStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase(statusName))
                .findFirst()
                .orElse(null);

        if (returnStatus == null) {
            returnStatus = new Status();
            returnStatus.setStatusName(statusName);
            statusRepository.save(returnStatus);
        }
        
        request.setStatusId(returnStatus);
        request.setDateIn(today.toString());
        request.setTimeIn(LocalTime.now().withNano(0).toString());
        requestRepository.save(request);
        logger.info("Returned asset with request ID: {}, by user: {}", requestId, request.getUserId());


        if (isLate) {
            Users user = request.getUserId();
            user.setFlag(user.getFlag() + 1);
            userRepository.save(user);
            logger.info("Returned user with request ID: {} as been FLAGGED for late return, by user: {}", requestId, user.getUsername());
            redirectAttributes.addFlashAttribute("error", "Asset returned LATE! User flagged (" + user.getFlag() + "/3).");
        } else {
            redirectAttributes.addFlashAttribute("success", "Asset " + request.getAssetId().getItem() + " marked as returned.");
        }

        return "redirect:/admin/manage-requests";
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Users adminUser = (Users) model.getAttribute("user");
        if (adminUser == null || !"Administrator".equalsIgnoreCase(adminUser.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "manage-users");

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());

        return "manage_users";
    }

    @PostMapping("/admin/reset-user-flags")
    public String resetUserFlags(@RequestParam Integer userId, RedirectAttributes redirectAttributes) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setFlag(0);
            userRepository.save(user);
            logger.info("Flags reset for user: {}", user.getUsername());
            redirectAttributes.addFlashAttribute("success", "Flags reset for user " + user.getUsername());
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/admin/manage-users";
    }

    @PostMapping("/admin/block-user")
    public String blockUser(@RequestParam Integer userId, RedirectAttributes redirectAttributes) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setFlag(3); // 3 or more blocks login
            userRepository.save(user);
            logger.info("User blocked: {}", user.getUsername());
            redirectAttributes.addFlashAttribute("success", "User " + user.getUsername() + " has been blocked.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/admin/manage-organization")
    public String manageOrganizationSettings(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Users user = (Users) model.getAttribute("user");
        if (user == null || !"Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "manage-organization");

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("conditions", conditionRepository.findAll());

        // Usage checks for UI
        model.addAttribute("usedCategoryIds", assetRepository.findAll().stream().filter(a -> a.getRemoved() == null || !a.getRemoved()).map(a -> a.getCategoryId().getCategoryId()).collect(Collectors.toSet()));
        model.addAttribute("usedDepartmentIds", userRepository.findAll().stream().map(u -> u.getDepartmentId().getDepartmentId()).collect(Collectors.toSet()));
        model.addAttribute("usedRoleIds", userRepository.findAll().stream().map(u -> u.getRoleId().getRoleId()).collect(Collectors.toSet()));
        model.addAttribute("usedStatusIds", requestRepository.findAll().stream().map(r -> r.getStatusId().getStatusId()).collect(Collectors.toSet()));
        model.addAttribute("usedConditionIds", requestRepository.findAll().stream().map(r -> r.getConditionId().getConditionId()).collect(Collectors.toSet()));

        return "manage_organization";
    }

    @PostMapping("/admin/add-category")
    public String addCategory(@RequestParam String categoryName, RedirectAttributes redirectAttributes) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Category name cannot be empty.");
            return "redirect:/admin/manage-organization";
        }
        Categories category = new Categories();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);
        logger.info("Category added: {}", categoryName);
        redirectAttributes.addFlashAttribute("success", "Category added successfully!");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/delete-category")
    public String deleteCategory(@RequestParam Integer categoryId, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted: {}", categoryId);
        redirectAttributes.addFlashAttribute("success", "Category deleted.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/add-department")
    public String addDepartment(@RequestParam String departmentName, RedirectAttributes redirectAttributes) {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Department name cannot be empty.");
            return "redirect:/admin/manage-organization";
        }
        Departments department = new Departments();
        department.setDepartmentName(departmentName);
        departmentRepository.save(department);
        logger.info("Department added: {}", departmentName);
        redirectAttributes.addFlashAttribute("success", "Department added successfully!");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/delete-department")
    public String deleteDepartment(@RequestParam Integer departmentId, RedirectAttributes redirectAttributes) {
        departmentRepository.deleteById(departmentId);
        logger.info("Department deleted: {}", departmentId);
        redirectAttributes.addFlashAttribute("success", "Department deleted.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/add-role")
    public String addRole(@RequestParam String roleName, RedirectAttributes redirectAttributes) {
        Roles role = new Roles();
        role.setRoleName(roleName);
        roleRepository.save(role);
        logger.info("Role added: {}", roleName);
        redirectAttributes.addFlashAttribute("success", "Role added.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/delete-role")
    public String deleteRole(@RequestParam Integer roleId, RedirectAttributes redirectAttributes) {
        roleRepository.deleteById(roleId);
        logger.info("Role deleted: {}", roleId);
        redirectAttributes.addFlashAttribute("success", "Role deleted.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/add-status")
    public String addStatus(@RequestParam String statusName, RedirectAttributes redirectAttributes) {
        Status status = new Status();
        status.setStatusName(statusName);
        statusRepository.save(status);
        logger.info("Status added: {}", statusName);
        redirectAttributes.addFlashAttribute("success", "Status added.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/delete-status")
    public String deleteStatus(@RequestParam Integer statusId, RedirectAttributes redirectAttributes) {
        statusRepository.deleteById(statusId);
        logger.info("Status deleted: {}", statusId);
        redirectAttributes.addFlashAttribute("success", "Status deleted.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/add-condition")
    public String addCondition(@RequestParam String conditionName, RedirectAttributes redirectAttributes) {
        Condition condition = new Condition();
        condition.setConditionName(conditionName);
        conditionRepository.save(condition);
        logger.info("Condition added: {}", conditionName);
        redirectAttributes.addFlashAttribute("success", "Condition added.");
        return "redirect:/admin/manage-organization";
    }

    @PostMapping("/admin/delete-condition")
    public String deleteCondition(@RequestParam Integer conditionId, RedirectAttributes redirectAttributes) {
        conditionRepository.deleteById(conditionId);
        logger.info("Condition deleted: {}", conditionId);
        redirectAttributes.addFlashAttribute("success", "Condition deleted.");
        return "redirect:/admin/manage-organization";
    }

    @GetMapping("/admin/reports")
    public String adminReports(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Users user = (Users) model.getAttribute("user");
        if (user == null || !"Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", "reports");

        List<Assets> activeAssets = assetRepository.findAll().stream()
                .filter(a -> a.getRemoved() == null || !a.getRemoved())
                .collect(Collectors.toList());
        List<Assets> deletedAssets = assetRepository.findAll().stream()
                .filter(a -> a.getRemoved() != null && a.getRemoved())
                .collect(Collectors.toList());
        List<Request> allRequests = requestRepository.findAll();
        List<Users> allUsers = userRepository.findAll();
        List<Comments> allComments = commentRepository.findAll();

        // Asset Distribution by Category
        Map<String, Long> assetsByCategory = activeAssets.stream()
                .collect(Collectors.groupingBy(a -> a.getCategoryId().getCategoryName(), Collectors.counting()));
        model.addAttribute("assetsByCategory", assetsByCategory);

        // Request Status
        Map<String, Long> requestsByStatus = allRequests.stream()
                .collect(Collectors.groupingBy(r -> r.getStatusId().getStatusName(), Collectors.counting()));
        model.addAttribute("requestsByStatus", requestsByStatus);

        // Department Activity
        Map<String, Long> requestsByDepartment = allRequests.stream()
                .collect(Collectors.groupingBy(r -> r.getUserId().getDepartmentId().getDepartmentName(), Collectors.counting()));
        model.addAttribute("requestsByDepartment", requestsByDepartment);

        //Top Active Users
        Map<String, Long> topUsers = allRequests.stream()
                .collect(Collectors.groupingBy(r -> r.getUserId().getUsername(), Collectors.counting()));
        List<Map.Entry<String, Long>> sortedTopUsers = topUsers.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("topUsers", sortedTopUsers);
        
        //Asset Condition Breakdown
        Map<String, Long> assetsByCondition = allRequests.stream()
                .collect(Collectors.groupingBy(r -> r.getConditionId().getConditionName(), Collectors.counting()));
        model.addAttribute("assetsByCondition", assetsByCondition);

        // Detailed Asset Report
        model.addAttribute("assetReports", activeAssets.stream().map(a -> {
            // Find current status of the asset
            String status = allRequests.stream()
                    .filter(r -> r.getAssetId().getAssetId().equals(a.getAssetId()))
                    .sorted(Comparator.comparing(Request::getRequestId).reversed())
                    .findFirst()
                    .map(r -> (r.getDateIn() == null || r.getDateIn().trim().isEmpty()) ? "Checked Out" : "Available")
                    .orElse("Available");
            
            return Map.of(
                "item", a.getItem(),
                "serial", a.getSerialNumber(),
                "category", a.getCategoryId().getCategoryName(),
                "status", status
            );
        }).collect(Collectors.toList()));

        // Data for Comment Table
        model.addAttribute("comments", allComments);
        model.addAttribute("deletedAssets", deletedAssets);

        // Summary Counts
        model.addAttribute("totalAssets", activeAssets.size());
        model.addAttribute("totalRequests", allRequests.size());
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("lateReturns", allRequests.stream().filter(r -> "Late".equalsIgnoreCase(r.getStatusId().getStatusName())).count());

        return "manage_reports";
    }

    @GetMapping("/dashboard/electronics")
    public String dashboardElectronics(Model model, Principal principal) {
        return getRelevantAsset("Electronics", principal, model);
    }

    @GetMapping("/dashboard/rooms")
    public String dashboardRooms(Model model, Principal principal) {
        return getRelevantAsset("Rooms", principal, model);
    }

    @GetMapping("/dashboard/tools")
    public String dashboardTools(Model model, Principal principal) {
        return getRelevantAsset("Tools", principal, model);
    }

    @GetMapping("/dashboard/stationery")
    public String dashboardStationery(Model model, Principal principal) {
        return getRelevantAsset("Stationery", principal, model);
    }

    @GetMapping("/dashboard/support")
    public String dashboardSupport(Model model, Principal principal) {
        return getRelevantAsset("Support", principal, model);
    }

    @GetMapping("/dashboard/others")
    public String dashboardOther(Model model, Principal principal) {
        return getRelevantAsset("Others", principal, model);
    }

    public String getRelevantAsset(String assetName, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = (Users) model.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if ("Administrator".equalsIgnoreCase(user.getRoleId().getRoleName())) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("hideNavbar", true);
        model.addAttribute("showDashboardLayout", true);
        model.addAttribute("activeTab", assetName.toLowerCase());

        // Identify assets that are currently requested with no return date
        Set<Integer> unavailableAssetIds = requestRepository.findAll().stream()
                .filter(r -> r.getDateIn() == null || r.getDateIn().trim().isEmpty())
                .map(r -> r.getAssetId().getAssetId())
                .collect(Collectors.toSet());

        // Fetch relevant assets based on the category
        Categories category = categoryRepository.findAll().stream()
                .filter(c -> c.getCategoryName().equalsIgnoreCase(assetName))
                .findFirst()
                .orElse(null);

        if (category != null) {
            // Only show assets that are in the specified category, NOT currently checked out, AND NOT removed
            List<Assets> availableAssets = assetRepository.findAll().stream()
                    .filter(a -> a.getCategoryId().getCategoryId().equals(category.getCategoryId()))
                    .filter(a -> !unavailableAssetIds.contains(a.getAssetId()))
                    .filter(a -> a.getRemoved() == null || !a.getRemoved()) // Filter out removed assets
                    .filter(a -> a.getConditionId() != null && "Working".equalsIgnoreCase(a.getConditionId().getConditionName())) // Filter by Working condition
                    .collect(Collectors.toList());
            model.addAttribute("assets", availableAssets);
        } else {
            model.addAttribute("assets", List.of());
        }

        return "dashboard_"+assetName.toLowerCase();
    }

    @PostMapping("/dashboard/request-asset")
    public String requestAsset(@RequestParam Integer assetId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = (Users) model.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/dashboard";
        }

        Assets asset = assetRepository.findById(assetId).orElse(null);
        String redirectTarget = "/dashboard/electronics";
        if (asset != null && asset.getCategoryId() != null &&
            "Rooms".equalsIgnoreCase(asset.getCategoryId().getCategoryName())) {
            redirectTarget = "/dashboard/rooms";
        }

        // Check if asset is null or has been removed
        if (asset == null || (asset.getRemoved() != null && asset.getRemoved())) {
            redirectAttributes.addFlashAttribute("error", "Asset not found or unavailable.");
            return "redirect:/dashboard";
        }

        // Re-verify availability to prevent double-requesting
        boolean isAlreadyRequested = requestRepository.findAll().stream()
                .anyMatch(r -> r.getAssetId().getAssetId().equals(assetId) && (r.getDateIn() == null || r.getDateIn().trim().isEmpty()));

        if (isAlreadyRequested) {
            redirectAttributes.addFlashAttribute("error", "Asset is currently unavailable.");
            return "redirect:" + redirectTarget;
        }

        // Create a new request
        Request newRequest = new Request();
        newRequest.setAssetId(asset);
        newRequest.setUserId(user);
        newRequest.setDateOut(LocalDate.now().toString());
        newRequest.setTimeOut(LocalTime.now().withNano(0).toString());


        Status pendingStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Pending"))
                .findFirst()
                .orElse( null);

        if (pendingStatus == null) {
            pendingStatus = new Status();
            pendingStatus.setStatusName("Pending");
            statusRepository.save(pendingStatus);
        }

        newRequest.setStatusId(pendingStatus);

        Condition workingCondition = conditionRepository.findAll().stream()
                .filter(c -> c.getConditionName().equalsIgnoreCase("Working"))
                .findFirst()
                .orElse(null);

        if (workingCondition == null) {
            workingCondition = new Condition();
            workingCondition.setConditionName("Working");
            conditionRepository.save(workingCondition);
        }

        newRequest.setConditionId(workingCondition);

        requestRepository.save(newRequest);
        logger.info("Asset with ID: {}, requested by: {}", assetId, user.getUsername());
        redirectAttributes.addFlashAttribute("success", "Request for " + asset.getItem() + " submitted successfully! Awaiting admin approval.");
        return "redirect:" + redirectTarget;
    }

    @PostMapping("/dashboard/cancel-request")
    public String cancelRequest(@RequestParam Integer requestId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
            return "redirect:/dashboard";
        }

        Users user = (Users) model.getAttribute("user");
        if (user == null || !request.getUserId().getUsername().equals(user.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized action.");
            return "redirect:/dashboard";
        }

        if (!"Pending".equalsIgnoreCase(request.getStatusId().getStatusName())) {
            redirectAttributes.addFlashAttribute("error", "Only pending requests can be canceled.");
            return "redirect:/dashboard";
        }

        Status canceledStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Canceled"))
                .findFirst()
                .orElse(null);

        if (canceledStatus == null) {
            canceledStatus = new Status();
            canceledStatus.setStatusName("Canceled");
            statusRepository.save(canceledStatus);
        }

        request.setStatusId(canceledStatus);
        request.setDateIn(LocalDate.now().toString());
        request.setTimeIn(LocalTime.now().withNano(0).toString());

        requestRepository.save(request);
        logger.info("Request with ID: {}, canceled by: {}", requestId, user.getUsername());
        redirectAttributes.addFlashAttribute("success", "Request for " + request.getAssetId().getItem() + " has been canceled.");
        return "redirect:/dashboard";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("hideNavbar", false);
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userRepository.findByEmail(email).ifPresentOrElse(user -> {
            // send email
            String token = UUID.randomUUID().toString();

            String resetLink = "http://localhost:8080/reset-password?token=" + token;
            String subject = "Password Reset Request";
            String body = "Hello " + user.getFname() + ",\n\n" +
                    "You requested a password reset. Please click the link below to reset your password:\n" +
                    resetLink + "\n\n" +
                    "If you did not request this, please ignore this email.";
            
            try {
                emailService.sendEmail(user.getEmail(), subject, body);
                logger.info("Password reset email sent to: {}", email);
            } catch (Exception e) {
                logger.error("Failed to send password reset email to: {}", email, e);
            }

            redirectAttributes.addFlashAttribute("success", "If an account with that email exists, a password reset link has been sent.");
        }, () -> {
            logger.warn("Password reset attempted for non-existent email: {}", email);
            redirectAttributes.addFlashAttribute("success", "If an account with that email exists, a password reset link has been sent.");
        });
        return "redirect:/login";
    }
}
