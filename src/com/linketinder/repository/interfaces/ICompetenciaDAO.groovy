package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.CompetenciaDTO

interface ICompetenciaDAO {
    List<CompetenciaDTO> listarCompetencias()
    List<CompetenciaDTO> listarCompetenciasDeCandidatoOuVaga(Integer usuarioId, String nomeTabela)
    Integer adicionarCompetencia(String competencia)
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId)
    Integer obterIdDeCompetencia(String competenciaString)
    CompetenciaDTO obterCompetenciaPeloId(Integer id)
    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia)
    void deleteCompetencia(Integer id)
}