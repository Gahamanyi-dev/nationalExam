package com.example.server.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "links")
public class Link {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    public Link(String url) {
        this.url = url;
    }

    public Link() {

    }
}
