package com.toyota.rentalcar.dev.Board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class passwordForm {

    private String password;

    public passwordForm(String password) {
        this.password = password;
    }
}
