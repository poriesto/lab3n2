import java.util.Random;

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
    final private int[] Fibanachi;
    final private Random rnd = new Random();
    private int check = 0;
    final private Object monitor = new Object();
    Work(int lenght) {
        Fibanachi = new int[lenght];
        double golden = (1 + Math.sqrt(5)) / 2;

        for(int i = 0; i < Fibanachi.length; i++){
            double tmp = (((Math.pow(golden, i)) - (Math.pow(-golden, -i)))/(2*golden -1));
            Fibanachi[i] = (int)tmp;
        }
    }
    public void SomeWork(){
   }

    public void getFib(){
        String str = "Полная последовательность чисел Фибаначи до " + Fibanachi.length + " элемента \n";
        for(int aFibanachi : Fibanachi){
            str += aFibanachi + " ";
        }
        System.out.println(str + "\n");
    }
}
public class lab3n2 {
    public static void main(String[] argv){
        System.out.print("Потоки работают попеременно.\n");
        Work wrk = new Work(30);
        wrk.getFib();
        wrk.SomeWork();
    }
}