/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.repository;

import br.gov.to.secad.aede.domain.Log;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**Interface que estende a classe JpaRepository, responsável por persistir ações
 * referentes a classe Log.
 * @see Log
 *
 * @author wellyngton.santos
 */
public interface ILogRepository extends JpaRepository<Log, Serializable>{
    
}
