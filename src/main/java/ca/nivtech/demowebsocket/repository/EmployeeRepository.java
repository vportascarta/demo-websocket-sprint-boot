package ca.nivtech.demowebsocket.repository;

import ca.nivtech.demowebsocket.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
