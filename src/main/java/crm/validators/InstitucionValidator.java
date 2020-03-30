package crm.validators;

import crm.entities.Institucion;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.lang.*;

/**
 * Validador de los forms relacionados con la clase {@link crm.entities.Institucion}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class InstitucionValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return Institucion.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors){
        Institucion institucion = (Institucion) target;
        ValidationUtils.rejectIfEmpty(errors, "nomInstitucion", "empty.institucion.nomInstitucion", "Ingrese nombre de la institucion");
    }
}
