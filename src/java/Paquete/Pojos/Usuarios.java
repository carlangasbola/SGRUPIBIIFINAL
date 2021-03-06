package Paquete.Pojos;
// Generated 1/07/2019 07:23:53 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private int idUsuarios;
     private Roles roles;
     private String userLogin;
     private String passsword;
     private Set listaGrupos = new HashSet(0);
     private Set reportePracticas = new HashSet(0);
     private DatosUsuario datosUsuario;
     private Set practicas = new HashSet(0);
     private Set unidadGrupos = new HashSet(0);

    public Usuarios() {
    }

	
    public Usuarios(int idUsuarios, Roles roles, String userLogin, String passsword) {
        this.idUsuarios = idUsuarios;
        this.roles = roles;
        this.userLogin = userLogin;
        this.passsword = passsword;
    }
    public Usuarios(int idUsuarios, Roles roles, String userLogin, String passsword, Set listaGrupos, Set reportePracticas, DatosUsuario datosUsuario, Set practicas, Set unidadGrupos) {
       this.idUsuarios = idUsuarios;
       this.roles = roles;
       this.userLogin = userLogin;
       this.passsword = passsword;
       this.listaGrupos = listaGrupos;
       this.reportePracticas = reportePracticas;
       this.datosUsuario = datosUsuario;
       this.practicas = practicas;
       this.unidadGrupos = unidadGrupos;
    }
   
    public int getIdUsuarios() {
        return this.idUsuarios;
    }
    
    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
    public String getUserLogin() {
        return this.userLogin;
    }
    
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    public String getPasssword() {
        return this.passsword;
    }
    
    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
    public Set getListaGrupos() {
        return this.listaGrupos;
    }
    
    public void setListaGrupos(Set listaGrupos) {
        this.listaGrupos = listaGrupos;
    }
    public Set getReportePracticas() {
        return this.reportePracticas;
    }
    
    public void setReportePracticas(Set reportePracticas) {
        this.reportePracticas = reportePracticas;
    }
    public DatosUsuario getDatosUsuario() {
        return this.datosUsuario;
    }
    
    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    public Set getPracticas() {
        return this.practicas;
    }
    
    public void setPracticas(Set practicas) {
        this.practicas = practicas;
    }
    public Set getUnidadGrupos() {
        return this.unidadGrupos;
    }
    
    public void setUnidadGrupos(Set unidadGrupos) {
        this.unidadGrupos = unidadGrupos;
    }




}


