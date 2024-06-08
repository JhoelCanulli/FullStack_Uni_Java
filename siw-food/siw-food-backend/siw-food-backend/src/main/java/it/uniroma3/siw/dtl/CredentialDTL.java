package it.uniroma3.siw.dtl;

import it.uniroma3.siw.model.Role;

public class CredentialDTL {
	
	private String code;
    private String unam;
    private String pwrd;
    private Role role;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUnam() {
		return unam;
	}

	public void setUnam(String unam) {
		this.unam = unam;
	}

	public String getPwrd() {
		return pwrd;
	}

	public void setPwrd(String pwrd) {
		this.pwrd = pwrd;
	}

	public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
