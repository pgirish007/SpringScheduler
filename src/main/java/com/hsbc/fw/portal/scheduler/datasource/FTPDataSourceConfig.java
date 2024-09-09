package com.hsbc.fw.portal.scheduler.datasource;

import org.springframework.stereotype.Component;

@Component
public class FTPDataSourceConfig implements DataSourceConfig {

    /**
     *
     */
    @Override
    public void fetchConfiguration() {
        // this method will fetch the configuration from the FTP server
        // and then save the configuration to the database
        System.out.println("Fetching configuration from FTP server");
    }
}
