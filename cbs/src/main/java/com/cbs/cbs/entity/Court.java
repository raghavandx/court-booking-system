package com.cbs.cbs.entity;

import javax.persistence.*;

@Entity
@Table(name = "Court")
@NamedQueries({
        @NamedQuery(name = "courtquery", query = "select c from Court c, Booking b where b.bookingDate = :bookingDate")}
)
public class Court {

    public static String COURT_QUERY = "courtquery";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CourtNumber")
    private Integer number;

    @Column(name = "CourtName")
    private String name;

    public Court() {

    }

    public Court(Long courtId, Integer number, String name) {
        this.id = courtId;
        this.number = number;
        this.name = name;
    }

    public Long getCourtId() {
        return id;
    }

    public void setCourtId(Long courtId) {
        this.id = courtId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
