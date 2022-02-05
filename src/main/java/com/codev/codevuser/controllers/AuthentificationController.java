package com.codev.codevuser.controllers;

import com.codev.codevuser.entities.UserEntity;
import com.codev.codevuser.jwtClasses.JwtTokenUtil;
import com.codev.codevuser.repositories.UserRepository;
import com.codev.codevuser.services.JwtUserDetailsService;
import com.codev.codevuser.validations.ValidAjoutUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RequestMapping("/authentification")
@RestController
@CrossOrigin
public class AuthentificationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    private UserRepository utilisateurRepository;

    @Autowired
    public AuthentificationController(UserRepository UtilisateurRepository) {
        this.utilisateurRepository = UtilisateurRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserEntity util) {
        Optional<UserDetails> userDetails = appelAuthentication(util.getLogin(), util.getPassword());
        Optional<UserEntity> _util = userDetailsService.getUtilisateurFromUsername(util.getLogin());
        if(userDetails.isPresent() && _util.isPresent()){
            final String token = jwtTokenUtil.generateToken(userDetails.get());

            // On renvoit le token
            HashMap<String, String> json = new HashMap<>();
            json.put("token", token);

            return ResponseEntity.ok(json);
        }else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated({ValidAjoutUtilisateur.class}) @RequestBody UserEntity util){
        Optional<UserEntity> _util = userDetailsService.getUtilisateurFromUsername(util.getLogin());
        if(_util.isEmpty()){
            userDetailsService.register(util);

            return ResponseEntity.ok().build();
        }else
            return ResponseEntity.unprocessableEntity().build();
    }

    private Optional<UserDetails> appelAuthentication(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return Optional.ofNullable(userDetails);
    }


}
