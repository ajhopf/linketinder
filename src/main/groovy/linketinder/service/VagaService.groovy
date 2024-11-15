package linketinder.service

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Competencia
import linketinder.model.Vaga
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.dtos.EnderecoDTO
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO
import linketinder.model.mappers.CompetenciaMapper
import linketinder.model.mappers.EnderecoMapper
import linketinder.model.mappers.VagaMapper
import linketinder.repository.VagaRepository

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

    List<CompetenciaDTO> mapCompetenciasToCompetenciaDto(List<Competencia> competencias) {
        List<CompetenciaDTO> competenciaDTOList = []

        for (competencia in competencias) {
            Integer competenciaId = competenciaService.obterIdDeCompetencia(competencia.competencia)

            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
            competenciaDTO.id = competenciaId
            competenciaDTOList << competenciaDTO
        }

        return competenciaDTOList
    }

    VagaRequestDTO construirVagaRequestDto(Vaga vaga) {
        EnderecoDTO enderecoDTO = EnderecoMapper.toDTO(vaga.endereco)
        Integer enderecoId = enderecoService.adicionarEndereco(enderecoDTO)

        VagaRequestDTO vagaRequestDTO = VagaMapper.toRequestDTO(vaga, enderecoId)

        List<CompetenciaDTO> competenciaDTOList = mapCompetenciasToCompetenciaDto(vaga.competencias)

        vagaRequestDTO.competenciaDTOList = competenciaDTOList

        return vagaRequestDTO
    }

    Vaga obterVagaPeloId (Integer vagaId) throws VagaNotFoundException, RepositoryAccessException {
        try {
            VagaResponseDTO vagaResponseDTO = repository.obterVagaPeloId(vagaId)

            Vaga vaga = VagaMapper.toEntity(vagaResponseDTO)
            vaga.competencias = competenciaService.listarCompetenciasDeVaga(vagaId)

            return vaga
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    List<Vaga> listarVagas() throws RepositoryAccessException  {
        try {
            List<Vaga> vagas = []

            List<VagaResponseDTO> vagasDTOList = repository.listarVagas()

            for (vagaDTO in vagasDTOList) {
                Vaga vaga = VagaMapper.toEntity(vagaDTO)

                List<Competencia> competencias = competenciaService.listarCompetenciasDeVaga(vagaDTO.id)

                vaga.competencias = competencias
                vagas << vaga
            }

            return vagas
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    void adicionarCompetenciasDaVaga(List<Competencia> competencias, Integer vagaId) {
        competencias.each {competencia ->
            try {
                competenciaService.adicionarCompetenciaVaga(competencia, vagaId)
            } catch(CompetenciaNotFoundException e) {
                println "Não foi possível adicionar a competencia $competencia à vaga."
                println e.getMessage()
            }
        }

    }

    Integer adicionarVaga(Vaga vaga) throws RepositoryAccessException {
        try {
            VagaRequestDTO vagaRequestDTO = construirVagaRequestDto(vaga)

            Integer vagaId = repository.adicionarVaga(vagaRequestDTO)

            adicionarCompetenciasDaVaga(vaga.competencias, vagaId)

            return vagaId
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    void updateVaga(Vaga vagaAtualizada) throws RepositoryAccessException {
        try {
            competenciaService.deletarCompetenciasDeVaga(vagaAtualizada.id)

            VagaRequestDTO vagaRequestDTO = construirVagaRequestDto(vagaAtualizada)

            repository.updateVaga(vagaAtualizada.id, vagaRequestDTO)

            adicionarCompetenciasDaVaga(vagaAtualizada.competencias, vagaAtualizada.id)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }

    }

    void deletarVaga(Integer vagaId) throws RepositoryAccessException, VagaNotFoundException{
        try {
            repository.obterVagaPeloId(vagaId)
            repository.deletarVaga(vagaId)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (VagaNotFoundException e) {
            throw e
        }
    }

}
