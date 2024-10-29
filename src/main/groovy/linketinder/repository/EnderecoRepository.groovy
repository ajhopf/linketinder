package linketinder.repository

import groovy.sql.GroovyRowResult
import linketinder.model.dtos.EnderecoDTO
import linketinder.repository.interfaces.EnderecoDAO
import groovy.sql.Sql

class EnderecoRepository implements EnderecoDAO{
    private static EnderecoRepository instance = null
    private Sql sql = null

    private EnderecoRepository(Sql sql) {
        this.sql = sql
    }

    static synchronized EnderecoRepository getInstance(Sql sql) {
        if (instance == null) {
            instance = new EnderecoRepository(sql)
        }
        return instance
    }

    private static EnderecoDTO rowToEnderecoDTO(GroovyRowResult row) {
        EnderecoDTO enderecoDTO = new EnderecoDTO()

        if (row == null) {
            return enderecoDTO
        }

        enderecoDTO.cep = row.cep
        enderecoDTO.pais = row.pais
        enderecoDTO.estado = row.estado
        enderecoDTO.cidade = row.cidade

        return enderecoDTO
    }

    EnderecoDTO obterEnderecoPeloId(Integer id) {
        GString stmt = """
            SELECT e.cep, e.pais, e.estado, e.cidade
            FROM enderecos e             
            WHERE e.id =  $id
        """
        GroovyRowResult row = sql.firstRow(stmt)

        return rowToEnderecoDTO(row)
    }

    @Override
    EnderecoDTO obterEnderecoDoUsuarioPeloId(Integer usuarioId) {
        GString endereco = """
                SELECT e.cep, e.pais, e.estado, e.cidade, eu.usuario_id
                FROM enderecos e INNER JOIN enderecos_usuario eu
                ON e.id = eu.endereco_id
                WHERE eu.usuario_id =  $usuarioId
            """

        GroovyRowResult row = sql.firstRow(endereco)

        if (row == null) {
            return new EnderecoDTO()
        }

        EnderecoDTO enderecoDTO = rowToEnderecoDTO(row)

        enderecoDTO.usuarioId = row.usuario_id as Integer

        return enderecoDTO
    }


    @Override
    Integer obterIdDeEnderecoPeloCep(String cep) {
        GString enderecoQuery = """
            SELECT id 
            FROM enderecos e
            WHERE e.cep LIKE $cep
        """

        GroovyRowResult row = sql.firstRow(enderecoQuery)

        if (row == null) {
            return -1
        }

        return row.id as Integer
    }

    @Override
    Integer adicionarNovoEndereco(EnderecoDTO enderecoDTO) {
        String inserirNovoEndereco = """
            INSERT INTO enderecos(cep, cidade, estado, pais)
            VALUES (?, ?, ?, ?)
        """

        List<String> enderecoParams = [enderecoDTO.cep, enderecoDTO.cidade, enderecoDTO.estado, enderecoDTO.pais]

        def keys = sql.executeInsert(inserirNovoEndereco, enderecoParams)

        Integer novoEnderecoId = keys[0][0] as Integer

        return novoEnderecoId
    }

    @Override
    void adicionarEnderecoParaUsuario(Integer usuarioId, Integer enderecoId) {
        GString inserirNovoEnderecoParaUsuario = """
            INSERT INTO enderecos_usuario (usuario_id, endereco_id)
            VALUES ($usuarioId, $enderecoId)
        """

        sql.executeInsert(inserirNovoEnderecoParaUsuario)
    }

    @Override
    void updateEnderecoUsuario(Integer usuarioId, Integer enderecoId) {
        GString updateEndereco = """
            UPDATE enderecos_usuario
            SET  endereco_id = $enderecoId
            WHERE usuario_id = $usuarioId
        """

        sql.executeUpdate(updateEndereco)
    }
}