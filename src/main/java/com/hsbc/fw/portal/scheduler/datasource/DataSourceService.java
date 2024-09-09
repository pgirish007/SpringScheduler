package com.hsbc.fw.portal.scheduler.datasource;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceService {
    private Map<String, DataSourceConfig> dataSourceMap = new HashMap<>();

    public DataSourceService() {
        // Register all available data sources
        dataSourceMap.put("FTP", new FTPDataSourceConfig());
        dataSourceMap.put("SPLUNK", new SplunkDataSourceConfig());
        dataSourceMap.put("ORACLE", new OracleDBDataSourceConfig());
    }

    public void fetchDataSourceConfig(String sourceType) {
        DataSourceConfig dataSource = dataSourceMap.get(sourceType.toUpperCase());
        if (dataSource != null) {
            dataSource.fetchConfiguration();
        } else {
            System.out.println("Data source not found: " + sourceType);
        }
    }

    public DataSourceConfig getDataSource(String sourceType) {
        DataSourceConfig dataSource = dataSourceMap.get(sourceType.toUpperCase());
        if (dataSource != null) {
            return dataSource;
        } else {
            System.out.println("Data source not found: " + sourceType);
            return null;
        }
    }
}

