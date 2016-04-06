/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.util;

/**
 *
 * @author alex.santos
 * @version 1.0
 *
 */
public class FiltroEspecification {

    private Object valor;
    
    private Object valor2;
    
    private Object valor3;

    private String tipo;

    private String fildeNivel1;

    private String filderNivel2;

    private String filderNivel3;

    private String tipoComparacao;

    private String operadorLogico;

    private boolean distinct;

    public FiltroEspecification(Object valor, String tipo, String fildeNivel1, 
            String filderNivel2, String filderNivel3, String tipoComparacao, 
            String operadorLogico, boolean distinct) {
        this.valor = valor;
        this.tipo = tipo;
        this.fildeNivel1 = fildeNivel1;
        this.filderNivel2 = filderNivel2;
        this.filderNivel3 = filderNivel3;
        this.tipoComparacao = tipoComparacao;
        this.operadorLogico = operadorLogico;
        this.distinct = distinct;
    }

    /**
     *
     * @return Object
     */
    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public Object getValor2() {
        return valor2;
    }

    public void setValor2(Object valor2) {
        this.valor2 = valor2;
    }

    public Object getValor3() {
        return valor3;
    }

    public void setValor3(Object valor3) {
        this.valor3 = valor3;
    }
    

    /**
     *
     * @return String Esse retorno é o filde do campo da classe mapeada ex:
     * Sala.Nome
     */
    public String getFildeNivel1() {
        return fildeNivel1;
    }

    public void setFildeNivel1(String fildeNivel1) {
        this.fildeNivel1 = fildeNivel1;
    }

    /**
     *
     * @return Esse retorno serve caso queria pegar o filho de um objeto mapeado
     * exemplo Sala.Aluno.nome
     */
    public String getFilderNivel2() {
        return filderNivel2;
    }

    public void setFilderNivel2(String filderNivel2) {
        this.filderNivel2 = filderNivel2;
    }

    /**
     *
     * @return Esse retorno serve para no caso queria pegar o filho de um filho
     * exemplo Sala.aluno.pessoa.nome
     */
    public String getFilderNivel3() {
        return filderNivel3;
    }

    public void setFilderNivel3(String filderNivel3) {
        this.filderNivel3 = filderNivel3;
    }

    /**
     *
     * @return O tipo de comparação que será utilizado na SQL exemplo de valores
     *
     * Ex: equal or like
     */
    public String getTipoComparacao() {
        return tipoComparacao;
    }

    public void setTipoComparacao(String tipoComparacao) {
        this.tipoComparacao = tipoComparacao;
    }

    public String getOperadorLogico() {
        return operadorLogico;
    }

    public void setOperadorLogico(String operadorLogico) {
        this.operadorLogico = operadorLogico;
    }

}
