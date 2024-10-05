package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.EnderecoDTO

interface IEnderecoDAO {
    EnderecoDTO obterEnderecoPeloId(Integer id)
}
