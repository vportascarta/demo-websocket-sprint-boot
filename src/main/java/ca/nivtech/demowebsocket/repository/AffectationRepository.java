package ca.nivtech.demowebsocket.repository;

import ca.nivtech.demowebsocket.domain.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {}
