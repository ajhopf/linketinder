package com.linketinder.service

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

    List<Competencia> listarCompetenciasDeUsuario(Integer usuarioId) {
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
