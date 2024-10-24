package developplan.domain.model.employee;

import org.springframework.data.annotation.Id;

public record Employee(@Id Long id, String firstName, String lastName) {
}