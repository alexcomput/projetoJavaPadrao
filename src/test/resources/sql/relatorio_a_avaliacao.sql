
SELECT 
	av.numfunc,
	av.nome,
	f.id as id_formulario,
	f.nome as descricao,
	f.quadro,
	f.ocupantes,
	gr.id as id_grupo,
	gr.nome as descricao_g,
	gr.ordem as ordemgrupo,
	ft.id as id_fator,
	ft.nome as questao,
	ft.ordem as ordemfator,
	tb.nota,
	tb.nota_revisao




/*
SELECT FLOOR(AVG(CASE WHEN tt.nota_revisao > 0 THEN tt.nota_revisao ELSE tt.nota END)) FROM aede.tabulacao as tt, aede.avaliacao as ava 
		WHERE tt.avaliacao_id = ava.id AND ava.numfunc = 1000691 GROUP BY tt.nota_revisao

SELECT FLOOR(AVG(tt.nota_revisao)) FROM aede.tabulacao as tt, aede.avaliacao as ava 
		WHERE tt.avaliacao_id = ava.id AND ava.numfunc = 1000691
SELECT SUM(tt.nota) FROM aede.tabulacao as tt, aede.avaliacao as ava 
		WHERE tt.avaliacao_id = ava.id AND ava.numfunc = 1000691
SELECT tt.nota, tt.nota_revisao, * from aede.tabulacao tt where tt.avaliacao_id = 6776

SELECT ava.id, ava.numfunc, tt.nota, tt.nota_revisao, * from aede.tabulacao tt
inner join aede.avaliacao ava
on tt.avaliacao_id = ava.id AND ava.numfunc = 1000691
*/

FROM
	aede.avaliacao as av,
	aede.formulario as f,
	aede.grupo_fator as gr,
	aede.fator as ft,
	aede.tabulacao as tb
WHERE
	av.formulario = f.id AND
	f.id = gr.formulario AND 
	gr.id = ft.grupo_fator AND
	av.id = tb.avaliacao_id AND
	tb.fator = ft.id AND
	f.excluido = 'N' AND 
	gr.ativo = 'true' AND
	f.ativo = 'true' AND
	ft.ativo = 'true' AND 
	ft.excluido = 'N' --AND
	--av.numfunc = 1004158
GROUP BY
	av.numfunc,
	gr.nome,
	ft.id,
	av.nome,
	gr.id,
	ft.nome,
	ft.ordem,
	f.id,
	f.nome,
	f.quadro,
	gr.ordem,
	f.ocupantes,
	tb.nota,
	tb.nota_revisao
ORDER BY
	av.nome,
	gr.ordem,
	ft.ordem


