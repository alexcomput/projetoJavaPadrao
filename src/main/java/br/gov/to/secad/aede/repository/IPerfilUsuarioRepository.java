/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

 import br.gov.to.secad.aede.domain.PerfilUsuario;
import br.gov.to.secad.aede.domain.Usuario;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface que estende a classe JpaRepository, responsável por persistir ações
 * referentes a classe Fator.
 *
 * @see Formulario
 *
 * @author wellyngton.santos
 */
public interface IPerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Serializable>, JpaSpecificationExecutor {

    /**
     * Atributo estático responsável por armazenar uma query que seleciona um
     * fator através do nome, ou seja, sua descrição.
     */
    public final static String FIND_BY_CPF = "SELECT p "
            + "FROM Permissao p, PerfilUsuarioOrgao pu "
            + "WHERE p.usuario = :usuario AND  pu.permissaoUsuario.id= p.id AND pu.excluido='N' ";

    public final static String FIND_BY_CPF_USUARIO = "SELECT p "
            + "FROM Permissao p "
            + "WHERE p.usuario = :usuario";

    /**
     * Método que recebe como argumento uma string nome de um suposto fator para
     * que a interface faça uma busca.
     */
    @Query(FIND_BY_CPF)
    public List<PerfilUsuario> findByUsuario(@Param("usuario") Usuario usuario);

    @Query(FIND_BY_CPF_USUARIO)
    public PerfilUsuario findByCpfUuario(@Param("usuario") Usuario usuario);

    @Query(value = "SELECT * FROM aede.perfil_usuario WHERE cpf = ?1 AND perfil_id= ?2 ", nativeQuery = true)
    public PerfilUsuario findByCpfPerfil(  String cpf,  Integer idPerfil);

    @Query(value = "SELECT * FROM aede.perfil_usuario WHERE cpf = ?1 AND perfil_id= ?2 AND lotado_orgao_id = ?3 " , nativeQuery = true)
    public PerfilUsuario findByCpfPerfilOrgao(  String cpf,  Integer idPerfil, Integer idOrgao);
}
