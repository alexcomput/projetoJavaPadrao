/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.view;

import br.gov.to.secad.aede.util.MensagensController;
import br.gov.to.secad.aede.domain.Menu;
import br.gov.to.secad.aede.service.MenuService;
import br.gov.to.secad.aede.util.MenuUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author alex.santos
 */
@ManagedBean
@ViewScoped
public class MenuController implements Serializable {

    private static final long serialVersionUID = 1L;

    private MenuModel modelMenu;

    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    private Integer idmenuPai1 = 0;

    private Integer idmenuPai2 = 0;

    private Integer idmenuPai3 = 0;

    private Menu menu = new Menu();

    private ArrayList<Menu> listaMenus;

    private ArrayList<Menu> listaMenusNivel1;

    private ArrayList<Menu> listaMenusNivel2 = new ArrayList<Menu>();

    private ArrayList<Menu> listaMenusNivel3 = new ArrayList<Menu>();

    private boolean visualizar1;

    private boolean visualizar2;

    public void init() {
        // ATRIBUI TODOS OS MENUS PARA A VARIAVEL listaMenus
        listarMenus();
    }

    public void carregarMenuSelectIte(Integer nivel) {
        switch (nivel) {
            case 1:
                // CARREGAR MODULO
                listaMenusNivel1 = listarMenuNivel(nivel, nivel);
                listaMenusNivel3.clear();

                break;
            case 2:
                //  CARREGAR SUB-SERVIÇO
                listaMenusNivel2 = listarMenuNivel(nivel, idmenuPai1);
                listaMenusNivel3.clear();
                break;

            case 3:
                // CARREGAR SERVIÇO
                listaMenusNivel3 = listarMenuNivel(nivel, idmenuPai2);
                break;
        }
    }
    /*
     public void init(Integer nivel) {
     // ATRIBUI TODOS OS MENUS PARA A VARIAVEL listaMenus

     switch (nivel) {
     case 1:
     //    System.out.println("Consultar por nivel 1");
     listaMenusNivel1 = listarMenuNivel(nivel, nivel);
     listaMenusNivel3.clear();

     break;
     case 2:
     //  System.out.println("Consultar por nivel 2");
     listaMenusNivel2 = listarMenuNivel(nivel, idmenuPai1);
     listaMenusNivel3.clear();
     break;

     case 3:
     // System.out.println("Consultar por nivel 3");
     listaMenusNivel3 = listarMenuNivel(nivel, idmenuPai2);
     break;
     }
     }
     */

    public ArrayList<Menu> listarCpfMenu(String cpf, Integer idGrupoUsuario) {
        return menuService.findByMenu(cpf, idGrupoUsuario);
    }

    public ArrayList<Menu> listarTodosMenus() {
        return menuService.findAllMenu();
    }

    public void listarMenus() {
        listaMenus = listarTodosMenus();
    }

    public ArrayList<Menu> getListaMenus() {
        return listaMenus;
    }

    public ArrayList<Menu> listarMenuNivel(Integer nivel, Integer idPai) {
        return menuService.findNiverMenu(nivel, idPai);
    }

    public void setListaMenus(ArrayList<Menu> listaMenus) {
        this.listaMenus = listaMenus;
    }

    public MenuModel getModelMenu() {
        return modelMenu;
    }

    public void setModelMenu(MenuModel modelMenu) {
        this.modelMenu = modelMenu;
    }

    public Integer getIdmenuPai1() {
        return idmenuPai1;
    }

    public void setIdmenuPai1(Integer idmenuPai1) {
        this.idmenuPai1 = idmenuPai1;
    }

