package ru.rksp.shanaurin.gate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rksp.shanaurin.data.api.StudentGateApi;
import ru.rksp.shanaurin.data.model.StudentGateCreateRequest;
import ru.rksp.shanaurin.data.model.StudentGateResponse;
import ru.rksp.shanaurin.gate.client.api.StudentDataApi;
import ru.rksp.shanaurin.gate.client.model.StudentDataCreateRequest;
import ru.rksp.shanaurin.gate.client.model.StudentDataResponse;

@RestController
@RequiredArgsConstructor
public class StudentGateController implements StudentGateApi {

    private final StudentDataApi studentsFeignClient;

    /**
     * Проксирует создание студента во внутренний DATA-SERVICE.
     * Получает запрос от клиента, преобразует модель и перенаправляет.
     */
    @Override
    public ResponseEntity<StudentGateResponse> createStudent(StudentGateCreateRequest request) {
        StudentDataCreateRequest dataRequest = new StudentDataCreateRequest();
        dataRequest.setFullName(request.getFullName());
        dataRequest.setPassport(request.getPassport());

        StudentDataResponse dataResponse = studentsFeignClient.createStudentDataInData(dataRequest);

        StudentGateResponse gateResponse = new StudentGateResponse();
        gateResponse.setId(dataResponse.getId());
        gateResponse.setFullName(dataResponse.getFullName());
        gateResponse.setPassport(dataResponse.getPassport());

        return ResponseEntity.status(201).body(gateResponse);
    }

    @Override
    public ResponseEntity<StudentGateResponse> getStudent(Long id) {
        StudentDataResponse dataResponse = studentsFeignClient.getStudentDataByIdFromData(id);

        StudentGateResponse gateResponse = new StudentGateResponse();
        gateResponse.setId(dataResponse.getId());
        gateResponse.setFullName(dataResponse.getFullName());
        gateResponse.setPassport(dataResponse.getPassport());

        return ResponseEntity.status(200).body(gateResponse);
    }
}