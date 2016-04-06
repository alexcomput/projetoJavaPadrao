
------------- ATUAL

SELECT 
	av.status,
	av.nome,
	av.cargo_nome,
	av.numfunc,
	av.numvinc,
	f.quadro,
	av.setor_nome,
	av.regional_nome,
	av.etapa,
	f.ocupantes
FROM aede.avaliacao as av
	INNER JOIN aede.formulario as f ON av.formulario = f.id
WHERE
	f.excluido = 'N' AND
	f.ativo = true AND
	f.id = 42 AND 
	(av.status = 'TABULADA' OR av.status = 'RECURSO' OR av.status = 'CRIADA COMISSAO') AND
	av.id = 6792 AND
	av.numfunc = 1002163
	

select f.nome, * from aede.avaliacao as av
inner join aede.formulario as f
on av.formulario = f.id

select * from aede.avaliacao
select * from aede.tabulacao
select * from aede.formulario


------------- ANTIGO

SELECT 
	av.id as id_avaliacao,
	av.numfunc,
	av.numvinc,
	av.nome,
	av.cargo_nome,
	f.id as id_formulario,
	f.nome as descricao,
	f.quadro,
	gr.id as id_grupo,
	gr.nome as descricao_g,
	gr.ordem as ordemgrupo,
	ft.id as id_fator,
	ft.nome as questao,
	ft.ordem as ordemfator,
	av.setor_nome,
	av.regional_nome,
	av.etapa
FROM
	aede.avaliacao as av,
	aede.formulario as f,
	aede.grupo_fator as gr,
	aede.fator as ft
WHERE
	av.formulario = f.id AND
	f.id = gr.formulario AND
	gr.id = ft.grupo_fator AND
	f.excluido = 'N' AND
	gr.ativo = 't' AND
	f.ativo = 't' AND
	ft.ativo = 't' AND
	ft.excluido = 'N' AND
	f.id = 42 AND 
	(av.status = 'PROCESSADA' OR av.status = 'RECURSO')
	--AND av.id = 3
GROUP BY
	av.id,
	av.etapa,
	av.regional_nome,
	av.setor_nome,
	av.cargo_nome,
	av.numvinc,
	av.numfunc,
	--av.num_func,
	gr.nome,
	ft.id,
	av.nome,
	gr.id,
	ft.nome,
	ft.ordem,
	f.id,
	f.nome,
	f.quadro,
	gr.ordem
ORDER BY
	av.nome,
	gr.ordem,
	ft.ordem