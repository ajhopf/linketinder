package linketinder.repository.interfaces

import linketinder.model.dtos.CompetenciaDTO

interface CompetenciaDAO {
    List<CompetenciaDTO> listarCompetencias()
    List<CompetenciaDTO> listarCompetenciasDeCandidato(Integer candidatoId)
    List<CompetenciaDTO> listarCompetenciasDeVaga(Integer vagaId)

    Integer adicionarCompetencia(String competencia)
    void adicionarCompetenciaCandidato(CompetenciaDTO competenciaDTO, Integer usuarioId)
    void adicionarCompetenciaVaga(CompetenciaDTO competenciaDTO, Integer vagaId)

    Integer obterIdDeCompetencia(String competenciaString)
    CompetenciaDTO obterCompetenciaPeloId(Integer id)

    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia)

    void deleteCompetencia(Integer id)

    void deletarCompetenciasCandidato(Integer candidatoId)
    void deletarCompetenciasVaga(Integer vagaId)
}