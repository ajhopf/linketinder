package com.linketinder.service

import com.linketinder.model.Empresa
import com.linketinder.repository.EmpresaRepository

class EmpresaService {
    EmpresaRepository repository

    EmpresaService (EmpresaRepository repository) {
        this.repository = repository
    }

    void adicionarEmpresa(Empresa empresa) {
        repository.adicionarEmpresa(empresa)
    }

    List<Empresa> listarEmpresas() {
        repository.listarEmpresas()
    }
}
