package com.linketinder.repository

import com.linketinder.exceptions.CandidatoNotFoundException
import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.enums.Afinidade
import com.linketinder.repository.interfaces.ICompetenciaDAO
import com.linketinder.service.CompetenciaService
import com.linketinder.util.InputHelpers
import groovy.sql.Sql

import java.sql.SQLException

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
    List<CompetenciaDTO> listarCompetencias() {
        def statement = """
                SELECT *
                FROM competencias c
            """

        List<CompetenciaDTO> competencias = []

        sql.eachRow(statement) {row ->
            CompetenciaDTO competencia = new CompetenciaDTO()
            competencia.competencia = row.getString('competencia')
            competencia.id = row.getInt('id')

            competencias << competencia
        }

        return competencias
    }

    @Override
    CompetenciaDTO obterCompetenciaPeloId(Integer id) {
        def statement = """
                SELECT *
                FROM competencias c
                WHERE c.id = $id 
            """

        CompetenciaDTO competenciaDTO = new CompetenciaDTO()

        this.sql.eachRow(statement) {row ->
            competenciaDTO.competencia = row.getString('competencia')
            competenciaDTO.id = row.getInt('id')
        }

        if (competenciaDTO == null) {
            throw new CompetenciaNotFoundException("Não foi possível localizar a competencia com o id = $id")
        }

        return competenciaDTO
    }

    @Override
    Integer adicionarCompetencia(String competencia) {
        def inserirCompetencia = """
            INSERT INTO competencias (competencia)
            VALUES ($competencia)
        """

        def keys = sql.executeInsert(inserirCompetencia)

        return keys[0][0]
    }

    @Override
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId)  {
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

    @Override
    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia) {
        def statement = """
            UPDATE competencias c
            SET competencia = $competencia.competencia
            WHERE c.id = $competenciaId
        """

        def result = sql.executeUpdate(statement)

        if (result == 0 ) {
            throw new CompetenciaNotFoundException('Não foi possível atualizar a competencia')
        }
    }

    @Override
    void deleteCompetencia(Integer id) {
        def statement = """
            DELETE FROM competencias c
            WHERE c.id = $id
        """

        int rowsAffected = sql.executeUpdate(statement);

        if (rowsAffected == 0) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar uma competencia com id $id")
        }
    }
}
