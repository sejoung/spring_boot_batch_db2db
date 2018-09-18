package com.github.sejoung.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages="com.github.sejoung.repositories.bdb", entityManagerFactoryRef="bDBEntityManager", transactionManagerRef="bDBtm")
public class BDBConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.bdb.datasource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "bDBEntityManager")
    public LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(mysqlDataSource()).packages("com.github.sejoung.model.bdb").build();
    }
    
    @Bean(name="bDBtm")
    public PlatformTransactionManager transactionManager1(@Qualifier("bDBEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
