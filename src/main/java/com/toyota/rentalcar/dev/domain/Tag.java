package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tbl_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long   id;
    private String title;

    @OneToMany(mappedBy = "tag")
    private List<RentalCarTag> rentalCarTagIds = new ArrayList<>();

    @Builder
    public Tag(String title){
        this.title = title;
    }
}
