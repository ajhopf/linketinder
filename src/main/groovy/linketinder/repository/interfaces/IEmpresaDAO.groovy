package linketinder.repository.interfaces

import linketinder.model.dtos.EmpresaDTO

interface IEmpresaDAO {
    List<EmpresaDTO> listarEmpresas()
    Integer adicionarEmpresa(EmpresaDTO empresa)
    EmpresaDTO obterEmpresaPeloId(Integer id)
    void updateEmpresa(EmpresaDTO empresaDTO)
    void deleteEmpresaPeloId(Integer id)
}