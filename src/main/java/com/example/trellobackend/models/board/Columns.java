package com.example.trellobackend.models.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @ElementCollection
    @CollectionTable(name = "card_order_ids", joinColumns = @JoinColumn(name = "columns_id"))
    @OrderColumn
    @Column(name = "card_order_id")
    private List<Long> cardOrderIds;
    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;
}
