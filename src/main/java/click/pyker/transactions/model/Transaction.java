package click.pyker.transactions.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private BigDecimal amount;

    // Constructors, getters, and setters
}

