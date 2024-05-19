package uz.etc.etcfitness.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.etc.etcfitness.enums.Gender;
import uz.etc.etcfitness.util.annotation.Phone;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotEmpty(message = "Firstname is mandatory")
    @NotNull(message = "Firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "Lastname is mandatory")
    @NotNull(message = "Lastname is mandatory")
    private String lastname;
    @Phone(message = "Phone is not valid")
    @NotEmpty(message = "Phone is mandatory")
    @NotNull(message = "Phone is mandatory")
    private String phone;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;

}
