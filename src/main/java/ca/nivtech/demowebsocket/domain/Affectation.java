package ca.nivtech.demowebsocket.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Affectation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String displayName;

    @Column
    private RemunerationType remunerationType;

    @Column
    private AffectationType affectationType;

    @OneToMany(mappedBy="affectation")
    private Set<Quart> quarts;

    public Affectation() {
    }

    public Affectation(String displayName, RemunerationType remunerationType, AffectationType affectationType) {
        this.displayName = displayName;
        this.remunerationType = remunerationType;
        this.affectationType = affectationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public RemunerationType getRemunerationType() {
        return remunerationType;
    }

    public void setRemunerationType(RemunerationType remunerationType) {
        this.remunerationType = remunerationType;
    }

    public AffectationType getAffectationType() {
        return affectationType;
    }

    public void setAffectationType(AffectationType affectationType) {
        this.affectationType = affectationType;
    }
}
