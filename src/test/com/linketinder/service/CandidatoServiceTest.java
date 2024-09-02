package com.linketinder.service;

import com.linketinder.model.Candidato;
import com.linketinder.model.Empresa;
import com.linketinder.repository.CandidatoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class CandidatoServiceTest {
    CandidatoRepository repository = mock(CandidatoRepository.class);
    CandidatoService candidatoService = new CandidatoService(repository);

    @Nested
    @DisplayName("Método: adicionarCandidato")
    class AdicionarCandidatoTests {
        @Test
        @DisplayName("Quando adiciona o primeiro candidato, deve retornar um candidato com um ID 0")
        void adicionarCandidatoPrimeiroCandidatoTemIdZero() {
            //Arrange
            List<Candidato> candidatos = new ArrayList<>();
            when(repository.listarCandidatos()).thenReturn(candidatos);

            Candidato novoCandidato = new Candidato();
            doNothing().when(repository).adicionarCandidato(novoCandidato);

            // Act
            Candidato candidatoResultado = candidatoService.adicionarCandidato(novoCandidato);

            // Assert
            assertEquals(0, candidatoResultado.getId());
        }

        @Test
        @DisplayName("Quando adiciona novo candidato, deve retornar um candidato com um ID maior que o maior ID já existente")
        void adicionarEmpresaGeraNovoIdCorretamente() {
            //Arrange
            Candidato candidatoExistente = new Candidato();
            candidatoExistente.setId(5);
            List<Candidato> candidatos = new ArrayList<>();
            candidatos.add(candidatoExistente);

            when(repository.listarCandidatos()).thenReturn(candidatos);

            Candidato novoCandidato = new Candidato();

            doNothing().when(repository).adicionarCandidato(novoCandidato);

            // Act
            Candidato candidatoResultado = candidatoService.adicionarCandidato(novoCandidato);

            // Assert
            assertEquals(6, candidatoResultado.getId());
        }

        @Test
        @DisplayName("Quando adiciona novo candidato, deve invocar a função adicionarCandidato do repository com o novo candidato como parametro")
        void adicionarEmpresaInvocaRepositoryAdicionarEmpresa() {
            //Arrange
            Candidato novoCandidato = new Candidato();

            when(repository.listarCandidatos()).thenReturn(new ArrayList<>());
            doNothing().when(repository).adicionarCandidato(novoCandidato);

            // Act
            candidatoService.adicionarCandidato(novoCandidato);

            // Assert
            verify(repository, times(1)).adicionarCandidato(novoCandidato);
        }
    }

    @Nested
    @DisplayName("Método: listarCandidatos")
    class ListarCandidatosTests {
        @Test
        @DisplayName("Quando o repository está vazio, deve retornar uma lista vazia")
        void listarCandidatosComListaVazia() {
            //Arrange
            List<Candidato> candidatos = new ArrayList<>();

            when(repository.listarCandidatos()).thenReturn(candidatos);

            // Act
            List<Candidato> listaResultado = candidatoService.listarCandidatos();

            // Assert
            assertEquals(0, listaResultado.size());
        }

        @Test
        @DisplayName("Quando o repository contém itens, deve retornar uma lista com todos itens")
        void listarCandidatosComListaComItens() {
            //Arrange
            List<Candidato> candidatos = new ArrayList<>();
            candidatos.add(new Candidato());
            candidatos.add(new Candidato());
            candidatos.add(new Candidato());
            candidatos.add(new Candidato());

            when(repository.listarCandidatos()).thenReturn(candidatos);

            // Act
            List<Candidato> listaResultado = candidatoService.listarCandidatos();

            // Assert
            assertEquals(4, listaResultado.size());
        }
    }


}