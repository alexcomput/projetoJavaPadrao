/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.domain;

import br.gov.to.secad.ergon.domain.Servidor;
import br.gov.to.secad.aede.util.MensagensController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

/**
 *
 * @author wellyngton.santos
 */
@Entity
@Table(schema = "aede", name = "perfil_usuario")
public class PerfilUsuario implements Serializable {

    @Id
    @SequenceGenerator(name = "idperfil_usuario", sequenceName = "permissao_usuario_id_seq", schema = "aede", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idperfil_usuario")
    private Integer id;

    @JoinFetch(JoinFetchType.INNER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @JoinFetch(JoinFetchType.INNER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    private Usuario usuario;

    @Column(name = "lotado_orgao_id")
    private Integer lotadoOrgaoId;

    @Column(name = "lotado_orgao")
    private String lotadoOrgao;

    @Column(name = "nome")
    private String nomeServidor;

    @Column(name = "lotado_regional_id")
    private String lotadoRegionalId;

    @Column(name = "lotado_regional")
    private String lotadoRegional;

    @Column(name = "lotado_setor_id")
    private String lotadoSetorId;

    @Column(name = "lotado_setor")
    private String lotadoSetor;

    @JoinFetch(JoinFetchType.INNER)
    @OneToMany(mappedBy = "permissaoUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PerfilUsuarioOrgao> perfilUsuarioOrgaos;

    @Column(name = "email")
    private String emailServidor;

    public Integer getId() {
        return id;
    }

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public List<PerfilUsuarioOrgao> getPerfilUsuarioOrgaos() {
        return perfilUsuarioOrgaos;
    }

    public List<PerfilUsuarioOrgao> getPerfilUsuarioOrgaosAtivos() {
        List<PerfilUsuarioOrgao> retornoUsuarioAtivos = new ArrayList<PerfilUsuarioOrgao>();

        for (PerfilUsuarioOrgao pf : perfilUsuarioOrgaos) {
            if (pf.getAtivo().equals("S") && pf.getExcluido().equals("N")) {
                retornoUsuarioAtivos.add(pf);
            }
        }
        return retornoUsuarioAtivos;
    }

    public void setPerfilUsuarioOrgaos(List<PerfilUsuarioOrgao> perfilUsuarioOrgaos) {
        this.perfilUsuarioOrgaos = perfilUsuarioOrgaos;
    }

    public Integer getLotadoOrgaoId() {
        return lotadoOrgaoId;
    }

    public void setLotadoOrgaoId(Integer lotadoOrgaoId) {
        this.lotadoOrgaoId = lotadoOrgaoId;
    }

    public String getLotadoOrgao() {
        return lotadoOrgao;
    }

    public void setLotadoOrgao(String lotadoOrgao) {
        this.lotadoOrgao = lotadoOrgao;
    }

    public String getLotadoRegional() {
        return lotadoRegional;
    }

    public void setLotadoRegional(String lotadoRegional) {
        this.lotadoRegional = lotadoRegional;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario cpf) {
        this.usuario = cpf;
    }

    public String getLotadoRegionalId() {
        return lotadoRegionalId;
    }

    public void setLotadoRegionalId(String lotadoRegionalId) {
        this.lotadoRegionalId = lotadoRegionalId;
    }

    public String getRegional() {
        return lotadoRegional;
    }

    public void setRegional(String regional) {
        this.lotadoRegional = regional;
    }

    public String getLotadoSetorId() {
        return lotadoSetorId;
    }

    public void setLotadoSetorId(String lotadoSetorId) {
        this.lotadoSetorId = lotadoSetorId;
    }

    public String getLotadoSetor() {
        return lotadoSetor;
    }

    public void setLotadoSetor(String lotadoSetor) {
        this.lotadoSetor = lotadoSetor;
    }

    public void setDadosServidor(Servidor servidor) {
        if (servidor != null) {
            //this.setloOrgao(servidor.getSigla_orgao());
            this.lotadoOrgaoId = servidor.getOrgaoId();
            this.lotadoOrgao = servidor.getOrgaoNome();
            this.nomeServidor = servidor.getNome();
            this.lotadoSetor = servidor.getSetor();
            this.lotadoSetorId = servidor.getSetorId();
            this.nomeServidor = servidor.getNome();
        } else {
            System.out.println("Servidor não encontrado na Base de dados!");
            MensagensController.addFatal("Servidor não encontrado na Base de dados.");
        }
    }

    public String getEmailServidor() {
        return emailServidor;
    }

    public void setEmailServidor(String emailServidor) {
        this.emailServidor = emailServidor;
    }
}
