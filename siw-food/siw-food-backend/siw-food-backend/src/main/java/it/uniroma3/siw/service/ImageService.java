package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.ImageRepository;

public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository; 
	
	public Image findByLink(String filePath) {
		return this.imageRepository.findByLink(filePath);
	}
	
	public boolean existsByLink(String filePath) {
		
		return this.imageRepository.existsByLink(filePath);
	}
}
