/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.Menu;
import br.gov.to.secad.aede.repository.IMenuRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alex.santos
 */
@Service
public class MenuService {

    @Autowired
    IMenuRepository iMenuRepository;

    @Transactional(timeout = 10)
    public ArrayList<Menu> findByMenu(String cpf, Integer idGrupoUsuario) {
        try {
            return iMenuRepository.findByMenu(cpf, idGrupoUsuario);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public ArrayList<Menu> findByMenuModulo(String cpf, Integer idGrupoUsuario, Integer nivel,Integer meuIdPai) {
        try {
            return iMenuRepository.findByMenuNivel(cpf, idGrupoUsuario, nivel,meuIdPai);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public ArrayList<Menu> findAllMenu() {
        return iMenuRepository.findAllMenu();
    }

    @Transactional(timeout = 10)
    public ArrayList<Menu> findNiverMenu(Integer nivel, Integer idPai) {
        try {
            return iMenuRepository.findNiverMenu(nivel, idPai);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public Menu findMenuId(Integer idMenu) {
        try {
            return iMenuRepository.findMenuId(idMenu);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    public boolean salvar(Menu menu) {
        try {
            iMenuRepository.save(menu);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getLocalizedMessage());
        }
        return false;
    }

    public boolean removerMenu(Menu menu) {
        try {
            iMenuRepository.delete(menu);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getLocalizedMessage());
        }
        return false;
    }

    public ArrayList<Menu> findBreadCrumbMenu(String modulo, String servico, String subservico) {
        try {
            return iMenuRepository.findBreadCrumbMenu(modulo, servico, subservico);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }
}
