package com.linketinder.util;

import com.linketinder.model.Empresa;
import com.linketinder.model.Identificavel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MyUtilTest {
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Nested
    @DisplayName("Método: getIntInput")
    class GetIntInputTests {
        @Test
        @DisplayName("Quando digitar um input dentro da margem informada, deve retornar o valor digitado")
        void getIntInputComInputValido() {
            //arrange
            provideInput("5");
            Scanner sc = new Scanner(System.in);

            //act
            int result = MyUtil.getIntInput(1, 10, "Digite um número:", sc);

            //assert
            assertEquals(5, result);
        }

        @Test
        @DisplayName("Quando digitar um input fora da margem informada, deve invocar a OpcaoInvalidaException e printar o output correspondente")
        void getIntInputComInputInvalido() {
            //arrange
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            provideInput("5\n3\n"); //segunda vez será válido
            Scanner sc = new Scanner(System.in);
            System.setOut(new PrintStream(outputStreamCaptor));

            //act
            MyUtil.getIntInput(1, 3, "Digite um número:", sc);

            //assert
            assertEquals("Digite um número:\nEscolha um número entre 1 e 3", outputStreamCaptor.toString().trim());
        }

        @Test
        @DisplayName("Quando digitar um input que não seja um número, deve chamar InputMismatchException e printar o output correspondente")
        void getIntInputComInputString() {
            //arrange
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            provideInput("f\n3\n"); //segunda vez será válido
            Scanner sc = new Scanner(System.in);
            System.setOut(new PrintStream(outputStreamCaptor));

            //act
            MyUtil.getIntInput(1, 3, "Digite um número:", sc);

            //assert
            assertEquals("Digite um número:\nVocê deve escolher utilizando um número de 1 a 3", outputStreamCaptor.toString().trim());
        }
    }

    @Nested
    @DisplayName("Método: obterString")
    class ObterStringTests {
        @Test
        @DisplayName("Quando digitar um texto, deverá retornar este texto")
        void obterString() {
            String mensagem = "esta é uma mensagem teste";
            provideInput(mensagem);
            Scanner sc = new Scanner(System.in);

            //act
            String result = MyUtil.obterString("title", sc);

            //assert
            assertEquals(mensagem, result);
        }
    }

    @Nested
    @DisplayName("Método: gerarNovoId")
    class GerarNovoIdTests {
        @Test
        @DisplayName("Quando fornecida uma lista vazia, deve retornar 0")
        void gerarNovoIdEmUmaListaVaziaRetornaIdZero() {
            //arrange
            List<Identificavel> lista = new ArrayList<>();

            //act
            int resultado = MyUtil.gerarNovoId(lista);
            //assert

            assertEquals(0, resultado);
        }

        @Test
        @DisplayName("Quando fornecida uma lista com itens, deve retornar o maior ID presente na lista acrescido de 1")
        void gerarNovoIdEmUmaListaComItens() {
            //arrange
            List<Identificavel> lista = new ArrayList<>();
            Empresa empresa = new Empresa();
            Empresa outraEmpresa = new Empresa();
            empresa.setId(10);
            outraEmpresa.setId(3);
            lista.add(empresa);
            lista.add(outraEmpresa);

            //act
            int resultado = MyUtil.gerarNovoId(lista);
            //assert

            assertEquals(11, resultado);
        }
    }

}