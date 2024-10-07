package com.linketinder.repository

import com.linketinder.model.Vaga
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.EmpresaDTO
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
        return null
    }

    @Override
    Integer adicionarVaga(Vaga vaga) {
        return null
    }

    @Override
    void updateVaga(Integer vagaId, VagaRequestDTO vaga) {

    }

    @Override
    void deleteVaga(Integer id) {

    }
}
