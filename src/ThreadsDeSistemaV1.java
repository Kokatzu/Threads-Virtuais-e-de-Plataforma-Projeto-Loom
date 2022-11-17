import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class ThreadsDeSistemaV1 {
    public static void main(String[] args) {

        // Para monitorar o número de Threads do Sistema usados pelo teste, escreva o seguinte código:
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
            System.out.println(threadInfo.length + " Threads de Sistema");
        }, 1, 1, TimeUnit.SECONDS);

        long l = System.currentTimeMillis();

        // O pool de threads de agendamento obtém e imprime o número de threads do sistema a cada segundo, o que é conveniente para observar o número de threads.
        try (var executor = Executors.newCachedThreadPool()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println(i);
                    return i;
                });
            });
        }
        System.out.printf("Tempo de execução：%d ms", System.currentTimeMillis() - l);
    }
}