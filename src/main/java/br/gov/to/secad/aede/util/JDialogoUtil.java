/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author alex.santos
 */
public class JDialogoUtil {

    public static void abrirDialogo(String descricaoJDialog, String contentHeigt, String contentWidth,
            boolean Modal, boolean resizable, Map<String, List<String>> param) {
        Map<String, Object> opcao = new HashMap<>();
        opcao.put("modal", Modal);
        opcao.put("resizable", resizable);
        opcao.put("contentHeight", contentHeigt);
        opcao.put("contentWidth", contentWidth);
        /*
         Referencia o nome do arquivo sem o Xhtml
         */
        RequestContext.getCurrentInstance().openDialog(descricaoJDialog, opcao, param);
    }

    public static String retornarParametro(String parametro) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(parametro);
    }
}
