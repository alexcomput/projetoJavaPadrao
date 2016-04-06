package br.gov.to.secad.ergon;

import org.eclipse.persistence.sessions.SessionEvent;
import org.eclipse.persistence.sessions.SessionEventAdapter;

public class ErgonSessionEventListener extends SessionEventAdapter {

    /*
    EXECUTA ANTES DE QUALQUER SQL DO ERGO SER GERADA
    ELA SETA EM QUAL EMPRESA IRA SER USADA
    
    */
    @Override
    public void postAcquireUnitOfWork(SessionEvent event) {
        event.getSession().getActiveSession().executeNonSelectingSQL("BEGIN FLAG_PACK.SET_EMPRESA(1); END;");
    }

}
