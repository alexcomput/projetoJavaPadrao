--Assumimos a existência do banco: "bcoproducao"

--Não podemos REMOVER um ROLE antes da REMOÇÃO do banco de dados, pois o banco de dados pertence ao ROLE(nos casos de crição de banco dados)
--Peçamos então ao administrador do banco para fazê-lo
DROP ROLE a2p;
--Desde da versão 8.1 o postresql assume USERS e GRUPS como ROLE. Todos pertecem ao cluster!
--cria o grupo do modulo a2p.
CREATE ROLE a2p
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
--cria o usuario do modulo a2p
CREATE ROLE a2p_user LOGIN
  ENCRYPTED PASSWORD 'a2p_user'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
GRANT a2p TO a2p_user;

-- DROP SCHEMA a2p;
CREATE SCHEMA a2p
  AUTHORIZATION a2p;

GRANT ALL ON SCHEMA a2p TO a2p;
GRANT ALL ON SCHEMA a2p TO "Administrador" WITH GRANT OPTION;
COMMENT ON SCHEMA a2p IS 'Schema utilizado pelo módulo Autenticação e Autorização por Perfil';