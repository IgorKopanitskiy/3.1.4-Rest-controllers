package com.kopanitskiy.security.services;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.repositories.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
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
    public List<Role> getRolesByIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Role> roles = roleDao.findByRoleIDs(roleIds);

        if (roles.isEmpty()) {
            throw new RuntimeException("Роли не найдены для указанных идентификаторов: " + roleIds);
        }

        return roles;
    }

}
