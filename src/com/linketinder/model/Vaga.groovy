package com.linketinder.model

import groovy.transform.ToString

@ToString
class Vaga extends Identificavel {
    String nome
    String descricao
    Endereco endereco
    Empresa empresa
    List<Competencia> competencias
}
