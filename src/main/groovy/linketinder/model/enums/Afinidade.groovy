package linketinder.model.enums

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum Afinidade {
    MUITO_BAIXA(1),
    BAIXA(2),
    MEDIA(3),
    ALTA(4),
    MUITO_ALTA(5)

    private final int afinidade

    Afinidade(int afinidade) {
        this.afinidade = afinidade
    }

    static Afinidade fromValor(int valor) {
        for (Afinidade afinidade : Afinidade.values()) {
            if (afinidade.afinidade == valor) {
                return afinidade
            }
        }
        throw new IllegalArgumentException('A afinidade aceita valores de 1 a 5')
    }

    @JsonCreator
    public static Afinidade fromString(String value) {
        return Afinidade.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }

    int getAfinidade() {
        return afinidade
    }
}