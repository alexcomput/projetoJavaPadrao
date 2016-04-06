/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient ;/**
         *
         * @author alex.santos
         */

@Entity
@Table(name = "menu", schema = "aede")
public class Menu implements Serializable {

    @Id
    @SequenceGenerator(name = "menu_id", sequenceName = "modulo_id_seq", schema = "aede", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id")
    private Integer id;
    @Column
    private String descricao;

    @Column
    private String obs;
    @Column
    private String excluido = "N";
    @Column
    private String ativo = "S";
    @Column
    private Integer ordem;

    @JoinColumn(name = "id_pai", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menuPai;

    @Column(updatable = false)
    private String url;

    @Column
    private String icone;

    @Column
    private Integer nivel;

    @Transient
     private List<Menu> listaFilhos = new ArrayList<>();

    public List<Menu> getListaFilhos() {
        return listaFilhos;
    }

    public void setListaFilhos(List<Menu> listaFilhos) {
        this.listaFilhos = listaFilhos;
    }

    public Integer getId() {
        return id;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Menu getMenuPai() {
        return menuPai;
    }

    public void setMenuPai(Menu menuPai) {
        this.menuPai = menuPai;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Menu other = (Menu) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", descricao=" + descricao + '}';
    }


}
