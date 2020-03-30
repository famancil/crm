package crm.validators;

import crm.entities.AntecedenteEducacional;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * Validador de los forms relacionados con la clase {@link crm.entities.AntecedenteEducacional}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class AntecedenteEducacionalValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return AntecedenteEducacional.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        AntecedenteEducacional antecedente = (AntecedenteEducacional) target;
        ValidationUtils.rejectIfEmpty(errors, "anioIngreso", "empty.antecedente.anioIngreso", "Ingrese año de ingreso");
        ValidationUtils.rejectIfEmpty(errors, "anioEgreso", "empty.antecedente.anioEgreso", "Ingrese año de egreso");
        ValidationUtils.rejectIfEmpty(errors, "anioTitulo", "empty.antecedente.anioTitulo", "Ingrese año de titulo");
    }

}
