package com.kopanitskiy.security.services;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.repositories.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Роль пользователя с id: " + id + "не найдена"));
    }

}
