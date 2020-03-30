package crm.validators;

import crm.entities.UsuarioEmpresaUsmempleo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Component
public class UsuarioEmpresaUsmempleoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return UsuarioEmpresaUsmempleo.class.isAssignableFrom(clazz); }

    @Override
    public void validate(Object target, Errors errors) {
        UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo = (UsuarioEmpresaUsmempleo) target;
        ValidationUtils.rejectIfEmpty(errors, "nombreCompleto", "empty.usuarioEmpresaUsmempleo.nombreCompleto", "Ingrese nombre del Perfil Publicador");
        ValidationUtils.rejectIfEmpty(errors, "rut", "empty.usuarioEmpresaUsmempleo.rut", "Ingrese rut del Perfil Publicador");
        ValidationUtils.rejectIfEmpty(errors, "password", "empty.usuarioEmpresaUsmempleo.password", "Ingrese contraseña");
        ValidationUtils.rejectIfEmpty(errors, "email", "empty.usuarioEmpresaUsmempleo.email", "Ingrese email");
        ValidationUtils.rejectIfEmpty(errors, "fono", "empty.usuarioEmpresaUsmempleo.fono", "Ingrese telefono particular");
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        if(!usuarioEmpresaUsmempleo.getEmail().isEmpty()){
            Matcher matcher1 = pattern.matcher(usuarioEmpresaUsmempleo.getEmail());
            if(!matcher1.matches())  errors.rejectValue("email", "notAuthorized.usuarioEmpresaUsmempleo.email", "Ingrese un email valido");
        }
    }
}
