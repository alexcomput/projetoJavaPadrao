/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

import br.gov.to.secad.aede.domain.Menu;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author alex.santos
 */
public interface IMenuRepository extends JpaRepository<Menu, Serializable> {

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

    /*/ já traz os menus com as urls montadas
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
     + "		 ORDER BY url, ordem;" , nativeQuery = true) */
    @Query(value = "SELECT DISTINCT mo.icone, mo.id, mo.descricao,mo.obs,  mo.url, mo.nivel, mo.id_pai, "
            + " mo.ordem "
            + " FROM aede.menu as mo, aede.menu_usuario mu , aede.perfil_usuario pa\n"
            + "          WHERE mo.id = mu.menu_id AND pa.perfil_id= mu.perfil_id AND mu.perfil_id = ?2   "
            + " AND mo.excluido='N' AND mu.excluido='N' ORDER BY ordem , mo.id_pai; ", nativeQuery = true)
    ArrayList<Menu> findByMenu(String cpf, Integer idGrupoUsuario);

    @Query(value = "SELECT DISTINCT mo.icone, mo.id, mo.descricao,mo.obs,  mo.url, mo.nivel, mo.id_pai, "
            + " mo.ordem  FROM aede.menu as mo, aede.menu_usuario mu , aede.perfil_usuario pa "
            + "          WHERE mo.id = mu.menu_id AND pa.perfil_id= mu.perfil_id  AND mu.perfil_id = ?2   "
            + " AND mo.excluido='N' AND mu.excluido='N' AND mo.nivel=?3 AND mo.id_pai= ?4  ORDER BY ordem , mo.id_pai; ", nativeQuery = true)
    ArrayList<Menu> findByMenuNivel(String cpf, Integer idGrupoUsuario, Integer nivel, Integer meuIdPai);

    // Retorna todos os menus JA COM A URL 
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

    /*
     Sql que retorna para monstar o BreadCrumb
     */
//    @Query(value = "WITH RECURSIVE menu_tree (icone,id, descricao, url, nivel, id_pai, ordem,ativo) "
//            + "                 AS ( SELECT mo.icone,mo.id, mo.descricao, mo.url || mo.url, 0, mo.id_pai,1,mo.ativo,mo.excluido,mo.obs "
//            + "                  FROM aede.menu as mo	 "
//            + "           	WHERE mo.id_pai is NULL UNION ALL "
//            + "              SELECT mn.icone ,mn.id,mn.descricao, mt.url || '/' || mn.url, mt.nivel + 1,mt.id,   mn.ordem,mn.ativo,mn.excluido,mn.obs "
//            + "             	FROM aede.menu mn, menu_tree mt  "
//            + "             		WHERE mn.id_pai = mt.id  AND mn.excluido='N'  ) "
//            + "                 SELECT DISTINCT * FROM menu_tree WHERE nivel > 0 AND ( url = ?1 OR url = ?2 OR url = ?3 ) ORDER BY url, ordem; ",
//            nativeQuery = true)
    @Query(value = "SELECT DISTINCT mo.icone, mo.id, mo.descricao,mo.obs,  mo.url, mo.nivel, mo.id_pai,  "
            + "mo.ordem  FROM aede.menu as mo, aede.menu_usuario mu , aede.perfil_usuario pa "
            + "  WHERE mo.id = mu.menu_id AND pa.id= mu.perfil_id   AND mo.excluido='N' AND mu.excluido='N' "
            + " AND ( mo.url = ?1  OR (mo.url = ?2 AND mo.id_pai = (SELECT id FROM aede.menu WHERE url= ?1 AND id = mo.id_pai  )) "
            + "OR (mo.url = ?3 AND mo.id_pai = (SELECT id FROM aede.menu WHERE url = ?2 AND id = mo.id_pai )  ) )  ORDER BY NIVEL", nativeQuery = true)
    public ArrayList<Menu> findBreadCrumbMenu(String modulo, String servico, String subServico);

    @Query(FIND_BY_ID)
    public Menu findMenuId(@Param("id") Integer id);
}
