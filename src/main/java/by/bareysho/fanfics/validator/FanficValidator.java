package by.bareysho.fanfics.validator;

import by.bareysho.fanfics.model.Fanfic;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FanficValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Fanfic.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Fanfic fanfic = (Fanfic) o;
        fanficNameValidate(fanfic.getFanficName(), errors);
        fanficDescriptionValidate(fanfic.getDescription(), errors);
    }

    private void fanficNameValidate(String name, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fanficName", "Required", "Required");
        if (name.length() != 0 && (name.length() < 4 || name.length() > 32)) {
            errors.rejectValue("fanficName", "fanfics.errorName");
        }
    }

    private void fanficDescriptionValidate(String description, Errors errors) {
        if (description.length() > 500) {
            errors.rejectValue("description", "fanfics.descriptionError");
        }
    }

    public void fanficGenresValidate(String selectedGenres, Errors errors) {
        if (selectedGenres == null || selectedGenres.length() == 0) {
            errors.rejectValue("genres", "Required");
        }
    }
}
