package linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false)
class Endereco {
    String pais
    String estado
    String cep
    String cidade
}
