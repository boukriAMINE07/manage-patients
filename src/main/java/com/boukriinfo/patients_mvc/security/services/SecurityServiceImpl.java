package com.boukriinfo.patients_mvc.security.services;

import com.boukriinfo.patients_mvc.security.entities.AppRole;
import com.boukriinfo.patients_mvc.security.entities.AppUser;
import com.boukriinfo.patients_mvc.security.repositories.AppRoleRepository;
import com.boukriinfo.patients_mvc.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j  //utiliser pour loger des informations
@AllArgsConstructor
@Transactional //tous les methodes transactionnelles et que spring va gerer les transactions
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser saveNewUser(String name, String password, String rePassword) {
        if(!password.equals(rePassword)) throw new RuntimeException("Password not match !!");
        String hashedPWD=passwordEncoder.encode(password);
        AppUser appUser=new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(name);
        appUser.setPassword(hashedPWD);
        appUser.setActive(true);
        AppUser savedAppUser=appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole!=null) throw new RuntimeException("Role"+roleName+" Already exist !");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole=appRoleRepository.save(appRole);
        return savedAppRole;
    }



    @Override
    public void removeRoleFromUser(String userName, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(userName);
        if(appUser==null) throw new RuntimeException("Username"+userName+" not found !");
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role"+roleName+" not found !");

        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
         AppUser appUser=appUserRepository.findByUsername(userName);
        if(appUser==null) throw new RuntimeException("Username"+userName+" not found !");
         AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role"+roleName+" not found !");

        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String userName) {

        return appUserRepository.findByUsername(userName);
    }
}
