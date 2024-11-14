package linketinder.model.mappers

import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Endereco
import linketinder.model.dtos.CandidatoControllerResponseDTO
import linketinder.model.dtos.CandidatoDTO

class CandidatoMapper {
    static CandidatoDTO toDTO(Candidato candidato) {
        return new CandidatoDTO(
                id: candidato.id,
                nome: candidato.nome,
                sobrenome: candidato.sobrenome,
                email: candidato.email,
                descricao: candidato.descricao,
                cpf: candidato.cpf,
                dataNascimento: candidato.dataNascimento,
                senha: candidato.senha,
                telefone: candidato.telefone
        )
    }

    static List<CandidatoControllerResponseDTO> toControllerResponseDTOList(List<Candidato> candidatos) {
        List<CandidatoControllerResponseDTO> candidatoControllerResponseDTOS = []

        candidatos.each {Candidato  candidato ->
            candidatoControllerResponseDTOS << toControllerResponseDTO(candidato)
        }

        return candidatoControllerResponseDTOS
    }

    static CandidatoControllerResponseDTO toControllerResponseDTO(Candidato candidato) {
        return new CandidatoControllerResponseDTO(
                id: candidato.id,
                nome: candidato.nome,
                sobrenome: candidato.sobrenome,
                email: candidato.email,
                descricao: candidato.descricao,
                cpf: candidato.cpf,
                dataNascimento: candidato.dataNascimento,
                telefone: candidato.telefone,
                endereco: candidato.endereco,
                competencias: candidato.competencias
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
                competencias:  competencias,
                senha: candidatoDTO.senha,
                telefone: candidatoDTO.telefone
        )
    }
}
