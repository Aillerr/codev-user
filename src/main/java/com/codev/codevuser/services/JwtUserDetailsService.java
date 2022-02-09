package com.codev.codevuser.services;

import java.util.ArrayList;
import java.util.Optional;

import com.codev.codevuser.entities.UserEntity;
import com.codev.codevuser.jwtClasses.JwtTokenUtil;
import com.codev.codevuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository utilisateurRepository;

    // on initialise
    @Autowired
    public JwtUserDetailsService(UserRepository UtilisateurRepository, JwtTokenUtil JwtTokenUtil) {
        this.utilisateurRepository = UtilisateurRepository;
    }

    public Optional<UserEntity> getUtilisateurFromUsername(String username) {
        UserEntity unUtilisateur = utilisateurRepository.searchLogin(username);
        return Optional.ofNullable(unUtilisateur);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity unUtilisateur = null;
        unUtilisateur = utilisateurRepository.searchLogin(username);
        if (unUtilisateur != null) {
            return new User(unUtilisateur.getLogin(), unUtilisateur.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserEntity loadFullUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity unUtilisateur = null;
        unUtilisateur = utilisateurRepository.searchLogin(username);
        if (unUtilisateur != null) {
            return unUtilisateur;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public void register(UserEntity util) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(util.getPassword());
        util.setPassword(pass);
        utilisateurRepository.save(util);
    }
}