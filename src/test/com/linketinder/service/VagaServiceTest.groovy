package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Competencia
import com.linketinder.model.Vaga
import com.linketinder.model.dtos.VagaResponseDTO
import com.linketinder.model.enums.Afinidade
import com.linketinder.model.mappers.VagaMapper
import com.linketinder.repository.VagaRepository
import spock.lang.Specification

import java.sql.SQLException

import static org.mockito.Mockito.*;

class VagaServiceTest extends Specification {
    VagaRepository repository = mock(VagaRepository.class)

    CompetenciaService competenciaService = mock(CompetenciaService.class);
    VagaService vagaService = new VagaService(repository, competenciaService);

    def "listarVagas() retorna lista de vagas"() {
        given:
            VagaResponseDTO vagaResponseDTO = new VagaResponseDTO()
            vagaResponseDTO.nome = 'Teste'
            vagaResponseDTO.id = 1

            List<VagaResponseDTO> vagaResponseDTOS = [vagaResponseDTO]
            List<Competencia> competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
            when(repository.listarVagas()).thenReturn(vagaResponseDTOS)
            when(competenciaService.listarCompetenciasDeUsuarioOuVaga(any(Integer), any(String)))
                .thenReturn(competencias)
        when:
            List<Vaga> result = vagaService.listarVagas()
        then:
            result.size() == 1
            result[0].nome == 'Teste'
            result[0].competencias.size() == 1
    }

}
