--ATENÇÃO: este script deve ser executado após a excução do script usr.sql e com a sessão aberta como usuário a2p_user


ALTER TABLE bcoproducao.a2p.grupo_permissao DROP CONSTRAINT grupo_grupo_permissao_fk;
ALTER TABLE bcoproducao.a2p.usuario_grupo DROP CONSTRAINT grupo_perfil_fk;
ALTER TABLE bcoproducao.a2p.usuario_perfil DROP CONSTRAINT perfil_usuario_perfil_fk;
ALTER TABLE bcoproducao.a2p.grupo_permissao DROP CONSTRAINT permissao_grupo_permissao_fk;
ALTER TABLE bcoproducao.a2p.permissao DROP CONSTRAINT recurso_permissoes_fk;
ALTER TABLE bcoproducao.a2p.grupo DROP CONSTRAINT sistema_grupo_fk;
ALTER TABLE bcoproducao.a2p.perfil DROP CONSTRAINT sistema_perfil_fk;
ALTER TABLE bcoproducao.a2p.recurso DROP CONSTRAINT sistema_programa_fk;
ALTER TABLE bcoproducao.a2p.repara_senha DROP CONSTRAINT usuario_repara_senha_fk;
ALTER TABLE bcoproducao.a2p.usuario_grupo DROP CONSTRAINT usuario_usuario_grupo_fk;
ALTER TABLE bcoproducao.a2p.usuario_perfil DROP CONSTRAINT usuario_usuario_perfil_fk;

DROP TABLE bcoproducao.a2p.grupo;
DROP TABLE bcoproducao.a2p.grupo_permissao;
DROP TABLE bcoproducao.a2p.perfil;
DROP TABLE bcoproducao.a2p.permissao;
DROP TABLE bcoproducao.a2p.recurso;
DROP TABLE bcoproducao.a2p.repara_senha;
DROP TABLE bcoproducao.a2p.sistema;
DROP TABLE bcoproducao.a2p.usuario;
DROP TABLE bcoproducao.a2p.usuario_grupo;
DROP TABLE bcoproducao.a2p.usuario_perfil;

CREATE SEQUENCE a2p.usuario_id_seq;

CREATE TABLE a2p.usuario (
                id INTEGER NOT NULL DEFAULT nextval('a2p.usuario_id_seq'),
                cpf VARCHAR(11) NOT NULL,
                senha VARCHAR(50) NOT NULL,
                dica_de_senha VARCHAR(255) NOT NULL,
                dt_criacao DATE NOT NULL,
                dt_desativacao TIMESTAMP NOT NULL,
                dt_expiracao DATE,
                fl_ativo BOOLEAN DEFAULT false,
                fl_altera_senha_proximo_logon BOOLEAN DEFAULT false,
                email VARCHAR(100) NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id)
);
COMMENT ON TABLE a2p.usuario IS 'Campos fornecidos pela tabela ger.ger_pessoa:
CPF;
Nome;
Endereço';
COMMENT ON COLUMN a2p.usuario.cpf IS 'CPF do individuo - fornecido pela tabela ger.ger_pessoa';


ALTER SEQUENCE a2p.usuario_id_seq OWNED BY a2p.usuario.id;

CREATE TABLE a2p.repara_senha (
                usuario_id INTEGER NOT NULL,
                codigo_reparacao VARCHAR(32) NOT NULL,
                dt_solicitacao TIMESTAMP NOT NULL,
                dt_reparacao TIMESTAMP,
                ip_solicitacao VARCHAR(20) NOT NULL,
                ip_reparacao VARCHAR(20),
                CONSTRAINT repara_senha_pk PRIMARY KEY (usuario_id, codigo_reparacao)
);
COMMENT ON COLUMN a2p.repara_senha.usuario_id IS 'identificador de usuário';
COMMENT ON COLUMN a2p.repara_senha.codigo_reparacao IS 'hash gerado para reparar a senha';
COMMENT ON COLUMN a2p.repara_senha.dt_solicitacao IS 'data e hora da solicitacao';
COMMENT ON COLUMN a2p.repara_senha.dt_reparacao IS 'data que a reparação foi realizada';
COMMENT ON COLUMN a2p.repara_senha.ip_solicitacao IS 'endereço IP que solicitou a reparação';
COMMENT ON COLUMN a2p.repara_senha.ip_reparacao IS 'endereço IP que reparou';


