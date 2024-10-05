package com.linketinder.repository

import com.linketinder.model.Candidato
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.repository.interfaces.ICandidatoDAO
import groovy.sql.Sql

class CandidatoRepository implements ICandidatoDAO {
    private Sql sql = null

    CandidatoRepository(Sql sql) {
        this.sql = sql
    }

    List<Candidato> candidatos = []
    void adicionarCandidato(Candidato candidato) { candidatos << candidato }

    @Override
    List<CandidatoDTO> listarCandidatos() {
        def stmt = 'SELECT * FROM candidatos c INNER JOIN usuarios u ON c.usuario_id = u.id'

        List<CandidatoDTO> candidatoDTOList = []

        this.sql.eachRow(stmt) { row ->
            CandidatoDTO candidatoDTO = new CandidatoDTO()
            candidatoDTO.id = row.getInt('id')
            candidatoDTO.nome = row.getString('nome')
            candidatoDTO.sobrenome = row.getString('sobrenome')
            candidatoDTO.cpf = row.getString('cpf')
            candidatoDTO.dataNascimento = row.getDate('data_nascimento')
            candidatoDTO.email = row.getString('email')
            candidatoDTO.descricao = row.getString('descricao')

            candidatoDTOList << candidatoDTO
        }

        return candidatoDTOList
    }
}

