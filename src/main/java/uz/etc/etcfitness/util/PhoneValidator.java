package uz.etc.etcfitness.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.etc.etcfitness.util.annotation.Phone;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    // Define the phone pattern for exactly 12 digits
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{12}$");

    @Override
    public void initialize(Phone phone) {
        // Do nothing
    }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
        if (phoneField == null) {
            return false;
        }

        // Check if the input phone number matches our pattern
        return PHONE_PATTERN.matcher(phoneField).matches();
    }
}
