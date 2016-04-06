package br.gov.to.secad.aede.domain;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by thaniel.alves on 15/03/2016.
 */
@ManagedBean
@ViewScoped
public class ConfiguraTagConteudo {

    private String nomeClasse;
    private String nomeTag;
    private Map<String,String> mapAtributos;

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }

    public void setMapAtributos(Map<String, String> mapAtributos) {
        this.mapAtributos = mapAtributos;
    }

    public Map<String, String> getMapAtributo() {
        return mapAtributos;
    }

    public ArrayList<Map.Entry<String,String>> getMapAtributos() {
        Set<Map.Entry<String,String>> productSet = mapAtributos.entrySet();
        return new ArrayList<Map.Entry<String, String>>(productSet);
    }
}
