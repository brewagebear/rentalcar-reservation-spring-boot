package com.toyota.rentalcar.dev.Board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PasswordForm {

    @NotNull
    private String password;
    public PasswordForm(String password) {
        this.password = password;
    }
}
