package com.linketinder.service


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
//
    Candidato adicionarCandidato(Candidato candidato) {
//        Integer id = MyUtil.gerarNovoId(listarCandidatos())
//        candidato.id = id

        CandidatoDTO candidatoDTO = CandidatoMapper.toDTO(candidato)

        repository.adicionarCandidato(candidato)

        candidato
    }

    List<Candidato> listarCandidatos() {
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
