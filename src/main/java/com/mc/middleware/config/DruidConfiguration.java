package com.mc.middleware.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfiguration {
    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;
    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.druid.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.remove-abandoned}")
    private boolean removeAbandoned;
    @Value("${spring.datasource.druid.remove-abandoned-timeout}")
    private int removeAbandonedTimeout;
    @Value("${spring.datasource.druid.log-abandoned}")
    private boolean logAbandoned;

    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setMinIdle(this.minIdle);
        dataSource.setMaxWait(this.maxWait);
        dataSource.setValidationQuery(this.validationQuery);
        dataSource.setTestOnBorrow(this.testOnBorrow);
        dataSource.setTestOnReturn(this.testOnReturn);
        dataSource.setTestWhileIdle(this.testWhileIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        dataSource.setRemoveAbandoned(this.removeAbandoned);
        dataSource.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
        dataSource.setLogAbandoned(this.logAbandoned);

        return dataSource;
    }
}
