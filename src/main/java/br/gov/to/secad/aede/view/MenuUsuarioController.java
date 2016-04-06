/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.view;

import br.gov.to.secad.aede.util.MensagensController;
import br.gov.to.secad.aede.domain.Menu;
import br.gov.to.secad.aede.service.MenuService;
import br.gov.to.secad.aede.service.MenuUsuarioService;
import br.gov.to.secad.aede.service.PerfilService;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.gov.to.secad.aede.domain.MenuUsuario;
import br.gov.to.secad.aede.domain.Perfil;

import java.util.List;
import java.util.Objects;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author alex.santos
 */
@ManagedBean
@ViewScoped
public class MenuUsuarioController implements Serializable {

    private static final long serialVersionUID = 1L;

    private TreeNode root; //Nó raiz

    private TreeNode[] nosSelecionados; //Nós a serem exibidos

    @SuppressWarnings("unchecked")
    private List<Menu> menusPermitidos = new ArrayList(); //Menus que o perfil selecionado pode acessar

    @ManagedProperty(value = "#{menuUsuarioService}")
    private MenuUsuarioService menuUsuarioService;

    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    @ManagedProperty(value = "#{perfilService}")
    private PerfilService perfilService;

    private Integer idPerfilSelecionado = 0;

    public Integer getIdPerfilSelecionado() {
        return idPerfilSelecionado;
    }

    public void setIdPerfilSelecionado(Integer idPerfilSelecionado) {
        this.idPerfilSelecionado = idPerfilSelecionado;
    }

    public TreeNode[] getNosSelecionados() {
        return nosSelecionados;
    }

    public void setNosSelecionados(TreeNode[] nosSelecionados) {
        this.nosSelecionados = nosSelecionados;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setPerfilService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setMenuUsuarioService(MenuUsuarioService menuUsuarioService) {
        this.menuUsuarioService = menuUsuarioService;
    }

    public void salvarPrivilegio() {
        //Recupera os menus permitidos do perfil
        List<MenuUsuario> menuUsuarios = menuUsuarioService.findMenuUsuarioIdPerfil(idPerfilSelecionado);

        //Remove as permissões antigas
        menuUsuarioService.removePermissoesAntigas(menuUsuarios);

        //Recupera o perfil selecionado
        Perfil perfil = perfilService.findPerfilID(idPerfilSelecionado);

        try{
            //Salva as novas permissões do perfil
            for (TreeNode no : nosSelecionados) {
                Menu folha = (Menu) no.getData();
                MenuUsuario menuUsuario = new MenuUsuario();
                menuUsuario.setMenu(folha);
                menuUsuario.setPerfil(perfil);
                menuUsuarioService.salvarMenuUsuario(menuUsuario);
            }
            MensagensController.addInfo("Registro inserido com sucesso!");
            carregaPermissoesPerfil();
        }catch(Exception e){
            System.out.println("ERRO AO SALVAR PERMISSÕES! "+e.getMessage());
        }
    }
    public void carregaPermissoesPerfil() {

        //Menu Geral pai de todos nivel(0)
        root = new DefaultTreeNode("Geral", null);

        //Lista dos menus que o perfil selecionado tem permissão
        menusPermitidos = new ArrayList<>();

        //Recupera os Menus do Perfil selecionado
        List<MenuUsuario> menuUsuarioList = menuUsuarioService.findMenuUsuarioIdPerfil(idPerfilSelecionado);

        //Recupera todos os menus
        List<Menu> todosMenus = menuService.findAllMenu();

        //Coloca os menus acessíveis do perfil em uma lista temporária
        for(MenuUsuario menuusuario : menuUsuarioList){
            menusPermitidos.add(menuusuario.getMenu());
        }

        // Cria os modulos NIVEL 1
        for (Menu listaMenu2 : todosMenus) {

            if (listaMenu2.getMenuPai().getId() == 1) {
                //atribui a raiz geral como pai dos modulos de nivel 1
                TreeNode treeNodeModulo = new DefaultTreeNode(listaMenu2, root);
                //Verifica se este menu esta na lista dos permitidos do perfil
                if (isPermitido(listaMenu2)) {
                    //Faz com que esse nó da árvore volte selecionado para a tela
                    treeNodeModulo.setSelected(true);
                }
                //Faz com que a árvore seja mostrada expandida ao carregar a página
                treeNodeModulo.setExpanded(true);

                // Cria o serviço NIVEL 2
                for (Menu listaMenu1 : todosMenus) {
                    if (Objects.equals(listaMenu2.getId(), listaMenu1.getMenuPai().getId())) {
                        TreeNode treeNodeSubservico = new DefaultTreeNode(listaMenu1, treeNodeModulo);
                        if (isPermitido(listaMenu1)) {
                            treeNodeSubservico.setSelected(true);
                        }
                        treeNodeSubservico.setExpanded(true);

                        //Cria o Sub-serviço NIVEL 3
                        for (Menu listaMenu : todosMenus) {
                            if (Objects.equals(listaMenu1.getId(), listaMenu.getMenuPai().getId())) {
                                TreeNode treeNodeServico = new DefaultTreeNode(listaMenu, treeNodeSubservico);
                                if (isPermitido(listaMenu)) {
                                    treeNodeServico.setSelected(true);
                                }
                                treeNodeServico.setExpanded(true);
                            }
                        }
                    }
                }
            }
        }
    }

    //Verifica se o menu atual pertence ao perfil (tem permissão no menu)
    private boolean isPermitido(Menu menu) {
        boolean retorno = false;
        for (Menu m : menusPermitidos) {
            if (m.equals(menu)){
                retorno = true;
                break;
            }
        }
        return retorno;
    }
}
