package developplan.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import developplan.application.LdapUserService;

@RestController
@RequestMapping("/api/users")
public class LdapUserController {

	private final LdapUserService ldapUserService;

	public LdapUserController(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	@GetMapping
	public List<String> getUsers() {
		return ldapUserService.getAllUsers();
	}
}
