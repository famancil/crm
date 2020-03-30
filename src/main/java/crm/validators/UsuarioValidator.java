package crm.validators;

import crm.entities.Usuario;
import crm.services.ColegioService;
import crm.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Component
public class UsuarioValidator implements Validator {

    /**
     * Servicio utilizado para el manejo de la entidad {@link crm.entities.Usuario} y sus entidades asociadas.
     */
    @Autowired
    private UsuarioService usuarioService;


    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    /**
     * Toma un {@link crm.entities.Usuario} y valida que los campos nombre, apellidoPaterno
     * sean no nulos, ademas verifica el formato de email.
     * @param target
     * @param errors
     * {@link crm.validators.UsuarioValidator}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;
        ValidationUtils.rejectIfEmpty(errors, "nombres", "empty.usuario.nombres", "Ingrese los nombres del usuario");
        ValidationUtils.rejectIfEmpty(errors, "apellidoPaterno", "empty.usuario.apellidoPaterno", "Ingrese apellido paterno");
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        Boolean valido = false;
        if(usuario.getId() == null && usuario.getRut()!=null && usuarioService.getUsuarioByRut(String.valueOf(usuario.getRut())) != null){
            errors.rejectValue("rut", "notAuthorized.usuario.rut", "El rut ingresado ya existe");
        }
        if(usuario.getRut()!=null && usuario.getDigitoVerificador().isEmpty()){
            ValidationUtils.rejectIfEmpty(errors, "digitoVerificador", "empty.usuario.digitoVerificador", "Ingrese su digito verificador");
        }
        if(usuario.getRut()==null && !usuario.getDigitoVerificador().isEmpty()){
            ValidationUtils.rejectIfEmpty(errors, "rut", "empty.usuario.rut", "Ingrese un rut");
        }
        else if(usuario.getRut()!= null && !usuario.getDigitoVerificador().isEmpty()){
            Integer rutAux = usuario.getRut();
            Character dv = usuario.getDigitoVerificador().toUpperCase().charAt(0);
            valido = validarRut(rutAux, dv);
        }
        if(!valido && usuario.getRut()!=null){
            errors.rejectValue("rut", "notAuthorized.usuario.rut", "Ingrese un rut valido");
        }

        //SE VERIFICA FECHA DE NACIMIENTO
        if(usuario.getFechaNacimiento()!=null){
            String anno = usuario.getFechaNacimiento().toString().split(" ")[5];
            Date now = new java.util.Date();
            String actual = now.toString().split(" ")[5];
            if(Integer.parseInt(anno)>Integer.parseInt(actual)){
                errors.rejectValue("fechaNacimiento", "empty.usuario.fechaNacimiento", "Ingrese una fecha de nacimiento valida");
            }
        }
        if(!usuario.getEmailOpcional().isEmpty()){
            Matcher matcher1 = pattern.matcher(usuario.getEmailOpcional());
            if(!matcher1.matches())  errors.rejectValue("emailOpcional", "notAuthorized.usuario.emailOpcional", "Ingrese un email valido");
        }
        if(!usuario.getEmailLaboral().isEmpty()){
            Matcher matcher2 = pattern.matcher(usuario.getEmailLaboral());
            if(!matcher2.matches())  errors.rejectValue("emailLaboral", "notAuthorized.usuario.emailLaboral", "Ingrese un email valido");
        }
        if(usuario.getCodigoPostal()!=null){
            if(usuario.getCodigoPostal()%1!=0){
                errors.rejectValue("codigoPostal", "notAuthorized.usuario.codigoPostal", "Ingrese un codigo postal valido");
            }
        }
        if(!usuario.getDireccion().isEmpty()){
            if(usuario.getDireccion().length()<10){
                errors.rejectValue("direccion", "notAuthorized.usuario.direccion", "Ingrese una direccion valida");
            }
        }
        if(usuario.getPreferenciaUsuarioUsmempleo() != null) {
            if (usuario.getPreferenciaUsuarioUsmempleo().getInicioPermisoPost() != null && usuario.getPreferenciaUsuarioUsmempleo().getFinPermisoPost() != null) {
                if (usuario.getPreferenciaUsuarioUsmempleo().getInicioPermisoPost().after(usuario.getPreferenciaUsuarioUsmempleo().getFinPermisoPost())) {
                    errors.rejectValue("preferenciaUsuarioUsmempleo.inicioPermisoPost", "notAuthorized.usuario.preferenciaUsuarioUsmempleo.inicioPermisoPost", "La fecha de inicio no puede ser posterior a la fecha de fin.");
                }
            }
        }

        // revisa caso que se ingresa password pero no correo ( para la creacion del login aexa )
        if( usuario.getEmail().isEmpty() && !usuario.getCredencialesAcceso().getPassword().isEmpty() ){
            errors.rejectValue("email", "notAuthorized.usuario.email", "Ingrese email junto a la password");
        }
    }

    public boolean validarRut(Integer rutAux, Character dv){
        /*
        if(rutAux==null || dv.compareTo(' ')==0){
            return validacion;
        }*/
        int m = 0, s = 1;
        for (; rutAux != 0; rutAux /= 10) {
            s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
        }
        if ((char) s != 0 && dv == (char) (s + 47))
            return true;
        if ((char) s == 0 && (dv == 'k' || dv == 'K'))
            return true;
        /*if (dv == (char) (s != 0 ? s + 47 : 75)) {
            validacion = true;
        }*/
        return false;
    }
}
