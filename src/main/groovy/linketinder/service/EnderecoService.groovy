package linketinder.service


import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Endereco
import linketinder.model.dtos.EnderecoDTO
import linketinder.model.mappers.EnderecoMapper
import linketinder.repository.EnderecoRepository

import java.sql.SQLException

class EnderecoService {
    EnderecoRepository repository

    EnderecoService(EnderecoRepository repository) {
        this.repository = repository
    }

    Endereco obterEnderecoPeloId(Integer id) {
        try {
            EnderecoDTO enderecoDTO = repository.obterEnderecoPeloId(id)

            return EnderecoMapper.toEntity(enderecoDTO)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    Endereco obterEnderecoDoUsuario(Integer id)  {
        try {
            EnderecoDTO enderecoDto = repository.obterEnderecoDoUsuarioPeloId(id)

            return EnderecoMapper.toEntity(enderecoDto)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    Integer adicionarEndereco(EnderecoDTO endereco) {
        try {
            Integer enderecoId = repository.obterIdDeEnderecoPeloCep(endereco.cep)

            if (enderecoId == -1) {
                enderecoId = repository.adicionarNovoEndereco(endereco)
            }

            return enderecoId
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }

    }

    private void manipularEnderecoHelper(Endereco endereco, Integer usuarioId, Closure enderecoFn) {
        try {
            EnderecoDTO enderecoDTO = EnderecoMapper.toDTO(endereco, usuarioId)

            Integer enderecoId = adicionarEndereco(enderecoDTO)

            enderecoFn.call(usuarioId, enderecoId)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void adicionarEnderecoParaUsuario(Endereco endereco, Integer usuarioId) {
        manipularEnderecoHelper(endereco, usuarioId) { Integer id, Integer enderecoId ->
            repository.adicionarEnderecoParaUsuario(id, enderecoId)
        }
    }

    void updateEnderecoDoUsuario(Endereco endereco, Integer usuarioId) {
        manipularEnderecoHelper(endereco, usuarioId) { Integer id, Integer enderecoId ->
            repository.updateEnderecoUsuario(id, enderecoId)
        }
    }

}
