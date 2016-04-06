package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.Usuario;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe que fornece o serviço de autenticação para aplicação.
 *
 * @author wellyngton.santos
 */
@Service
public class AuthenticationService {

    /**
     * Atributo manager que é utilizado nas operações de autenticação no
     * sistema.
     */
    @Autowired
    @Qualifier(value = "authenticationManager")
    private AuthenticationManager authenticationManager;

    /**
     * Método que cria o token de autenticação do usuário na aplicação, usando
     * métodos do springframework security. Argumentos: username e password
     * (senha já encriptada)
     */
    //public boolean login(String username, String password) {
    public boolean login(Usuario usuario) {
        try {
            /**
             * Cria-se o token para verificar autenticidade com as informações
             * fornecidas nos argumentos
             */
            //UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
             
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
            //System.out.println(password+" + "+ username);
            /**
             * Instância de autenticação recebe um retorno com o token
             * anteriormente criado.
             */ 
             
            Authentication authenticate = authenticationManager.authenticate(token);

            /**
             * Se a autenticação aconteceu é setado no contexto a instÂncia de
             * um objeto de autenticação. Conferindo assim acesso ao usuário.
             */
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().
                        setAuthentication(authenticate);
                return true;
            }
        } catch (AuthenticationException e) {
        }
        return false;
    }

    /**
     * Método para efetuar logout do sistema. É tornado nula a variável de
     * contexto que tornou o usuário autenticado.
     */
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        invalidateSession();
    }

    /**
     * Método responsável por verificar qual o usuário está logado no momento.
     */
    public Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
    }

    /**
     * Método que torna inválido a sessão atual. Esse método é chamado no dentro
     * do método logout().
     */
    private void invalidateSession() {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
    }

}
