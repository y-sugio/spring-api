package developplan.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.stereotype.Service;

import developplan.config.RandomLdapContextSourceProvider;

@Service
public class LdapAuthService {

	@Autowired
	private RandomLdapContextSourceProvider randomLdapContextSourceProvider;

	public boolean authenticate(String domain, String username, String password) {
		// ランダムにADサーバを選択
		LdapContextSource contextSource = randomLdapContextSourceProvider.getRandomContextSource(domain);
		try {
			// LDAPのBindAuthenticatorを使って認証
			BindAuthenticator authenticator = new BindAuthenticator(contextSource);
			authenticator.setUserDnPatterns(new String[] { "uid={0},ou=people" }); // 必要に応じて変更
			authenticator.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return true; // 認証成功
		} catch (Exception e) {
			return false; // 認証失敗
		}
	}
}
