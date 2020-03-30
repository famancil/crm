package crm.validators;

import crm.entities.ActividadExalumno;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validador de los forms relacionados con la clase {@link crm.entities.ActividadExalumno}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public class ActividadExalumnoValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return ActividadExalumno.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        ActividadExalumno actividad = (ActividadExalumno) target;
        ValidationUtils.rejectIfEmpty(errors, "tipoCargo.codCargo", "empty.actividad.tipoCargo.codCargo", "Ingrese un cargo");
        ValidationUtils.rejectIfEmpty(errors, "empresa.razonSocial", "empty.actividad.empresa.razonSocial", "Ingrese una empresa");
    }
}
