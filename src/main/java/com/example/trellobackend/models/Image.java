package com.example.trellobackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Image")
@Builder    //https://www.javaguides.net/2019/03/project-lombok-builder-pattern-using-builder-annotation.html
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob    //https://www.javaguides.net/2020/10/lob-jpa-annotation-with-example.html
    @Column(name = "image")
    private byte[] image;
}
