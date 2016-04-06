
package br.gov.to.secad.aede.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**Classe usada para retornar mensagens pertinentes ao sistema.
 *
 * @author wellyngton.santos
 */
public class MensagensController {
    /**Método usado para retornar mensagens de sucesso em ação.*/
    public static void addInfo(String msg) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,msg, "INFO"));  
    }  
  /**Método usado para retornar mensagens de atenção.*/
    public static void addWarn(String msg) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg,"ATENÇÃO"));  
    }  
  /**Método usado para retornar mensagens de erro.*/
    public static void addError(String msg) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,"ERRO"));  
    }  
   /**Método usado para retornar mensagens de erro fatais.*/
    public static void addFatal(String msg) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg,"ERRO FATAL"));  
    }  
}
