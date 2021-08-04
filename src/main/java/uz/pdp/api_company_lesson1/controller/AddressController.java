package uz.pdp.api_company_lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;


    /**
     * Addressni qoshadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody Address address) {
        ApiResponse apiResponse = addressService.addAddress(address);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Addressni qaytaradigan metod
     * @return List</ Address>
     */
    @GetMapping
    public ResponseEntity<List<Address>> get() {
        List<Address> addresses = addressService.get();
        return ResponseEntity.ok(addresses);
    }


    /**
     * Addressni ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Address
     * Address bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Address addressByID = addressService.getByID(id);
        return ResponseEntity.ok(addressByID);
    }

    /**
     * Addressni ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = addressService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Addressni ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param address Addess
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@RequestBody Address address){
        ApiResponse apiResponse = addressService.edit(id, address);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
