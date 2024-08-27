package com.linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false)
abstract class Pessoa extends Identificavel {
    String nome, email, descricao
    Endereco endereco
    List<Competencia> competencias
}