    public MenuModel formarMenu(ArrayList<Menu> listamenu) {
        // CHAMA A CLASSE MENUUTIL PARA FORMAR O MENU ORGANIZADO
        this.modelMenu = MenuUtil.formarMenu(listamenu);
        return this.modelMenu;
    }
    /* public MenuModel formarMenu(ArrayList<Menu> listamenu) {
     modelMenu = new DefaultMenuModel();
    
     for (int i = 0; i < listamenu.size(); i++) {
     //System.out.println(listamenu.get(i).getDescricao() + " : " + listamenu.get(i).getId_pai().getId());
     if (listamenu.get(i).getId_pai().getId() == 1) {
     Menu menuModulo1 = listamenu.get(i);
    
     //Cria o MODULO NIVEL 1
     DefaultSubMenu modulo = new DefaultSubMenu(menuModulo1.getDescricao(), menuModulo1.getIcone());
    
     for (int j = 0; j < listamenu.size(); j++) {
     if (Objects.equals(menuModulo1.getId(), listamenu.get(j).getId_pai().getId())) {
     Menu menuItem2 = listamenu.get(j);
     // Cria o serviço NIVEL 2
     DefaultSubMenu servico = new DefaultSubMenu(menuItem2.getDescricao(), menuItem2.getIcone());
     // Adiciona no menu
     modulo.addElement(servico);
     for (int h = 0; h < listamenu.size(); h++) {
     if (Objects.equals(menuItem2.getId(), listamenu.get(h).getId_pai().getId())) {
     //Cria o Sub-serviço NIVEL 3
     Menu menuItem3 = listamenu.get(h);
     DefaultMenuItem itemSubServico = new DefaultMenuItem(menuItem3.getDescricao(), menuItem3.getIcone(), menuItem3.getUrl());
    
     servico.addElement(itemSubServico);
     //System.out.println(menuModulo1.getDescricao() + "->" + menuItem2.getDescricao() + "->" + menuItem3.getDescricao());
     //agora adiiconar os menus..
     }
     }
     }
     }
     this.modelMenu.addElement(modulo);
     }
     //       System.out.println(menu.getDescricao() + " : " + menu.getId() + " : " + menu.getUrl());
     }
     return this.modelMenu;
     }*/

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public MenuModel getMenuBar() {
        return modelMenu;
    }

    public void setMenuBar(MenuModel modelMenu) {
        this.modelMenu = modelMenu;
    }

    public ArrayList<Menu> getListaMenusNivel1() {
        return listaMenusNivel1;
    }

    public void setListaMenusNivel1(ArrayList<Menu> listaMenusNivel1) {
        this.listaMenusNivel1 = listaMenusNivel1;
    }

    public ArrayList<Menu> getListaMenusNivel2() {
        return listaMenusNivel2;
    }

    public void setListaMenusNivel2(ArrayList<Menu> listaMenusNivel2) {
        this.listaMenusNivel2 = listaMenusNivel2;
    }

    public ArrayList<Menu> getListaMenusNivel3() {
        return listaMenusNivel3;
    }

    public void setListaMenusNivel3(ArrayList<Menu> listaMenusNivel3) {
        this.listaMenusNivel3 = listaMenusNivel3;
    }

    public void visualizar(Integer i) {

        switch (i) {
            case 1:
                carregarMenuSelectIte(2);
                break;
            case 2:
                carregarMenuSelectIte(3);
                break;
        }
    }

    public boolean isVisualizar1() {
        return visualizar1;
    }

    public void setVisualizar1(boolean visualizar1) {
        this.visualizar1 = visualizar1;
    }

    public boolean isVisualizar2() {
        return visualizar2;
    }

