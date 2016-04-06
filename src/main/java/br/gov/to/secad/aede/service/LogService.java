/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.Log;
import br.gov.to.secad.aede.repository.ILogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**Classe que fornece os serviços para o spring manipular a classe Log
 *
 * @author wellyngton.santos
 */
@Service
public class LogService {
    /**
     Atributo iLogRepository usado para tratar ações na entidade Log no banco.
     */
    @Autowired
    ILogRepository iLogRepository;
    /**
     Método usado para salvar a Entidade Log
     */
    public void save(Log log){
        try{
            iLogRepository.save(log);
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
        }
    } 
    
}
