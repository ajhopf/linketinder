package linketinder.model.dtos

class VagaRequestDTO {
    Integer id
    String nome
    String descricao
    Integer empresaId
    Integer enderecoId
    List<CompetenciaDTO> competenciaDTOList
}
