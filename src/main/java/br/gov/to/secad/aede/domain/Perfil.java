/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * Classe que implementa os perfis(grupos) de usuário do sistema, o que define
 * quais acesso o usuário terá.
 *
 * @author wellyngton.santos
 */
@Entity
@Table(name = "perfil", schema = "aede")
public class Perfil implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;
    /**
     * Atributo identificador da classe
     */
    @Id
    @SequenceGenerator(name = "idperfil", sequenceName = "perfil_id", schema = "aede", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idperfil")
    private Integer id;
    /**
     * Atributo dcgrupo - guarda a descrição do grupo de usuário
     */
    private String descricao;

    public Integer getId() {
        return id;
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

    @Override
    public String getAuthority() {
        return descricao;
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
        final Perfil other = (Perfil) obj;
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
        return "Perfil{" + "id=" + id + ", descricao=" + descricao + '}';
    }

}
