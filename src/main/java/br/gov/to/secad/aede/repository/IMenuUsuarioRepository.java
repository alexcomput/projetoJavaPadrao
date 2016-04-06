/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

import br.gov.to.secad.aede.domain.Menu;
import br.gov.to.secad.aede.domain.MenuUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author alex.santos
 */
public interface IMenuUsuarioRepository extends JpaRepository<MenuUsuario, Serializable> {

    /**
     * "WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai,
     * ordem) " + "AS ( " + " SELECT mo.icone,mo.id, mo.descricao, mo.url ||
     * mo.url, 0, mo.id_pai, 1 FROM aede.menu as mo" + "	WHERE mo.id_pai is NULL
     * " + " UNION ALL \n" + "	SELECT mn.icone ,mn.id,mn.descricao, mt.url ||
     * \'/\' || mn.url, mt.nivel + 1,mt.id, mn.ordem " + "	FROM aede.menu mn,
     * menu_tree mt " + "	WHERE mn.id_pai = mt.id " + ") " + "SELECT * FROM
     * menu_tree " + "	WHERE nivel > 0 " + "	ORDER BY url, ordem;"
     *
     *
     * WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai, ordem)
     * AS ( SELECT mo.icone,mo.id, mo.descricao, mo.url || mo.url, 0, mo.id_pai,
     * 1 FROM aede.menu as mo	WHERE mo.id_pai is NULL UNION ALL SELECT mn.icone
     * ,mn.id,mn.descricao, mt.url || '/' || mn.url, mt.nivel + 1,mt.id,
     * mn.ordem * FROM aede.menu mn, menu_tree mt , aede.menu_usuario as au ,
     * aede.grupo_usuario as gr WHERE mn.id_pai = mt.id AND au.id_menu = mn.id
     * AND (au.cpf_usuario= '00539232190' OR (gr.id=1 AND au.grupo_id=1)))
     * SELECT DISTINCT * FROM menu_tree WHERE nivel > 0 ORDER BY url, ordem;
     *
     *
     */
    public final static String FIND_BY_ID = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.id = :id AND m.excluido='N'";

    @Query(value = "WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai, ordem) "
            + "AS ( "
            + "  SELECT   mo.icone,mo.id, mo.descricao,  mo.url || mo.url, 0, mo.id_pai,  1  FROM aede.menu as mo"
            + "	WHERE mo.id_pai is NULL "
            + " UNION ALL \n"
            + "	  SELECT  mn.icone ,mn.id,mn.descricao, mt.url || \'/\' || mn.url, mt.nivel + 1,mt.id, mn.ordem "
            + "		FROM aede.menu mn, menu_tree mt , aede.menu_usuario as au , aede.perfil_usuario as gr "
            + "			WHERE mn.id_pai = mt.id  "
            + "                      AND mn.excluido='N' "
            + "                      AND mn.ativo='S' "
            + "                     AND au.menu_id = mn.id "
            + "                     AND (au.cpf_usuario = ?1  OR (gr.id = ?2 AND au.perfil_id = ?2 )))  "
            + "SELECT  DISTINCT * FROM menu_tree "
            + "	WHERE nivel > 0  "
            + "		 ORDER BY url, ordem;", nativeQuery = true)
    ArrayList<Menu> findByMenu(String cpf, Integer idGrupoUsuario);

    // Retorna todos os menus
    @Query(value = "WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai, ordem,ativo)"
            + "      AS ( SELECT mo.icone,mo.id, mo.descricao, mo.url || mo.url, 0, mo.id_pai,1,mo.ativo,mo.excluido,mo.obs"
            + "       FROM aede.menu as mo	"
            + "		WHERE mo.id_pai is NULL UNION ALL"
            + " SELECT mn.icone ,mn.id,mn.descricao, mt.url || '/' || mn.url, mt.nivel + 1,mt.id,   mn.ordem,mn.ativo,mn.excluido,mn.obs"
            + "	FROM aede.menu mn, menu_tree mt "
            + "		WHERE mn.id_pai = mt.id  AND mn.excluido='N' )"
            + "      SELECT DISTINCT * FROM menu_tree WHERE nivel > 0 ORDER BY url, ordem;",
            nativeQuery = true)
    ArrayList<Menu> findAllMenu();

    @Query(value = "WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai, ordem)"
            + "      AS ( SELECT mo.icone,mo.id, mo.descricao, mo.url || mo.url, 0, mo.id_pai,1 "
            + "       FROM aede.menu as mo	"
            + "		WHERE mo.id_pai is NULL UNION ALL"
            + " SELECT mn.icone ,mn.id,mn.descricao, mt.url || '/' || mn.url, mt.nivel + 1,mt.id,   mn.ordem      "
            + "	FROM aede.menu mn, menu_tree mt "
            + "		WHERE mn.id_pai = mt.id  AND mn.excluido='N' )"
            + "      SELECT DISTINCT * FROM menu_tree WHERE nivel = ?1 AND id_pai= ?2 ORDER BY url, ordem;",
            nativeQuery = true)
    public ArrayList<Menu> findNiverMenu(Integer nivel, Integer idPai);

    @Query(FIND_BY_ID)
    public Menu findMenuId(@Param("id") Integer id);

    /*public final static String FIND_BY_IDPerfil = "SELECT mu FROM MenuUsuario mu WHERE mu.perfil.id = :id AND  mu.menu.id= :idMenu";*/

    /*@Query(FIND_BY_IDPerfil)
    public ArrayList<MenuUsuario> findMenuUsuarioIdPerfil(@Param("id") Integer idPerfil, @Param("idMenu") Integer idMenu);*/

    public final static String FIND_BY_IDPerfil = "SELECT mu FROM MenuUsuario mu WHERE mu.perfil.id = :id";

    @Query(FIND_BY_IDPerfil)
    public ArrayList<MenuUsuario> findMenuUsuarioIdPerfil(@Param("id") Integer idPerfil);
}
