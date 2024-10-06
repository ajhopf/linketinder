package com.linketinder.repository.interfaces

import com.linketinder.model.Competencia
import com.linketinder.model.dtos.CompetenciaDTO

interface ICompetenciaDAO {
    List<CompetenciaDTO> listarCompetenciasDeUsuario(Integer usuarioId)
    List<CompetenciaDTO> listarCompetencias()
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId)
    Integer adicionarCompetencia(String competencia)
    Integer obterIdDeCompetencia(String competenciaString)
    CompetenciaDTO obterCompetenciaPeloId(Integer id)
    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia)
}