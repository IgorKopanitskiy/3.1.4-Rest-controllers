package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.exceptionHandling.UserIncorrectData;
import com.kopanitskiy.security.exceptionHandling.UsernameExistsException;
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


    @GetMapping("/users")
    public ResponseEntity<List<User>> showAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }



    @PostMapping("/users")
    public ResponseEntity<UserIncorrectData> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UsernameExistsException exception) {
            throw new UsernameExistsException("Пользователь с таким логином уже существует");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserIncorrectData> delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(new UserIncorrectData("Пользователь c id: " + id + " удален"), HttpStatus.OK);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<UserIncorrectData> update(@PathVariable("id") long id,
                                                  @Valid @RequestBody User user,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectData(error), HttpStatus.BAD_REQUEST);
        }
        try {
            String oldPassword = userService.getUserById(id).getPassword();
            if (oldPassword.equals(user.getPassword())) {
                user.setPassword(oldPassword);
                userService.updateUser(user);
            } else {
                userService.saveUser(user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UsernameExistsException exception) {
            throw new UsernameExistsException("Пользователь с таким логином уже существует");
        }
    }

    //Ошибки валидации
    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }

}