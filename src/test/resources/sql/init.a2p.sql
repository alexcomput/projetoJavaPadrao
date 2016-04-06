--SISTEMA
INSERT INTO a2p.sistema(id, descricao, sigla, dt_inicio, dt_desativacao, dt_termino,fl_ativo)
VALUES (1, 'Autenticação e Autorização por Perfil', 'A2P', '2011-11-04 00:00:00', null, null, true);

--ARTEFATO
INSERT INTO a2p.RECURSO(id, sistema_id, descricao, url, dt_inicio, dt_termino, id_tipo)
VALUES (1, 1, 'formulário de edição de usuário', '/admin/editUsuario.html', '2011-11-04 00:00:00', null, ?);

--PERMISSAO
INSERT INTO a2p.permissao(id, descricao, id_artefato, dt_inicio, dt_termino)
VALUES (1, 'CRUD', 1, '2011-11-04 00:00:00', null);

--GRUPO
INSERT INTO a2p.grupo(id, sistema_id, descricao, fl_ativo, dt_desativacao, dt_inicio, dt_termino)
VALUES (1, 1, 'FOLHA - faz o quê no sistema de folha?', true, null, '2011-11-04 00:00:00', null);

--GRUPO_PERMISSAO
INSERT INTO a2p.grupo_permissao(id_grupo, id_permissao, dt_inicio, dt_termino)
VALUES (1, 1, '2011-11-04 00:00:00', null);
--USUARIO --> senha 12345678
INSERT INTO a2p.usuario(id, login, e_mail, senha, nome, sexo, dt_nascimento, fl_ativo, dt_desativacao, dt_criacao, fl_alterasenha, celular, end_cep, end_logradouro, end_uf, end_municipio, end_bairro, dt_expiracao)
VALUES (1,'10000000000','a2p_user@secad.to.gov.br','/KN9DXHo3sd8iK8Mq8ivKoSFLbqJBoVI', 'A2P User - Usuário administrador do módulo A2P', 'M', '2011-11-04 00:00:00', true, null, '2011-11-04 00:00:00', true, '(63)32181536', '77000000', 'Praça dos Girassóis', 'TO', 'Palmas', 'Centro', null);

--USUARIO_GRUPO
INSERT INTO a2p.usuario_grupo(id_grupo, id_usuario, dt_inicio, dt_termino)
VALUES (1, 1, '2011-11-04 00:00:00', null);
