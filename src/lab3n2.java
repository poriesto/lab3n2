/**
 * Created by Александр on 15.02.14.
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

public class lab3n2 {
    static double golden = (1 + Math.sqrt(5))/2;

    private static Object monitor = new Object();
    public static void Thr1() {
        final double[] number = new double[20];
        number[0] = 0;
        number[1] = 1;
        new Thread(){
            public void run(){
                synchronized (number){
                    for(int i = 0; i < number.length; i++){
                        number[i] = ((Math.pow(golden, i)) - (Math.pow(-golden, -i)))/(2*golden -1);
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
        final int[] number = new int[15];
        Thread th1 = new Thread(){
            public void run(){
                synchronized (monitor){
                    double tmp;
                    for(int i = 0; i < number.length; i++){
                        tmp = ((Math.pow(golden, i)) - (Math.pow(-golden, -i)))/(2*golden -1);
                        number[i] = (int)tmp;
                        System.out.println(this.getName() + " number[" + i + "] = " + number[i]);
                        try {
                            monitor.wait();
                            monitor.notify();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread th2 = new Thread(){
            public void run(){
                int i = 0;
                synchronized (monitor){
                    while(i != number.length){
                        if(number[i] % 2 == 0 || number[i] == 0){
                            System.out.println(this.getName() + " Четное число пришло number[" + i + "] = " + number[i]);
                            i++;
                            monitor.notify();
                            try {
                                monitor.wait();
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println(this.getName() + " Нечетное число пришло number[" + i + "] = " + 0);
                            i++;
                            monitor.notify();
                            try {
                                monitor.wait();
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        th1.start();
        th2.start();
    }

    public static void main(String[] argv){
        lab3n2 l1 = new lab3n2();
        /*System.out.println("Второй поток ждет, когда первый поток завершит всю свою работу с данными.\n");
        l1.Thr1();*/
        System.out.print("Потоки работают попеременно.\n");
        l1.Thr2();
    }
}