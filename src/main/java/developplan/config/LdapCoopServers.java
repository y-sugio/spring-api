package developplan.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "ldap.coop")
@Getter
@Setter
public class LdapCoopServers {

	private List<LdapCoopServer> servers;

	@Getter
	@Setter
	public static class LdapCoopServer {
		private String url;
		private String base;
		private String dn;
		private String password;
	}
}
