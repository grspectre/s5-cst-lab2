package ru.rksp.shanaurin.gate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rksp.shanaurin.gate.client.ApiClient;
import ru.rksp.shanaurin.gate.client.api.StudentDataApi;

@Configuration
public class FeignClientConfig {
    @Value("${data.service.url}")
    private String dataServiceUrl;

    @Bean
    public StudentDataApi studentDataApi() {
        ApiClient apiClient = new ApiClient();
        // адрес сервиса data-service
        apiClient.setBasePath(dataServiceUrl);
        return apiClient.buildClient(StudentDataApi.class);
    }
}