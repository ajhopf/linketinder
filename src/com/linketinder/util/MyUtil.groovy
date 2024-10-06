package com.linketinder.util

import com.linketinder.model.Identificavel

class MyUtil {

    static <T extends Identificavel> int gerarNovoId(List<T> list) {
        int newId
        if (list.size() > 0) {
            int maiorId = 0

            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i)
                if (t.getId() > maiorId) {
                    maiorId = t.getId()
                }
            }

            newId = maiorId + 1
        } else {
            newId = 0
        }
        return newId
    }
}
