/* formulario1.jrxml */
SELECT 
	av.nome,
	av.cargo_nome,
	av.numfunc,
	av.numvinc,
	av.dtadmissao,
	av.dtavaliacao
FROM aede.avaliacao as av
WHERE av.id = 6764

----------------------------------

/* formulario1_membro_comissao.jrxml */
SELECT 
	--av.nome,
	mc.nome as membro
FROM aede.avaliacao as av
INNER JOIN aede.comissao c ON av.comissao = c.id
INNER JOIN aede.membro_comissao mc ON mc.comissao_id = c.id
WHERE av.id = 6764

----------------------------------

select * from aede.membro
select * from aede.membro_comissao
select * from aede.comissao

select nome, comissao, formulario, cargo_id, cargo_nome from aede.avaliacao

select c.id, av.nome, av.orgao_sigla, av.setor_uf, av.cargo_nome, av.etapa, av.comissao from aede.avaliacao av
inner join aede.comissao c on av.comissao = c.id


