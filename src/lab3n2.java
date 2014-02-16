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
    public static void Thr1() {
        final int[] number = new int[20];
        number[0] = 0;
        number[1] = 1;
        new Thread(){
            public void run(){
                synchronized (number){
                    for(int i = 2; i < number.length; i++){
                        number[i] = number[i-1] + number[i-2];
                        System.out.println("Success!" + this.getName() + " Result thread 1 = " + number[i]);
                    }
                    System.out.println("Success!" + this.getName());
                }
            }
        }.start();
        new Thread(){
            public void run(){
                synchronized (number){
                    for(int i = 0; i < number.length; i++){
                            if(number[i] % 2 == 0 || number[i] == 0){
                                System.out.println("Success!" + this.getName() + " Result thread 1 = " + number[i]);
                            }
                            else{
                                number[i] = 0;
                                System.out.println("Success!" + getName() + " Fib = " + number[i]);
                            }
                        }
                    }
                }
        }.start();
    }
    public static void Thr2(){
        final int[] number = new int[20];
        final Object monitor = new Object();
        number[0] = 0;
        number[1] = 1;
        new Thread(){
            public void run(){
                synchronized (monitor){
                    for(int i = 2; i < number.length; i++){
                        number[i] = number[i-1] + number[i-2];
                        System.out.println("Success!" + this.getName() + " Result = " + number[i]);
                        try {
                            monitor.wait();
                            monitor.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Success!" + this.getName());
                }
            }
        }.start();
        new Thread(){
            public void run(){
                synchronized (monitor){
                    for(int i = 0; i < number.length; i++) {
                        int aNumber = number[i];
                        if (aNumber % 2 == 0 || aNumber == 0) {
                            System.out.println("Success!" + this.getName() + " Result = " + aNumber);
                            try {
                                monitor.wait();
                                monitor.notify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Success!" + getName() + " Fib = " + 0);
                            try {
                                monitor.wait();
                                monitor.notifyAll();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.start();
    }

    public static void main(String[] argv){
        lab3n2 l1 = new lab3n2();
        //l1.Thr1();
        //System.out.print("wait()");
        l1.Thr2();
    }
}