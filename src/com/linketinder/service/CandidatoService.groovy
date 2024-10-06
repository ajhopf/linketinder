package com.linketinder.service

import com.linketinder.exceptions.CandidatoNotFoundException
import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.mappers.CandidatoMapper
import com.linketinder.repository.CandidatoRepository

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
            List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuario(usuarioId)

            return CandidatoMapper.toEntity(candidatoDTO, endereco, competencias)
        } catch (SQLException e){
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (CandidatoNotFoundException e) {
            throw e
        }
    }

    void deletarCandidatoPeloId(Integer usuarioId) throws RepositoryAccessException, CandidatoNotFoundException{
       try {
            repository.obterCandidatoPeloId(usuarioId)
            repository.deletarCandidatoPeloId(usuarioId)
       } catch (SQLException e) {
           throw new RepositoryAccessException(e.getMessage(), e.getCause())
       } catch (CandidatoNotFoundException e) {
           throw e
       }
    }

    void adicionarCandidato(Candidato candidato) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CandidatoDTO candidatoDTO = CandidatoMapper.toDTO(candidato)

            for (competencia in candidato.competencias) {
                competenciaService.verificarSeCompetenciaExiste(competencia.competencia)
            }

            Integer usuarioId = repository.adicionarCandidato(candidatoDTO)

            enderecoService.adicionarEndereco(candidato.endereco, usuarioId)

            for (competencia in candidato.competencias) {
                competenciaService.adicionarCompetenciaDeUsuario(competencia, usuarioId)
            }

            println "Candidato criado com sucesso! Id: $usuarioId"
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw new CompetenciaNotFoundException(e.getMessage() + ' . Candidato não foi criado')
        }

    }

    List<Candidato> listarCandidatos() throws SQLException {
        List<Candidato> candidatos = []

        try {
            List<CandidatoDTO> candidatoDTOList = repository.listarCandidatos()

            for (candidato in candidatoDTOList) {
                Endereco endereco = enderecoService.obterEnderecoDoUsuario(candidato.id)
                List<Competencia> competencias = competenciaService.listarCompetenciasDeUsuario(candidato.id)
                candidatos << CandidatoMapper.toEntity(candidato, endereco, competencias)
            }

            return candidatos
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }
}
