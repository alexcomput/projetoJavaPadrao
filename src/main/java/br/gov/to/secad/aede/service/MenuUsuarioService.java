/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.MenuUsuario;
import br.gov.to.secad.aede.repository.IMenuUsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alex.santos
 */
@Service
public class MenuUsuarioService {

    @Autowired
    IMenuUsuarioRepository iMenuUsuarioRepository;

    public boolean salvarMenuUsuario(MenuUsuario menuUsuario) {
        try {
            iMenuUsuarioRepository.save(menuUsuario);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getLocalizedMessage());
        }
        return false;
    }

    public List<MenuUsuario> findALL(Integer idMenu) {
        return iMenuUsuarioRepository.findAll();
    }

    public List<MenuUsuario> findMenuUsuarioIdPerfil(Integer idPerfil) {
        return iMenuUsuarioRepository.findMenuUsuarioIdPerfil(idPerfil);
    }

    //Remove os menus existentes do perfil antes de atribuir os novos
    public boolean removePermissoesAntigas(List<MenuUsuario> menuUsuarios) {
        try {
            for(MenuUsuario menuUsuario : menuUsuarios) {
                iMenuUsuarioRepository.delete(menuUsuario);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getLocalizedMessage());
        }
        return false;
    }

}
