package com.hsbc.fw.portal.scheduler.datasource;

import org.springframework.stereotype.Component;

@Component
public class OracleDBDataSourceConfig implements DataSourceConfig {

    /**
     *
     */
    @Override
    public void fetchConfiguration() {
        // this method will fetch the configuration from the Oracle DB server
        // and then save the configuration to the database
        System.out.println("Fetching configuration from Oracle DB server");
    }

}
