package it.uniroma3.siw.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.dto.UserDTO;
import it.uniroma3.siw.mapper.UserConverter;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Role;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ChefRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Chef user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user; 
    }

    public Chef registerNewUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        userDTO.setRole(Role.REGISTERED_COOK);
        Chef user = UserConverter.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public boolean authenticate(String username, String password) {
        Optional<Chef> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
        	Chef user = userOptional.get();
            String hashedPassword = user.getPassword();
            return passwordEncoder.matches(password, hashedPassword);
        }
        return false;
    }

    public Optional<Chef> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


