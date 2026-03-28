package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.exceptionHandling.UserIncorrectData;
import com.kopanitskiy.security.services.RoleService;
import com.kopanitskiy.security.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleService roleService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<UserIncorrectData> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }

        List<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .toList();

        userService.saveUser(user, roleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/users/{id}")

    public ResponseEntity<UserIncorrectData> update(@PathVariable("id") long id,
                                                    @Valid @RequestBody User user,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }

        List<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .toList();

        userService.updateUser(id, user, roleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    //Ошибки валидации
    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }

}