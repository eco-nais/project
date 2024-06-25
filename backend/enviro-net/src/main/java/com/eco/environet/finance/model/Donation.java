package com.eco.environet.finance.model;

import com.eco.environet.users.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "donationBuilder")
@Table(name="donations", schema = "finance")
public class Donation extends Revenue{
    @ManyToOne
    private User donator;

    public Donation(Revenue revenue){
        super(revenue.getId(), revenue.getCreatedOn(), revenue.getType(), revenue.getAmount());
    }

    public User getDonator() { return donator; }
    public void setDonator(User donator) {
        this.donator = donator;
    }
}
