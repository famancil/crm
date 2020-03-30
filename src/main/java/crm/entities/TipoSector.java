package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_sector. Contiene un listado con los posibles sectores
 * a los que una {@link Empresa} puede pertenecer. En la version actual del sistema, los sectores
 * disponibles son:
 *      Sin Información
 *      Aeronaves / Astilleros
 *      Agrícola / Ganadera
 *      Agropecuaria
 *      Agua / Obras Sanitarias
 *      Arquitectura / Diseño / Decoración
 *      Automotriz
 *      Banca / Financiera
 *      Biotecnologia
 *      Carpintería / Muebles
 *      Científica
 *      Combustibles (Gas / Petróleo)
 *      Comercio Mayorista
 *      Comercio Minorista
 *      Confecciones
 *      Construcción
 *      Consultoria / Asesoría
 *      Consumo masivo
 *      Defensa
 *      Educación / Capacitación
 *      Energía / Electricidad / Electrónica
 *      Entretenimiento
 *      Estudios Jurídicos
 *      Exportación / Importación
 *      Farmacéutica
 *      Forestal / Papel / Celulosa
 *      Gobierno
 *      Hotelería / Restaurantes
 *      Imprenta / Editoriales
 *      Informatica / Tecnologia
 *      Ingeniería
 *      Inmobiliaria/Propiedades
 *      Internet
 *      Inversiones (Soc / Cías / Holding)
 *      Logística / Distribución
 *      Manufacturas Varias
 *      Medicina / Salud
 *      Medios de Comunicación
 *      Metalmecánica
 *      Minería
 *      Petroleo / Gas / Combustibles
 *      Naviera
 *      Pesquera / Cultivos Marinos
 *      Publicidad / Marketing / RRPP
 *      Química
 *      Seguros / Previsión
 *      Servicios Financieros Varios
 *      Servicios Varios
 *      Siderurgia
 *      Tecnologías de Información
 *      Textil
 *      Turismo
 *      Otra Actividad
 *      Ventas
 *      Distribuidora
 *      Comercio Exterior
 *      Agencia de Aduanas
 *      Alimentos
 *      Retail
 *      Grandes Tiendas
 *      Comercio Electrónico
 *      Comercial
 *      Productora de Cine y Tv
 *      Industrial
 *      Salmonera
 *      Pesquera
 *      Seguridad
 *      Atelier de diseño
 *      Boutique
 *      Grandes Almacenes
 *      Servicios funerarios
 *      Afore
 *      Agroindustria
 *      Ambiental
 *      Bebidas
 *      Cemento y Materiales
 *      Cultura
 *      Departamentales
 *      Despachos de Abogados
 *      Distribuidora
 *      Editorial e Imprenta
 *      Electrónica de Consumo
 *      Investigación
 *      Maquinaria y Equipo
 *      Papel y Cartón
 *      Poder ejecutivo y administración pública
 *      Poder judicial"
 *      Poder legislativo
 *      Sector energético
 *      Servicios de Salud
 *      Siderurgía y Metalurgía
 *      Tabaco
 *      Vidrio y envases
 *      Hipermercados
 *      Recursos Humanos
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tipo_sector", schema = "org")
public class TipoSector {

    /**
     * Identificador del sector.
     */
    private Short codigo;

    /**
     * Nombre del sector.
     */
    private String nombre;

    public TipoSector() {
        Short s = 0;
        this.setCodigo(s);
    }

    @Id
    @Column(name = "cod_sector")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codSector) {
        this.codigo = codSector;
    }

    @Column(name = "nom_sector")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomSector) {
        this.nombre = nomSector;
    }

}