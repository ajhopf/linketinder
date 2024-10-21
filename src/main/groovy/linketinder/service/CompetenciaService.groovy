package linketinder.service

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Competencia

import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.enums.TabelaCompetencia
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


    List<Competencia> listarCompetencias() throws RepositoryAccessException {
        try {
            List<CompetenciaDTO> competenciaDTOList = repository.listarCompetencias()

            List<Competencia> competenciasList = []

            competenciaDTOList.each {competencia ->
                competenciasList << CompetenciaMapper.toEntity(competencia)
            }

            return competenciasList
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }


    List<Competencia> listarCompetenciasDeUsuarioOuVaga(Integer usuarioId, TabelaCompetencia tabela) throws RepositoryAccessException {

        try {
            List<CompetenciaDTO> competenciasDTO = repository.listarCompetenciasDeCandidatoOuVaga(usuarioId, tabela.getNomeTabela())
            List<Competencia> competencias = []

            for (competencia in competenciasDTO) {
                competencias << CompetenciaMapper.toEntity(competencia)
            }

            return competencias
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }


    void adicionarCompetencia(String competencia) throws RepositoryAccessException {
        try {
            Integer competenciaId = repository.adicionarCompetencia(competencia)
            println "Competencia adicionada com sucesso. Id gerado: $competenciaId"
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    void adicionarCompetenciaDeEntidade(Competencia competencia, Integer entidadeId, boolean isVaga = false) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
            Integer competenciaId = repository.obterIdDeCompetencia(competencia.competencia)
            competenciaDTO.id = competenciaId

            if (isVaga) {
                repository.adicionarCompetenciasVaga(competenciaDTO, entidadeId)
            } else {
                repository.adicionarCompetenciaUsuario(competenciaDTO, entidadeId)
            }

        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }


    Integer verificarSeCompetenciaExiste(String competencia) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            return repository.obterIdDeCompetencia(competencia)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }


    void updateCompetencia(Integer id, Competencia competenciaAtualizada) throws RepositoryAccessException, CompetenciaNotFoundException {
        try {
            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competenciaAtualizada)

            repository.updateCompetencia(id, competenciaDTO)

            println "Competência atualizada"
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }


    void deletarCompetencia(Integer id) throws RepositoryAccessException, CompetenciaNotFoundException{
        try {
            repository.deleteCompetencia(id)

            println "Competência deletada"
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        } catch (CompetenciaNotFoundException e) {
            throw e
        }
    }

    void deletarCompetenciaEntidade(Integer entidadeId, String tabelaString) {
        try {
            repository.deleteCompetenciasEntidade(entidadeId, tabelaString)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }
}
