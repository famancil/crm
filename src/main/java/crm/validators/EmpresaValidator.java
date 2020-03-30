package crm.validators;

import crm.entities.Empresa;
import crm.repositories.EmpresaRepository;
import crm.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Component
public class EmpresaValidator implements Validator{

    @Autowired
    private EmpresaService empresaService;

    /**
     * Util cuando se requiere validar una empresa que es un atributo de otra clase
     */
    private String context;

    /**
     * Constructor por defecto utiliza contexto vacio
     */
    public EmpresaValidator() {
        this.context = "";
    }

    /**
     * Constructor que acepta un contexto como parametro
     * @param context
     */
    public EmpresaValidator(String context)
    {
        this.context = context.concat(".");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Empresa.class.isAssignableFrom(clazz);
    }

    /**
     * Toma una {@link crm.entities.Empresa} y valida los campos de la entidad
     * @param target
     * @param errors
     * {@link crm.validators.EmpresaValidator}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Override
    public void validate(Object target, Errors errors) {
        Empresa empresa = (Empresa) target;
        validarIdExtranjera(errors, empresa);
        validarRutEmpresa(errors, empresa);
        validarNombreFantasiaEmpresa(errors, empresa);
        validarRazonSocial(errors, empresa);
        validarGiroEmpresa(errors, empresa);
        validarNumeroEmpleados(errors, empresa);
        validarNumeroContratos(errors, empresa);
        validarSigla(errors, empresa);
        validarDescripcion(errors, empresa);
        validarUrl(errors, empresa);
    }

    private void validarUrl(Errors errors, Empresa empresa) {
        String field = this.context.concat("url");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.url", "Ingrese URL de la empresa");
        if (empresa.getUrl() != null && empresa.getUrl().length() > 200) {
            errors.rejectValue(field, "notAuthorized.empresa.url", "La URL de la empresa no puede tener mas de 200 caracteres");
        }
    }

    private void validarDescripcion(Errors errors, Empresa empresa) {
        String field = this.context.concat("descripcion");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.descripcion", "Ingrese descripcion de la empresa");
        if (empresa.getDescripcion() != null && empresa.getDescripcion().length() > 255) {
            errors.rejectValue(field, "notAuthorized.empresa.descripcion", "La descripcion de la empresa no puede tener mas de 255 caracteres");
        }
    }

    private void validarSigla(Errors errors, Empresa empresa) {
        String field = this.context.concat("sigla");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.sigla", "Ingrese sigla de la empresa");
        if(empresa.getSigla() != null && empresa.getSigla().length() > 100) {
            errors.rejectValue(field, "notAuthorized.empresa.sigla", "La sigla de la empresa no puede tener mas de 100 caracteres");
        }
    }

    private void validarNumeroContratos(Errors errors, Empresa empresa) {
        if(empresa.getNumContratAnu() != null && empresa.getNumContratAnu() < 0){
            String field = this.context.concat("numContratAnu");
            errors.rejectValue(field, "notAuthorized.empresa.numContratAnu", "El numero de contratos anuales no puede ser negativo");
        }
    }

    private void validarNumeroEmpleados(Errors errors, Empresa empresa) {
        if (empresa.getNumEmpleados() != null && empresa.getNumEmpleados() < 0) {
            String field = this.context.concat("numEmpleados");
            errors.rejectValue(field, "notAuthorized.empresa.numEmpleados", "El numero de empleados no puede ser negativo");
        }
    }

    private void validarRutEmpresa(Errors errors, Empresa empresa) {
        String PATTERN_RUT = "[0-9]+";
        Pattern pattern = Pattern.compile(PATTERN_RUT);
        String field = this.context.concat("rutEmpresa");
        if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()!=null && empresa.getRutEmpresa().isEmpty() && empresa.getIdEmpresaExtranjera().isEmpty()) {
            ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.rutEmpresa", "Ingrese Rut o Id Extranjero");
        }else if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()==null && empresa.getRutEmpresa().isEmpty()) {
            ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.rutEmpresa", "Ingrese Rut");
        }
/*        if(empresa.getRutEmpresa()!=null && !empresa.getRutEmpresa().isEmpty()) {
            if(empresaService.getEmpresaByRut(empresa.getRutEmpresa()) != null) errors.rejectValue(field, "notAuthorized.empresa.rutEmpresa", "El rut ingresado ya existe");
            Matcher matcher = pattern.matcher(empresa.getRutEmpresa());
            if (!matcher.matches()) {
                errors.rejectValue(field, "notAuthorized.empresa.rutEmpresa", "Ingrese un rut valido");
            }
            if (empresa.getRutEmpresa().length() > 10) {
                errors.rejectValue(field, "notAuthorized.empresa.rutEmpresa", "El rut de la empresa no puede tener mas de 10 caracteres");
            }

        }*/
    }

    private void validarGiroEmpresa(Errors errors, Empresa empresa) {
        String field = this.context.concat("giroEmpresa");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.giroEmpresa", "Ingrese giro de la empresa");
        if (empresa.getGiroEmpresa() != null && empresa.getGiroEmpresa().length() > 500) {
            errors.rejectValue(field, "notAuthorized.empresa.giroEmpresa", "El giro de la empresa no puede tener mas de 500 caracteres");
        }
    }

    private void validarRazonSocial(Errors errors, Empresa empresa) {
        String field = this.context.concat("razonSocial");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.razonSocial", "Ingrese razon social");
        if (empresa.getRazonSocial() != null && empresa.getRazonSocial().length() > 500) {
            errors.rejectValue(field, "notAuthorized.empresa.razonSocial", "La razon social de la empresa no puede tener mas de 500 caracteres");
        }
    }

    private void validarNombreFantasiaEmpresa(Errors errors, Empresa empresa) {
        String field = this.context.concat("nombreFantasiaEmpresa");
        ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.nombreFantasiaEmpresa", "Ingrese nombre de fantasia");
        if (empresa.getNombreFantasiaEmpresa() != null && empresa.getNombreFantasiaEmpresa().length() > 500) {
            errors.rejectValue(field, "notAuthorized.empresa.nombreFantasiaEmpresa", "El nombre de fantasia de la empresa no puede tener mas de 500 caracteres");
        }
    }

    private void validarIdExtranjera(Errors errors, Empresa empresa) {
        String field = this.context.concat("idEmpresaExtranjera");
        if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()!=null && empresa.getRutEmpresa().isEmpty() && empresa.getIdEmpresaExtranjera().isEmpty()) {
            ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.idEmpresaExtranjera", "Ingrese Rut o Id Extranjero");
        }else if(empresa.getRutEmpresa()==null && empresa.getIdEmpresaExtranjera()!=null && empresa.getIdEmpresaExtranjera().isEmpty()){
            ValidationUtils.rejectIfEmpty(errors, field, "empty.empresa.idEmpresaExtranjera", "Ingrese Id Extranjero");
        }
        if(empresa.getIdEmpresaExtranjera()!=null && !empresa.getIdEmpresaExtranjera().isEmpty()) {
            if(empresaService.buscarEmpresaPorIdEmpresaExtranjera(empresa.getIdEmpresaExtranjera()) != null) errors.rejectValue(field, "notAuthorized.empresa.idEmpresaExtranjera", "El id ingresado ya existe");
            String PATTERN_RUT = "[0-9]+";
            Pattern pattern = Pattern.compile(PATTERN_RUT);
            Matcher matcher = pattern.matcher(empresa.getIdEmpresaExtranjera());
            if (!matcher.matches()) {
                empresa.setIdEmpresaExtranjera("");
                errors.rejectValue(field, "notAuthorized.empresa.idEmpresaExtranjera", "Ingrese un id valido");
            }
            if (empresa.getIdEmpresaExtranjera().length() > 20) {
                errors.rejectValue(field, "notauthorized.empresa.idEmpresaExtranjera", "El identificador no puede tener mas de 20 caracteres");
            }
        }
    }
}
