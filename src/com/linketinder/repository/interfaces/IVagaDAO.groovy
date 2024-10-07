package com.linketinder.repository.interfaces

import com.linketinder.model.Vaga
import com.linketinder.model.dtos.VagaRequestDTO

interface IVagaDAO {
    List<VagaRequestDTO> listarVagas()
    List<VagaRequestDTO> listarVagasDeEmpresa(Integer usuarioId)
    Integer adicionarVaga(Vaga vaga)
    void updateVaga(Integer vagaId, VagaRequestDTO vaga)
    void deleteVaga(Integer id)
}