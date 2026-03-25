package com.kopanitskiy.security.services;


import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.repositories.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleServiceImpl roleService;


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    //Приватный метод, чтобы не дублировать код
//    private void setUserRoles(User user, List<Long> roles) {
//        List<Role> roleList = roleService.getRolesByIds(roles);
//
//        Set<Role> roleSet = new HashSet<>(roleList);
//        user.setRoles(roleSet);
//    }

    @Override
    @Transactional
    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id: " + id + "не найден"));
    }


    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User userUpdate) {
        userDao.save(userUpdate);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + username + "не найден"));
    }
}

