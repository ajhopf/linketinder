package com.linketinder.service;

import com.linketinder.model.Empresa;
import com.linketinder.repository.EmpresaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class EmpresaServiceTest {
    EmpresaRepository repository = mock(EmpresaRepository.class);
    EmpresaService empresaService = new EmpresaService(repository);

    @Nested
    @DisplayName("Método: adicionarEmpresa")
    class AdicionarEmpresaTests {
        @Test
        @DisplayName("Quando adiciona a primeira empresa, deve retornar uma empresa com um ID 0")
        void adicionarEmpresaPrimeiraEmpresaTemIdZero() {
            //Arrange
            List<Empresa> empresas = new ArrayList<>();
            when(repository.listarEmpresas()).thenReturn(empresas);

            Empresa novaEmpresa = new Empresa();
            doNothing().when(repository).adicionarEmpresa(novaEmpresa);

            // Act
            Empresa empresaResultado = empresaService.adicionarEmpresa(novaEmpresa);

            // Assert
            assertEquals(0, empresaResultado.getId());
        }

        @Test
        @DisplayName("Quando adiciona nova empresa, deve retornar uma empresa com um ID maior que o maior ID já existente")
        void adicionarEmpresaGeraNovoIdCorretamente() {
            //Arrange
            Empresa empresaExistente = new Empresa();
            empresaExistente.setId(5);
            List<Empresa> empresas = new ArrayList<>();
            empresas.add(empresaExistente);

            when(repository.listarEmpresas()).thenReturn(empresas);

            Empresa novaEmpresa = new Empresa();

            doNothing().when(repository).adicionarEmpresa(novaEmpresa);

            // Act
            Empresa empresaResultado = empresaService.adicionarEmpresa(novaEmpresa);

            // Assert
            assertEquals(6, empresaResultado.getId());
        }

        @Test
        @DisplayName("Quando adiciona nova empresa, deve invocar a função adicionarEmpresa com a nova empresa do repository como parametro")
        void adicionarEmpresaInvocaRepositoryAdicionarEmpresa() {
            //Arrange
            Empresa novaEmpresa = new Empresa();

            when(repository.listarEmpresas()).thenReturn(new ArrayList<>());
            doNothing().when(repository).adicionarEmpresa(novaEmpresa);

            // Act
            Empresa empresaResultado = empresaService.adicionarEmpresa(novaEmpresa);

            // Assert
            verify(repository, times(1)).adicionarEmpresa(novaEmpresa);
        }
    }

    @Nested
    @DisplayName("Método: listarEmpresa")
    class ListarEmpresaTests {
        @Test
        @DisplayName("Quando o repository está vazio, deve retornar uma lista vazia")
        void listarEmpresasComListaVazia() {
            //Arrange
            List<Empresa> empresas = new ArrayList<>();

            when(repository.listarEmpresas()).thenReturn(empresas);

            // Act
            List<Empresa> listaResultado = empresaService.listarEmpresas();

            // Assert
            assertEquals(0, listaResultado.size());
        }

        @Test
        @DisplayName("Quando o repository contém itens, deve retornar uma lista com todos itens")
        void listarEmpresasComListaComItens() {
            //Arrange
            List<Empresa> empresas = new ArrayList<>();
            empresas.add(new Empresa());
            empresas.add(new Empresa());
            empresas.add(new Empresa());

            when(repository.listarEmpresas()).thenReturn(empresas);

            // Act
            List<Empresa> listaResultado = empresaService.listarEmpresas();

            // Assert
            assertEquals(3, listaResultado.size());
        }
    }

}