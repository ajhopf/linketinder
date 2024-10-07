package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.VagaRequestDTO
import com.linketinder.model.dtos.VagaResponseDTO

interface IVagaDAO {
    List<VagaResponseDTO> listarVagas()
    List<VagaRequestDTO> listarVagasDeEmpresa(Integer usuarioId)
    Integer adicionarVaga(VagaRequestDTO vaga)
    void updateVaga(Integer vagaId, VagaRequestDTO vaga)
    void deleteVaga(Integer id)
}