    public void setVisualizar2(boolean visualizar2) {
        this.visualizar2 = visualizar2;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getIdmenuPai2() {
        return idmenuPai2;
    }

    public void setIdmenuPai2(Integer idmenuPai2) {
        this.idmenuPai2 = idmenuPai2;
    }

    public Integer getIdmenuPai3() {
        return idmenuPai3;
    }

    public void setIdmenuPai3(Integer idmenuPai3) {
        this.idmenuPai3 = idmenuPai3;
    }

    public boolean ativodoConfere(String ativo) {
        if (ativo.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public void salvarMenu() {
        /*O Pai 1 é o pai dos Modulos ele não aparece no 
         MENU PARA O USUARIO FINAL
         Se o usuario não tiver seleiconado nenhum então pega o 
         Menu geral que é do MODULO
         */
        Integer idPai = 1;
        if (this.idmenuPai3 > 0) {
            idPai = idmenuPai3;
        } else {
            if (this.idmenuPai2 > 0) {
                idPai = idmenuPai2;
            } else {
                if (this.idmenuPai1 > 0) {
                    idPai = idmenuPai1;
                }
            }
        }
        this.menu.setMenuPai(findMenuId(idPai));
        this.menu.setNivel(this.menu.getMenuPai().getNivel() + 1);
        if (menuService.salvar(this.menu)) {
            MensagensController.addInfo("Registro Inserido com sucesso!");
            //Atualiza a lista novamente da Grid.
            listarMenus();
            this.menu = new Menu();
        } else {
            MensagensController.addInfo("Erro ao inserido com sucesso!");
        }
    }

    public Menu findMenuId(Integer idMenu) {
        return menuService.findMenuId(idMenu);
    }

    public void removeMenu(Menu menu) {
        menu.setExcluido("S");
        System.out.println("Alex teste");
        if (editar(menu)) {
            MensagensController.addInfo("Registro removido com sucesso!");
            listaMenus.remove(menu);
        } else {
            MensagensController.addError("Erro ao remover registro!");
        }
    }

    public boolean editar(Menu menu) {
        return menuService.salvar(menu);
    }

    //metodos ajax para dataTable
    public void onEdit(RowEditEvent event) {
        if (menuService.salvar((Menu) event.getObject())) {
            MensagensController.addInfo("Registro alterado com sucesso!");
        } else {
            MensagensController.addError("Erro ao alterar registro!");
        }
    }

    /*
     Monta o link de navegação onde o usuário está 
     */
    public MenuModel findBreadCrumbMenu(List<Menu> listaMenu) {
        /*
         pega a URL do sistema
         */
        FacesContext fc = FacesContext.getCurrentInstance().getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        final String url = request.getRequestURI();
        /*
         fim pega url
         */
        return atribuiURL(url, listaMenu);
    }

    public void getServico(String url) {

        String servico = "";

        String[] vetUrl = url.split("/");
        for (int i = 0; i < vetUrl.length; i++) {
            if (i == 3) {
                servico = "/" + vetUrl[2] + "/" + vetUrl[3];
            }
        }
    }

    public String getSubServico(String url) {

        String servico = "";

        String[] vetUrl = url.split("/");
        for (int i = 0; i < vetUrl.length; i++) {
            if (i == 3) {
                servico = "/" + vetUrl[2] + "/" + vetUrl[3];
            }
        }
        return servico;
    }

    public void getArrayModulo() {

    }

    public String getArrayModulo(String url, Integer indece) {
        String[] vetUrl = url.split("/");
        String modulo = "";
        for (int i = 0; i < vetUrl.length; i++) {
            if (i == indece) {
                modulo = "/" + vetUrl[indece];
            }

        }
        return modulo;
    }
    /*
     Monta o menu de acordo uma URL enviada
     */

    public MenuModel atribuiURL(String url, List<Menu> listaMenu) {

        String servico = "";
        String subServico = "";
        String modulo = "";
        String[] vetUrl = url.split("/");
        /*
         Divide o MODULO -> SUB SERVIÇO E SERVIÇO PARA PEGAR DE ACORDO 
         ONDE O USUARIO ESTA.
         */
        for (int i = 0; i < vetUrl.length; i++) {
            if (i == 2) {
                // PEGA O MODULO
                modulo = vetUrl[2];
            } else if (i == 3) {
                // PEGA O SUB-SERVIÇO
                subServico = vetUrl[3];
            } else if (i == 4) {
                // PEGA O SERVIÇO
                servico = vetUrl[4];
            }
        }

        // Consulta no banco
        //ArrayList<Menu> menuLi = menuService.findBreadCrumbMenu(modulo, servico, subServico);
        ArrayList<Menu> menuLi = menuBreadCrumbMenu(modulo, subServico, servico, listaMenu);
        return MenuUtil.formarMenuBreadCrumb(menuLi);
    }

    public ArrayList<Menu> menuBreadCrumbMenu(String modulo, String subservico, String servico, List<Menu> listaMenu) {
        ArrayList<Menu> retorno = new ArrayList<>();
        if (!listaMenu.isEmpty()) {
            for (Menu menuM : listaMenu) {

                if (menuM.getNivel() == 1 && modulo.equals(menuM.getUrl())) {
                    // Pega o sub-servico
                    for (Menu menuS : listaMenu) {
                        // Pega o servico
                        if (menuS.getNivel() == 2 && subservico.trim().equals(menuS.getUrl().trim()) && Objects.equals(menuM.getId(), menuS.getMenuPai().getId())) {
                            for (Menu menuSe : listaMenu) {
                                if (menuSe.getNivel() == 3 && servico.equals(menuSe.getUrl()) && Objects.equals(menuSe.getMenuPai().getId(), menuS.getId())) {
                                    retorno.add(menuM);
                                    retorno.add(menuS);
                                    retorno.add(menuSe);
                                    return retorno;
                                }
                            }
                        }
                    }
                }
            }
        }
        return retorno;
    }
    /*
     Inicio Forma o MENU BLOCOS / Tiles 
     */

    public List<Menu> menuModulo(List<Menu> modulo, Integer nivel, Integer menuPaiId) {
        List<Menu> retorno = new ArrayList<Menu>();
        if (modulo != null && !modulo.isEmpty()) {
            for (Menu menuM : modulo) {
                if (Objects.equals(menuM.getNivel(), nivel) && menuPaiId == menuM.getMenuPai().getId()) {
                    retorno.add(menuM);
                }
            }
        }
        return retorno;
    }
    /*
     FIM formar o MENU BLOCOS /  Tiles
     */

    public String atribuirPrivilegio() {
        return "privilegio";
    }
}
