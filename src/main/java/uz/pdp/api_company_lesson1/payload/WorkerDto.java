package uz.pdp.api_company_lesson1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    private String fullName;
    @NotNull(message = "Telefon raqam bo'sh bo'lmasligi kerak")
    private String phoneNumber;
    private Integer addressId;
    private Integer departmentId;


}
