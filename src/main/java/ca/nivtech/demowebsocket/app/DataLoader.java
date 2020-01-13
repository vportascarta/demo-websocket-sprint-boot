package ca.nivtech.demowebsocket.app;

import ca.nivtech.demowebsocket.domain.*;
import ca.nivtech.demowebsocket.repository.AffectationRepository;
import ca.nivtech.demowebsocket.repository.DivisionRepository;
import ca.nivtech.demowebsocket.repository.EmployeeRepository;
import ca.nivtech.demowebsocket.repository.QuartRepository;
import com.github.javafaker.Faker;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class DataLoader implements ApplicationRunner {

    public static final int NB_EMPLOYEES = 100;
    public static final int NB_DIVISIONS = 3;
    public static final int NB_AFFECTATION_GROUPS = 2;
    public static final int NB_QUARTS = 200;
    private static final int NB_WEEKS_APART = 4;

    private EmployeeRepository employeeRepository;
    private DivisionRepository divisionRepository;
    private AffectationRepository affectationRepository;
    private QuartRepository quartRepository;

    @Autowired
    public DataLoader(EmployeeRepository employeeRepository, DivisionRepository divisionRepository, AffectationRepository affectationRepository, QuartRepository quartRepository) {
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
        this.affectationRepository = affectationRepository;
        this.quartRepository = quartRepository;
    }

    public void run(ApplicationArguments args) {
        Faker faker = Faker.instance(Locale.CANADA_FRENCH);

        List<Employee> employees = new ArrayList<>(NB_EMPLOYEES);
        List<Division> divisions = new ArrayList<>(NB_DIVISIONS);
        List<Affectation> affectations = new ArrayList<>(NB_AFFECTATION_GROUPS * 3);

        if (employeeRepository.count() == 0) {
            for (int i = 0; i < NB_EMPLOYEES; i++) {
                employees.add(employeeRepository.save(Employee.createRandom(faker)));
            }
        }

        if (divisionRepository.count() == 0) {
            for (int i = 0; i < NB_DIVISIONS; i++) {
                divisions.add(divisionRepository.save(new Division("Division " + i)));
            }
        }

        if (affectationRepository.count() == 0) {
            for (int i = 0; i < NB_AFFECTATION_GROUPS; i++) {
                affectations.add(affectationRepository.save(new Affectation("Travail " + i, RemunerationType.PAID, AffectationType.WORK)));
                affectations.add(affectationRepository.save(new Affectation("Absence " + i, RemunerationType.PAID, AffectationType.ABSENCE)));
                affectations.add(affectationRepository.save(new Affectation("Non dispo " + i, RemunerationType.NOT_PAID, AffectationType.NON_DISPO)));
            }
        }

        if (quartRepository.count() == 0) {
            Date from = Date.from(LocalDateTime.now().minusWeeks(NB_WEEKS_APART).toInstant(ZoneOffset.UTC));
            Date to = Date.from(LocalDateTime.now().plusWeeks(NB_WEEKS_APART).toInstant(ZoneOffset.UTC));

            for (int i = 0; i < NB_QUARTS; i++) {
                Employee employee = employees.get(faker.number().numberBetween(0, NB_EMPLOYEES));
                Division division = divisions.get(faker.number().numberBetween(0, NB_DIVISIONS));
                Affectation affectation = affectations.get(faker.number().numberBetween(0, (NB_AFFECTATION_GROUPS * 3)));

                quartRepository.save(new Quart(employee,
                        faker.date().between(from, to).toInstant().atZone(ZoneId.systemDefault()),
                        Duration.of(faker.number().numberBetween(1, 8), ChronoUnit.HOURS),
                        division, affectation
                ));
            }
        }
    }
}
