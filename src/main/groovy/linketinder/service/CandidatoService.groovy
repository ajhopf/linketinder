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
            List<Competencia> competencias = competenciaService.listarCompetenciasDeCandidato(usuarioId)

            return CandidatoMapper.toEntity(candidatoDTO, endereco, competencias)
        } catch (SQLException e){
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }


    List<Candidato> listarCandidatos() throws SQLException {
        List<Candidato> candidatos = []

        try {
            List<CandidatoDTO> candidatoDTOList = repository.listarCandidatos()

            for (candidatoDTO in candidatoDTOList) {
                Endereco endereco = enderecoService.obterEnderecoDoUsuario(candidatoDTO.id)
                List<Competencia> competencias = competenciaService.listarCompetenciasDeCandidato(candidatoDTO.id)
                Candidato candidato = CandidatoMapper.toEntity(candidatoDTO, endereco, competencias)

                candidatos << candidato
            }

            return candidatos
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void adicionarCompetenciasDoCandidato(List<Competencia> competencias, Integer candidatoId) {
        competencias.each {competencia ->
            try {
                competenciaService.adicionarCompetenciaCandidato(competencia, candidatoId)
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

            competenciaService.deletarCompetenciasDeCandidato(candidatoDTO.id)
            adicionarCompetenciasDoCandidato(candidato.competencias, candidatoDTO.id)

            enderecoService.updateEnderecoDoUsuario(candidato.endereco, candidatoDTO.id)
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
