-- ----------------------------
--  Table structure for rol_acceso
-- ----------------------------
CREATE SEQUENCE crm.rol_acceso_sequence;
DROP TABLE IF EXISTS "crm"."rol_acceso";
CREATE TABLE "crm"."rol_acceso" (
	"id" int2 PRIMARY KEY NOT NULL DEFAULT nextval('crm.rol_acceso_sequence'),
	"nombre" VARCHAR(100) NOT NULL UNIQUE,
  "nombre_presentacion" VARCHAR(100) NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"explicito" BOOLEAN NOT NULL DEFAULT TRUE,
	"rut_usuario" integer NOT NULL DEFAULT 81668700,
	"fecha_creacion" timestamp(6) NOT NULL DEFAULT now(),
	"fecha_modificacion" timestamp(6) NOT NULL DEFAULT now()
)
WITH (OIDS=FALSE);
ALTER TABLE "crm"."rol_acceso" OWNER TO "crm";
COMMENT ON TABLE "crm"."rol_acceso" IS 'Posibles roles que un usuario puede poseer para permisos dentro del sistema CRM';
COMMENT ON COLUMN "crm"."rol_acceso"."nombre_presentacion" IS 'Nombre que se utiliza para mostrar en las vistas del sistema CRM. Debe estar formateado para estos fines.';
COMMENT ON COLUMN "crm"."rol_acceso"."explicito" IS 'El rol de la fila posee permisos que deben ser obtenidos
explicitamente desde la tabla crm.autorizacion_usuario. Si es FALSE entonces los permisos son implicitos, es decir, no
es necesario especificar ningun permiso en crm.autorizacion_usuario para este rol ya que implicitamente los posee todos.';
GRANT ALL PRIVILEGES ON crm.rol_acceso to postgres;

-- ----------------------------
--  Table structure for permiso_acceso
-- ----------------------------
CREATE SEQUENCE crm.permiso_acceso_sequence;
DROP TABLE IF EXISTS "crm"."permiso_acceso";
CREATE TABLE "crm"."permiso_acceso" (
	"codigo" int2 PRIMARY KEY NOT NULL DEFAULT nextval('crm.permiso_acceso_sequence'),
	"nombre" VARCHAR(100) NOT NULL UNIQUE,
	"rut_usuario" integer NOT NULL DEFAULT 81668700,
	"fecha_creacion" timestamp(6) NOT NULL DEFAULT now(),
	"fecha_modificacion" timestamp(6) NOT NULL DEFAULT now()
)
WITH (OIDS=FALSE);
ALTER TABLE "crm"."permiso_acceso" OWNER TO "crm";
COMMENT ON TABLE "crm"."permiso_acceso" IS 'Posibles permisos que un usuario puede poseer sobre un objeto dentro del sistema CRM.
Por ejemplo: leer, escribir, actualizar, etc.';
GRANT ALL PRIVILEGES ON crm.permiso_acceso to postgres;

-- ----------------------------
--  Table structure for autorizacion_usuario
-- ----------------------------
CREATE SEQUENCE crm.autorizacion_usuario_sequence;
DROP TABLE IF EXISTS "crm"."autorizacion_usuario";
CREATE TABLE "crm"."autorizacion_usuario" (
	"id" INT8 PRIMARY KEY NOT NULL DEFAULT nextval('crm.autorizacion_usuario_sequence'),
	"usuario_id" BIGINT NOT NULL REFERENCES "dbo"."usuario_aexa"(usuaex_id) ON UPDATE CASCADE ON DELETE CASCADE,
	"rol_acceso_id" INT2 NOT NULL REFERENCES "crm"."rol_acceso"(id) ON UPDATE CASCADE ON DELETE CASCADE,
	"nombre_objeto" VARCHAR(100) NOT NULL,
	"global" BOOLEAN NOT NULL DEFAULT FALSE ,
	"restriccion" VARCHAR(255),
	"rut_usuario" integer NOT NULL DEFAULT 81668700,
	"fecha_creacion" timestamp(6) NOT NULL DEFAULT now(),
	"fecha_modificacion" timestamp(6) NOT NULL DEFAULT now()
)
WITH (OIDS=FALSE);
ALTER TABLE "crm"."autorizacion_usuario" OWNER TO "crm";

CREATE INDEX  "usuario_id_autorizacion_usuario_fk_index" ON "crm"."autorizacion_usuario" USING btree(usuario_id ASC NULLS LAST);
CREATE INDEX  "rol_acceso_id_autorizacion_usuario_fk_index" ON "crm"."autorizacion_usuario" USING btree(rol_acceso_id ASC NULLS LAST);
COMMENT ON TABLE "crm"."autorizacion_usuario" IS 'Autorizaciones de permisos que posee un usuario con un rol determinado dentro del sistema CRM.
Por ejemplo si posee el rol ROL_AYUDANTE podria tener permisos de lectura y escritura sobre algun objeto. Ademas podria poseer otro rol
por ejemplo el ROL_TEMPORAL y tener permisos de lectura sobre un objeto distinto al que posee en ROL_AYUDANTE.';
COMMENT ON COLUMN "crm"."autorizacion_usuario"."global" IS 'Si es TRUE entonces no posee ninguna restriccion sobre el objeto asignado. Si es
FALSE entonces es necesario observar la columna restriccion para conocer que restriccion se debe aplicar antes de asignar el permiso
sobre el objeto actual.';
COMMENT ON COLUMN "crm"."autorizacion_usuario"."restriccion" IS 'La restriccion a aplicar antes de asignar el permiso sobre
el objeto actual. Por ejemplo para una carrera puede ser carrera.id=73 que significaria que el permiso asignado solo aplica
para la carrera con id=73.';
GRANT ALL PRIVILEGES ON crm.autorizacion_usuario to postgres;

-- ----------------------------
--  Table structure for autorizacion_usuario_permiso_acceso
-- ----------------------------
DROP TABLE IF EXISTS "crm"."autorizacion_usuario_permiso_acceso";
CREATE TABLE "crm"."autorizacion_usuario_permiso_acceso" (
	"permiso_acceso_id" INT2 NOT NULL REFERENCES "dbo"."permiso_acceso"(codigo) ON UPDATE CASCADE ON DELETE CASCADE,
	"autorizacion_usuario_id" INT8 NOT NULL REFERENCES "dbo"."autorizacion_usuario"(id) ON UPDATE CASCADE ON DELETE CASCADE,
	"rut_usuario" integer NOT NULL DEFAULT 81668700,
	"fecha_creacion" timestamp(6) NOT NULL DEFAULT now(),
	"fecha_modificacion" timestamp(6) NOT NULL DEFAULT now(),
	PRIMARY KEY(permiso_acceso_id, autorizacion_usuario_id)
)
WITH (OIDS=FALSE);
ALTER TABLE "crm"."autorizacion_usuario_permiso_acceso" OWNER TO "crm";
COMMENT ON TABLE "crm"."permiso_acceso" IS 'Posibles permisos que un usuario puede poseer sobre un objeto dentro del sistema CRM.
Por ejemplo: leer, escribir, actualizar, etc.';
GRANT ALL PRIVILEGES ON crm.autorizacion_usuario_permiso_acceso to postgres;
