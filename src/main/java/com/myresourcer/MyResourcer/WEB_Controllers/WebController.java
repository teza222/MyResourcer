package com.myresourcer.MyResourcer.WEB_Controllers;

import com.myresourcer.MyResourcer.DTOs.DTO_Users;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import com.myresourcer.MyResourcer.Services.GET_ServiceManager;
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
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
    private PasswordEncoder passwordEncoder;

    // This method will run before any @RequestMapping method and add the 'user' object to the model
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
        model.addAttribute("roles", serviceManager.getAllRoles());
        model.addAttribute("departments", serviceManager.getAllDepartments());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute DTO_Users user) {
        Roles role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        Departments department = departmentRepository.findById(user.getDepartmentId()).orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setRoleId(role);
        newUser.setDepartmentId(department);
        newUser.setFlag(1); // Active user

        userRepository.save(newUser);
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
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
        model.addAttribute("activeTab", "home");
        
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

        Assets asset = new Assets();
        asset.setItem(item);
        asset.setSerialNumber(serialNumber);
        asset.setSpecifications(specifications);
        asset.setCategoryId(category);
        asset.setMobile(isMobile);
        asset.setRemoved(false); // New assets are not removed by default

        assetRepository.save(asset);
        redirectAttributes.addFlashAttribute("success", "Asset '" + item + "' added successfully!");
        return "redirect:/admin/manage-assets";
    }

    @PostMapping("/admin/delete-asset")
    public String deleteAsset(@RequestParam Integer assetId, RedirectAttributes redirectAttributes) {
        Assets asset = assetRepository.findById(assetId).orElse(null);
        if (asset != null) {
            asset.setRemoved(true); // Soft delete: set isRemoved to true
            assetRepository.save(asset);
            redirectAttributes.addFlashAttribute("success", "Asset '" + asset.getItem() + "' has been removed.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Asset not found.");
        }
        return "redirect:/admin/manage-assets";
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

        // Re-verify availability (prevent double-requesting)
        boolean isAlreadyRequested = requestRepository.findAll().stream()
                .anyMatch(r -> r.getAssetId().getAssetId().equals(assetId) && (r.getDateIn() == null || r.getDateIn().trim().isEmpty()));

        if (isAlreadyRequested) {
            redirectAttributes.addFlashAttribute("error", "Asset is currently unavailable.");
            return "redirect:" + redirectTarget;
        }

        Request newRequest = new Request();
        newRequest.setAssetId(asset);
        newRequest.setUserId(user);
        newRequest.setDateOut(LocalDate.now().toString());
        newRequest.setTimeOut(LocalTime.now().withNano(0).toString());

        Status pendingStatus = statusRepository.findAll().stream()
                .filter(s -> s.getStatusName().equalsIgnoreCase("Pending"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Pending status not found."));
        newRequest.setStatusId(pendingStatus);

        Condition workingCondition = conditionRepository.findAll().stream()
                .filter(c -> c.getConditionName().equalsIgnoreCase("Working"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Working condition not found."));
        newRequest.setConditionId(workingCondition);

        requestRepository.save(newRequest);
        redirectAttributes.addFlashAttribute("success", "Request for " + asset.getItem() + " submitted successfully!");
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
        redirectAttributes.addFlashAttribute("success", "Request for " + request.getAssetId().getItem() + " has been canceled.");
        return "redirect:/dashboard";
    }
}
