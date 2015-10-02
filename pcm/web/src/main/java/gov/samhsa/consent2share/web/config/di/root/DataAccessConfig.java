package gov.samhsa.consent2share.web.config.di.root;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = { "gov.samhsa.consent2share.domain" })
public class DataAccessConfig {

	@Value("${database.driverClassName}")
	private String databaseDriverClassName;

	@Value("${database.password}")
	private String databasePassword;

	@Value("${database.url}")
	private String databaseUrl;

	@Value("${database.username}")
	private String databaseUsername;

	@Bean
	public MigrationVersion baselineVersion() {
		MigrationVersion baselineVersion = MigrationVersion.fromVersion("1.0.37");
		return baselineVersion;

	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(databaseUrl);
		dataSource.setDriverClassName(databaseDriverClassName);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000);
		dataSource.setNumTestsPerEvictionRun(3);
		dataSource.setMinEvictableIdleTimeMillis(1800000);
		dataSource.setValidationQuery("SELECT 1");
		return dataSource;

	}

	@Bean
	@DependsOn("flyway")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceUnitName("persistenceUnit");
		entityManagerFactory
		.setPackagesToScan("gov.samhsa.consent2share.domain");
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
		java.util.Map<String, Object> jpaPropertyMap = entityManagerFactory
				.getJpaPropertyMap();
		jpaPropertyMap.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaPropertyMap.put("hibernate.ejb.naming_strategy",
				"org.hibernate.cfg.ImprovedNamingStrategy");
		jpaPropertyMap.put("hibernate.connection.charSet", "UTF-8");
		entityManagerFactory.setJpaPropertyMap(jpaPropertyMap);
		return entityManagerFactory;

	}

	@Bean(name="flyway",initMethod="migrate")
	public Flyway flyway() {
		Flyway flyway = new Flyway();
		flyway.setBaselineVersion(baselineVersion());
		flyway.setBaselineOnMigrate(true);
		flyway.setDataSource(dataSource());
		return flyway;

	}

	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;

	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getNativeEntityManagerFactory());
		return transactionManager;

	}


}
