package br.gov.to.secad.ergon.domain;

import br.gov.to.secad.aede.domain.Pessoa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author wellyngton.santos Classe que representa um funcionário. Um
 * funcionário na estrutura do ERGON, sistema de RH do Estado do Tocantins, é
 * uma entidade que contém as informações pessoais de um servidor.
 */
@Entity
/**
 * Para fins dessa aplicação, foi criada um view com intuito de capturar
 * informações relevantes a mesma.
 */
@Table(name = "C_DTI.VW_SERVIDOR_ATIVO")
public class Servidor extends Pessoa implements Serializable {

    /**
     * Atributo orgaoFantasia - é o atributo que guarda a informação da
     * representação do órgão de lotação atual do servidor.
     *
     */
    @Id
    private Number cpf;

    /**
     * Atributo numfunc - é o atributo que guarda a informação do número
     * funcional do servidor. O número funcional é comparável a uma matrícula
     * funcional de um servidor.
     */
    private Integer numfunc;
    /**
     * Atributo numvinc - é o atributo que guarda a informação do número vínculo
     * do servidor. O número vínculo determina qual o número de vezes em que um
     * servidor criou vínculo com a Administração.
     */
    private Integer numvinc;

    @Column(name = "FANTASIA")
    private String orgaoFantasia;

    @Column(name = "ORGAONOME")
    private String orgaoNome;

    /**
     * Atributo orgaoId - é o atributo que guarda a informação do código
     * referente ao órgão de lotação atual do servidor.
     */
    @Column(name = "ORGAO")
    private Integer orgaoId;
    /**
     * Atributo setor - informação do nome do setor de lotação do servidor
     * avaliado
     */
    @Column(name = "NOMESETOR")
    protected String setor;

    /**
     * Atributo setorId - informação do código identificador do setor de lotação
     * do servidor avaliado
     */
    @Column(name = "SETOR")
    private String setorId;

    @Column(name = "SETORMUNICIPIO")
    private String setorMunicipio;

    @Column(name = "CARGO")
    private Integer cargoId;

    @Column(name = "NOMECARGO")
    private String cargoNome;

    @Column(name = "CARGOREFERENCIA")
    private String cargoReferencia;

    @Column(name = "FUNCAOCARGO")
    private String cargoFuncao;

    @Column(name = "FUNCAONOME")
    private String funcaoNome;

    @Column(name = "FUNCAOREFERENCIA")
    private String funcaoReferencia;

    @Column(name = "DTADMISSAO")
    private String dtAdmissao;

    /**
     * No banco do ERGON as strings são guardadas com letras todas maiúsculas.
     * Afim de retorna valores mais agradáveis ao usuário final, foi se
     * utilizado a Classe WordUtils com o método capitalizeFully que transforma
     * a string com letras maiúsculas em um padrão mais organizado. Ex: ENTAO ->
     * Entao.
     */
    public String getOrgaoNome() {
        return orgaoNome;
    }

    public void setOrgaoNome(String orgaoNome) {
        this.orgaoNome = orgaoNome;
    }

    public String getSetorMunicipio() {
        return setorMunicipio;
    }

    public void setSetorMunicipio(String setorMunicipio) {
        this.setorMunicipio = setorMunicipio;
    }

    public String getCargoNome() {
        return cargoNome;
    }

    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }

    public String getCargoReferencia() {
        return cargoReferencia;
    }

    public void setCargoReferencia(String cargoReferencia) {
        this.cargoReferencia = cargoReferencia;
    }

    public String getCargoFuncao() {
        return cargoFuncao;
    }

    public void setCargoFuncao(String cargoFuncao) {
        this.cargoFuncao = cargoFuncao;
    }

    public String getFuncaoNome() {
        return funcaoNome;
    }

    public void setFuncaoNome(String funcaoNome) {
        this.funcaoNome = funcaoNome;
    }

    public String getFuncaoReferencia() {
        return funcaoReferencia;
    }

    public void setFuncaoReferencia(String funcaoReferencia) {
        this.funcaoReferencia = funcaoReferencia;
    }

    public String getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(String dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public String getOrgaoFantasia() {
        return orgaoFantasia;
    }

    public void setOrgaoFantasia(String orgaoFantasia) {
        this.orgaoFantasia = orgaoFantasia;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
//
//    public String getCargo() {
//        return WordUtils.capitalizeFully(cargoNome);
//    } 
// 
    @Override
    public String getCpf() {
        if (cpf == null) {
            return "";
        }
        return cpf.toString();
    }

    public void setCpf(Number cpf) {
        this.cpf = cpf;
    }

    public Integer getNumfunc() {
        return numfunc;
    }

    public void setNumfunc(Integer numfunc) {
        this.numfunc = numfunc;
    }

    public Integer getNumvinc() {
        return numvinc;
    }

    public void setNumvinc(Integer numvinc) {
        this.numvinc = numvinc;
    }

    public Integer getOrgaoId() {
        return orgaoId;
    }

    public void setOrgaoId(Integer orgaoId) {
        this.orgaoId = orgaoId;
    }

    public String getSetorId() {
        return setorId;
    }

    public void setSetorId(String setorId) {
        this.setorId = setorId;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

}
