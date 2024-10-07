package com.linketinder.repository

import com.linketinder.exceptions.EmpresaNotFoundException
import com.linketinder.exceptions.VagaNotFoundException
import com.linketinder.model.dtos.VagaRequestDTO
import com.linketinder.model.dtos.VagaResponseDTO
import com.linketinder.repository.interfaces.IVagaDAO
import groovy.sql.GroovyResultSet
import groovy.sql.Sql

class VagaRepository implements IVagaDAO {
    Sql sql

    VagaRepository(Sql sql) {
        this.sql = sql
    }

    static VagaResponseDTO rowToDto(GroovyResultSet row) {
        VagaResponseDTO vagaResponseDTO = new VagaResponseDTO()

        vagaResponseDTO.id = row.getInt('id')
        vagaResponseDTO.nome = row.getString('nome')
        vagaResponseDTO.descricao = row.getString('descricao')
        vagaResponseDTO.cidade = row.getString('cidade')
        vagaResponseDTO.estado = row.getString('estado')

        return vagaResponseDTO
    }

    @Override
    List<VagaResponseDTO> listarVagas() {
        def stmt = """
            SELECT v.id, v.nome, v.descricao, u.nome as nome_empresa, u.descricao as descricao_empresa, en.cidade, en.estado
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
    List<VagaRequestDTO> listarVagasDeEmpresa(Integer usuarioId) {
        null
    }

    VagaResponseDTO obterVagaPeloId(Integer vagaId) {
        def stmt = """
            SELECT v.id, v.nome, v.descricao, u.nome as nome_empresa, u.descricao as descricao_empresa, en.cidade, en.estado
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
        def stmt = """
            INSERT INTO vagas (nome, descricao, empresa_id, endereco_id)
            VALUES (?, ?, ?, ?)
        """

        def competenciasVagaStmt = """
            INSERT INTO competencias_vaga (vaga_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        def vagaParams = [vaga.nome, vaga.descricao, vaga.empresaId, vaga.enderecoId]

        def keys = sql.executeInsert(stmt, vagaParams)
        Integer vagaId = keys[0][0] as Integer

        for (competencia in vaga.competenciaDTOList) {
            def params = [vagaId, competencia.id, competencia.anosExperiencia, competencia.afinidade.getAfinidade()]

            sql.executeInsert(competenciasVagaStmt, params)
        }

        return vagaId
    }

    @Override
    void updateVaga(Integer vagaId, VagaRequestDTO vaga) {
        def stmt = """
            UPDATE vagas
            SET nome = $vaga.nome, descricao = $vaga.descricao, endereco_id = $vaga.enderecoId
        """

        def competenciasVagaStmt = """
            INSERT INTO competencias_vaga (vaga_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        def row = sql.executeUpdate(stmt)

        if (row == 0) {
            throw new VagaNotFoundException("Não foi possível localizar a vaga com id $vaga.id")
        }

        for (competencia in vaga.competenciaDTOList) {
            def params = [vagaId, competencia.id, competencia.anosExperiencia, competencia.afinidade.getAfinidade()]

            sql.executeInsert(competenciasVagaStmt, params)
        }
    }

    @Override
    void deleteVaga(Integer id) {
        def stmt = """
            DELETE FROM vagas
            WHERE id = $id
        """

        sql.executeUpdate(stmt)
    }

    void deletarCompetenciasVaga(Integer vagaId) {
        def stmt = """
            DELETE FROM competencias_vaga
            WHERE vaga_id = $vagaId
        """

        sql.executeUpdate(stmt)
    }
}
