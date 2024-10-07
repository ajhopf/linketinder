package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Competencia
import com.linketinder.model.Vaga
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.dtos.EnderecoDTO
import com.linketinder.model.dtos.VagaRequestDTO
import com.linketinder.model.dtos.VagaResponseDTO
import com.linketinder.model.mappers.EnderecoMapper
import com.linketinder.model.mappers.VagaMapper
import com.linketinder.repository.VagaRepository

import java.sql.SQLException

class VagaService {
    VagaRepository repository
    CompetenciaService competenciaService
    EnderecoService enderecoService

    VagaService (VagaRepository repository, CompetenciaService competenciaService, EnderecoService enderecoService) {
        this.repository = repository
        this.competenciaService = competenciaService
        this.enderecoService = enderecoService
    }

    List<Vaga> listarVagas() throws RepositoryAccessException  {
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

    Integer adicionarVaga(Vaga vaga) throws RepositoryAccessException {
        try {
            EnderecoDTO enderecoDTO = EnderecoMapper.toDTO(vaga.endereco)
            Integer enderecoId = enderecoService.adicionarEndereco(enderecoDTO)

            VagaRequestDTO vagaRequestDTO = VagaMapper.toRequestDTO(vaga, enderecoId)

            List<CompetenciaDTO> competenciaDTOList = []

            for (competencia in vaga.competencias) {
                Integer competenciaId = competenciaService.verificarSeCompetenciaExiste(competencia.competencia)
                CompetenciaDTO competenciaDTO = new CompetenciaDTO(
                        competencia: competencia.competencia,
                        anosExperiencia: competencia.anosExperiencia,
                        afinidade: competencia.afinidade,
                        id: competenciaId
                )
                competenciaDTOList << competenciaDTO
            }

            vagaRequestDTO.competenciaDTOList = competenciaDTOList

            Integer vagaId = repository.adicionarVaga(vagaRequestDTO)

            return vagaId
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }
}
