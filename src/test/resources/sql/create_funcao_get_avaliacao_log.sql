CREATE FUNCTION aede.get_log_avaliacao()
            RETURNS trigger AS
            $body$
            BEGIN
            	INSERT INTO aede.avaliacao_log SELECT old.*;
            	RETURN NULL;
            END;
            $body$
            LANGUAGE 'plpgsql'
            VOLATILE
            CALLED ON NULL INPUT
            SECURITY INVOKER;