package com.linketinder.repository.interfaces


import com.linketinder.model.dtos.EnderecoDTO

interface IEnderecoDAO {
    EnderecoDTO obterEnderecoDoUsuarioPeloId(Integer id)
    Integer obterIdDeEnderecoPeloCep(String cep)
    Integer adicionarNovoEndereco(EnderecoDTO enderecoDTO)
    void adicionarEnderecoParaUsuario(Integer usuarioId, Integer enderecoId)
}
