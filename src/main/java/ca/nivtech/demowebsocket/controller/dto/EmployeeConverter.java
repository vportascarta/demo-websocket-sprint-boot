package ca.nivtech.demowebsocket.controller.dto;

import ca.nivtech.demowebsocket.domain.Employee;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeConverter implements DTOConverter<EmployeeDTO, Employee> {
    @Override
    public Employee toDomain(EmployeeDTO from) {
        return new Employee(
                from.getId(), from.getMatricule(), from.getFirstName(), from.getLastName(),
                LocalDateTime.parse(from.getSeniorityDate(), DateTimeFormatter.ISO_LOCAL_DATE).atZone(ZoneId.systemDefault()),
                from.getAdditionnalInfo(), from.isDeleted()
        );
    }

    @Override
    public EmployeeDTO toDTO(Employee from) {
        return new EmployeeDTO(
                from.getId(), from.getMatricule(), from.getFirstName(), from.getLastName(),
                from.getSeniorityDate().format(DateTimeFormatter.ISO_LOCAL_DATE), from.getAdditionnalInfo(),
                from.isDeleted()
        );
    }
}
