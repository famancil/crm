package crm.validators;

import crm.entities.Carrera;
import crm.entities.ContactoHistoricoEmpresa;
import crm.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validador para la entidad {@link crm.entities.ContactoHistoricoEmpresa}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class ContactoHistoricoEmpresaValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ContactoHistoricoEmpresa.class.isAssignableFrom(aClass);
    }


    /**
     * Valida un objeto entidad {@link crm.entities.ContactoHistoricoEmpresa}, verificando que los campos estén
     * correctos
     *
     * @param target
     * @param errors
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Override
    public void validate(Object target, Errors errors) {
        ContactoHistoricoEmpresa contactoHistoricoEmpresa = (ContactoHistoricoEmpresa) target;

        ValidationUtils.rejectIfEmpty(errors, "asunto", "empty.contactoHistoricoEmpresa.asunto", "Ingrese un Asunto del contacto");
        ValidationUtils.rejectIfEmpty(errors, "acuerdos", "empty.contactoHistoricoEmpresa.acuerdos", "Ingrese Acuerdos del contacto");
        ValidationUtils.rejectIfEmpty(errors, "fechaContacto", "empty.contactoHistoricoEmpresa.fechaContacto", "Ingrese Fecha de realización del contacto");

    }


}
