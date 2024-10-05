package com.linketinder.service


import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.EnderecoDTO
import com.linketinder.model.mappers.EnderecoMapper
import com.linketinder.repository.EnderecoRepository

import java.sql.SQLException

class EnderecoService {
    EnderecoRepository repository

    EnderecoService(EnderecoRepository repository) {
        this.repository = repository
    }

    Endereco obterEnderecoDoUsuario(Integer id)  {
        try {
            EnderecoDTO enderecoDto = repository.obterEnderecoPeloId(id)

            return EnderecoMapper.toEntity(enderecoDto)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }
}
