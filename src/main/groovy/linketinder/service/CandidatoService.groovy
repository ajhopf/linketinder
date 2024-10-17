package linketinder.service

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Endereco
import linketinder.model.dtos.CandidatoDTO
import linketinder.model.enums.TabelaCompetencia
import linketinder.model.mappers.CandidatoMapper
import linketinder.repository.CandidatoRepository

import java.sql.SQLException

class CandidatoService {
    CandidatoRepository repository
    EnderecoService enderecoService
    CompetenciaService competenciaService

    CandidatoService (CandidatoRepository repository, EnderecoService enderecoService, CompetenciaService competenciaService) {
        this.repository = repository
        this.enderecoService = enderecoService
        this.competenciaService = competenciaService
    }

    Candidato obterCandidatoPeloId(Integer usuarioId) throws RepositoryAccessException, CandidatoNotFoundException {
        try{
            CandidatoDTO candidatoDTO = repository.obterCandidatoPeloId(usuarioId)
            Endereco endereco = enderecoService.obterEnderecoDoUsuario(usuarioId)
            List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuarioOuVaga(usuarioId, TabelaCompetencia.COMPETENCIAS_CANDIDATO)

            return CandidatoMapper.toEntity(candidatoDTO, endereco, competencias)
        } catch (SQLException e){
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    List<Candidato> listarCandidatos() throws SQLException {
        List<Candidato> candidatos = []

        try {
            List<CandidatoDTO> candidatoDTOList = repository.listarCandidatos()

            for (candidato in candidatoDTOList) {
                Endereco endereco = enderecoService.obterEnderecoDoUsuario(candidato.id)
                List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuarioOuVaga(candidato.id, TabelaCompetencia.COMPETENCIAS_CANDIDATO)
                candidatos << CandidatoMapper.toEntity(candidato, endereco, competencias)
            }

            return candidatos
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    private void adicionarCompetenciasDoCandidato(List<Competencia> competencias, Integer candidatoId) {
        competencias.each {competencia ->
            try {
                competenciaService.adicionarCompetenciaDeUsuario(competencia, candidatoId)
            } catch(CompetenciaNotFoundException e) {
                println "Não foi possível adicionar a competencia $competencia ao candidato."
                println e.getMessage()
            }
        }
    }

    Integer adicionarCandidato(Candidato candidato) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CandidatoDTO candidatoDTO = CandidatoMapper.toDTO(candidato)

            Integer candidatoId = repository.adicionarCandidato(candidatoDTO)

            enderecoService.adicionarEnderecoParaUsuario(candidato.endereco, candidatoId)

            adicionarCompetenciasDoCandidato(candidato.competencias, candidatoId)

            return candidatoId
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void updateCandidato(Candidato candidato) throws SQLException {
        try {
            CandidatoDTO candidatoDTO = CandidatoMapper.toDTO(candidato)

            repository.updateCandidato(candidatoDTO)

            competenciaService.deletarCompetenciaEntidade(candidatoDTO.id, 'competencias_usuario')
            adicionarCompetenciasDoCandidato(candidato.competencias, candidatoDTO.id)

            enderecoService.adicionarEnderecoParaUsuario(candidato.endereco, candidatoDTO.id, true)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void deletarCandidatoPeloId(Integer usuarioId) throws RepositoryAccessException, CandidatoNotFoundException{
        try {
            repository.deletarCandidatoPeloId(usuarioId)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }
}
