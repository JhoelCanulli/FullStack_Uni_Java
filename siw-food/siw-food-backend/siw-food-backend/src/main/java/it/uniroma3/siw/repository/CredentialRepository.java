package it.uniroma3.siw.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByUsername(String username);
    boolean existsByUsername(String username);
}
