package com.toyota.rentalcar.dev.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TBL_REPLIES")
@NoArgsConstructor
public class Reply extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String replyText;
    private String replier;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
