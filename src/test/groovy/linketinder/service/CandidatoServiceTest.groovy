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
        when(competenciaService.listarCompetenciasDeCandidato(any(Integer))).thenReturn(competencias)
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
        1 * competenciaService.adicionarCompetenciaCandidato(competencias[0], candidatoId)
        1 * competenciaService.adicionarCompetenciaCandidato(competencias[1], candidatoId)
    }

    def "deve tratar CompetenciaNotFoundException ao adicionar competencias"() {
        given: "uma lista de competencias e um candidatoId"
        List<Competencia> competencias = [new Competencia(nome: "Java"), new Competencia(nome: "Groovy")]
        Integer candidatoId = 1

        and: "o serviço de competencia lança uma exceção para a segunda competencia"
        competenciaService.adicionarCompetenciaDeEntidade(competencias[0], candidatoId) >> {}
        competenciaService.adicionarCompetenciaDeEntidade(competencias[1], candidatoId) >> { throw new CompetenciaNotFoundException("Competência não encontrada") }

        when: "adicionarCompetenciasDoCandidato é chamado"
        candidatoService.adicionarCompetenciasDoCandidato(competencias, candidatoId)

        then: "o serviço de competencia deve ser chamado para cada competencia"
        1 * competenciaService.adicionarCompetenciaDeEntidade(competencias[0], candidatoId)
        1 * competenciaService.adicionarCompetenciaDeEntidade(competencias[1], candidatoId)

        and: "não lança exceção e imprime a mensagem de erro para a competencia não encontrada"
        noExceptionThrown()
    }

    void "adicionarCandidato() cria novo candidato com suas competencias e endereco"() {
        given:
        Candidato candidato = new Candidato()
        candidato.endereco = new Endereco()
        candidato.competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
       when(competenciaService.verificarSeCompetenciaExiste(any(String))).thenReturn(1)

        when(repository.adicionarCandidato(any(CandidatoDTO))).thenReturn(1)

        when:
        candidatoService.adicionarCandidato(candidato)

        then:
        verify(competenciaService, times(1)).adicionarCompetenciaDeEntidade(any(Competencia), eq(1))
        verify(enderecoService, times(1)).adicionarEnderecoParaUsuario(any(Endereco), eq(1))
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
        when(repository.obterCandidatoPeloId(1)).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.deletarCandidatoPeloId(1)

        then:
        thrown(CandidatoNotFoundException)
    }

    void "updateCandidato lança CompetenciaNotFoundException quando tenta inserir candidato com competencia invalida"(){
        given:
        when(competenciaService.verificarSeCompetenciaExiste(any(String))).thenThrow(CompetenciaNotFoundException.class)
        Competencia competencia = new Competencia('Java', 1, Afinidade.ALTA)

        when:
        candidatoService.updateCandidato(new Candidato(competencias: [competencia]))

        then:
        thrown(CompetenciaNotFoundException)
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
