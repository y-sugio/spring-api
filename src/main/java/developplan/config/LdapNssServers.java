package developplan.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "ldap.nss")
@Getter
@Setter
public class LdapNssServers {
	private List<LdapNssServer> servers;

	@Getter
	@Setter
	public static class LdapNssServer {
		private String url;
		private String base;
		private String dn;
		private String password;
	}
}
