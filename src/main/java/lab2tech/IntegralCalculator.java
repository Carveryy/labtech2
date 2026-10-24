package lab2tech;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IntegralCalculator {
    public double calculate(double a, double b, int n, Function f, int threadsCount) throws InterruptedException, ExecutionException {
        double h = (b - a) / n;

        // Створення пулу потоків
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Future<Double>> futures = new ArrayList<>();

        int intervalsPerTask = n / threadsCount;
        int remainder = n % threadsCount;

        int currentStart = 1;
        for (int i = 0; i < threadsCount; i++) {
            int intervals = intervalsPerTask + (i < remainder ? 1 : 0);
            final int start = currentStart;
            final int end = currentStart + intervals - 1;

            // Створення задачі, яка повертає значення (Callable)
            Callable<Double> task = () -> {
                double sum = 0;
                for (int k = start; k <= end; k++) {
                    double x_k = a + k * h;
                    double x_k_minus_1 = a + (k - 1) * h;
                    double mid = (x_k_minus_1 + x_k) / 2.0;

                    sum += 2 * f.calculate(mid);
                    if (k < n) {
                        sum += f.calculate(x_k);
                    }
                }
                return sum;
            };

            // Відправка задачі на виконання та збереження об'єкта Future
            futures.add(executor.submit(task));
            currentStart = end + 1;
        }

        double totalSum = 0.5 * f.calculate(a) + 0.5 * f.calculate(b);

        // Отримання результатів з усіх потоків
        for (Future<Double> future : futures) {
            totalSum += future.get();
        }

        // Завершення роботи виконавця
        executor.shutdown();

        return (h / 3.0) * totalSum;
    }
}