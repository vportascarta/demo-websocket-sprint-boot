package ca.nivtech.demowebsocket.controller;

import ca.nivtech.demowebsocket.controller.dto.EmployeeConverter;
import ca.nivtech.demowebsocket.controller.dto.EmployeeDTO;
import ca.nivtech.demowebsocket.domain.Employee;
import ca.nivtech.demowebsocket.repository.EmployeeRepository;
import ca.nivtech.demowebsocket.utils.LockedMap;
import ca.nivtech.demowebsocket.utils.SSEUtils;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final Faker faker = Faker.instance(Locale.CANADA_FRENCH);
    private static final EmployeeConverter employeeConverter = new EmployeeConverter();

    private final SimpMessagingTemplate messaging;
    private final EmployeeRepository employeeRepository;
    private final LockedMap<Long, Long> employeesLocked = new LockedMap<>();

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, SimpMessagingTemplate messaging) {
        this.employeeRepository = employeeRepository;
        this.messaging = messaging;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        logger.debug("Get employee " + id);
        var employeeOptional = employeeRepository.findById(id);
        return ResponseEntity.of(employeeOptional.map(employeeConverter::toDTO));
    }

    @GetMapping("/all")
    public SseEmitter getEmployeesChunked() {
        logger.debug("Get all employees");

        return SSEUtils.createDataFetchSSE(emitter -> {
            var employeePage = employeeRepository.findAll(PageRequest.of(0, 5));
            while (true) {
                if (employeePage.hasContent()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                        break;
                    }
                    try {
                        var employeeDTOS = employeeConverter.toDTO(employeePage.getContent());
                        employeeDTOS.forEach(employeeDTO -> {
                            if(employeesLocked.isLocked(employeeDTO.getId())) {
                                employeeDTO.setLocked(true);
                            }
                        });

                        emitter.send(SseEmitter.event()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .name(SSEUtils.GET_ALL_EVENT_NAME)
                                .data(employeeDTOS)
                        );
                    } catch (IOException ignored) {
                        break;
                    }
                    try {
                        emitter.send(SseEmitter.event()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .name(SSEUtils.PROGRESSION_EVENT_NAME)
                                .data(100.0 * employeePage.getNumber() / employeePage.getTotalPages())
                        );
                    } catch (IOException ignored) {
                        break;
                    }
                }
                if (employeePage.hasNext()) {
                    employeePage = employeeRepository.findAll(employeePage.nextPageable());
                } else {
                    break;
                }
            }
        });
    }

    @PostMapping("/random")
    @Valid
    public ResponseEntity<?> random() {
        var saveEmployee = employeeRepository.save(Employee.createRandom(faker));
        messaging.convertAndSend("/topics/employee", employeeConverter.toDTO(saveEmployee));
        return ResponseEntity.ok(true);
    }

    @PostMapping
    @Valid
    public ResponseEntity<?> add(@RequestBody EmployeeDTO employeeDTO) {
        var saveEmployee = employeeRepository.save(employeeConverter.toDomain(employeeDTO));
        messaging.convertAndSend("/topics/employee", employeeConverter.toDTO(saveEmployee));
        return ResponseEntity.ok(true);
    }

    @PutMapping
    @Valid
    public ResponseEntity<?> update(@RequestBody EmployeeDTO employeeDTO) {
        var saveEmployee = employeeRepository.save(employeeConverter.toDomain(employeeDTO));
        messaging.convertAndSend("/topics/employee", employeeConverter.toDTO(saveEmployee));
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeRepository.findById(id).ifPresent(employee -> {
            employee.setDeleted(true);
            var saveEmployee = employeeRepository.save(employee);
            messaging.convertAndSend("/topics/employee", employeeConverter.toDTO(saveEmployee));
        });
        return ResponseEntity.ok(true);
    }

    @PostMapping("/lock/{id}")
    public ResponseEntity<?> lock(@PathVariable Long id) {
        employeesLocked.lock(id, 0L);
        var employeeDTOOptional = employeeRepository.findById(id).map(employeeConverter::toDTO);
        employeeDTOOptional.ifPresent(employeeDTO -> {
            employeeDTO.setLocked(true);
            messaging.convertAndSend("/topics/employee", employeeDTO);
        });

        return ResponseEntity.ok(employeeDTOOptional.isPresent());
    }

    @PostMapping("/unlock/{id}")
    public ResponseEntity<?> unlock(@PathVariable Long id) {
        employeesLocked.unlock(id, 0L);
        var employeeDTOOptional = employeeRepository.findById(id).map(employeeConverter::toDTO);
        employeeDTOOptional.ifPresent(employeeDTO -> {
            messaging.convertAndSend("/topics/employee", employeeDTO);
        });

        return ResponseEntity.ok(employeeDTOOptional.isPresent());
    }
}
