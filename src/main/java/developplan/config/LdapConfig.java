package developplan.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;

import developplan.config.LdapCoopServers.LdapCoopServer;
import developplan.config.LdapNssServers.LdapNssServer;

@Configuration
public class LdapConfig {

	@Autowired
	private LdapCoopServers ldapCoopServers;

	@Autowired
	private LdapNssServers ldapNssServers;

	@Bean(name = "coopLdapContextSources")
	List<LdapContextSource> coopLdapContextSources() {
		// COOPドメインのサーバ設定
		List<LdapCoopServer> servers = this.ldapCoopServers.getServers();
		List<LdapContextSource> contexts = servers.stream().map(server -> {
			LdapContextSource contextSource = new LdapContextSource();
			contextSource.setUrl(server.getUrl());
			contextSource.setBase(server.getBase());
			contextSource.setUserDn(server.getDn());
			contextSource.setPassword(server.getPassword());
			contextSource.afterPropertiesSet();
			return contextSource;
		}).toList();
		return contexts;
	}

	@Bean(name = "nssLdapContextSources")
	List<LdapContextSource> nssLdapContextSources() {
		// NSSドメインのサーバ設定
		List<LdapNssServer> servers = this.ldapNssServers.getServers();
		List<LdapContextSource> contexts = servers.stream().map(server -> {
			LdapContextSource contextSource = new LdapContextSource();
			contextSource.setUrl(server.getUrl());
			contextSource.setBase(server.getBase());
			contextSource.setUserDn(server.getDn());
			contextSource.setPassword(server.getPassword());
			contextSource.afterPropertiesSet();
			return contextSource;
		}).toList();
		return contexts;
	}
}
