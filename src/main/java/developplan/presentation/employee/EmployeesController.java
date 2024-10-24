package developplan.presentation.employee;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import developplan.application.EmployeeService;
import developplan.domain.model.employee.Employee;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeesController {

	@Autowired
	EmployeeService service;

	private static final Logger log = LoggerFactory.getLogger(EmployeesController.class);

	@PostMapping
	public void createEmployee(@RequestBody(required = true) Employee employee) {
		service.save(employee);
	}

	@GetMapping
	public List<Employee> search(@RequestParam(value = "fisrtName", defaultValue = "") String fisrtName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName) {
		List<Employee> employees = service.search(fisrtName, lastName);
		return employees;
	}

	@GetMapping("/{id}")
	public Employee findById(@PathVariable("id") Long id) {
		EmployeeResponse res = new EmployeeResponse();
		Optional<Employee> employee = service.findById(id);
		log.info(employee.get().toString());
		return employee.get();
	}
}
