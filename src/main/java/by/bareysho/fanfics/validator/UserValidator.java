package by.bareysho.fanfics.validator;

import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Fanfic;
import by.bareysho.fanfics.service.UserService;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ResourceBundle;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomUser user = (CustomUser) o;
        validateUsername(user.getUsername(), errors);
        validateFirstName(user.getFirstName(), errors);
        validateFirstName(user.getLastName(), errors);
        validateEmail(user.getEmail(), errors);
    }

    private void validateUsername(String username, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (username.length() < 8 || username.length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(username) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
    }

    public void validateEmail(String email, Errors errors) {
        CustomUser customUser = userService.getLoginUser();
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!email.equals("") && customUser.hasRole("SOC_USER")) {
            if (!emailValidator.isValid(email))
                errors.rejectValue("email", "userForm.email");

            if (userService.findByEmail(email) != null && !customUser.getEmail().equals(email))
                errors.rejectValue("email", "userForm.emailDuplicate");
        }
    }

    public void validateFirstName(String firstName, Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required", "Required");
        if(firstName.length() < 2 || firstName.length() > 20){
            errors.rejectValue("firstName", "userForm.firstNameError");
        }
    }

    public void validateLastName(String lastName, Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required", "Required");
        if(lastName.length() < 2 || lastName.length() > 20){
            errors.rejectValue("lastName", "userForm.lastNameError");
        }
    }

    private void validatePassword(String password, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (password.length() < 8 || password.length() > 32)
            errors.rejectValue("password", "userForm.password");
    }

    private void validatePasswordConfirmation(String passwordConfirmation, String password, Errors errors) {
        if (!passwordConfirmation.equals(password)) {
            errors.rejectValue("confirmPassword", "userForm.passwordConfirametion");
        }
    }



}