CREATE SEQUENCE a2p.sistema_id_seq;

CREATE TABLE a2p.sistema (
                id INTEGER NOT NULL DEFAULT nextval('a2p.sistema_id_seq'),
                descricao VARCHAR(255) NOT NULL,
                nome VARCHAR(100) NOT NULL,
                sigla VARCHAR(10) NOT NULL,
                dt_inicio TIMESTAMP NOT NULL,
                dt_criacao DATE NOT NULL,
                dt_desativacao TIMESTAMP,
                dt_termino TIMESTAMP,
                fl_ativo BOOLEAN DEFAULT false NOT NULL,
                CONSTRAINT sistema_pk PRIMARY KEY (id)
);
COMMENT ON COLUMN a2p.sistema.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';
COMMENT ON COLUMN a2p.sistema.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';
COMMENT ON COLUMN a2p.sistema.fl_ativo IS 'Está em atividade, em uso?';


ALTER SEQUENCE a2p.sistema_id_seq OWNED BY a2p.sistema.id;

CREATE SEQUENCE a2p.perfil_id_seq;

CREATE TABLE a2p.perfil (
                id INTEGER NOT NULL DEFAULT nextval('a2p.perfil_id_seq'),
                sistema_id INTEGER NOT NULL,
                dt_inicio TIMESTAMP NOT NULL,
                dt_termino TIMESTAMP,
                descricao VARCHAR(100) NOT NULL,
                CONSTRAINT perfil_pk PRIMARY KEY (id)
);
COMMENT ON COLUMN a2p.perfil.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';
COMMENT ON COLUMN a2p.perfil.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';


ALTER SEQUENCE a2p.perfil_id_seq OWNED BY a2p.perfil.id;

CREATE UNIQUE INDEX perfil_uk
 ON a2p.perfil
 ( id, sistema_id );

CREATE TABLE a2p.usuario_perfil (
                usuario_id INTEGER NOT NULL,
                perfil_id INTEGER NOT NULL,
                dt_termino TIMESTAMP,
                dt_inicio TIMESTAMP NOT NULL,
                fl_ativo BOOLEAN DEFAULT false NOT NULL,
                dt_desativacao TIMESTAMP,
                CONSTRAINT usuario_perfil_pk PRIMARY KEY (usuario_id, perfil_id)
);
COMMENT ON COLUMN a2p.usuario_perfil.usuario_id IS 'identificador de usuário';
COMMENT ON COLUMN a2p.usuario_perfil.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';
COMMENT ON COLUMN a2p.usuario_perfil.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';


CREATE SEQUENCE a2p.recurso_id_seq;

CREATE TABLE a2p.recurso (
                id INTEGER NOT NULL DEFAULT nextval('a2p.recurso_id_seq'),
                sistema_id INTEGER NOT NULL,
                descricao VARCHAR(255) NOT NULL,
                nome VARCHAR(100) NOT NULL,
                dt_criacao DATE NOT NULL,
                dt_termino TIMESTAMP,
                dt_inicio TIMESTAMP NOT NULL,
                id_tipo SMALLINT,
                CONSTRAINT recurso_pk PRIMARY KEY (id)
);
COMMENT ON COLUMN a2p.recurso.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';
COMMENT ON COLUMN a2p.recurso.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';


ALTER SEQUENCE a2p.recurso_id_seq OWNED BY a2p.recurso.id;

CREATE UNIQUE INDEX artefato_uk
 ON a2p.recurso
 ( id, sistema_id );

CREATE SEQUENCE a2p.permissao_id_seq;

CREATE TABLE a2p.permissao (
                id INTEGER NOT NULL DEFAULT nextval('a2p.permissao_id_seq'),
                descricao VARCHAR(255) NOT NULL,
                nome VARCHAR(100) NOT NULL,
                recurso_id INTEGER NOT NULL,
                dt_inicio TIMESTAMP NOT NULL,
                dt_termino TIMESTAMP,
                CONSTRAINT permissao_pk PRIMARY KEY (id)
);
COMMENT ON COLUMN a2p.permissao.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';
COMMENT ON COLUMN a2p.permissao.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';


