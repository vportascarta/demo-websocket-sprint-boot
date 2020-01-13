package ca.nivtech.demowebsocket.domain;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

@Entity
public class Quart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @Column
    private ZonedDateTime start;

    @Column
    private Duration duration;

    @ManyToOne
    @JoinColumn(name="division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "affectation_id")
    private Affectation affectation;

    public Quart() {
    }

    public Quart(Employee owner, ZonedDateTime start, Duration duration, Division division, Affectation affectation) {
        this.owner = owner;
        this.start = start;
        this.duration = duration;
        this.division = division;
        this.affectation = affectation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Affectation getAffectation() {
        return affectation;
    }

    public void setAffectation(Affectation affectation) {
        this.affectation = affectation;
    }
}
