/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.util;

/**
 *
 * @author alex.santos
 */
public class CpfUltilConverte {

    public static String cpfConverteString(String cpf) {
        Integer tamanhoCpf = cpf.toString().trim().length();
        String zeros = "";
        if (cpf != null) {
            while (tamanhoCpf < 11) {
                zeros = "0".concat(zeros);
                tamanhoCpf++;
            }
            return zeros.concat(cpf.toString().trim());
        }
        return "";
    }

}
