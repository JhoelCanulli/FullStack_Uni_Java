package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.model.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
	 public Image findByLink(String link);
	 public boolean existsByLink(String filePath);

}
