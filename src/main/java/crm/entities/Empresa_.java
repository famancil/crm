package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Empresa.class)
public abstract class Empresa_ {

	public static volatile SingularAttribute<Empresa, TipoSector> sector;
	public static volatile SingularAttribute<Empresa, Boolean> logo;
	public static volatile SingularAttribute<Empresa, String> urlSlug;
	public static volatile SingularAttribute<Empresa, Pais> pais;
	public static volatile SingularAttribute<Empresa, String> razonSocial;
	public static volatile SingularAttribute<Empresa, String> giroEmpresa;
	public static volatile SingularAttribute<Empresa, Integer> numEmpleados;
	public static volatile SingularAttribute<Empresa, TipoVigencia> vigencia;
	public static volatile SingularAttribute<Empresa, String> descripcion;
	public static volatile SingularAttribute<Empresa, String> idEmpresaExtranjera;
	public static volatile SingularAttribute<Empresa, String> url;
	public static volatile SingularAttribute<Empresa, Boolean> headHunter;
	public static volatile SingularAttribute<Empresa, Long> id;
	public static volatile SingularAttribute<Empresa, Boolean> premiumEmpresa;
	public static volatile SingularAttribute<Empresa, Boolean> estado;
	public static volatile SingularAttribute<Empresa, String> sigla;
	public static volatile SingularAttribute<Empresa, Integer> rutUsuario;
	public static volatile SingularAttribute<Empresa, String> rutEmpresa;
	public static volatile SingularAttribute<Empresa, Short> numContratAnu;
	public static volatile SingularAttribute<Empresa, Date> fechaModificacion;
	public static volatile SingularAttribute<Empresa, TipoNivelFacturacion> nivelFacturacion;
	public static volatile SingularAttribute<Empresa, String> nombreFantasiaEmpresa;
	public static volatile SingularAttribute<Empresa, Boolean> boletinExalumnos;

}

