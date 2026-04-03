package com.myresourcer.MyResourcer.WEB_Controllers;

import com.myresourcer.MyResourcer.Models.Categories;
import com.myresourcer.MyResourcer.Services.DELETE_ServiceManager;
import com.myresourcer.MyResourcer.Services.GET_ServiceManager;
import com.myresourcer.MyResourcer.Services.POST_ServiceManager;
import com.myresourcer.MyResourcer.Services.PUT_ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/categories")
public class CategoryWebController {

    @Autowired
    private GET_ServiceManager getService;

    @Autowired
    private POST_ServiceManager postService;

    @Autowired
    private PUT_ServiceManager putService;

    @Autowired
    private DELETE_ServiceManager deleteService;


    // List all categories
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", getService.getAllCategories());
        return "categories/list";
    }

    // Show add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Categories());
        return "categories/form";
    }

    // Save new category
    @PostMapping("/save")
    public String save(@ModelAttribute("category") Categories category) {
        postService.addCategory(category);
        return "redirect:/web/categories";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Categories category = getService.getAllCategories().stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        model.addAttribute("category", category);
        return "categories/form";
    }

    // Update existing category
    @PutMapping("/update")
    public String update(@PathVariable Integer id, @RequestBody Categories category) {
        putService.updateCategory(id, category);
        return "redirect:/web/categories";
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        deleteService.deleteCategory(id);
        return "redirect:/web/categories";
    }
}