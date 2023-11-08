package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreReqDTO implements Validator {

    private String fullName;
    private String email;
    private String phone;
    private String address;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerCreReqDTO creReqDTO = (CustomerCreReqDTO) o;

        String fullName = creReqDTO.fullName;
        String address = creReqDTO.address;

        if (fullName.length() < 3) {
            errors.rejectValue("fullName", "fullName.length", "Tên phải có ít nhất là 3 ký tự");
        }

        if (address.length() < 3) {
            errors.rejectValue("address", "address.length", "Địa chỉ phải có ít nhất là 3 ký tự");
        }
    }
}
