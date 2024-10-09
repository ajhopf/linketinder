package linketinder.service

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

    Vaga obterVagaPeloId (Integer vagaId) {
        try {
            VagaResponseDTO vagaResponseDTO = repository.obterVagaPeloId(vagaId)

            return VagaMapper.toEntity(vagaResponseDTO)
        } catch (VagaNotFoundException e) {
            throw e
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

                List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuarioOuVaga(vagaDTO.id, 'competencias_vaga')

                vaga.competencias = competencias
                vagas << vaga
            }

            return vagas
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    List<CompetenciaDTO> mapCompetenciasToCompetenciaDto(List<Competencia> competencias) {
        List<CompetenciaDTO> competenciaDTOList = []

        for (competencia in competencias) {
            Integer competenciaId = competenciaService.verificarSeCompetenciaExiste(competencia.competencia)
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
            competenciaDTO.id = competenciaId
            competenciaDTOList << competenciaDTO
        }

        return competenciaDTOList
    }

    Integer adicionarVaga(Vaga vaga, boolean isUpdate = false) throws RepositoryAccessException {
        try {
            if (isUpdate) {
                competenciaService.deletarCompetenciaEntidade(vaga.id, 'competencias_vaga')
            }
            EnderecoDTO enderecoDTO = EnderecoMapper.toDTO(vaga.endereco)
            Integer enderecoId = enderecoService.adicionarEndereco(enderecoDTO)

            VagaRequestDTO vagaRequestDTO = VagaMapper.toRequestDTO(vaga, enderecoId)

            List<CompetenciaDTO> competenciaDTOList = mapCompetenciasToCompetenciaDto(vaga.competencias)

            vagaRequestDTO.competenciaDTOList = competenciaDTOList

            Integer vagaId
            if (isUpdate) {
                repository.updateVaga(vaga.id, vagaRequestDTO)
                vagaId = vaga.id
            } else {
                vagaId = repository.adicionarVaga(vagaRequestDTO)
            }

            return vagaId
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    void deleteVaga(Integer vagaId) throws RepositoryAccessException, VagaNotFoundException{
        try {
            repository.obterVagaPeloId(vagaId)
            repository.deleteVaga(vagaId)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (VagaNotFoundException e) {
            throw e
        }
    }

}
