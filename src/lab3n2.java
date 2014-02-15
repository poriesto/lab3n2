/**
 * Created by Александр on 15.02.14.
 */
/*
* 3. Создать 2 потока, один из которых генерирует случайным образом число Фибоначчи в разделенную между потоками переменную ,
а другой считывает это число , определяет четное ли оно , и выводит на печать каждое четное число.
Цикл для каждого потока выполняется число раз заданное параметром.
Нужно выводить записываемое число для первого потока и каждое найденное четное число для второго потока или 0, если число нечетное. Выполнить задание с использованием конструкции synchronized .
Не использовать в этом задании флаги для синхронизации потоков, а только методы wait и notify.
Также не использовать любые задержки для потоков после начала их работы в виде методов sleep, yield или wait c параметром.
(Числа Фибоначчи — элементы числовой последовательности
0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, …
в которой каждое последующее число равно сумме двух предыдущих чисел. )
* */
import java.util.Random;

 public class lab3n2 {
    public final static Object one = new Object(), two = new Object();

    public static void main(String[] args){
        final int[] number = {0};
        final Thread th1 = new Thread(){
            public void run(){
                Thread.yield();
                synchronized (two){
                    Random rnd = new Random();
                    number[0] = rnd.nextInt(10);
                    System.out.println("Success!" + this.getName() + " Generated number = " + number[0]);
                }
            }
        };
        Thread th2 = new Thread(){
            public void run(){
                synchronized (two){
                    Thread.yield();
                    synchronized (one){
                        if(number[0] % 2 == 0 || number[0] == 0){
                        System.out.println("Success!" + this.getName() + " Result thread 1 = " + number[0]);
                        }
                        else{
                            System.out.println("Success!" + getName() + " " + th1.getName() + " without numbers");
                        }
                    }
                }
            }
        };
            th1.start();
            th2.start();
    }
}
