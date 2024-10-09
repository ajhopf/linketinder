package linketinder.repository.interfaces

import linketinder.model.dtos.EnderecoDTO

interface IEnderecoDAO {
    EnderecoDTO obterEnderecoDoUsuarioPeloId(Integer id)
    Integer obterIdDeEnderecoPeloCep(String cep)
    Integer adicionarNovoEndereco(EnderecoDTO enderecoDTO)
    void adicionarEnderecoParaUsuario(Integer usuarioId, Integer enderecoId)
    void updateEnderecoUsuario(Integer usuarioId, Integer enderecoId)
}
