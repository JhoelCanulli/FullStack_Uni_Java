package it.uniroma3.siw.mapper;

import it.uniroma3.siw.dto.UserDTO;
import it.uniroma3.siw.model.Chef;

public class UserConverter {
    public static UserDTO toDTO(Chef user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());  // In genere non si passa la password al client
        dto.setRole(user.getRole());
        return dto;
    }

    public static Chef toEntity(UserDTO dto) {
        Chef user = new Chef();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}
