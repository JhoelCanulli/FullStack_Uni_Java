package it.uniroma3.siw.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import it.uniroma3.siw.dto.UserDTO;
import it.uniroma3.siw.mapper.UserConverter;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Role;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        System.out.println("Attempting to register user: " + userDTO.getUsername());
        try {
            if ("admin".equalsIgnoreCase(userDTO.getUsername())) {
                return ResponseEntity.badRequest().body(null);
            }
            
            Chef user = userService.registerNewUser(userDTO);
            return ResponseEntity.ok(UserConverter.toDTO(user)); 
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Role.ADMIN.toString()))) {
            return "redirect:/admin";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Role.REGISTERED_COOK.toString()))) {
            return "redirect:/cook";
        } else {
            return "redirect:/access-denied";
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> new ResponseEntity<>(UserConverter.toDTO(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}

