package crm.validators;

import crm.forms.RegistrarEmpresaForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistrarEmpresaFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrarEmpresaForm.class.isAssignableFrom(clazz);
    }

    /**
     * Valida la entidad Empresa y la entidad SucursalEmpresa contenidas en la clase RegistrarEmpresaForm
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        RegistrarEmpresaForm form = (RegistrarEmpresaForm) target;

        EmpresaValidator empresaValidator = new EmpresaValidator("empresa");
        empresaValidator.validate(form.getEmpresa(), errors);

        SucursalEmpresaValidator sucursalEmpresaValidator = new SucursalEmpresaValidator("sucursalEmpresa");
        sucursalEmpresaValidator.validate(form.getSucursalEmpresa(), errors);
    }
}
