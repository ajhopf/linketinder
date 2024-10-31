package linketinder.controller

import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Empresa
import linketinder.service.EmpresaService

class EmpresaController {
    private final EmpresaService empresaService

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    List<Empresa> listarEmpresas() throws RepositoryAccessException {
        return empresaService.listarEmpresas()
    }

    Empresa obterEmpresaPeloId(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        return empresaService.obterEmpresaPeloId(id)
    }

    Integer adicionarEmpresa(Empresa empresa) throws RepositoryAccessException {
        Integer id = empresaService.adicionarEmpresa(empresa)

        return id
    }

    void editarEmpresa(Empresa empresaAtualizada) throws RepositoryAccessException, EmpresaNotFoundException {
        empresaService.editarEmpresa(empresaAtualizada)
    }

    void deletarEmpresa(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        empresaService.deleteEmpresa(id)
    }

}
