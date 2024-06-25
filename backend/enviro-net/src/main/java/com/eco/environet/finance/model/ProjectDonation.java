package com.eco.environet.finance.model;

import com.eco.environet.projects.model.Project;
import com.eco.environet.users.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "projectDonationBuilder")
@Table(name="project_donations", schema = "finance")
public class ProjectDonation extends Revenue {
    @ManyToOne
    private Project project;
    @ManyToOne
    private User donator;

    public ProjectDonation(Donation donation){
        super(donation.getId(), donation.getCreatedOn(), donation.getType(), donation.getAmount());
        this.donator = donation.getDonator();
    }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public User getDonator() { return donator; }
    public void setDonator(User donator) { this.donator = donator; }
}
