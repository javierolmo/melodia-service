package com.javi.uned.pfgbackend.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Host
    @Value("${DB_HOST:melodiasqlserver.database.windows.net}")
    private String dbHost;

    // Port
    @Value("${DB_PORT:1433}")
    private String dbPort;

    // Database user
    @Value("${DB_USER:melodia}")
    private String dbUser;

    // Database password
    @Value("${DB_PASS:47921093mM?}")
    private String dbPass;

    // Database schema
    @Value("${DB_SCHEMA:melodia}")
    private String dbSchema;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        String url = String.format("jdbc:sqlserver://%s:%s;database=melodia;user=%s@melodiasqlserver;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", dbHost, dbPort, dbUser);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(dbUser);
        dataSourceBuilder.password(dbPass);
        return dataSourceBuilder.build();
    }

}
