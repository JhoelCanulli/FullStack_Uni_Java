package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
    select t from Token t inner join Chef c on t.chef.id = c.id
    where t.chef.id = :chefId and t.loggedOut = false
    """)
    List<Token> findAllAccessTokensByChef(@Param("chefId") long chefId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);
}
