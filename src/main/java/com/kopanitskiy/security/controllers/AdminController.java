package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.services.RoleServiceImpl;
import com.kopanitskiy.security.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;


    @GetMapping(value = "")
    public String welcome() {
        return "redirect:/admin/showAll";
    }


    @GetMapping("/showAll")
    public String showAll(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "adminView";
    }


    @GetMapping("/addNewUser")
    public String addNew(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "newUserView";
    }


    @PostMapping("/saveUser")
    public String save(@ModelAttribute("user") User user,
                       @RequestParam(required = false) List<Long> roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin/showAll";
    }


    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(required = false) List<Long> roles) {
        System.out.println("Метод post работает с id" + id);
        userService.updateUser(id, user, roles);
        return "redirect:/admin/showAll";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/showAll";
    }
}

