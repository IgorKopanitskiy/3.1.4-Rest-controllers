package com.kopanitskiy.security.repositories;

import com.kopanitskiy.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
