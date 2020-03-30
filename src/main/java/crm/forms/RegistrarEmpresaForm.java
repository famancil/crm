package crm.forms;

import crm.entities.Empresa;
import crm.entities.Provincia;
import crm.entities.Region;
import crm.entities.SucursalEmpresa;

public class RegistrarEmpresaForm {

    private Empresa empresa;

    private SucursalEmpresa sucursalEmpresa;

    private Region region;

    private Provincia provincia;

    public RegistrarEmpresaForm(){}

    public RegistrarEmpresaForm(Empresa empresa, SucursalEmpresa sucursalEmpresa, Region region, Provincia provincia) {
        this.empresa = empresa;
        this.sucursalEmpresa = sucursalEmpresa;
        this.region = region;
        this.provincia = provincia;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public SucursalEmpresa getSucursalEmpresa() {
        return sucursalEmpresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setSucursalEmpresa(SucursalEmpresa sucursalEmpresa) {
        this.sucursalEmpresa = sucursalEmpresa;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
