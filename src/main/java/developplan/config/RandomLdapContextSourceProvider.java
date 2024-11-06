package developplan.config;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Component
public class RandomLdapContextSourceProvider {

	@Autowired
	@Qualifier("coopLdapContextSources")
	private List<LdapContextSource> coopLdapServers;

	@Autowired
	@Qualifier("nssLdapContextSources")
	private List<LdapContextSource> nssLdapServers;

	public LdapContextSource getRandomContextSource(String domain) {
		final SecureRandom random = new SecureRandom();
		if ("nss".equalsIgnoreCase(domain)) {
			return nssLdapServers.get(random.nextInt(nssLdapServers.size()));
		} else if ("coop".equalsIgnoreCase(domain)) {
			return coopLdapServers.get(random.nextInt(coopLdapServers.size()));
		} else {
			throw new IllegalArgumentException("無効なドメイン: " + domain);
		}
	}
}
