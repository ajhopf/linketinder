package linketinder.repository

import linketinder.exceptions.EmpresaNotFoundException
import linketinder.model.dtos.EmpresaDTO
import linketinder.repository.interfaces.EmpresaDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

class EmpresaRepository implements EmpresaDAO {
    private static EmpresaRepository instance
    private Sql sql = null

    private EmpresaRepository(Sql sql) {
        this.sql = sql
    }

    static synchronized EmpresaRepository getInstance(Sql sql) {
        if (instance == null) {
            instance = new EmpresaRepository(sql)
        }
        return instance
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
        String stmt = 'SELECT * FROM empresas e INNER JOIN usuarios u ON e.usuario_id = u.id'

        List<EmpresaDTO> empresaDTOSList = []

        this.sql.eachRow(stmt) { row ->
            EmpresaDTO empresaDTO = rowToDto(row)

            empresaDTOSList << empresaDTO
        }

        return empresaDTOSList
    }

    @Override
    EmpresaDTO obterEmpresaPeloId(Integer id) {
        GString stmt = "SELECT * FROM empresas e INNER JOIN usuarios u ON e.usuario_id = u.id WHERE u.id = $id"

        EmpresaDTO empresaDTO = null

        this.sql.eachRow(stmt) { row ->
            empresaDTO = rowToDto(row)
        }

        if (empresaDTO == null) {
            throw new EmpresaNotFoundException ("Não foi possível localizar a empresa com o id = $id")
        }

        return empresaDTO
    }

    @Override
    Integer adicionarEmpresa(EmpresaDTO empresa) {
        Integer novaEmpresaId = null

        String insereEmUsuarios = """
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES (CAST(? AS tipo_usuario), ?, ?, ?, ?)
        """

        String insereEmEmpresas = """
            INSERT INTO empresas (usuario_id, cnpj)
            VALUES (?, ?)
        """

        List<String> usuarioParams = ['empresa', empresa.nome, empresa.email, empresa.senha, empresa.descricao]

        sql.withTransaction {
            def keys = sql.executeInsert(insereEmUsuarios, usuarioParams)
            novaEmpresaId = keys[0][0] as Integer
            def empresaParams = [novaEmpresaId, empresa.cnpj]
            sql.executeInsert(insereEmEmpresas, empresaParams)
        }

        return novaEmpresaId
    }

    @Override
    void updateEmpresa(EmpresaDTO empresaDTO) {
        GString updateUsuarioStatement = """
            UPDATE usuarios u
            SET nome = $empresaDTO.nome, email = $empresaDTO.email, senha = $empresaDTO.senha, descricao = $empresaDTO.descricao
            WHERE u.id = $empresaDTO.id
        """

        GString updateEmpresaStatement = """
            UPDATE empresas e
            SET cnpj = $empresaDTO.cnpj
            WHERE e.usuario_id = $empresaDTO.id
        """

        sql.withTransaction {
            int row = sql.executeUpdate(updateUsuarioStatement)

            if (row == 0) {
                throw new EmpresaNotFoundException("Não foi possível localizar a empresa com id $empresaDTO.id")
            }

            sql.executeInsert(updateEmpresaStatement)
        }
    }

    @Override
    void deletarEmpresaPeloId(Integer id) {
        GString statement = """
            DELETE FROM usuarios e
            WHERE id = $id
        """

        int rowsAffected = sql.executeUpdate(statement)

        if (rowsAffected == 0) {
            throw new EmpresaNotFoundException("Não foi possível encontrar uma empresa com id $id")
        }
    }
}
