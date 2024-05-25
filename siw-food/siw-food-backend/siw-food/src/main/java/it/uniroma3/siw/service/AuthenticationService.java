package it.uniroma3.siw.service;

import it.uniroma3.siw.model.AuthenticationResponse;
import it.uniroma3.siw.model.Token;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.TokenRepository;
import it.uniroma3.siw.repository.ChefRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

	@Autowired
    private ChefRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private TokenRepository tokenRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationResponse register(Chef request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null,"User already exist");
        }

        Chef chef = new Chef();
        chef.setName(request.getName());
        chef.setUsername(request.getUsername());
        chef.setPassword(passwordEncoder.encode(request.getPassword()));

        chef.setRole(request.getRole());
        
        chef = repository.save(chef);

        String accessToken = jwtService.generateAccessToken(chef);
        String refreshToken = jwtService.generateRefreshToken(chef);

        saveUserToken(accessToken, refreshToken, chef);

        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful");

    }

    public AuthenticationResponse authenticate(Chef request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Chef chef = repository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(chef);
        String refreshToken = jwtService.generateRefreshToken(chef);

        revokeAllTokenByUser(chef);
        saveUserToken(accessToken, refreshToken, chef);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");

    }
    private void revokeAllTokenByUser(Chef chef) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByChef(chef.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, Chef chef) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setChef(chef);
        tokenRepository.save(token);
    }

    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Estrai il token dall'intestazione di autorizzazione
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);

        // Estrai lo username dal token
        String username = jwtService.extractUsername(token);

        // Controlla se l'utente esiste nel database
        Chef chef = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No user found"));

        // Controlla se il token è valido
        if (jwtService.isValidRefreshToken(token, chef)) {
            // Genera il token di accesso e di refresh
            String accessToken = jwtService.generateAccessToken(chef);
            String refreshToken = jwtService.generateRefreshToken(chef);

            revokeAllTokenByUser(chef);
            saveUserToken(accessToken, refreshToken, chef);

            // Restituisci una risposta con il nuovo token
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(accessToken, refreshToken, "New token generated");
            return ResponseEntity.ok(authenticationResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}