package com.linketinder.service

import com.linketinder.model.Empresa
import com.linketinder.repository.EmpresaRepository
import com.linketinder.util.MyUtil

class EmpresaService {
    EmpresaRepository repository

    EmpresaService (EmpresaRepository repository) {
        this.repository = repository
    }

    Empresa adicionarEmpresa(Empresa empresa) {
        Integer id = MyUtil.gerarNovoId(listarEmpresas())
        empresa.id = id

        repository.adicionarEmpresa(empresa)

        empresa
    }

    List<Empresa> listarEmpresas() {
        repository.listarEmpresas()
    }
}
