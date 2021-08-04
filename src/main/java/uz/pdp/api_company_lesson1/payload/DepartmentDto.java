package uz.pdp.api_company_lesson1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "Bo'lim nomi bo'sh bo'lmasligi kerak")
    private String name;

    private Integer companyId;


}
