package click.pyker.transactions.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long sender;

    @Column(name = "receiver_id")
    private Long receiver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private BigDecimal amount;
    
    public Transaction(){

    }

    public Transaction(Long senderId, Long receiverId, BigDecimal amount) {
        this.sender = senderId;
        this.receiver = receiverId;
        this.amount = amount;
    }

    @PrePersist
    protected void onCreate() {
        transactionDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        transactionDate = new Date();
    }

    public Long getSender() {
        return this.sender;
    }

    public Long getId() {
        return this.id;
    }

    public Long getReceiver() {
        return this.receiver;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Date getTransactionDate(){
        return this.transactionDate;
    }

    public void setSender(Long senderId) {
        this.sender = senderId;
    }

    public void setReceiver(Long receiverId) {
        this.receiver = receiverId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isValid() {

        if(this.sender >= 0 && this.receiver >= 0 && this.amount.compareTo(BigDecimal.ZERO) > 0){
            return true;
        } 

        return false;
    }

}

