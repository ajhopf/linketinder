package com.linketinder.repository


import com.linketinder.model.dtos.EnderecoDTO
import com.linketinder.repository.interfaces.IEnderecoDAO
import groovy.sql.Sql

class EnderecoRepository implements IEnderecoDAO{
    private Sql sql = null

    EnderecoRepository(Sql sql) {
        this.sql = sql
    }

    @Override
    EnderecoDTO obterEnderecoPeloId(Integer id) {
        def endereco = """
                SELECT e.cep, e.pais, e.estado, e.cidade, eu.usuario_id
                FROM enderecos e
                INNER JOIN enderecos_usuario eu
                ON e.id = eu.endereco_id
                WHERE eu.usuario_id =  $id
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
}