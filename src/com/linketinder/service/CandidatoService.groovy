package com.linketinder.service

import com.linketinder.model.Candidato
import com.linketinder.repository.CandidatoRepository
import com.linketinder.util.MyUtil

class CandidatoService {
    CandidatoRepository repository

    CandidatoService (CandidatoRepository repository) {
        this.repository = repository
    }

    void adicionarCandidato(Candidato candidato) {
        Integer id = MyUtil.gerarNovoId(listarCandidatos())
        candidato.id = id

        repository.adicionarCandidato(candidato)
    }

    List<Candidato> listarCandidatos() {
        repository.listarCandidatos()
    }
}
