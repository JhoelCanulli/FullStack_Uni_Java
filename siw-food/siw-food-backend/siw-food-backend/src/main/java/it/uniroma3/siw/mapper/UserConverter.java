package it.uniroma3.siw.mapper;

import it.uniroma3.siw.dtl.CredentialDTL;
import it.uniroma3.siw.model.Credential;

public class UserConverter {
    public static CredentialDTL toDTL(Credential user) {
        CredentialDTL dtl = new CredentialDTL();
        dtl.setUnam(user.getUsername());
        dtl.setPwrd(user.getPassword());  
        dtl.setRole(user.getRole());
        return dtl;
    }

    public static Credential toEntity(CredentialDTL dtl) {
    	Credential user = new Credential();
        user.setUsername(dtl.getUnam());
        user.setPassword(dtl.getPwrd());
        user.setRole(dtl.getRole());
        return user;
    }
}
