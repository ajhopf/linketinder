package linketinder.service

import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Competencia
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.Vaga
import linketinder.model.dtos.EnderecoDTO
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO
import linketinder.model.enums.Afinidade
import linketinder.repository.VagaRepository
import spock.lang.Specification


import static org.mockito.Mockito.*

class VagaServiceTest extends Specification {
    VagaRepository repository = mock(VagaRepository.class)
    EnderecoService enderecoService = mock(EnderecoService.class)
    CompetenciaService competenciaService = mock(CompetenciaService.class)
    VagaService vagaService = new VagaService(repository, competenciaService, enderecoService)

    void "listarVagas() retorna lista de vagas"() {
        given:
            VagaResponseDTO vagaResponseDTO = new VagaResponseDTO()
            vagaResponseDTO.nome = 'Teste'
            vagaResponseDTO.id = 1

            List<VagaResponseDTO> vagaResponseDTOS = [vagaResponseDTO]
            List<Competencia> competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
            when(repository.listarVagas()).thenReturn(vagaResponseDTOS)
            when(competenciaService.listarCompetenciasDeVaga(any(Integer)))
                .thenReturn(competencias)
        when:
            List<Vaga> result = vagaService.listarVagas()
        then:
            result.size() == 1
            result[0].nome == 'Teste'
            result[0].competencias.size() == 1
    }

    void "adicionarVaga() retorna id da vaga quando adiciona vaga"() {
        given:
            Vaga vaga = new Vaga()
            List<Competencia> competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
            vaga.id = 1
            vaga.empresa = new Empresa(id: 1)
            vaga.competencias = competencias
            vaga.endereco = new Endereco(cep: "88063-948", cidade: "Florianopolis", estado: "Santa Catarina", pais: "Brasil")

            when(enderecoService.adicionarEndereco(any(EnderecoDTO))).thenReturn(1)
            when(competenciaService.verificarSeCompetenciaExiste(any(String))).thenReturn(1)
            when(repository.adicionarVaga(any(VagaRequestDTO))).thenReturn(1)
        when:
            Integer result = vagaService.adicionarVaga(vaga)
        then:
            result == 1
    }

    void "deleteVaga lança VagaNotFoundException quando não existe vaga com id informado"() {
        given:
            when(repository.obterVagaPeloId(1)).thenThrow(VagaNotFoundException.class)
        when:
            vagaService.deleteVaga(1)
        then:
            thrown(VagaNotFoundException)
    }
}
