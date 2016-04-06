CREATE TRIGGER set_log_avaliacao AFTER UPDATE OR DELETE
ON aede.avaliacao FOR EACH ROW
EXECUTE PROCEDURE aede.get_log_avaliacao();