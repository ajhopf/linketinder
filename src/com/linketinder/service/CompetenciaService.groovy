package com.linketinder.service

import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Competencia
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.mappers.CompetenciaMapper
import com.linketinder.repository.CompetenciaRepository

import java.sql.SQLException

class CompetenciaService {
    CompetenciaRepository repository

    CompetenciaService (CompetenciaRepository repository) {
        this.repository = repository
    }

    void adicionarCompetenciaDeUsuario(Competencia competencia, Integer usuarioId) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
            Integer competenciaId = repository.obterIdDeCompetencia(competencia.competencia)
            competenciaDTO.id = competenciaId

            repository.adicionarCompetenciaUsuario(competenciaDTO, usuarioId)

        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }

    void verificarSeCompetenciaExiste(String competencia) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            repository.obterIdDeCompetencia(competencia)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    List<Competencia> listarCompetenciasDeUsuario(Integer usuarioId) throws RepositoryAccessException {
        try {
            List<CompetenciaDTO> competenciasDTO = repository.listarCompetenciasDeUsuario(usuarioId)
            List<Competencia> competencias = []

            for (competencia in competenciasDTO) {
                competencias << CompetenciaMapper.toEntity(competencia)
            }

            return competencias
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

}
