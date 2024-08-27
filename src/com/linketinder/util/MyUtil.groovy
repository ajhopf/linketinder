package com.linketinder.util

import com.linketinder.exceptions.OpcaoInvalidaException

class MyUtil {
    static int getIntInput(int min, int max, String title, Scanner sc) {
        System.out.println(title);
        System.out.println();
        while (true) {
            try {
                String stringValue = sc.nextLine();

                int value = Integer.parseInt(stringValue);

                if (value < min || value > max) {
                    throw new OpcaoInvalidaException("Escolha um número entre " + min + " e " + max);
                }
                return value;
            } catch (OpcaoInvalidaException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Você deve escolher utilizando um número de " + min + " a " + max);
            } catch (NumberFormatException e) {
                System.out.println("Você deve escolher utilizando um número de " + min + " a " + max);
            }
        }
    }
}
