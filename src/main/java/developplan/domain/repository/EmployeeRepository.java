/**
 *
 */
package developplan.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import developplan.domain.model.employee.Employee;

/**
 * 社員に関するCRUDインタフェースを提供する
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {
	/**
	 * IDに紐づく社員を返す
	 *
	 * @param id 社員ID
	 * @return 社員
	 */
	@Override
	public Optional<Employee> findById(Long id);

	@Override
	Employee save(Employee entity);

	public List<Employee> findEmployeeByFirstNameContainingAndLastNameContaining(String firstName, String LastName);
}
