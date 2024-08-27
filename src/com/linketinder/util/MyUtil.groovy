package com.linketinder.util

import com.linketinder.exceptions.OpcaoInvalidaException
import com.linketinder.model.Identificavel

class MyUtil {
    static int getIntInput(int min, int max, String title, Scanner sc) {
        println title

        while (true) {
            try {
                String stringValue = sc.nextLine()

                int value = Integer.parseInt(stringValue)

                if (value < min || value > max) {
                    throw new OpcaoInvalidaException("Escolha um número entre $min e $max")
                }
                return value;
            } catch (OpcaoInvalidaException e) {
                println e.getMessage()
            } catch (InputMismatchException | NumberFormatException e) {
                println "Você deve escolher utilizando um número de $min a $max"
            }
        }
    }

    static String obterString(String title, Scanner sc) {
        println title
        return sc.nextLine()
    }

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
