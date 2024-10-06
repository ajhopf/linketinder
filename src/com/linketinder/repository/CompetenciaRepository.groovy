package com.linketinder.repository

import com.linketinder.exceptions.CompetenciaNotFoundException
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

    @Override
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId) {
        def inserirCompetencia = """
            INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        def competenciaParams = [usuarioId, competenciaDTO.id, competenciaDTO.anosExperiencia, competenciaDTO.afinidade.getAfinidade()]

        sql.executeInsert(inserirCompetencia, competenciaParams)
    }

    @Override
    Integer obterIdDeCompetencia(String competenciaString) {
        def statement = """
            SELECT id FROM competencias c WHERE c.competencia LIKE $competenciaString
        """

        def row = sql.firstRow(statement)

        if (row == null) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar a competência ${competenciaString}")
        }

        return row.id as Integer
    }
}
