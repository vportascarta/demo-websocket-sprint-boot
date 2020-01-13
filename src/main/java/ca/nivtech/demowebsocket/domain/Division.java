package ca.nivtech.demowebsocket.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String displayName;

    @OneToMany(mappedBy="division")
    private Set<Quart> quarts;

    public Division() {
    }

    public Division(String displayName) {
        this.displayName = displayName;
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
}
