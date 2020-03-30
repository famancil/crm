package crm.validators;

import crm.entities.Colegio;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador de los forms relacionados con la clase {@link crm.entities.Colegio}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class ColegioValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Colegio.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        Colegio colegio = (Colegio) target;

        ValidationUtils.rejectIfEmpty(errors, "nombreOficial", "empty.colegio.nombreOficial", "Ingrese un nombre para el colegio");

        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        if(!colegio.getEmail().isEmpty()) {
            Matcher matcher = pattern.matcher(colegio.getEmail());
            if (!matcher.matches())
                errors.rejectValue("email", "notAuthorized.colegio.email", "Ingrese un email valido");
        }
    }


}
