package it.uniroma3.siw.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.repository.CredentialRepository;
import it.uniroma3.siw.dtl.CredentialDTL;
import it.uniroma3.siw.mapper.UserConverter;
import it.uniroma3.siw.model.Credential;
import it.uniroma3.siw.model.Role;

@Service
public class CredentialService implements UserDetailsService {

    @Autowired
    private CredentialRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user; 
    }

    public Credential registerNewUser(CredentialDTL userDTL) {
        if (userRepository.existsByUsername(userDTL.getUnam())) {
            throw new RuntimeException("Username is already taken");
        }
        userDTL.setRole(Role.REGISTERED_COOK);
        Credential user = UserConverter.toEntity(userDTL);
        user.setPassword(passwordEncoder.encode(userDTL.getPwrd()));
        return userRepository.save(user);
    }

    public boolean authenticate(String username, String password) {
        Optional<Credential> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
        	Credential user = userOptional.get();
            String hashedPassword = user.getPassword();
            return passwordEncoder.matches(password, hashedPassword);
        }
        return false;
    }

    public Optional<Credential> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


