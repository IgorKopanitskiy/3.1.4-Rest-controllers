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

    @Override
    @Transactional
    public void saveUser(User user, List<Long> roles) {
        if (roles != null) {
            Set<Role> roleSet = new HashSet<>();
            for (Long roleId : roles) {
                Role role = roleService.getRoleById(roleId);
                if (role == null) {
                    throw new IllegalArgumentException("Роль с id: " + roleId + " не найдена");
                }
                roleSet.add(role);
            }
            user.setRoles(roleSet);
        }

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
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userDao.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User userUpdate, List<Long> roles) {
        User user = getUserById(id);
        user.setUsername(userUpdate.getUsername());
        user.setSurname(userUpdate.getSurname());
        user.setAge(userUpdate.getAge());
        user.setCitizenship(userUpdate.getCitizenship());
        user.setPassword(userUpdate.getPassword());

        // Преобразуем List<Long> в Set<Role>
        if (roles != null) {
            Set<Role> roleSet = new HashSet<>();
            for (Long roleId : roles) {
                Role role = roleService.getRoleById(roleId);
                if (role == null) {
                    throw new IllegalArgumentException("Роль с id: " + roleId + " не найдена");
                }
                roleSet.add(role);
            }
            user.setRoles(roleSet);
        }

        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с именем " + username + "не найден");
        }

        return user;
    }

}