ALTER SEQUENCE a2p.permissao_id_seq OWNED BY a2p.permissao.id;

CREATE UNIQUE INDEX permissao_uk
 ON a2p.permissao
 ( id, recurso_id );

CREATE SEQUENCE a2p.grupo_id_seq;

CREATE TABLE a2p.grupo (
                id INTEGER NOT NULL DEFAULT nextval('a2p.grupo_id_seq'),
                sistema_id INTEGER NOT NULL,
                descricao VARCHAR(100) NOT NULL,
                fl_ativo BOOLEAN DEFAULT false,
                dt_desativacao TIMESTAMP,
                dt_inicio TIMESTAMP NOT NULL,
                dt_termino TIMESTAMP,
                CONSTRAINT grupo_pk PRIMARY KEY (id)
);
COMMENT ON COLUMN a2p.grupo.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';
COMMENT ON COLUMN a2p.grupo.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';


ALTER SEQUENCE a2p.grupo_id_seq OWNED BY a2p.grupo.id;

CREATE UNIQUE INDEX grupo_uk
 ON a2p.grupo
 ( id, sistema_id );

CREATE TABLE a2p.usuario_grupo (
                grupo_id INTEGER NOT NULL,
                usuario_id INTEGER NOT NULL,
                CONSTRAINT usuario_grupo_pk PRIMARY KEY (grupo_id, usuario_id)
);
COMMENT ON COLUMN a2p.usuario_grupo.usuario_id IS 'identificador de usuário';
COMMENT ON COLUMN a2p.usuario_grupo.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';
COMMENT ON COLUMN a2p.usuario_grupo.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';


CREATE TABLE a2p.grupo_permissao (
                grupo_id INTEGER NOT NULL,
                permissao_id INTEGER NOT NULL,
                CONSTRAINT grupo_permissao_pk PRIMARY KEY (grupo_id, permissao_id)
);
COMMENT ON COLUMN a2p.grupo_permissao.dt_termino IS 'Estabelece o TÉRMINO de atividade deste';
COMMENT ON COLUMN a2p.grupo_permissao.dt_inicio IS 'Estabelece o INÍCIO de atividade deste';


ALTER TABLE a2p.usuario_grupo ADD CONSTRAINT usuario_usuario_grupo_fk
FOREIGN KEY (usuario_id)
REFERENCES a2p.usuario (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.repara_senha ADD CONSTRAINT usuario_repara_senha_fk
FOREIGN KEY (usuario_id)
REFERENCES a2p.usuario (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.usuario_perfil ADD CONSTRAINT usuario_usuario_perfil_fk
FOREIGN KEY (usuario_id)
REFERENCES a2p.usuario (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.recurso ADD CONSTRAINT sistema_programa_fk
FOREIGN KEY (sistema_id)
REFERENCES a2p.sistema (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.grupo ADD CONSTRAINT sistema_grupo_fk
FOREIGN KEY (sistema_id)
REFERENCES a2p.sistema (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.perfil ADD CONSTRAINT sistema_perfil_fk
FOREIGN KEY (sistema_id)
REFERENCES a2p.sistema (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.usuario_perfil ADD CONSTRAINT perfil_usuario_perfil_fk
FOREIGN KEY (perfil_id)
REFERENCES a2p.perfil (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.permissao ADD CONSTRAINT recurso_permissoes_fk
FOREIGN KEY (recurso_id)
REFERENCES a2p.recurso (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.grupo_permissao ADD CONSTRAINT permissao_grupo_permissao_fk
FOREIGN KEY (permissao_id)
REFERENCES a2p.permissao (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.grupo_permissao ADD CONSTRAINT grupo_grupo_permissao_fk
FOREIGN KEY (grupo_id)
REFERENCES a2p.grupo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE a2p.usuario_grupo ADD CONSTRAINT grupo_perfil_fk
FOREIGN KEY (grupo_id)
REFERENCES a2p.grupo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;