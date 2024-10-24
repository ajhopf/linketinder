package linketinder.service

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Competencia

import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.mappers.CompetenciaMapper
import linketinder.repository.CompetenciaRepository

import java.sql.SQLException

class CompetenciaService {
    CompetenciaRepository repository

    CompetenciaService (CompetenciaRepository repository) {
        this.repository = repository
    }


    Competencia obterCompetenciaPeloId(Integer id) {
        try {
            CompetenciaDTO competenciaDTO = repository.obterCompetenciaPeloId(id)
            return CompetenciaMapper.toEntity(competenciaDTO)
        } catch (SQLException e){
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }


    Integer obterIdDeCompetencia(String competencia) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            return repository.obterIdDeCompetencia(competencia)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    private List<Competencia> listarCompetenciasHelper(Closure<List<CompetenciaDTO>> listarCompetenciasFn) {
        try {
            List<CompetenciaDTO> competenciasDTOS = listarCompetenciasFn.call()
            List<Competencia> competencias = CompetenciaMapper.dtoListToEntityList(competenciasDTOS)

            return competencias
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    List<Competencia> listarCompetencias() throws RepositoryAccessException {
        return listarCompetenciasHelper { -> repository.listarCompetencias() }
    }

    List<Competencia> listarCompetenciasDeCandidato(Integer usuarioId) throws RepositoryAccessException {
        return listarCompetenciasHelper { -> repository.listarCompetenciasDeCandidato(usuarioId) }
    }

    List<Competencia> listarCompetenciasDeVaga(Integer vagaId) throws RepositoryAccessException {
        return listarCompetenciasHelper() { -> repository.listarCompetenciasDeVaga(vagaId) }
    }


    Integer adicionarCompetencia(String competencia) throws RepositoryAccessException {
        try {
            Integer competenciaId = repository.adicionarCompetencia(competencia)
            return competenciaId
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }


    private void adicionarCompetenciaParaEntidadeHelper(Competencia competencia, Integer entidadeId, Closure adicionarCompetenciaFn) {
        try {
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
            Integer competenciaId = repository.obterIdDeCompetencia(competencia.competencia)
            competenciaDTO.id = competenciaId

            adicionarCompetenciaFn.call(competenciaDTO, entidadeId)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void adicionarCompetenciaCandidato(Competencia competencia, Integer candidatoId) throws RepositoryAccessException, CompetenciaNotFoundException  {
        adicionarCompetenciaParaEntidadeHelper(competencia, candidatoId) { CompetenciaDTO competenciaDTO, Integer id ->
            repository.adicionarCompetenciaCandidato(competenciaDTO, id)
        }
    }

    void adicionarCompetenciaVaga(Competencia competencia, Integer vagaId) throws RepositoryAccessException, CompetenciaNotFoundException {
        adicionarCompetenciaParaEntidadeHelper(competencia, vagaId) { CompetenciaDTO competenciaDTO, Integer id ->
            repository.adicionarCompetenciaVaga(competenciaDTO, id)
        }
    }

    void updateCompetencia(Integer id, Competencia competenciaAtualizada) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competenciaAtualizada)

            repository.updateCompetencia(id, competenciaDTO)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }


    void deletarCompetencia(Integer id) throws RepositoryAccessException, CompetenciaNotFoundException{
        try {
            repository.deletarCompetencia(id)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }

    void deletarCompetenciasDeEntidadeHelper(Closure deletarFn) {
        try {
            deletarFn.call()
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    void deletarCompetenciasDeVaga(Integer vagaId) {
        deletarCompetenciasDeEntidadeHelper { -> repository.deletarCompetenciasVaga(vagaId) }
    }

    void deletarCompetenciasDeCandidato(Integer candidatoId) {
        deletarCompetenciasDeEntidadeHelper { -> repository.deletarCompetenciasCandidato(candidatoId) }
    }
}
