/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.ergon.repository;

import br.gov.to.secad.ergon.domain.Servidor;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wellyngton.santos Repositório para a classe Funcionário, responsável
 * pelas transações com o BD que tiverem essa classe como objeto.
 */
public interface IServidorRepository extends JpaRepository<Servidor, Serializable> {

    /**
     * Atributo estático responsável por armazenar uma query que seleciona um
     * funcionário através do cpf.
     */
    public final static String FIND_BY_CPF = "SELECT f "
            + "FROM Servidor f "
            + "WHERE f.cpf = :cpf";

    public final static String FIND_BY_CPF_ORGAO = "SELECT f "
            + "FROM Servidor f "
            + "WHERE f.cpf = :cpf AND f.orgaoId = :orgaoId";

    public final static String FIND_BY_NUM_FUNC = "SELECT f "
            + "FROM Servidor f "
            + "WHERE f.numfunc = :numfunc AND f.numvinc = :numvinc";

    public final static String FIND_SERVIDOR_POR_CPF = "SELECT * "
            + " FROM C_DTI.VW_SERVIDOR_ATIVO "
            + " WHERE cpf = ? "
            + " AND dtvac IS NULL";

    /**
     * Método que recebe como argumento uma string cpf de um suposto funcionário
     * para que a interface faça uma busca.
     *
     * @param cpf
     * @return
     */
    @Transactional(timeout = 10)
    @Query(FIND_BY_CPF)
    public Servidor findByCpf(@Param("cpf") Number cpf);

    @Query(FIND_BY_CPF_ORGAO)
    public Servidor findByCpfOrgao(@Param("cpf") Number cpf, @Param("orgaoId") Integer orgaoId);

    @Query(FIND_BY_NUM_FUNC)
    public Servidor findByNumfunc(@Param("numfunc") Integer numfunc, @Param("numvinc") Integer numvinc);

    @Query(FIND_BY_NUM_FUNC)
    public Servidor findByNumfuncNunvinc(@Param("numfunc") Integer numfunc, @Param("numvinc") Integer numvinc);

    @Query(value = FIND_SERVIDOR_POR_CPF, nativeQuery = true)
    public Servidor findPorCpf(Long cpf);

}
