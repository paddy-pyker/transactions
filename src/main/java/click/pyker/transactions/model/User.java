package click.pyker.transactions.model;


import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal accountBalance;

    private String username;
    
    public User(){
    }

    public User(String username, BigDecimal accountBalance){
        this.username = username;
        this.accountBalance = accountBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setAccountBalance(BigDecimal balance) {
        this.accountBalance = balance;
    }

    public BigDecimal getAccountBalance(){
        return this.accountBalance;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
    

}
