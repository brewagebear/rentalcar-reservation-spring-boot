package com.toyota.rentalcar.dev.Board.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PasswordFormForMember {

    @NotNull
    private String userEmail;

    @NotNull
    private String userPass;

    public PasswordFormForMember(String userEmail, String userPass) {
        this.userEmail = userEmail;
        this.userPass = userPass;
    }
}
