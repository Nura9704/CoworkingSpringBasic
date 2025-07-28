package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exceptions.*;
import org.example.manager.CoworkingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CoworkingController {

    @Autowired
    private CoworkingManager manager;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("spaces", manager.getSpaces());
        return "index";
    }

    @GetMapping("/addUserForm")
    public String showAddUserForm() {
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String name, @RequestParam boolean isAdmin, Model model) {
        try {
            manager.addUser(name, isAdmin);
            model.addAttribute("success", "User added successfully");
        } catch (InvalidInputException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "addUser";
    }

    @GetMapping("/spaces")
    public String viewSpaces(Model model) {
        model.addAttribute("spaces", manager.getSpaces());
        return "spaces";
    }
}
