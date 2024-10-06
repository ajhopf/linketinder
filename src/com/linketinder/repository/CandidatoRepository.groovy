package com.linketinder.repository

import com.linketinder.exceptions.CandidatoNotFoundException
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.repository.interfaces.ICandidatoDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

import java.sql.SQLException

class CandidatoRepository implements ICandidatoDAO {
    private Sql sql = null

    CandidatoRepository(Sql sql) {
        this.sql = sql
    }

    CandidatoDTO rowToDto(GroovyResultSet row) {
        CandidatoDTO candidatoDTO = new CandidatoDTO()

        candidatoDTO.id = row.getInt('id')
        candidatoDTO.nome = row.getString('nome')
        candidatoDTO.sobrenome = row.getString('sobrenome')
        candidatoDTO.cpf = row.getString('cpf')
        candidatoDTO.dataNascimento = row.getDate('data_nascimento').toLocalDate()
        candidatoDTO.email = row.getString('email')
        candidatoDTO.telefone = row.getString('telefone')
        candidatoDTO.descricao = row.getString('descricao')

        return candidatoDTO
    }

    @Override
    CandidatoDTO obterCandidatoPeloId(Integer id) throws CandidatoNotFoundException, SQLException {
        def stmt = "SELECT * FROM candidatos c INNER JOIN usuarios u ON c.usuario_id = u.id WHERE u.id = $id"

        CandidatoDTO candidatoDTO = null
        this.sql.eachRow(stmt) { row ->
           candidatoDTO = rowToDto(row)
        }

        if (candidatoDTO == null) {
            throw new CandidatoNotFoundException("Não foi possível localizar o candidato com o id = $id")
        }

        return candidatoDTO
    }

    @Override
    List<CandidatoDTO> listarCandidatos() throws SQLException {
        def stmt = 'SELECT * FROM candidatos c INNER JOIN usuarios u ON c.usuario_id = u.id'

        List<CandidatoDTO> candidatoDTOList = []

        this.sql.eachRow(stmt) { row ->
            CandidatoDTO candidatoDTO = rowToDto(row)

            candidatoDTOList << candidatoDTO
        }

        return candidatoDTOList
    }

    @Override
    Integer adicionarCandidato(CandidatoDTO candidato) throws SQLException{
        Integer novoUsuarioId = null

        def insereEmUsuarios = """
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES (CAST(? AS tipo_usuario), ?, ?, ?, ?)
        """

        def insereEmCandidatos = """
            INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone)
            VALUES (?, ?, CAST(? AS DATE), ?, ?)
        """

        def usuarioParams = ['candidato', candidato.nome, candidato.email, candidato.senha, candidato.descricao]

        sql.withTransaction {
            def keys = sql.executeInsert(insereEmUsuarios, usuarioParams)
            novoUsuarioId = keys[0][0] as Integer
            def candidatoParams = [novoUsuarioId, candidato.sobrenome, candidato.dataNascimento, candidato.cpf, candidato.telefone]
            sql.executeInsert(insereEmCandidatos, candidatoParams)
        }

        return novoUsuarioId
    }

    @Override
    void deletarCandidatoPeloId(Integer id) throws SQLException {
        def deletarUsuario = """
            DELETE FROM usuarios WHERE id = $id
        """

        sql.execute(deletarUsuario)
    }
}

