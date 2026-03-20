package com.kopanitskiy.security.services;

import com.kopanitskiy.security.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;


public interface UserService extends UserDetailsService {

    public List<User> getAllUsers();

    public void saveUser(User user, List<Long> roles);

    public User getUserById(Long id);

    public void deleteUser(Long id);

    public void updateUser(Long id, User userUpdate, List<Long> roles);

    @Override
    UserDetails loadUserByUsername(String email);
}
