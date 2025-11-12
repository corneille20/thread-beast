import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final long id;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(long id, BigDecimal initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Account " + id + " → balance=" + balance;
    }
}
