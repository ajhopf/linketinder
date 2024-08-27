package com.linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false, includeSuper = true)
class Empresa extends Pessoa {
    String cnpj
}
