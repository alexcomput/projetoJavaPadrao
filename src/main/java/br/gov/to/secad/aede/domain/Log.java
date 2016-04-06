package br.gov.to.secad.aede.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**Classe que implementa o histórico de ações realizadas no sistema (logs)
 *
 * @author wellyngton.santos
 */
@Entity
@Table(name = "log", schema = "aede")
public class Log implements Serializable{
    /**
    Atributo identificador da classe
     */
    @Id
    @SequenceGenerator(name="idlog", sequenceName = "log_id_seq", schema = "aede", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idlog")
    private Integer id;
    /**
    Atributo guarda o cpf do usuário que executa uma ação.
     */
    private String cpf;
    /**
    Atributo acao guarda a descrição da ação executada pelo usuário.
     */
    private String acao;
    /**
    Atributo ip guarda o endereço ip do usuário que executou uma ação.
     */
    private String ip;
    /**
    Atributo datalog guarda a data em que a ação foi executada.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datalog;
    /**
    Atributo horalog guarda o horário em que a ação foi executada.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date horalog;

    public Log() {
    }
    /**
    Construtor de classe que especifica o cpf, o ip e ação realizada.
     */
    public Log(String cpf, String ip, String acao){
        this.cpf = cpf;
        this.ip = ip;
        this.acao = acao;
        this.datalog = new Date();
        this.horalog = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDatalog() {
        return datalog;
    }

    public void setDatalog(Date datalog) {
        this.datalog = datalog;
    }

    public Date getHoralog() {
        return horalog;
    }

    public void setHoralog(Date horalog) {
        this.horalog = horalog;
    }
    
}
