package com.wiyata.backend.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Username {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    public String getFullName(){
        return String.format("%s %s",firstName,lastName);
    }
}
