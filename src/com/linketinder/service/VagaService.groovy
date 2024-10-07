package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Competencia
import com.linketinder.model.Vaga
import com.linketinder.model.dtos.VagaResponseDTO
import com.linketinder.model.mappers.VagaMapper
import com.linketinder.repository.VagaRepository

import java.sql.SQLException

class VagaService {
    VagaRepository repository
    CompetenciaService competenciaService

    VagaService (VagaRepository repository, CompetenciaService competenciaService) {
        this.repository = repository
        this.competenciaService = competenciaService
    }

    List<Vaga> listarVagas() {
        try {
            List<Vaga> vagas = []

            List<VagaResponseDTO> vagasDTOList = repository.listarVagas()

            for (vagaDTO in vagasDTOList) {
                Vaga vaga = VagaMapper.toEntity(vagaDTO)

                List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuarioOuVaga(vagaDTO.id, 'competencias_vaga')
                vaga.competencias = competencias
                vagas << vaga
            }

            return vagas
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }
}
