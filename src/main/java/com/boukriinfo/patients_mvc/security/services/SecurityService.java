package com.boukriinfo.patients_mvc.security.services;

import com.boukriinfo.patients_mvc.security.entities.AppRole;
import com.boukriinfo.patients_mvc.security.entities.AppUser;

public interface SecurityService
{
    AppUser saveNewUser(String name,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void removeRoleFromUser(String username,String rolename);
    void addRoleToUser(String userName,String roleName);
    AppUser loadUserByUsername(String userName);

}
