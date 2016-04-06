/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**Classe usada para criptografar senhas.
 *
 * @author wellyngton.santos
 */
public class HashPassword {
    /**
     Método usado para encriptar uma string com o método MD5.
     */
    public static String md5(String senha) throws NoSuchAlgorithmException {  
     MessageDigest md = MessageDigest.getInstance("MD5");  
     BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
     return  hash.toString(16);           
  }
}
