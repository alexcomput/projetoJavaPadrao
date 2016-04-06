/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.view;

import br.gov.to.secad.aede.util.MensagensController;
import br.gov.to.secad.aede.domain.Perfil;
import br.gov.to.secad.aede.service.PerfilService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author alex.santos
 */
@ManagedBean
@ViewScoped
public class PerfilController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{perfilService}")
    private PerfilService perfilService;

    private Perfil perfil = new Perfil();
    private Integer idPerfil;

    private List<Perfil> listaPerfil;

    public void init() {
        this.listaPerfil = perfilService.listarPerfilSemAdministrativo();
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public PerfilService getPerfilService() {
        return perfilService;
    }

    public void setPerfilService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    public List<Perfil> getListaPerfil() {
        return listaPerfil;
    }

    public void setListaPerfil(ArrayList<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }

    public void salvar() {
        if (mensagem(perfilService.salvar(this.perfil))) {
            listaPerfil.add(this.perfil);
            this.perfil = new Perfil();
        }
    }

    public void remover(Perfil perfil) {
        listaPerfil.remove(perfil);
        mensagem(perfilService.remover(perfil));
    }

    //metodos ajax para dataTable
    public void onEdit(RowEditEvent event) {
        mensagem(perfilService.salvar((Perfil) event.getObject()));
    }

    public boolean mensagem(boolean condicao) {
        if (condicao) {
            MensagensController.addInfo("Operação efetuado com sucesso!!");
            return true;
        } else {
            MensagensController.addError("Erro ao realizar operação, entre em contato com administrador");
            return false;
        }
    }

    //Redireciona o usuário para a página de permissões do Perfil
    public String atribuirPermissao(Integer idPerfil){
        return "/admin/cadastro/privilegio/index?faces-redirect=true&amp;includeViewParams=true&amp;idPerfilSelecionado="+idPerfil;
    }

}
