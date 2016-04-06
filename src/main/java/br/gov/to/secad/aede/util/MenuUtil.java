/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.util;

import br.gov.to.secad.aede.domain.Menu;
import java.util.ArrayList;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;
import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author alex.santos
 */
public class MenuUtil {

    public static MenuModel formarMenu(ArrayList<Menu> listaMenus) {
        
        MenuModel modelMenu = new DefaultMenuModel();
        for (Menu listaMenu2 : listaMenus) {
            //System.out.println(listamenu.get(i).getDescricao() + " : " + listamenu.get(i).getId_pai().getId());
            if (listaMenu2.getMenuPai().getId() == 1) {
                Menu menuModulo1 = listaMenu2;
                //Cria o MODULO NIVEL 1
                DefaultSubMenu modulo = new DefaultSubMenu(menuModulo1.getDescricao(), menuModulo1.getIcone());
                // Adiciona o link para 

                for (Menu listaMenu1 : listaMenus) {
                    if (Objects.equals(menuModulo1.getId(), listaMenu1.getMenuPai().getId())) {
                        Menu menuItem2 = listaMenu1;
                        // Cria o serviço NIVEL 2                        
                        DefaultSubMenu servico = new DefaultSubMenu(menuItem2.getDescricao(), menuItem2.getIcone());
                        // Adiciona no menu

                        modulo.addElement(servico);
                        for (Menu listaMenu : listaMenus) {
                            if (Objects.equals(menuItem2.getId(), listaMenu.getMenuPai().getId())) {
                                //Cria o Sub-serviço NIVEL 3
                                Menu menuItem3 = listaMenu;
                                DefaultMenuItem itemSubServico = new DefaultMenuItem(menuItem3.getDescricao(), menuItem3.getIcone(),
                                        "/" + menuModulo1.getUrl() + "/" + menuItem2.getUrl() + "/" + menuItem3.getUrl());

                                servico.addElement(itemSubServico);

                            }
                        }
                    }
                }
                modelMenu.addElement(modulo);
            }
        }
        return modelMenu;
    }

    public static ArrayList<Menu> formarMenuArrayList(ArrayList<Menu> listaMenus) {
        ArrayList<Menu> listaTempo = new ArrayList<>();

        for (Menu listaMenu2 : listaMenus) {
            //System.out.println(listamenu.get(i).getDescricao() + " : " + listamenu.get(i).getId_pai().getId());
            if (listaMenu2.getMenuPai().getId() == 1) {

                Menu menuModulo = listaMenu2;

                for (Menu listaMenu1 : listaMenus) {
                    if (Objects.equals(menuModulo.getId(), listaMenu1.getMenuPai().getId())) {
                        Menu menuSubServico = listaMenu1;
                        // Cria o serviço NIVEL 2    

                        for (Menu listaMenu : listaMenus) {
                            if (Objects.equals(menuSubServico.getId(), listaMenu.getMenuPai().getId())) {
                                //Cria o Sub-serviço NIVEL 3
                                Menu menuServico = listaMenu;
                                menuSubServico.getListaFilhos().add(menuServico);
                                menuModulo.getListaFilhos().add(menuSubServico);
                            }
                        }
                    }
                }
                listaTempo.add(menuModulo);
            }
        }
        return listaTempo;
    }

    public static MenuModel formarMenuBreadCrumb(ArrayList<Menu> listaMenus) {

        MenuModel modelMenu = new DefaultMenuModel();
        DefaultMenuItem itemSubServicoHome = new DefaultMenuItem("Home");
        itemSubServicoHome.setUrl("/");
        modelMenu.addElement(itemSubServicoHome);
        String url = "/";
        for (Menu listaMenu : listaMenus) {
            //Cria o Sub-serviço NIVEL 3
            Menu menuItem3 = listaMenu;
            //DefaultMenuItem itemSubServico = new DefaultMenuItem(menuItem3.getDescricao(), menuItem3.getIcone(), menuItem3.getUrl());
            DefaultMenuItem itemSubServico = new DefaultMenuItem(menuItem3.getDescricao());
            url = url + "" + menuItem3.getUrl();
            itemSubServico.setUrl(url);
            modelMenu.addElement(itemSubServico);
            url = url + "/";
        }
        return modelMenu;
    }

    public static void menuLink(ArrayList<Menu> listaMenus) {
        ArrayList<CommandLink> listaLink = new ArrayList<CommandLink>();

    }

}
