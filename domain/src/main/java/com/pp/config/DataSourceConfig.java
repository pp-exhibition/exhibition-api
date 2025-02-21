package com.pp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.exhibition")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());
        String dataSourceLog = System.lineSeparator() +
                "\t[DEBUG] Hikari Datasource" + System.lineSeparator() +
                "\tMinimum Idle \t\t\t\t\t: " + dataSource.getMinimumIdle() + System.lineSeparator() +
                "\tMaximum Pool Size \t\t\t\t: " + dataSource.getMaximumPoolSize() + System.lineSeparator() +
                "\tIdle Timeout \t\t\t\t\t: " + dataSource.getIdleTimeout() + System.lineSeparator() +
                "\tMax Lifetime \t\t\t\t\t: " + dataSource.getMaxLifetime() + System.lineSeparator() +
                "\tConnection Timeout \t\t\t\t: " + dataSource.getConnectionTimeout();
        log.debug(dataSourceLog);
        return dataSource;
    }

}
