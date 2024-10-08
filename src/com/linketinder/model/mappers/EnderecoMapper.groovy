package com.linketinder.model.mappers

import com.linketinder.model.Endereco
import com.linketinder.model.dtos.EnderecoDTO

class EnderecoMapper {
    static EnderecoDTO toDTO(Endereco endereco, Integer usuarioId) {
        return new EnderecoDTO(
                usuarioId: usuarioId,
                cep: endereco.cep,
                pais: endereco.pais,
                cidade: endereco.cidade,
                estado: endereco.estado
        )
    }

    static EnderecoDTO toDTO(Endereco endereco) {
        return new EnderecoDTO(
                cep: endereco.cep,
                pais: endereco.pais,
                cidade: endereco.cidade,
                estado: endereco.estado
        )
    }

    static Endereco toEntity(EnderecoDTO enderecoDTO) {
        return new Endereco(
                cep: enderecoDTO.cep,
                pais: enderecoDTO.pais,
                cidade: enderecoDTO.cidade,
                estado: enderecoDTO.estado
        )
    }
}
