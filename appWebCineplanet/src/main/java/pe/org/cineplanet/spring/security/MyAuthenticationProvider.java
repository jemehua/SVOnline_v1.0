/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.cineplanet.spring.security;


import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.UsuarioService;
import pe.org.cineplanet.util.Constantes;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 
 * @author Hever Pumallihua
 */
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UsuarioService usuarioService;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		Authentication auth = null;
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		List<Usuario> listUsuario = usuarioService.getListByUsername(username);
		
		for(Usuario usr : listUsuario){
			/*String plainText="";
			try {
				plainText = Seguridad.decrypt(usr.getPassword());
			} catch (Exception e) {
			}*/
			
			if (usr.getClave().equalsIgnoreCase(password)) {

				List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
				
				if(usr.getRol().getIdRol() == Constantes.ROL_ADM)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
				if(usr.getRol().getIdRol() == Constantes.ROL_EVA)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_EVALUADOR"));
				if(usr.getRol().getIdRol() == Constantes.ROL_GP)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_GP"));
				if(usr.getRol().getIdRol() == Constantes.ROL_JI)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_JI"));
				if(usr.getRol().getIdRol() == Constantes.ROL_TE)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_TE"));
				if(usr.getRol().getIdRol() == Constantes.ROL_JI_AND_TE)
					grantedAuths.add(new GrantedAuthorityImpl("ROLE_JI_TE"));
				// grantedAuths.add(new SimpleGrantedAuthority("Gerente Público"));

				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.getExternalContext().getSessionMap().put(usr.getUsuario(), usr);

				auth = new UsernamePasswordAuthenticationToken(
						username, password, grantedAuths);
				break;
			} else 
				auth = null;
		}
		
		return auth;
		
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
