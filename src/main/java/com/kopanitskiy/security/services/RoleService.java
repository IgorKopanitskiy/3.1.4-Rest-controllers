package com.kopanitskiy.security.services;


import com.kopanitskiy.security.entities.Role;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles();

    public List<Role> getRolesByIds(List<Long> roleIds);


}
