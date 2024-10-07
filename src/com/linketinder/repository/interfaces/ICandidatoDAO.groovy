package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.CandidatoDTO

interface ICandidatoDAO {
    List<CandidatoDTO> listarCandidatos()
    Integer adicionarCandidato(CandidatoDTO candidato)
    CandidatoDTO obterCandidatoPeloId(Integer id)
    void updateCandidato(CandidatoDTO candidatoDTO)
    void deletarCandidatoPeloId(Integer id)
}