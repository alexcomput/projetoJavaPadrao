/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.ergon.service;

import br.gov.to.secad.ergon.domain.Servidor;
import br.gov.to.secad.ergon.repository.IServidorRepository;
import br.gov.to.secad.aede.view.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**Classe que fornece os serviços para o spring manipular a classe Servidor
 *
 * @author wellyngton.santos
 */
@Service
public class ServidorService {
    /**
     Atributo iServidorRepository usado para tratar ações na entidade Servidor no banco.
     */
    @Autowired
    IServidorRepository iServidorRepository;
    /**
     Método usado para acionar o método de busca no repositório de Servidor.
     * O argumento recebe o valor de um cpf para ser pesquisado, o valor é setado
     * na chamada de método feita pela instância iServidorRepository.
     */

    @Transactional(timeout = 10)
    public Servidor findByCpf(String cpf){
        try{
            Servidor temp;
            
            switch(LoginController.getAutorizacao())
            {
                case 1:
                    temp = iServidorRepository.findByCpf(Long.parseLong(cpf));
                    break;
                case 2:
                    temp = iServidorRepository.findByCpf(Long.parseLong(cpf));
                    break;
                default:
                    temp = iServidorRepository.findByCpfOrgao(Long.parseLong(cpf), LoginController.getCurrentInstance().getOrgaoId());
                    break;
            }
            return temp;
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }
    
    @Transactional(timeout = 10)
    public Servidor findByCpfInterno(String cpf){
        try{
             return iServidorRepository.findByCpf(Long.parseLong(cpf));
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }
    // Verificar depois onde chama esse metodo para atualizar
    @Transactional(timeout = 10)
    public Servidor findByfindByNumfunc(Integer numfunc, Integer numvinc){
        try{
             return iServidorRepository.findByNumfuncNunvinc(numfunc, numvinc);
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }
    @Transactional(timeout = 10)
    public Servidor findByNumfuncNunvinc(Integer numfunc, Integer numvinc){
        try{
             return iServidorRepository.findByNumfuncNunvinc(numfunc, numvinc);
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }
    
    public Servidor findPorCpf(Long cpf){
        try{
            return iServidorRepository.findPorCpf(cpf);
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }
    
}
