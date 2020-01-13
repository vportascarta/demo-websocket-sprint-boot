package ca.nivtech.demowebsocket.controller.dto;

import ca.nivtech.demowebsocket.domain.AdditionnalInfoKey;
import ca.nivtech.demowebsocket.domain.Employee;
import ca.nivtech.demowebsocket.domain.Quart;
import com.github.javafaker.Faker;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EmployeeDTO {
    @Positive
    private Long id;

    @Length(max = 255)
    private String matricule;

    @Length(max = 255)
    private String firstName;

    @Length(max = 255)
    private String lastName;

    @NotNull
    private String seniorityDate;

    private Map<AdditionnalInfoKey, String> additionnalInfo;

    private boolean locked = false;

    @NotNull
    private boolean deleted;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String matricule, String firstName, String lastName, String seniorityDate, Map<AdditionnalInfoKey, String> additionnalInfo, boolean deleted) {
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

    public String getSeniorityDate() {
        return seniorityDate;
    }

    public void setSeniorityDate(String seniorityDate) {
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
