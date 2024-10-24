package linketinder.service

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Endereco
import linketinder.model.dtos.CandidatoDTO
import linketinder.model.enums.Afinidade
import linketinder.repository.CandidatoRepository

import spock.lang.Specification

import static org.mockito.Mockito.*

class CandidatoServiceTest extends Specification {
    CandidatoRepository repository = mock(CandidatoRepository.class)
    EnderecoService enderecoService = mock(EnderecoService.class)
    CompetenciaService competenciaService = mock(CompetenciaService.class)
    CandidatoService candidatoService = new CandidatoService(repository, enderecoService, competenciaService)

    void "listarCandidatos() retorna lista vazia com tabela sem candidatos"() {
        given:
        List<Candidato> resultadoEsperado = []
        List<CandidatoDTO> candidatoDTOS = []

        when:
        when(repository.listarCandidatos()).thenReturn(candidatoDTOS)
        List<Candidato> listaResultado = candidatoService.listarCandidatos()

        then:
        listaResultado == resultadoEsperado
    }

    void "listarCandidatos() retorna lista com candidatos"() {
        given:
        List<CandidatoDTO> candidatoDTOS = [new CandidatoDTO(id: 1), new CandidatoDTO(id: 2)]
        List<Competencia> competencias = []

        when:
        when(repository.listarCandidatos()).thenReturn(candidatoDTOS)
        when(enderecoService.obterEnderecoDoUsuario(any(Integer))).thenReturn(new Endereco())
        when(competenciaService.listarCompetenciasDeCandidato(1)).thenReturn(competencias)
        List<Candidato> listaResultado = candidatoService.listarCandidatos()

        then:
        listaResultado.size() == 2
    }

    void "listarCandidatos() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCandidatos()).thenThrow(RepositoryAccessException.class)

        when:
        candidatoService.listarCandidatos()

        then:
        thrown(RepositoryAccessException)
    }


    def "adicionarCompetenciasDoCandidato adiciona competencias ao candidato com sucesso"() {
        given:
            List<Competencia> competencias = [
                    new Competencia("Java", 10, Afinidade.MUITO_ALTA),
                    new Competencia("Groovy", 10, Afinidade.MUITO_ALTA)]
            Integer candidatoId = 1

        when: "adicionarCompetenciasDoCandidato é chamado"
            candidatoService.adicionarCompetenciasDoCandidato(competencias, candidatoId)

        then: "o serviço de competencia deve ser chamado para cada competencia"
            verify(competenciaService, times(1))
                    .adicionarCompetenciaCandidato(competencias[0], 1)
            verify(competenciaService, times(1))
                    .adicionarCompetenciaCandidato(competencias[1], 1)
    }

    def "adicionarCompetenciasDoCandidato deve tratar CompetenciaNotFoundException ao adicionar competencias"() {
        given:
            List<Competencia> competencias = [ new Competencia("Groovy", 1, Afinidade.ALTA) ]
            Integer candidatoId = 1

            when(competenciaService.adicionarCompetenciaCandidato(competencias[0], candidatoId)).thenThrow(CompetenciaNotFoundException.class)

        when:
            candidatoService.adicionarCompetenciasDoCandidato(competencias, candidatoId)

        then: "o serviço de competencia deve ser chamado para cada competencia"
            verify(competenciaService, times(1))
                    .adicionarCompetenciaCandidato(competencias[0], 1)

            noExceptionThrown()
    }

    void "adicionarCandidato() cria novo candidato com suas competencias e endereco"() {
        given:
            Candidato candidato = new Candidato()
            Endereco endereco = new Endereco()
            candidato.endereco = endereco
            Competencia competencia = new Competencia('Java', 1, Afinidade.ALTA)
            candidato.competencias = [competencia]
            when(competenciaService.obterIdDeCompetencia(any(String))).thenReturn(1)

            when(repository.adicionarCandidato(any(CandidatoDTO))).thenReturn(1)

        when:
            candidatoService.adicionarCandidato(candidato)

        then:
            verify(competenciaService, times(1)).adicionarCompetenciaCandidato(competencia, 1)
            verify(enderecoService, times(1)).adicionarEnderecoParaUsuario(endereco, 1)
    }

    void "obterCandidatoPeloId lança CandidatoNotFoundException quando não é encontrado um candidato"() {
        given:
        when(repository.obterCandidatoPeloId(any(Integer))).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.obterCandidatoPeloId(1)

        then:
        thrown(CandidatoNotFoundException)
    }

    void "deletarCandidatoPeloId invoca o metodo deletarCandidatoPeloId do Repository"() {
        when:
        doNothing().when(repository).deletarCandidatoPeloId(1)
        candidatoService.deletarCandidatoPeloId(1)

        then:
        verify(repository, times(1)).deletarCandidatoPeloId(1)
    }

    void "deletarCandidatoPeloId lança CandidatoNotFoundException quando não é encontrado um candidato"() {
        given:
        when(repository.deletarCandidatoPeloId(1)).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.deletarCandidatoPeloId(1)

        then:
        thrown(CandidatoNotFoundException)
    }

    void "updateCandidato lança CandidatoNotFoundException quando tenta atualizar candidato com id invalido"(){
        given:
        when(repository.updateCandidato(any(CandidatoDTO))).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.updateCandidato(new Candidato())

        then:
        thrown(CandidatoNotFoundException)
    }


}
