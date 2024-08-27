package com.linketinder.repository

import com.linketinder.model.Candidato

class CandidatoRepository {
    List<Candidato> candidatos = []
    void adicionarCandidato(Candidato candidato) { candidatos << candidato }
    List<Candidato> listarCandidatos() { candidatos }
}

