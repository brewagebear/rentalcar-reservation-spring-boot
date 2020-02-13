package com.toyota.rentalcar.dev.Board.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "TBL_REPLIES")
@NoArgsConstructor
public class Reply extends BaseTimeEntity implements PasswordProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Lob
    private String content;
    private String userName;

    @JsonBackReference
    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @NotNull
    @JsonIgnore
    @Size(min = 5, max = 60, message = "{password.size}")
    private String userPass;

    @Builder
    public Reply(String content, String userName, String userPass, Board board){
        this.content  = content;
        this.userName = userName;
        this.userPass = userPass;
        this.board    = board;
    }

    public void update(String replyText, String replier, String userPass, Board board){
        this.content  = replyText;
        this.userName = replier;
        setUserPass(userPass);
        this.board    = board;
    }

    public void setUserPass(String rawPassword){
        this.userPass = encodingPassword(rawPassword);
    }

    @Override
    public String encodingPassword(String submittedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(submittedPassword);
    }

    @Override
    public boolean passwordCheckWithPasswordEncoder(BCryptPasswordEncoder passwordEncoder, String submittedPassword) {
        return passwordEncoder.matches(submittedPassword, this.getUserPass());
    }
}
