package com.linketinder.service

import com.linketinder.model.Candidato
import com.linketinder.repository.CandidatoRepository

class CandidatoService {
    CandidatoRepository repository

    CandidatoService (CandidatoRepository repository) {
        this.repository = repository
    }

    void adicionarCandidato(Candidato candidato) {
        repository.adicionarCandidato(candidato)
    }

    List<Candidato> listarCandidatos() {
        repository.listarCandidatos()
    }
}
