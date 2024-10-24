package developplan.application;

import java.util.List;

import javax.naming.directory.Attributes;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class LdapUserService {

	private final LdapTemplate ldapTemplate;

	public LdapUserService(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	// ユーザ一覧を取得するメソッド
	public List<String> getAllUsers() {
		return ldapTemplate.search(LdapQueryBuilder.query().where("objectclass").is("person"), // "person" クラスのオブジェクトを検索
				(Attributes attrs) -> attrs.get("cn").get().toString() // "cn" (common name) を取得
		);
	}
}
