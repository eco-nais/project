package com.eco.environet.users.dto;

import com.eco.environet.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContactDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;

    public UserContactDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    @Override
    public String toString() {
        return name + " " + surname + " (" + username + ")";
    }
}
