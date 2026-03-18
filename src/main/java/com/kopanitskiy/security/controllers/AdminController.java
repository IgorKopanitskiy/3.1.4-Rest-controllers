package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.services.RoleServiceImpl;
import com.kopanitskiy.security.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
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
    public String showAll(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "adminView";
    }


    @GetMapping("/addNewUser")
    public String addNew(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "addNewView";
    }


    @PostMapping("/saveUser")
    public String save(@ModelAttribute("user") User user,
                       @RequestParam(required = false) List<Long> roles) {
        userService.saveUser(user, roles); // Передаем роли в метод сервиса
        return "redirect:/admin/showAll";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/showAll";
    }

    @GetMapping("/updateUser/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id); // Получаем пользователя по ID
        List<Role> allRoles = roleService.getAllRoles(); // Получаем все роли
        model.addAttribute("user", user); // Добавляем пользователя в модель
        model.addAttribute("allRoles", allRoles); // Добавляем список ролей в модель
        return "addNewView"; // Возвращаем представление для обновления пользователя
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(required = false) List<Long> roles) {

        userService.updateUser(id, user, roles); // Обновляем пользователя
        return "redirect:/admin/showAll"; // Перенаправляем на страницу со всеми пользователями
    }


}

