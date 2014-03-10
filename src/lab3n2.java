import java.util.Random;
import java.util.concurrent.*;

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
 class Work{
    final private Future<Integer>[] Fibanachi;
    final private Random rnd = new Random();
    private Integer check = 0;
    final private Object monitor = new Object();
    private final class FibNum implements Callable<Integer>{
        private Integer i;
        public FibNum(Integer i){this.i = i;}
        public Integer call(){
            Double golden = (1 + Math.sqrt(5)) /2;
            Double tmp = (((Math.pow(golden, i)) - (Math.pow(-golden, -i)))/(2*golden -1));
            return tmp.intValue();
        }
    }

    Work(int lenght) {
        Fibanachi = new Future[lenght];
        double golden = (1 + Math.sqrt(5)) / 2;
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for(int i = 0; i < Fibanachi.length; i++) Fibanachi[i] = pool.submit(new FibNum(i));
        pool.shutdown();
    }
    public void SomeWork(){
            new Thread(){
                public void run(){
                    synchronized (monitor){
                        for(Future<Integer>aFib : Fibanachi){
                            try {
                                check = Fibanachi[rnd.nextInt(Fibanachi.length)].get();
                                System.out.print("Thread1=>");
                                monitor.wait();
                                monitor.notify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
            new Thread(){
                public void run(){
                    for(Future<Integer>aFib : Fibanachi){
                        synchronized (monitor){
                            try {
                                System.out.println("Thread2 = " + check + " ,Current not randomed elements = " + aFib.get());
                                monitor.notify();
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
   }

    public void getFib() throws ExecutionException, InterruptedException {
        String str = "Полная последовательность чисел Фибаначи до " + Fibanachi.length + " элемента \n";
        for(Future<Integer> aFibanachi : Fibanachi){
            str += aFibanachi.get() + " ";

        }
        System.out.println(str + "\n");
    }
}
public class lab3n2 {
    public static void main(String[] argv) throws ExecutionException, InterruptedException {
        System.out.print("Потоки работают попеременно.\n");
        Work wrk = new Work(45);
        wrk.getFib();
        wrk.SomeWork();
    }
}