package developplan.config;

import java.io.File;
import java.net.InetAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldif.LDIFReader;

@Configuration
class InMemoryLdapServer {

	@Bean
	InMemoryDirectoryServer inMemoryDirectoryServer() throws Exception {
		// サーバーの設定
		InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=example,dc=com");

		// 特定のアドレスとポートを設定
		InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("default",
				InetAddress.getByName("localhost"), // ホストアドレス
				1389, // ポート番号
				null, // サーバーソケットファクトリ（デフォルト）
				null, // クライアントソケットファクトリ（デフォルト）
				null // SSLソケットファクトリ（デフォルト）
		);

		config.setListenerConfigs(listenerConfig);

		config.addAdditionalBindCredentials("cn=admin,dc=example,dc=com", "password");

		// インメモリLDAPサーバの起動
		InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
		server.importFromLDIF(true, new LDIFReader(new File("src/main/resources/initial_data.ldif")));
		server.startListening();
		return server;
	}

	@Bean
	LdapContextSource ldapContextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl("ldap://localhost:1389"); // LDAPサーバーのURL
		contextSource.setBase("dc=example,dc=com"); // ベースDN
		contextSource.setUserDn("cn=admin,dc=example,dc=com"); // 認証ユーザーDN
		contextSource.setPassword("password"); // 認証パスワード
		contextSource.afterPropertiesSet();
		return contextSource;
	}

	@Bean
	LdapTemplate ldapTemplate() {
		return new LdapTemplate(ldapContextSource());
	}
}
