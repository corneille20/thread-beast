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

        // init accounts
        BigDecimal fromPrev = from.getBalance();
        BigDecimal toPrev = to.getBalance();

        // locking both accounts
        Account first = from.getId() < to.getId() ? from : to;
        Account second = from.getId() < to.getId() ? to : from;

        first.getLock().lock();
        second.getLock().lock();

        // Check if from.getBalance() >= amount
        try {
            try {
                if (from.getBalance().compareTo(amount) >= 0) {
                    from.withdraw(amount);// withdraw
                    to.deposit(amount);// deposit
                    System.out.printf("%s: %s → %s  Amount=%s%n",
                            Thread.currentThread().getName(), from.getId(), to.getId(), amount);
                }
            } finally {
                //unlock in finally block
                first.getLock().unlock();
                second.getLock().unlock();
            }
        }catch (Exception ex){
            from.setBalance(fromPrev);
            to.setBalance(toPrev);
            // 💣 Optional: introduce a 5% random failure to simulate rollback testing
            if (ThreadLocalRandom.current().nextInt(100) < 5) throw new RuntimeException("Simulated failure");
            System.err.println("Nasubiye inyuma (ROLLBACK → " + ex.getMessage());
        }
    }
}
