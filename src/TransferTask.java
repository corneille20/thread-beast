import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class TransferTask implements Runnable {

    private final Account from;
    private final Account to;
    private final BigDecimal amount;

    public TransferTask(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        // 🧠 TODO: Implement safe transfer logic
        // 1. Lock both accounts in consistent order (to avoid deadlock)
        // 2. Check if from.getBalance() >= amount
        // 3. Withdraw from one, deposit into the other
        // 4. Always unlock in finally block
        // 5. Print thread name + success message

        // 💣 Optional: introduce a 5% random failure to simulate rollback testing
        // if (ThreadLocalRandom.current().nextInt(100) < 5) throw new RuntimeException("Simulated failure");
    }
}
