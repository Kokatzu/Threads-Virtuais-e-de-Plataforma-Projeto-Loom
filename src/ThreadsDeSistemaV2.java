import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class ThreadsDeSistemaV2 {
    public static void main(String[] args) {

        // Para monitorar o número de Threads do Sistema usados pelo teste, escreva o seguinte código:
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
            System.out.println(threadInfo.length + " Threads de Sistema");
        }, 1, 1, TimeUnit.SECONDS);

        long l = System.currentTimeMillis();

        // Agora usamos um pool de threads com tamanho fixo de 200 para resolver o problema de não aplicar muitos threads do sistema:
        try (var executor = Executors.newFixedThreadPool(200)) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println(i);
                    return i;
                });
            });
        }

        System.out.printf("Tempo de exeução: %dms\n", System.currentTimeMillis() - l);
    }
}
