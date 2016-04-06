/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author alex.santos
 */
@Entity
@Table(name = "orgao", schema = "aede")
public class Orgao implements Serializable {

    @Id
    @Column(name = "CODIGO")
    @OrderBy("CODIGO ASC")
    private Integer id;

    @Column
    private String nome;

    @Column
    private String fantasia;

    @Column
    private String razao;

    @Column
    private String cgc;

    @Column
    private String fone;

    @Column
    private String email;

    @Column
    private String web;

    @Column(name = "EMP_CODIGO")
    private Integer empCodigo;
 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    } 

    public Integer getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Integer empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.fantasia);
        hash = 17 * hash + Objects.hashCode(this.empCodigo);
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
        final Orgao other = (Orgao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fantasia, other.fantasia)) {
            return false;
        }
        if (!Objects.equals(this.empCodigo, other.empCodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
}
