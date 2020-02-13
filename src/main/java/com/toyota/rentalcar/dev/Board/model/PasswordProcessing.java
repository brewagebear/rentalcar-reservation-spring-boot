package com.toyota.rentalcar.dev.Board.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface PasswordProcessing {

    public String encodingPassword(String submittedPassword);
    public boolean passwordCheckWithPasswordEncoder(BCryptPasswordEncoder passwordEncoder, String submittedPassword);
}
