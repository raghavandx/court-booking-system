package com.cbs.cbs.entity;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Booking",uniqueConstraints = @UniqueConstraint(columnNames = { "playerid", "bookingDate"}))
@NamedQueries({
        @NamedQuery(name = "countbookings", query = "select b from Booking b where b.bookingDate = :bookingDate")}
        )
public class Booking {

    public static String COUNT_BOOKINGS = "countbookings";
    public Booking() {
    }

    public Booking(Long id, Date bookingDate, Player player) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.player = player;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerid", referencedColumnName = "Id", nullable = false)
    private Player player;

    @Column(name = "courtId", nullable = true)
    private Long courtId;

    @Transient
    public boolean isBookingUnavailable() {
        return bookingUnavailable;
    }

    public void setBookingUnavailable(boolean bookingUnavailable) {
        this.bookingUnavailable = bookingUnavailable;
    }

    private boolean bookingUnavailable;

    public Long getId() {
        return id;
    }

    public void setId(Long bookingId) {
        this.id = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }
}
