package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.CompetenciaDTO

interface ICompetenciaDAO {
    List<CompetenciaDTO> listarCompetenciasDeUsuario(Integer usuarioId)
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId)
    Integer obterIdDeCompetencia(String competenciaString)
}