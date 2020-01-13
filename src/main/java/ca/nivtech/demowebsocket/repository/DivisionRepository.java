package ca.nivtech.demowebsocket.repository;

import ca.nivtech.demowebsocket.domain.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {}
