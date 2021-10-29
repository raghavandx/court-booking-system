package com.cbs.cbs.entity;


import javax.persistence.*;

@Entity
@Table(name = "Player",uniqueConstraints = @UniqueConstraint(columnNames = { "playername"}))
public class Player {

    public Player() {
        
    }

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name = "playername")
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
