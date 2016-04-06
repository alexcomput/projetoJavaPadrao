package br.gov.to.secad.aede.util;

/**
 * Created by thaniel.alves on 06/01/2016.
 */
public class CalculaConceitoUtil {

    public static String calculaConceito(Integer notaFator){

        if(notaFator != 0) {
            if (notaFator <= 3) {
                return "insatisfatorio";
            } else if (notaFator <= 5) {
                return "regular";
            } else if (notaFator <= 8) {
                return "bom";
            } else if (notaFator <= 10) {
                return "excelente";
            }else{
                return "";
            }
        }else
            return "";
    }
}
