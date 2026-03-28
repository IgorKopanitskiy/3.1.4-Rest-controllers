package com.kopanitskiy.security.services;

import com.kopanitskiy.security.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;


public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void saveUser(User user, List<Long> roles);

    User getUserById(Long id);

    void deleteUserById(Long id);

    void updateUser(Long id, User userUpdate, List<Long> roles);

    @Override
    UserDetails loadUserByUsername(String username);
}
