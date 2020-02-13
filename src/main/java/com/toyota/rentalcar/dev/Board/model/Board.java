package com.toyota.rentalcar.dev.Board.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Entity
@Getter
@Table(name = "TBL_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity implements PasswordProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotNull
    private String userName;

    @NotNull
    @JsonIgnore
    @Size(min = 5, max = 60, message = "{password.size}")
    private String userPass;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Email
    private String email;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String content;

    @ColumnDefault("0")
    private Integer hit;

    @JsonManagedReference
    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<>();

    @Builder
    public Board(BoardType boardType, String userName, String userPass, String email, String title, String content){
        this.boardType = boardType;
        this.userName = userName;
        this.userPass = userPass;
        this.email    = email;
        this.title    = title;
        this.content  = content;
    }

    @PrePersist
    public void perPersist(){
        if(this.hit == null) this.hit = 0;
        if(this.boardType == null) this.boardType = BoardType.NON_FIXED_HEADER;
    }

    public void addReply(Reply reply) {
        this.replies.add(reply);
    }
    public void updateViewHit(Integer viewHit){
        this.hit = hit + viewHit;
    }
    public void updateBoardType(BoardType type){
        this.boardType = type;
    }

    public void updateArticle(Board board) {
        this.boardType = board.getBoardType();
        this.userName  = board.getUserName();
        setUserPass(board.getUserPass());
        this.email     = board.getEmail();
        this.title     = board.getTitle();
        this.content   = board.getContent();
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

