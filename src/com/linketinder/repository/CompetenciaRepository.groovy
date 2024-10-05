package com.linketinder.repository

import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.enums.Afinidade
import com.linketinder.repository.interfaces.ICompetenciaDAO
import groovy.sql.Sql

class CompetenciaRepository implements ICompetenciaDAO {
    private Sql sql = null

    CompetenciaRepository(Sql sql) {
        this.sql = sql
    }

    @Override
    List<CompetenciaDTO> listarCompetenciasDeUsuario(Integer usuarioId) {
        def statement = """
                select c.competencia, cu.afinidade, cu.anos_experiencia 
                from competencias_usuario cu
                INNER JOIN competencias c
                ON cu.competencia_id = c.id
                WHERE cu.usuario_id = $usuarioId;
            """

        List<CompetenciaDTO> competencias = []

        sql.eachRow(statement) {row ->
            CompetenciaDTO competencia = new CompetenciaDTO()
            competencia.competencia = row.getString('competencia')
            competencia.anosExperiencia = row.getInt('anos_experiencia')
            competencia.afinidade = Afinidade.fromValor(row.getInt('afinidade'))

            competencias << competencia
        }

        return competencias
    }
}
