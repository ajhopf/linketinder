package com.linketinder.service;

import com.linketinder.repository.CandidatoRepository;

import static org.mockito.Mockito.*;

class OldCandidatoServiceTest {
    CandidatoRepository repository = mock(CandidatoRepository.class);
    EnderecoService enderecoService = mock(EnderecoService.class);
    CompetenciaService competenciaService = mock(CompetenciaService.class);
    CandidatoService candidatoService = new CandidatoService(repository, enderecoService, competenciaService);
//
//    @Nested
//    @DisplayName("Método: adicionarCandidato")
//    class AdicionarCandidatoTests {
//        @Test
//        @DisplayName("Quando adiciona o primeiro candidato, deve retornar um candidato com um ID 0")
//        void adicionarCandidatoPrimeiroCandidatoTemIdZero() {
//            //Arrange
//            List<Candidato> candidatos = new ArrayList<>();
//            when(repository.listarCandidatos()).thenReturn(candidatos);
//
//            Candidato novoCandidato = new Candidato();
//            doNothing().when(repository).adicionarCandidato(novoCandidato);
//
//            // Act
//            Candidato candidatoResultado = candidatoService.adicionarCandidato(novoCandidato);
//
//            // Assert
//            assertEquals(0, candidatoResultado.getId());
//        }
//
//        @Test
//        @DisplayName("Quando adiciona novo candidato, deve retornar um candidato com um ID maior que o maior ID já existente")
//        void adicionarEmpresaGeraNovoIdCorretamente() {
//            //Arrange
//            Candidato candidatoExistente = new Candidato();
//            candidatoExistente.setId(5);
//            List<Candidato> candidatos = new ArrayList<>();
//            candidatos.add(candidatoExistente);
//
//            when(repository.listarCandidatos()).thenReturn(candidatos);
//
//            Candidato novoCandidato = new Candidato();
//
//            doNothing().when(repository).adicionarCandidato(novoCandidato);
//
//            // Act
//            Candidato candidatoResultado = candidatoService.adicionarCandidato(novoCandidato);
//
//            // Assert
//            assertEquals(6, candidatoResultado.getId());
//        }
//
//        @Test
//        @DisplayName("Quando adiciona novo candidato, deve invocar a função adicionarCandidato do repository com o novo candidato como parametro")
//        void adicionarEmpresaInvocaRepositoryAdicionarEmpresa() {
//            //Arrange
//            Candidato novoCandidato = new Candidato();
//
//            when(repository.listarCandidatos()).thenReturn(new ArrayList<>());
//            doNothing().when(repository).adicionarCandidato(novoCandidato);
//
//            // Act
//            candidatoService.adicionarCandidato(novoCandidato);
//
//            // Assert
//            verify(repository, times(1)).adicionarCandidato(novoCandidato);
//        }
//    }


}