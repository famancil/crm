package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_area.
 * Contiene un listado con los tipos de area en las
 * cuales se puede desenvolver una empresa.
 *
 *0     "Sin Información"
 *1	    Administracion
 *2	    "Administracion Publica"
 *3	    Aduanas
 *4	    Agronomía
 *5	    "Alimentos y Bebidas"
 *6	    Ambiental
 *7	    Amparo
 *8	    Arquitectura
 *9	    Aseo
 *10    "Asistente y Secretaria"
 *11    Auditoría
 *12	"Automatización / Instrumentación"
 *13	"Banca y Servicios Financieros"
 *14	Bursátil
 *15	"Cadena de Suministro"
 *16	Calidad
 *17	Capacitación
 *18	"Category management"
 *19	Clinica
 *20	Cobranza
 *21	"Comedores Industriales"
 *22	Comercial
 *23	"Comercio Exterior"
 *24	Compras
 *25	"Computación e Informática"
 *26	"Comunicación / Medios"
 *27	"Comunicación Audiovisual"
 *28	"Comunicación Institucional"
 *29	Confección
 *30	Construcción
 *31	Contabilidad
 *32	Contaduría
 *33	Contraloría
 *34	"Control de Calidad"
 *35	"Control de Gestión"
 *36	Corporativa
 *37	Costura
 *38	Crédito
 *39	"Cuentas clave"
 *40	Decoración
 *41	Derecho
 *42	"Derecho Administrativo"
 *43	"Derecho Aduanero"
 *44	"Derecho Bancario y Bursátil"
 *45	"Derecho Civil"
 *46	"Derecho Constitucional"
 *47	"Derecho Electoral"
 *48	"Derecho Energético"
 *49	"Derecho Familiar"
 *50	"Derecho Financiero"
 *51	"Derecho Internacional"
 *52	"Derecho Laboral"
 *53	"Derecho Mercantil"
 *54	"Derecho Notarial"
 *55	"Derecho Penal"
 *56	"Derechos Humanos"
 *57	Desarrollo
 *58	"Desarrollo de Producto"
 *59	Despacho
 *60	Dibujante
 *61	Digitadores
 *62	"Dirección en servicios de salud"
 *63	"Dirección General"
 *64	Diseño
 *65	"Diseño Audiovisual"
 *66	"Diseño de Confecciones"
 *67	"Diseño Industrial"
 *68	Distribución
 *69	Docencia
 *70	Economía
 *71	Edición/Redacción
 *72	Edificación
 *73	"Editorialismo médico"
 *74	"Educación / Docencia"
 *75	"Educación Física"
 *76	"Educación médica"
 *77	Electricidad
 *78	Electrónica
 *79	EmpaqueEnvasado
 *80	Epidemiología
 *81	Estética
 *82	"Estimulación temprana"
 *83	Estudio
 *84	Facturación
 *85	Finanzas
 *86	Fiscal
 *87	Fotografía
 *88	Gastronomía
 *89	Gestión
 *90	Hospitalaria
 *91	Hoteles
 *92	Idiomas
 *93	"Ilustración de Figurín"
 *94	Impuestos
 *95	Impuestos
 *96	Informática
 *97	"Informática - Hardware"
 *98	"Informática - Internet"
 *99	Ingeniería
 *100	"Inteligencia de Negocios"
 *101	Internet
 *102	"Inversión Extranjera"
 *103	Inversiones
 *104	Investigación
 *105	"Investigación clínica"
 *106	"Investigación de mercados"
 *107	Laboratorio
 *108	Leyes
 *109	Litigio
 *110	Logística
 *111	Mantención
 *112	"Marketing / Mercadeo"
 *113	Mecánica
 *114	"Medicina y Salud"
 *115	"Medio Ambiente"
 *116	"Medios de información"
 *117	Mercadotecnia
 *118	"Negocios Internacionales"
 *119	Nóminas
 *120	Operaciones
 *121	"Organización de eventos"
 *122	Orientación
 *123	"Otra Área"
 *124	Patronaje
 *125	Periodismo
 *126	"Planificación y Desarrollo"
 *127	"Precios de transferencia"
 *128	"Prevención de Riesgos"
 *129	"Producción y Manufactura"
 *130	Promociones
 *131	"Propiedad Industrial e Intelectual"
 *132	Propiedades
 *133	Proyectos
 *134	Psicopedagógia
 *135	Publicidad
 *136	Química
 *137	Recepción
 *138	"Reclutamiento y Selección"
 *139	"Recursos Humanos"
 *140	"Redes y Telecomunicaciones"
 *141	"Relaciones Públicas"
 *142	Salud
 *143	"Salud Pública"
 *144	"Secretaria Bilingue"
 *145	"Seguridad e Higiene"
 *146	Seguros
 *147	"Servicio al Cliente"
 *148	"Servicio Técnico"
 *149	Servicios
 *150	"Servicios de Seguridad"
 *151	"Servicios Generales"
 *152	"Servicios Sociales"
 *153	"Sociedades Mercantiles"
 *154	"Soporte Administrativo"
 *155	Suministros
 *156	"Tecnologías de Información"
 *157	Telecomunicaciones
 *158	Teléfonos
 *159	Telemarketing
 *160	"Todas las Areas"
 *161	"Trade Marketing"
 *162	Tributarios
 *163	"Turismo y Hotelería"
 *164	Ventas
 *165	Veterinaria
 *166	"Naviera / Naval"
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_area", schema = "empleo")
public class TipoArea {
    /*
    * Id del area
    */
    private Short codigo;

    /*
        * Nombre del Area
        */
    private String nombre;

    public TipoArea() {
    }

    @Id
    @Column(name = "cod_area")
    @JsonView(ResponseView.MainView.class)
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_area")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
