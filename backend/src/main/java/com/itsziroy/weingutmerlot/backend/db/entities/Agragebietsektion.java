package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "agragebietsektionen")
public class Agragebietsektion {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agragebiet_id", nullable = false)
    private Agragebiet agragebiet;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "size", nullable = false)
    private Integer size;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weinrebe_id", nullable = false)
    private Weinrebe weinrebe;

    public Agragebiet getAgragebiet() {
        return agragebiet;
    }

    public void setAgragebiet(Agragebiet agragebiet) {
        this.agragebiet = agragebiet;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Weinrebe getWeinrebe() {
        return weinrebe;
    }

    public void setWeinrebe(Weinrebe weinrebe) {
        this.weinrebe = weinrebe;
    }

}