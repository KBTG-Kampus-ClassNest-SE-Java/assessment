package com.kbtg.bootcamp.lottery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EnvironmentVariableTest
{
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Test
    public void testDataSourceUrl() {
        assertThat(datasourceUrl).isNotNull();
        System.out.println("DataSource URL: " + datasourceUrl);
        // You can add more assertions here to validate the format or the content of the datasourceUrl
    }
}
