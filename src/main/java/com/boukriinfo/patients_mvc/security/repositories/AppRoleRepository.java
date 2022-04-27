package com.boukriinfo.patients_mvc.security.repositories;

import com.boukriinfo.patients_mvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String rolename);
}
