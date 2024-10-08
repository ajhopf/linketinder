package com.linketinder.model.mappers

import com.linketinder.model.Endereco
import com.linketinder.model.Vaga
import com.linketinder.model.dtos.VagaRequestDTO
import com.linketinder.model.dtos.VagaResponseDTO

class VagaMapper {
    static VagaRequestDTO toRequestDTO(Vaga vaga, Integer enderecoId) {
        return new VagaRequestDTO(
                id: vaga.id,
                nome: vaga.nome,
                descricao: vaga.descricao,
                empresaId: vaga.empresa.id,
                enderecoId: enderecoId
        )
    }

    static Vaga toEntity(VagaRequestDTO vagaDTO, Endereco endereco) {
        return new Vaga(
                id: vagaDTO.id,
                nome: vagaDTO.nome,
                descricao: vagaDTO.descricao,
                endereco: endereco,
        )
    }

    static Vaga toEntity(VagaResponseDTO vagaResponseDTO) {
        Endereco enderecoSimples = new Endereco()
        enderecoSimples.estado = vagaResponseDTO.estado
        enderecoSimples.cidade = vagaResponseDTO.cidade

        return new Vaga(
                id: vagaResponseDTO.id,
                nome: vagaResponseDTO.nome,
                descricao: vagaResponseDTO.descricao,
                endereco: enderecoSimples,
        )
    }
}
