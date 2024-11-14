package linketinder

import linketinder.config.LinketinderConfig

import linketinder.view.MenuInicial


class Main {
    static void main(String[] args) {
        try {
            Map controllers = LinketinderConfig.getBeans()


            MenuInicial.iniciar(
                    controllers.competenciaController,
                    controllers.empresaController,
                    controllers.vagaController,
                    controllers.candidatoController)
        } catch (Exception e) {
            println e.getStackTrace()
            println e
        }
    }

}


