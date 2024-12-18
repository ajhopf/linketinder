package linketinder.repository

import linketinder.exceptions.VagaNotFoundException
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO
import linketinder.repository.interfaces.VagaDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

class VagaRepository implements VagaDAO {
    private static VagaRepository instance = null
    private Sql sql

    private VagaRepository(Sql sql) {
        this.sql = sql
    }

    static synchronized VagaRepository getInstance(Sql sql) {
        if (instance == null) {
            instance = new VagaRepository(sql)
        }
        return instance
    }

    static VagaResponseDTO rowToDto(GroovyResultSet row) {
        VagaResponseDTO vagaResponseDTO = new VagaResponseDTO()

        vagaResponseDTO.id = row.getInt('id')
        vagaResponseDTO.nome = row.getString('nome')
        vagaResponseDTO.pais = row.getString('pais')
        vagaResponseDTO.cep = row.getString('cep')
        vagaResponseDTO.descricao = row.getString('descricao')
        vagaResponseDTO.cidade = row.getString('cidade')
        vagaResponseDTO.estado = row.getString('estado')
        vagaResponseDTO.empresaId = row.getString('empresa_id') as int
        vagaResponseDTO.empresa = row.getString("nome_empresa")

        return vagaResponseDTO
    }

    @Override
    List<VagaResponseDTO> listarVagas() {
        String stmt = """
            SELECT v.id, v.nome, v.descricao, u.id as empresa_id, u.nome as nome_empresa, u.descricao as descricao_empresa, en.cidade, en.estado, en.pais, en.cep
            FROM vagas v
            INNER JOIN usuarios u ON u.id = v.empresa_id
            INNER JOIN enderecos en ON v.endereco_id = en.id; 
        """

        List<VagaResponseDTO> vagaResponseDTOS = []

        this.sql.eachRow(stmt) { row ->
            VagaResponseDTO vagaResponseDTO = rowToDto(row)

            vagaResponseDTOS << vagaResponseDTO
        }

        return vagaResponseDTOS
    }

    @Override
    VagaResponseDTO obterVagaPeloId(Integer vagaId) {
        GString stmt = """
            SELECT v.id, v.nome, v.descricao, u.id as empresa_id, u.nome as nome_empresa, u.descricao as descricao_empresa, en.cidade, en.estado, en.pais, en.cep
            FROM vagas v
            INNER JOIN usuarios u ON u.id = v.empresa_id
            INNER JOIN enderecos en ON v.endereco_id = en.id
            WHERE v.id = $vagaId
        """

        VagaResponseDTO vagaResponseDTO

        this.sql.eachRow(stmt) { row ->
            vagaResponseDTO = rowToDto(row)
        }

        if (vagaResponseDTO == null) {
            throw new VagaNotFoundException("Vaga com id $vagaId não encontrada")
        }

        return vagaResponseDTO
    }

    @Override
    Integer adicionarVaga(VagaRequestDTO vaga) {
        String stmt = """
            INSERT INTO vagas (nome, descricao, empresa_id, endereco_id)
            VALUES (?, ?, ?, ?)
        """

        List<Object> vagaParams = [vaga.nome, vaga.descricao, vaga.empresaId, vaga.enderecoId]

        def keys = sql.executeInsert(stmt, vagaParams)
        return  keys[0][0] as Integer

    }

    @Override
    void updateVaga(Integer vagaId, VagaRequestDTO vaga) {
        String stmt = """
            UPDATE vagas
            SET nome = ?, descricao = ?, endereco_id = ?
            WHERE id = ?
        """

        int row = sql.executeUpdate(stmt, [vaga.nome, vaga.descricao, vaga.enderecoId, vagaId])

        if (row == 0) {
            throw new VagaNotFoundException("Não foi possível localizar a vaga com id $vaga.id")
        }
    }

    @Override
    void deletarVaga(Integer id) {
        GString stmt = """
            DELETE FROM vagas
            WHERE id = $id
        """

        sql.executeUpdate(stmt)
    }

}
