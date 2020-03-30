package crm.validators;

import crm.entities.Carrera;
import crm.entities.Usuario;
import crm.services.CarreraService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador para la entidad {@link crm.entities.Carrera}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class CarreraValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Carrera.class.isAssignableFrom(aClass);
    }


    /**
     * Valida un objeto entidad {@link crm.entities.Carrera}, verificando que los campos NombreCarrera y
     * Titulo estén en el formado deseado
     *
     * @param target
     * @param errors
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Override
    public void validate(Object target, Errors errors) {
        Carrera carrera = (Carrera) target;

        ValidationUtils.rejectIfEmpty(errors, "nombreCarrera", "empty.Carrera.nombreCarrera", "Ingrese Nombre de la carrera");
        ValidationUtils.rejectIfEmpty(errors, "abreviacion", "empty.Carrera.abreviacion", "Ingrese Abreviación de la carrera");
        ValidationUtils.rejectIfEmpty(errors, "titulo", "empty.Carrera.titulo", "Ingrese Titulo de la carrera");
        ValidationUtils.rejectIfEmpty(errors, "duracion", "empty.Carrera.duracion", "Ingrese una duración de la carrera");

        // caso en que se ingrese numeros negativos a la duracion
        if(carrera.getDuracion() != null &&  carrera.getDuracion() < 0){
            errors.rejectValue("duracion", "notAuthorized.carrera.duracion", "Ingrese una duración válida");
        }

        // caso en que se ingrese una cantidad de caracteres mayor a lo permitido en la base de datos
        if(carrera.getNombreCarrera().length() > 400){
            errors.rejectValue("nombreCarrera", "notAuthorized.carrera.nombreCarrera", "Ingrese un Nombre de Carrera menor a 400 caracteres");
        }
    }


}
