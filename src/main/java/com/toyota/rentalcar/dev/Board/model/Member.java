package com.toyota.rentalcar.dev.Board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_MEMBER", uniqueConstraints = {@UniqueConstraint(columnNames = "userEmail")})
public class Member extends BaseTimeEntity implements PasswordProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @NotNull
    private String userName;

    @JsonIgnore
    @Size(min = 5, max = 60, message = "{password.size}")
    private String userPass;

    @Email
    @NotNull
    private String userEmail;

    @JsonIgnore
    boolean enabled;

    @Builder
    public Member(MemberRole memberRole, String userEmail,String userName, String userPass) {
        this.role = memberRole;
        this.userName = userName;
        this.userEmail = userEmail;
        setUserPass(userPass);
    }

    @PrePersist
    public void perPersist(){
        if(!this.enabled) this.enabled = true;
    }

    public void setUserPass(String rawPass){
        this.userPass = encodingPassword(rawPass);
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
