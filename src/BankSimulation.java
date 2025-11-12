import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

public class BankSimulation {

    public static void main(String[] args) throws InterruptedException {
        int accountCount = 10;
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        List<Account> accounts = new ArrayList<>();

        // 1. Initialize accounts
        for (int i = 0; i < accountCount; i++) {
            accounts.add(new Account(i + 1, initialBalance));
        }

        // 2. Create thread pool
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // 3. Submit transfer tasks
        int operations = 100000;
        for (int i = 0; i < operations; i++) {
            Account from = accounts.get(ThreadLocalRandom.current().nextInt(accountCount));
            Account to = accounts.get(ThreadLocalRandom.current().nextInt(accountCount));
            if (from == to) continue; // skip same-account transfers

            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(1, 200));
            pool.submit(new TransferTask(from, to, amount));
        }

        // 4. Shutdown + await completion
        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        // 5. Verify total balance consistency
        BigDecimal total = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("==== Final Account Balances ====");
        accounts.forEach(System.out::println);
        System.out.println("TOTAL = " + total);
    }
}
