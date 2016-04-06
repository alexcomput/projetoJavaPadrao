/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

import br.gov.to.secad.aede.domain.Perfil;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author alex.santos
 */
public interface IPerfilRepository extends JpaRepository<Perfil, Serializable>, JpaSpecificationExecutor {

    public final static String FIND_BY_ALL = "SELECT p FROM Perfil p";

    public final static String FIND_BY_ID = "SELECT p FROM Perfil p"
            + " WHERE p.id = :id";

    @Query(FIND_BY_ALL)
    public ArrayList<Perfil> listarPerfil();

    @Query(FIND_BY_ID)
    public Perfil findPerfilID(@Param(value = "id") Integer idPerfil);

}
