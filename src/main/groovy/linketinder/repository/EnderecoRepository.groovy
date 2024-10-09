package linketinder.repository

import linketinder.model.dtos.EnderecoDTO
import linketinder.repository.interfaces.IEnderecoDAO
import groovy.sql.Sql

class EnderecoRepository implements IEnderecoDAO{
    private Sql sql = null

    EnderecoRepository(Sql sql) {
        this.sql = sql
    }

    EnderecoDTO obterEnderecoPeloId(Integer id) {
        def stmt = """
            SELECT e.cep, e.pais, e.estado, e.cidade
            FROM enderecos e             
            WHERE e.id =  $id
        """
        def row = sql.firstRow(stmt)

        if (row == null) {
            return new EnderecoDTO()
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO()

        enderecoDTO.cep = row.cep
        enderecoDTO.pais = row.pais
        enderecoDTO.estado = row.estado
        enderecoDTO.cidade = row.cidade

        return enderecoDTO
    }

    @Override
    EnderecoDTO obterEnderecoDoUsuarioPeloId(Integer usuarioId) {
        def endereco = """
                SELECT e.cep, e.pais, e.estado, e.cidade, eu.usuario_id
                FROM enderecos e INNER JOIN enderecos_usuario eu
                ON e.id = eu.endereco_id
                WHERE eu.usuario_id =  $usuarioId
            """

        def row = sql.firstRow(endereco)

        if (row == null) {
            return new EnderecoDTO()
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO()
        enderecoDTO.cep = row.cep
        enderecoDTO.pais = row.pais
        enderecoDTO.estado = row.estado
        enderecoDTO.cidade = row.cidade
        enderecoDTO.usuarioId = row.usuario_id as Integer

        return enderecoDTO
    }



    @Override
    Integer obterIdDeEnderecoPeloCep(String cep) {
        def enderecoQuery = """
            SELECT id 
            FROM enderecos e
            WHERE e.cep LIKE $cep
        """

        def row = sql.firstRow(enderecoQuery)

        if (row == null) {
            return -1
        }

        return row.id as Integer
    }

    @Override
    Integer adicionarNovoEndereco(EnderecoDTO enderecoDTO) {
        def inserirNovoEndereco = """
            INSERT INTO enderecos(cep, cidade, estado, pais)
            VALUES (?, ?, ?, ?)
        """

        def enderecoParams = [enderecoDTO.cep, enderecoDTO.cidade, enderecoDTO.estado, enderecoDTO.pais]

        def keys = sql.executeInsert(inserirNovoEndereco, enderecoParams)

        Integer novoEnderecoId = keys[0][0] as Integer

        return novoEnderecoId
    }

    @Override
    void adicionarEnderecoParaUsuario(Integer usuarioId, Integer enderecoId) {
        def inserirNovoEnderecoParaUsuario = """
            INSERT INTO enderecos_usuario (usuario_id, endereco_id)
            VALUES ($usuarioId, $enderecoId)
        """

        sql.executeInsert(inserirNovoEnderecoParaUsuario)
    }

    @Override
    void updateEnderecoUsuario(Integer usuarioId, Integer enderecoId) {
        def updateEndereco = """
            UPDATE enderecos_usuario
            SET  endereco_id = $enderecoId
            WHERE usuario_id = $usuarioId
        """

        sql.executeUpdate(updateEndereco)
    }
}