package com.linketinder.repository

import com.linketinder.model.dtos.EmpresaDTO
import com.linketinder.repository.interfaces.IEmpresaDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

import java.sql.SQLException

class EmpresaRepository implements IEmpresaDAO {
    private Sql sql = null

    EmpresaRepository(Sql sql) {
        this.sql = sql
    }

    static EmpresaDTO rowToDto(GroovyResultSet row) throws SQLException {
        EmpresaDTO empresaDTO = new EmpresaDTO()

        empresaDTO.id = row.getInt('id')
        empresaDTO.nome = row.getString('nome')
        empresaDTO.cnpj = row.getString('cnpj')
        empresaDTO.email = row.getString('email')
        empresaDTO.descricao = row.getString('descricao')

        return empresaDTO
    }

    @Override
    List<EmpresaDTO> listarEmpresas() throws SQLException {
        def stmt = 'SELECT * FROM empresas e INNER JOIN usuarios u ON e.usuario_id = u.id'

        List<EmpresaDTO> empresaDTOSList = []

        this.sql.eachRow(stmt) { row ->
            EmpresaDTO empresaDTO = rowToDto(row)

            empresaDTOSList << empresaDTO
        }

        return empresaDTOSList
    }

    @Override
    Integer adicionarEmpresa(EmpresaDTO empresa) throws SQLException  {
        Integer novaEmpresaId = null

        def insereEmUsuarios = """
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES (CAST(? AS tipo_usuario), ?, ?, ?, ?)
        """

        def insereEmEmpresas = """
            INSERT INTO empresas (usuario_id, cnpj)
            VALUES (?, ?)
        """

        def usuarioParams = ['empresa', empresa.nome, empresa.email, empresa.senha, empresa.descricao]

        sql.withTransaction {
            def keys = sql.executeInsert(insereEmUsuarios, usuarioParams)
            novaEmpresaId = keys[0][0] as Integer
            def empresaParams = [novaEmpresaId, empresa.cnpj]
            sql.executeInsert(insereEmEmpresas, empresaParams)
        }

        return novaEmpresaId
    }


    @Override
    EmpresaDTO obterEmpresaPeloId(Integer id) {
        return null
    }

    @Override
    EmpresaDTO updateEmpresa(Object EmpresaDTO) {
        return null
    }

    @Override
    void deleteEmpresaPeloId(Integer id) {

    }
}
