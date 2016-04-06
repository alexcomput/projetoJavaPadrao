SELECT a.dtfim, * FROM aede.avaliacao a WHERE a.dtini = '2015-09-01'
SELECT * FROM aede.avaliacao a WHERE a.dtini BETWEEN '2015-09-01' AND '2015-09-02'
SELECT * FROM aede.avaliacao a WHERE a.dtini = '2015-09-01';

select * from aede.avaliacao
select l.etapa, * from aede.log_avaliacao l
inner join aede.avaliacao a ON l.avaliacao_id = a.id
where a.numfunc = '1004298' and a.etapa = 1


/*
1002163
1002856
11159804
1002929
11159804
1000691
1004298
*/
