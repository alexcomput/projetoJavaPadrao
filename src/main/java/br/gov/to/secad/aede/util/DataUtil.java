
package br.gov.to.secad.aede.util;

import java.util.Date;

/**
 *
 * @author wellyngton.santos
 * Classe para tratar operações com datas na aplicação.
 */
public class DataUtil {
    /*
     Método para comparar data inicial e final de um prazo dado. Onde d1 = inicio, e d2 = final.
     */
    public static boolean validaDatas(Date d1, Date d2){
        try{
            if(d2==null){
                return true;
            }
            return d2.after(d1);
        }catch(Exception e){
            MensagensController.addError("Validar Datas: "+e.getMessage());
            return false;
        }
    }
    
}
