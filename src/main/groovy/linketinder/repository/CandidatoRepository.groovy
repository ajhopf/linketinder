package linketinder.repository

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.model.dtos.CandidatoDTO
import linketinder.repository.interfaces.CandidatoDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql


class CandidatoRepository implements CandidatoDAO {
    private static CandidatoRepository instance = null
    private Sql sql = null

    private CandidatoRepository(Sql sql) {
        this.sql = sql
    }

    static synchronized CandidatoRepository getInstance(Sql sql) {
        if (instance == null) {
            instance = new CandidatoRepository(sql)
        }
        return instance
    }

    private static CandidatoDTO rowToDto(GroovyResultSet row) {
        CandidatoDTO candidatoDTO = new CandidatoDTO()

        candidatoDTO.id = row.getInt('id')
        candidatoDTO.nome = row.getString('nome')
        candidatoDTO.sobrenome = row.getString('sobrenome')
//        candidatoDTO.senha = row.getString('senha')
        candidatoDTO.cpf = row.getString('cpf')
        candidatoDTO.dataNascimento = row.getDate('data_nascimento').toLocalDate()
        candidatoDTO.email = row.getString('email')
        candidatoDTO.telefone = row.getString('telefone')
        candidatoDTO.descricao = row.getString('descricao')

        return candidatoDTO
    }

    @Override
    CandidatoDTO obterCandidatoPeloId(Integer id) {
        GString stmt = "SELECT * FROM candidatos c INNER JOIN usuarios u ON c.usuario_id = u.id WHERE u.id = $id"

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
    List<CandidatoDTO> listarCandidatos() {
        String stmt = 'SELECT * FROM candidatos c INNER JOIN usuarios u ON c.usuario_id = u.id'

        List<CandidatoDTO> candidatoDTOList = []

        this.sql.eachRow(stmt) { row ->
            CandidatoDTO candidatoDTO = rowToDto(row)

            candidatoDTOList << candidatoDTO
        }

        return candidatoDTOList
    }

    @Override
    Integer adicionarCandidato(CandidatoDTO candidato) {
        Integer novoUsuarioId = null

        String insereEmUsuarios = """
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES (CAST(? AS tipo_usuario), ?, ?, ?, ?)
        """

        String insereEmCandidatos = """
            INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone)
            VALUES (?, ?, CAST(? AS DATE), ?, ?)
        """

        List<String> usuarioParams = ['candidato', candidato.nome, candidato.email, candidato.senha, candidato.descricao]

        sql.withTransaction {
            def keys = sql.executeInsert(insereEmUsuarios, usuarioParams)
            novoUsuarioId = keys[0][0] as Integer
            List candidatoParams = [novoUsuarioId, candidato.sobrenome, candidato.dataNascimento, candidato.cpf, candidato.telefone]
            sql.executeInsert(insereEmCandidatos, candidatoParams)
        }

        return novoUsuarioId
    }

    @Override
    void updateCandidato(CandidatoDTO candidatoDTO) {
        GString updateUsuarioStatement = """
            UPDATE usuarios u
            SET nome = $candidatoDTO.nome, email = $candidatoDTO.email, senha = $candidatoDTO.senha, descricao = $candidatoDTO.descricao
            WHERE u.id = $candidatoDTO.id
        """

        GString updateCandidatoStatement = """
            UPDATE candidatos c
            SET cpf = $candidatoDTO.cpf, sobrenome = $candidatoDTO.sobrenome, data_nascimento = $candidatoDTO.dataNascimento, telefone = $candidatoDTO.telefone
            WHERE c.usuario_id = $candidatoDTO.id
        """

        sql.withTransaction {
            int row = sql.executeUpdate(updateUsuarioStatement)

            if (row == 0) {
                throw new CandidatoNotFoundException("Não foi possível localizar o candidato com id $candidatoDTO.id")
            }

            sql.executeInsert(updateCandidatoStatement)
        }
    }

    @Override
    void deletarCandidatoPeloId(Integer id) {
        GString deletarUsuario = """
            DELETE FROM usuarios WHERE id = $id
        """

        int row = sql.executeUpdate(deletarUsuario)

        if (row == 0) {
            throw new CandidatoNotFoundException("Não foi possível localizar o candidato com id $id")
        }
    }
}

