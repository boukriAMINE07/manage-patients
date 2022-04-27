package com.boukriinfo.patients_mvc.security.repositories;

import com.boukriinfo.patients_mvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository  extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
