/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.gov.to.secad.aede.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author wellyngton.santos
 */
public class LogPhaseListener  implements PhaseListener{
     // Antes de uma fase
    @Override
    public void afterPhase(PhaseEvent pe) {
    }

    // Depois de uma fase
    @Override
    public void beforePhase(PhaseEvent event) {
        
        System.out.println("FASE do Ciclo de Vida: " + event.getPhaseId());
        
    }

    // Qual fase o Listener atende
    @Override
    public PhaseId getPhaseId() {
        
        //Retorna todas as fases
        return PhaseId.ANY_PHASE;
        
    }

}
