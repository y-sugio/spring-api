/**
 *
 */
package developplan.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import developplan.domain.model.employee.Employee;
import developplan.domain.repository.EmployeeRepository;

/**
 *
 */
@Transactional
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Optional<Employee> findById(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee;
	}

	public List<Employee> search(String firstname, String lastname) {
		List<Employee> employees = employeeRepository.findEmployeeByFirstNameContainingAndLastNameContaining(firstname,
				lastname);
		return employees;
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
}
