package uz.pdp.api_company_lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.api_company_lesson1.entity.Department;
import uz.pdp.api_company_lesson1.entity.Worker;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.DepartmentDto;
import uz.pdp.api_company_lesson1.payload.WorkerDto;
import uz.pdp.api_company_lesson1.service.DepartmentService;
import uz.pdp.api_company_lesson1.service.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;


    /**
     * Xodim qo'shadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.add(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Xodim qaytaradigan metod
     * @return List</ Worker>
     */
    @GetMapping
    public ResponseEntity<List<Worker>> get() {
        List<Worker> workerList = workerService.get();
        return ResponseEntity.ok(workerList);
    }


    /**
     * Xodim ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Xodim
     * Xodim bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Worker workerServiceByID = workerService.getByID(id);
        return ResponseEntity.ok(workerServiceByID);
    }

    /**
     * XodimID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = workerService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Xodim ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param workerDto WorkerDto
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.edit(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
