package com.linketinder.repository

import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.EmpresaDTO
import com.linketinder.repository.interfaces.IEmpresaDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

class EmpresaRepository implements IEmpresaDAO {
    private Sql sql = null

    EmpresaRepository(Sql sql) {
        this.sql = sql
    }

    static EmpresaDTO rowToDto(GroovyResultSet row) {
        EmpresaDTO empresaDTO = new EmpresaDTO()

        empresaDTO.id = row.getInt('id')
        empresaDTO.nome = row.getString('nome')
        empresaDTO.cnpj = row.getString('cnpj')
        empresaDTO.email = row.getString('email')
        empresaDTO.descricao = row.getString('descricao')

        return empresaDTO
    }

    @Override
    List<EmpresaDTO> listarEmpresas() {
        def stmt = 'SELECT * FROM empresas e INNER JOIN usuarios u ON e.usuario_id = u.id'

        List<EmpresaDTO> empresaDTOSList = []

        this.sql.eachRow(stmt) { row ->
            EmpresaDTO empresaDTO = rowToDto(row)

            empresaDTOSList << empresaDTO
        }

        return empresaDTOSList
    }

    @Override
    Integer adicionarEmpresa(EmpresaDTO empresa) {
        return null
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
