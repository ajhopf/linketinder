package linketinder.exceptions

class EmpresaNotFoundException extends RuntimeException{
    EmpresaNotFoundException(String mensagem) {
        super(mensagem)
    }
}
