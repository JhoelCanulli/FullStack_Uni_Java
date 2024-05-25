package it.uniroma3.siw.filter;

import it.uniroma3.siw.service.ChefDetailsServiceImp;
import it.uniroma3.siw.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ChefDetailsServiceImp chefDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Token: " + token);
        String username = jwtService.extractUsername(token);
        System.out.println("Username extracted from token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails chefDetails = chefDetailsService.loadUserByUsername(username);
            System.out.println("UserDetails loaded: " + chefDetails.getUsername());

            if (jwtService.isValid(token, chefDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        chefDetails, null, chefDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication set in context");
            } else {
                System.out.println("Token is not valid");
            }
        } else {
            System.out.println("Username is null or context already has authentication");
        }
        filterChain.doFilter(request, response);
    }
}

