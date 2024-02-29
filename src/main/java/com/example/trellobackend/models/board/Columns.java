package com.example.trellobackend.models.board;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @ElementCollection
    private List<Long> cardOrderIds;
    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL)
    private List<Card> cards;
}
