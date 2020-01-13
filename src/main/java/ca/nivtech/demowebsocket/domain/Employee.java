package ca.nivtech.demowebsocket.domain;

import com.github.javafaker.Faker;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String matricule;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private ZonedDateTime seniorityDate;

    @ElementCollection(targetClass = String.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column
    private Map<AdditionnalInfoKey, String> additionnalInfo;

    @Column
    private boolean deleted;

    @OneToMany(mappedBy = "owner")
    private Set<Quart> quarts;

    public Employee() {
    }

    public Employee(Long id, String matricule, String firstName, String lastName, ZonedDateTime seniorityDate, Map<AdditionnalInfoKey, String> additionnalInfo, boolean deleted) {
        this.id = id;
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seniorityDate = seniorityDate;
        this.additionnalInfo = additionnalInfo;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getSeniorityDate() {
        return seniorityDate;
    }

    public void setSeniorityDate(ZonedDateTime seniorityDate) {
        this.seniorityDate = seniorityDate;
    }

    public Map<AdditionnalInfoKey, String> getAdditionnalInfo() {
        return additionnalInfo;
    }

    public void setAdditionnalInfo(Map<AdditionnalInfoKey, String> additionnalInfoMap) {
        this.additionnalInfo = additionnalInfoMap;
    }

    public String getAdditionnalInfo(AdditionnalInfoKey key) {
        return additionnalInfo.get(key);
    }

    public void setAdditionnalInfo(AdditionnalInfoKey key, String value) {
        this.additionnalInfo.put(key, value);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public static Employee createRandom(Faker faker) {
        return new Employee(null, faker.idNumber().valid(), faker.name().firstName(), faker.name().lastName(),
                faker.date().past(3650, 365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()),
                Collections.singletonMap(AdditionnalInfoKey.EXTERNAL_ID, faker.idNumber().validSvSeSsn()), false);
    }
}
