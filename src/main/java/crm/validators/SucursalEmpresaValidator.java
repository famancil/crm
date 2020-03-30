package crm.validators;

import crm.entities.SucursalEmpresa;
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
public class SucursalEmpresaValidator implements Validator{

    /**
     * Util cuando se requiere validar una sucursal que es un atributo de otra clase
     */
    private final String context;

    /**
     * Constructor por defecto utiliza contexto vacio
     */
    public SucursalEmpresaValidator() {
        this.context = "";
    }

    /**
     * Constructor que acepta un contexto como parametro
     * @param context
     */
    public SucursalEmpresaValidator(String context) {
        this.context = context.concat(".");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SucursalEmpresa.class.isAssignableFrom(clazz);
    }

    /**
     * Toma una {@link crm.entities.SucursalEmpresa} y valida los campos de la entidad
     * @param target
     * @param errors
     * {@link crm.validators.SucursalEmpresaValidator}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Override
    public void validate(Object target, Errors errors) {
        SucursalEmpresa sucursal = (SucursalEmpresa) target;
        validarSucSucursal(errors, sucursal);
        validarSucFax(errors, sucursal);
        validarSucFono(errors, sucursal);
        validarSucEmail(errors, sucursal);
        validarSucDireccion(errors, sucursal);
    }

    private void validarSucDireccion(Errors errors, SucursalEmpresa sucursal) {
        if(!sucursal.getSucDireccion().isEmpty()) {
            String field = this.context.concat("sucDireccion");
            if (sucursal.getSucDireccion().length() > 100) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucDireccion", "La direccion de la sucursal no puede tener mas de 100 caracteres");
            }
        }
    }

    private void validarSucEmail(Errors errors, SucursalEmpresa sucursal) {
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patternEmail = Pattern.compile(PATTERN_EMAIL);
        if(!sucursal.getSucEmail().isEmpty()) {
            Matcher matcher = patternEmail.matcher(sucursal.getSucEmail());
            String field = this.context.concat("sucEmail");
            if (!matcher.matches()) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucEmail", "Ingrese un email valido");
            }
            if (sucursal.getSucEmail().length() > 50) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucEmail", "El email de la sucursal no puede tener mas de 50 caracteres");
            }
        }
    }

    private void validarSucFono(Errors errors, SucursalEmpresa sucursal) {
        String PATTERN_FONO = "\\+?[0-9]+";
        Pattern patternFono = Pattern.compile(PATTERN_FONO);
        if(sucursal.getSucFono()!=null && !sucursal.getSucFono().isEmpty()) {
            Matcher matcher = patternFono.matcher(sucursal.getSucFono());
            String field = this.context.concat("sucFono");
            if (!matcher.matches()) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucFono", "Ingrese un numero telefonico valido");
            }
            if(sucursal.getSucFono().length() > 20) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucFono", "El fono de la sucursal no puede tener mas de 20 caracteres");
            }
        }
    }

    private void validarSucFax(Errors errors, SucursalEmpresa sucursal) {
        String PATTERN_FAX = "[0-9]+";
        Pattern patternFax = Pattern.compile(PATTERN_FAX);
        if(sucursal.getSucFax()!=null && !sucursal.getSucFax().isEmpty()) {
            Matcher matcher = patternFax.matcher(sucursal.getSucFax());
            String field = this.context.concat("sucFax");
            if (!matcher.matches()) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucFax", "Ingrese un fax valido");
            }
            if(sucursal.getSucFax().length() > 25) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucFax", "El fax de la sucursal no puede tener mas de 25 caracteres");
            }

        }
    }

    private void validarSucSucursal(Errors errors, SucursalEmpresa sucursal) {
        String field = this.context.concat("sucSucursal");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.sucursal.sucSucursal", "Ingrese nombre de la sucursal");
        if(sucursal.getSucSucursal() != null && !sucursal.getSucSucursal().isEmpty()) {
            if(sucursal.getSucSucursal().length() > 150) {
                errors.rejectValue(field, "notAuthorized.sucursal.sucSucursal", "El nombre de la sucursal no puede tener mas de 150 caracteres");
            }
        }
    }
}
