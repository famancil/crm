package crm.validators;

import crm.entities.ContactoHistoricoEmpresaPersonaParticipante;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by rocio on 09-06-16.
 */

@Component
public class ContactoHistoricoEmpresaPersonaParticipanteValidator implements Validator {

    @Override
    public boolean supports(Class<?>aClass){
        return ContactoHistoricoEmpresaPersonaParticipante.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContactoHistoricoEmpresaPersonaParticipante contacto=(ContactoHistoricoEmpresaPersonaParticipante) target;

        ValidationUtils.rejectIfEmpty(errors, "contactoHistoricoEmpresa.asunto", "empty.contactoHistoricoEmpresaPersonaParticipante.contactoHistoricoEmpresa.asunto", "El ausunto no puede estar vacio");
        ValidationUtils.rejectIfEmpty(errors, "contactoHistoricoEmpresa.acuerdos", "empty.contactoHistoricoEmpresaPersonaParticipante.contactoHistoricoEmpresa.acuerdos","La descripción no puede estar vacía");
        ValidationUtils.rejectIfEmpty(errors, "contactoHistoricoEmpresa.fechaContacto", "empty.contactoHistoricoEmpresaPersonaParticipante.contactoHistoricoEmpresa.fechaContacto", "La Fecha de contacto no puede estar vacía");
    }
}