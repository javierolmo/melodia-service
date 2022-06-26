package com.javi.uned.pfgbackend.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Connection String
    @Value("${DATABASE_CONNECTION_STRING:jdbc:sqlserver://melodiasqlserver.database.windows.net:1433;database=melodia;user=melodia@melodiasqlserver;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;}")
    private String connectionString;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(connectionString);
        dataSourceBuilder.password("47921093mM?");
        return dataSourceBuilder.build();
    }

}
