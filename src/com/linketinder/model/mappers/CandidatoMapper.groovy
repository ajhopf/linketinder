package com.linketinder.model.mappers

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO

class CandidatoMapper {
    static CandidatoDTO toDTO(Candidato candidato) {
        return new CandidatoDTO(
                id: candidato.id,
                nome: candidato.nome,
                sobrenome: candidato.sobrenome,
                email: candidato.email,
                descricao: candidato.descricao,
                cpf: candidato.cpf,
                dataNascimento: candidato.dataNascimento
        )
    }

    static Candidato toEntity(CandidatoDTO candidatoDTO, Endereco endereco, List<Competencia> competencias) {
        return new Candidato(
                id: candidatoDTO.id,
                nome: candidatoDTO.nome,
                sobrenome: candidatoDTO.sobrenome,
                email: candidatoDTO.email,
                descricao: candidatoDTO.descricao,
                cpf: candidatoDTO.cpf,
                dataNascimento: candidatoDTO.dataNascimento,
                endereco: endereco,
                competencias:  competencias
        )
    }
}
