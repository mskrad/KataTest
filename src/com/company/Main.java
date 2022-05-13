package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String instruction = "Введите операцию в виде: x + x; x - x; x / x; x * x. " +
                "Числа могут быть единовременно либо арабскими, либо римскими. " +
                "Числа должны быть целыми и в диапазоне от 1 до 10 включительно";
	    System.out.println(instruction);

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println("Результат: " + calc(input));
    }

    public static String calc(String input) {
        try {
            Map<String, Integer> numberMap = new HashMap<>(); //для конвертера
            numberMap.put("I"  ,    1);
            numberMap.put("II" ,    2);
            numberMap.put("III",    3);
            numberMap.put("IV" ,    4);
            numberMap.put("V" ,     5);
            numberMap.put("VI" ,    6);
            numberMap.put("VII" ,   7);
            numberMap.put("VIII" ,  8);
            numberMap.put("IX" ,    9);
            numberMap.put("X" ,    10);
            numberMap.put("XI"  ,  11);
            numberMap.put("XII" ,  12);
            numberMap.put("XIII",  13);
            numberMap.put("XIV" ,  14);
            numberMap.put("XV" ,   15);
            numberMap.put("XVI" ,  16);
            numberMap.put("XVII" , 17);
            numberMap.put("XVIII" ,18);
            numberMap.put("XIX" ,  19);
            numberMap.put("XX" ,   20);

            input = input.replace(" ", ""); //убираем пробелы, для того, чтобы привести всё к одному виду

            int indexMath;
            Operation opType;

            if (input.contains("+")) { //определяем вид операции и её индекс
                opType = Operation.SUM;
                indexMath = input.indexOf("+");
            } else if (input.contains("-")) {
                opType = Operation.SUBTRACT;
                indexMath = input.indexOf("-");
            } else if (input.contains("/")) {
                opType = Operation.DIVISION;
                indexMath = input.indexOf("/");
            } else if (input.contains("*")) {
                opType = Operation.MULTIPLY;
                indexMath = input.indexOf("*");
            } else throw new Exception("Невозможно определить операцию.");

            int xEndIndex = indexMath;
            int yStartIndex = indexMath+1;
            int yEndIndex = input.length();

                int x, y;
                boolean rome = false;

                try {
                    x = Integer.parseInt(input.substring(0, xEndIndex)); //если получится законвертить сразу, то это число
                    y = Integer.parseInt(input.substring(yStartIndex, yEndIndex));
                } catch (NumberFormatException exception) { //если ловим исключение
                    x = romeToArab(numberMap, input.substring(0, xEndIndex)); //вызов конвертера romeToArab
                    y = romeToArab(numberMap, input.substring(yStartIndex, yEndIndex));
                    rome = true;
                }

                if (x > 10 | y > 10 | x < 1 | y < 1) throw new Exception("Работать можно только с числами от 1 до 10");

                Integer result = opType.action(x, y); //само вычисление

                if (!rome) return result.toString(); //если арабские числа, то просто выводим
                else {
                    if (result > 0) { //проверяем на положительность
                        return arabToRome(numberMap, result); //выводим с помощью метода поиска ключа по значению
                    } else throw new Exception("Результатом работы с римскими цифрами может быть только положительные числа");
                }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "программа отработала с ошибкой.";
    }

     public static <K, V>  int romeToArab(Map<K, V> map,String romeNumber) throws Exception { //обычный поиск значения по ключу
        int result;
        try{
            result = (int) map.get(romeNumber.toUpperCase(Locale.ROOT)); //некрасиво, но вроде работает
        } catch (Exception e) { //опять же некрасиво, конечно, что множество ошибок ввода отлавливаются в одном catch
            throw new Exception("Допустимы только единовременно только римские, либо арабские, и никакие другие символы; обязательно в формате 'два операнда и один оператор'");
        }
        return result;
     }


    public static <K, V> K arabToRome(Map<K, V> map, int arabNumber) { //поиск ключа по значению
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(arabNumber)) {
                return entry.getKey();
            }
        }
        return null;
    }

    enum Operation{ //Операции
        SUM{
            public int action(int x, int y){ return x + y;}
        },
        SUBTRACT{
            public int action(int x, int y){ return x - y;}
        },
        MULTIPLY{
            public int action(int x, int y){ return x * y;}
        },
        DIVISION{
            public int action (int x, int y){ return x / y;}
        };
        public abstract int action(int x, int y);
    }

}
