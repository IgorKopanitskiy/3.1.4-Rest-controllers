package com.kopanitskiy.security.repositories;

import com.kopanitskiy.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id IN :roleIds")
    List<Role> findByRoleIDs(@Param("roleIds") List<Long> roleIds);
}
