package crm.services;


import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.UsuarioEmpresaUsmempleo}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class UsuarioUsmempleoEmpresaService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaRepository usuarioUsmempleoEmpresaRepository;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(UsuarioUsmempleoEmpresaService.class);




    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa} que esten asociados a una
     * {@link crm.entities.Empresa}, buscados según el Id de la empresa a la que pertenecen y posean un vigencia vigente).
     *
     * @param idEmpresa id de la empresa sobre la cual se desea buscar un {@link crm.entities.UsuarioUsmempleoEmpresa}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorIdEmpresa(Long idEmpresa) {
        return usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorIdEmpresa(idEmpresa);
    }

    public List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorCargo(String cargo) {
        if (cargo.equals("gerencia") || cargo.equals("Gerencia") || cargo.equals("GERENCIA") || cargo.equals("Gerente") || cargo.equals("GERENTE") || cargo.equals("gerente")) {
            return usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCargo("G");
        }
        else return usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCargo(cargo);
    }

    public Page<UsuarioUsmempleoEmpresa> buscarEmpleadosPorCargo(int pagina, String cargo,Short sector,String rut,String nombreFantasiaEmpresa){
        PageRequest pageRequest = new PageRequest(pagina, 20);
        if (rut.equals("")) {
            rut = "%";
        }
        if(nombreFantasiaEmpresa.equals(""))
            nombreFantasiaEmpresa="%";
        if (cargo.equals("gerencia") || cargo.equals("Gerencia") || cargo.equals("GERENCIA") || cargo.equals("Gerente") || cargo.equals("GERENTE") || cargo.equals("gerente")) {
            if(sector!=-1)return usuarioUsmempleoEmpresaRepository.buscarEmpleadosPorCargoSectorNombreFantasiaYRutEmpresa(pageRequest,"GE",sector,rut,nombreFantasiaEmpresa);
            else return usuarioUsmempleoEmpresaRepository.buscarEmpleadosPorCargoNombreEmpresaYRutEmpresa(pageRequest,"GE",nombreFantasiaEmpresa,rut);
        }
        else
            if(sector!=-1)return usuarioUsmempleoEmpresaRepository.buscarEmpleadosPorCargoSectorNombreFantasiaYRutEmpresa(pageRequest,cargo,sector,rut,nombreFantasiaEmpresa);
            else return usuarioUsmempleoEmpresaRepository.buscarEmpleadosPorCargoNombreEmpresaYRutEmpresa(pageRequest,cargo,nombreFantasiaEmpresa,rut);

    }

    public List<UsuarioUsmempleoEmpresa> buscarContactosPorCargo(String cargo) {
        if (cargo.equals("gerencia") || cargo.equals("Gerencia") || cargo.equals("GERENCIA") || cargo.equals("Gerente") || cargo.equals("GERENTE") || cargo.equals("gerente")) {
            return usuarioUsmempleoEmpresaRepository.buscarContactosPorCargo("GE");
        }
        else return usuarioUsmempleoEmpresaRepository.buscarContactosPorCargo(cargo);
    }




    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa}, buscados según el calce con el
     * nombre del UsuarioUsmempleoEmpresa pasado como parametro
     *
     * @param nombreUsuarioEmpresaUsmempleo Nombre del usuario desea buscar
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(String nombreUsuarioEmpresaUsmempleo) {
        return usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(nombreUsuarioEmpresaUsmempleo );
    }


}
