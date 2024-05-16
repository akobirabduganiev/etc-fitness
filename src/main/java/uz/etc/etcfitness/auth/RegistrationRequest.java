package uz.etc.etcfitness.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.etc.etcfitness.enums.Gender;

@Getter
@Setter
@Builder
public class RegistrationRequest {


    @NotEmpty(message = "Firstname is mandatory")
    @NotNull(message = "Firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "Lastname is mandatory")
    @NotNull(message = "Lastname is mandatory")
    private String lastname;
    @Email(message = "Phone is not well formatted")
    @NotEmpty(message = "Phone is mandatory")
    @NotNull(message = "Phone is mandatory")
    private String phone;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;

}
