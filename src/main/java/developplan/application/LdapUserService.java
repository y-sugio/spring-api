package developplan.application;

import java.util.List;

import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import developplan.config.RandomLdapContextSourceProvider;

@Service
public class LdapUserService {

	@Autowired
	private RandomLdapContextSourceProvider provider;

	// ユーザ一覧を取得するメソッド
	public List<String> getAllUsers() {
		LdapContextSource randomContextSource = this.provider.getRandomContextSource("coop");
		LdapTemplate ldapTemplate = new LdapTemplate(randomContextSource);
		return ldapTemplate.search(LdapQueryBuilder.query().where("objectclass").is("person"), // "person" クラスのオブジェクトを検索
				(Attributes attrs) -> attrs.get("cn").get().toString() // "cn" (common name) を取得
		);
	}
}
