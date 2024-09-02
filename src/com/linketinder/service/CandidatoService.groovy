package com.linketinder.service

import com.linketinder.model.Candidato
import com.linketinder.repository.CandidatoRepository
import com.linketinder.util.MyUtil

class CandidatoService {
    CandidatoRepository repository

    CandidatoService (CandidatoRepository repository) {
        this.repository = repository
    }

    Candidato adicionarCandidato(Candidato candidato) {
        Integer id = MyUtil.gerarNovoId(listarCandidatos())
        candidato.id = id

        repository.adicionarCandidato(candidato)

        candidato
    }

    List<Candidato> listarCandidatos() {
        repository.listarCandidatos()
    }
}
