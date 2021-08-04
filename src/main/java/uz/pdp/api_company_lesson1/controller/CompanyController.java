package uz.pdp.api_company_lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.CompanyDto;
import uz.pdp.api_company_lesson1.service.AddressService;
import uz.pdp.api_company_lesson1.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;


    /**
     * Companiya qoshadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.add(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Companiya qaytaradigan metod
     * @return List</ Company>
     */
    @GetMapping
    public ResponseEntity<List<Company>> get() {
        List<Company> companyList = companyService.get();
        return ResponseEntity.ok(companyList);
    }


    /**
     * Companiya ID si bo'yicha qaytaradigan metod
     *
     * @param id Integer
     * @return Companiya
     * Companiya bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Company companyByID = companyService.getByID(id);
        return ResponseEntity.ok(companyByID);
    }

    /**
     * Companiya ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = companyService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Companiya ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.edit(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
