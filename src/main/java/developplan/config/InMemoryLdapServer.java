package developplan.config;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;

import developplan.config.LdapCoopServers.LdapCoopServer;
import developplan.config.LdapNssServers.LdapNssServer;

@Configuration
class InMemoryLdapServer {

	@Autowired
	private LdapNssServers ldapNssServers;
	@Autowired
	private LdapCoopServers ldapCoopServers;

	@Bean
	InMemoryDirectoryServer inMemoryDirectoryServer() throws Exception {
		// サーバーの設定（複数のベースDNをサポート）
		InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=coop,dc=com", "dc=nss,dc=com");

		// 特定のアドレスとポートを設定
		InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("default",
				InetAddress.getByName("localhost"), // ホストアドレス
				1389, // ポート番号
				null, // サーバーソケットファクトリ（デフォルト）
				null, // クライアントソケットファクトリ（デフォルト）
				null // SSLソケットファクトリ（デフォルト）
		);

		config.setListenerConfigs(listenerConfig);

		// 各ドメインの管理者ユーザーを追加
		List<LdapNssServer> nssServers = ldapNssServers.getServers();
		List<LdapCoopServer> coopServers = ldapCoopServers.getServers();
		nssServers.forEach(server -> {
			try {
				config.addAdditionalBindCredentials(server.getDn(), server.getPassword());
			} catch (LDAPException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		});
		coopServers.forEach(server -> {
			try {
				config.addAdditionalBindCredentials(server.getDn(), server.getPassword());
			} catch (LDAPException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		});

		// インメモリLDAPサーバの起動
		InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
		server.importFromLDIF(true, new LDIFReader(new File("src/main/resources/initial_data.ldif")));
		server.startListening();
		return server;
	}
}
