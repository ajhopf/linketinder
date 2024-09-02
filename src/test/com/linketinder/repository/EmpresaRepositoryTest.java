package com.linketinder.repository;

import com.linketinder.model.Empresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpresaRepositoryTest {
    @Nested
    @DisplayName("Método: adicionarEmpresa")
    class AdicionarEmpresaTests {
        @Test
        @DisplayName("Quando método é invocado, adiciona a empresa à lista de empresas. ")
        void adicionarEmpresa() {
            //arrange
            EmpresaRepository empresaRepository = new EmpresaRepository();
            Empresa empresa = new Empresa();

            //act
            empresaRepository.adicionarEmpresa(empresa);

            //assert
            assertEquals(1, empresaRepository.getEmpresas().size());
        }
    }

    @Nested
    @DisplayName("Método: listaEmpresas")
    class ListarEmpresasTests {
        @Test
        @DisplayName("Quando método é invocado, retorna lista com todas empresas existentes ")
        void listarEmpresa() {
            //arrange
            EmpresaRepository empresaRepository = new EmpresaRepository();

            List<Empresa> empresas = new ArrayList<>();
            Empresa empresa = new Empresa();
            Empresa empresa2 = new Empresa();
            Empresa empresa3 = new Empresa();
            empresas.add(empresa);
            empresas.add(empresa2);
            empresas.add(empresa3);

            empresaRepository.setEmpresas(empresas);
            //act
            List<Empresa> resultado =  empresaRepository.listarEmpresas();

            //assert
            assertEquals(3, resultado.size());
            assertEquals(resultado.get(0), empresa);
            assertEquals(resultado.get(1), empresa2);
            assertEquals(resultado.get(2), empresa3);
        }
    }

}