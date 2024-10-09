package linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false, includeSuper = true)
class Empresa extends Usuario {
    String cnpj

    @Override
    String toString() {
        "Empresa: $super.nome, $super.email, cpnj: $cnpj"
    }

}
