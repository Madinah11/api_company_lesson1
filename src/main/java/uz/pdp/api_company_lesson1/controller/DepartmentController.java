package uz.pdp.api_company_lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.entity.Department;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.CompanyDto;
import uz.pdp.api_company_lesson1.payload.DepartmentDto;
import uz.pdp.api_company_lesson1.service.CompanyService;
import uz.pdp.api_company_lesson1.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;


    /**
     * Bo'lim qoshadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.add(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Bo'lim qaytaradigan metod
     * @return List</ Department>
     */
    @GetMapping
    public ResponseEntity<List<Department>> get() {
        List<Department> departmentList = departmentService.get();
        return ResponseEntity.ok(departmentList);
    }


    /**
     * Bo'lim ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Bo'lim
     * Bo'lim bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Department departmentServiceByID = departmentService.getByID(id);
        return ResponseEntity.ok(departmentServiceByID);
    }

    /**
     * Bo'lim ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = departmentService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Bo'lim ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit(@Valid @PathVariable Integer id,@RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.edit(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
