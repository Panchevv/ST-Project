package com.todo.project.persistence;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    /**
     * Configure data source by setting url, username and password from properties file.
     * @return data source
     */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                // TODO: create class that injects all needed secrets from the resource file and use them here for the DB connection
//                .url(properties.getUrl())
//                .username(properties.getUsername())
//                .password(properties.getPassword())
                .build();
    }

}
