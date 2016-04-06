/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

import br.gov.to.secad.aede.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface que estende a classe JpaRepository, responsável por persistir ações
 * referentes a classe Usuario.
 *
 * @see Usuario
 *
 * @author wellyngton.santos
 */
public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable>, JpaSpecificationExecutor {

    /**
     * Atributo estático responsável por armazenar uma query que seleciona um
     * usuário através do cpf.
     */
    public final static String FIND_BY_CPF = "SELECT u "
            + "FROM Usuario u "
            + "WHERE u.cpf = :cpf";
    public final static String FIND_BY_CPF_SENHA = "SELECT u "
            + "FROM Usuario u "
            + " WHERE u.cpf = :cpf AND u.senhahash= :senha";
    /**
     * Atributo estático responsável por armazenar uma query que seleciona um
     * usuário através do email.
     */
    public final static String FIND_BY_EMAIL = "SELECT u "
            + "FROM Usuario u "
            + "WHERE u.email = :email";
    /**
     * Atributo estático responsável por armazenar uma query que seleciona um
     * usuário através do email.
     */
    public final static String FIND_BY_CPF_LIKE = "SELECT u FROM Usuario u WHERE  u.cpf like :cpf%";

    /**
     * Método que recebe como argumento uma string cpf de um suposto usuário
     * para que a interface faça uma busca.
     */
    @Query(FIND_BY_CPF)
    public Usuario findByCpf(@Param("cpf") String cpf);

    @Query(FIND_BY_CPF_SENHA)
    public Usuario findByCpfSenha(@Param("cpf") String cpf, @Param("senha") String senha);

    /**
     * Método que recebe como argumento uma string email de um suposto usuário
     * para que a interface faça uma busca.
     */
    @Query(FIND_BY_EMAIL)
    public Usuario findByEmail(@Param("email") String email);

    @Query(FIND_BY_CPF_LIKE)
    public ArrayList<Usuario> findByCpfLike(@Param("cpf") String cpf);
}
