package Paquete.Pojos;
// Generated 1/07/2019 07:23:53 PM by Hibernate Tools 4.3.1



/**
 * EquipoUtilizado generated by hbm2java
 */
public class EquipoUtilizado  implements java.io.Serializable {


     private int idEquipoUtilizado;
     private EquipoLaboratorio equipoLaboratorio;
     private Practica practica;

    public EquipoUtilizado() {
    }

    public EquipoUtilizado(int idEquipoUtilizado, EquipoLaboratorio equipoLaboratorio, Practica practica) {
       this.idEquipoUtilizado = idEquipoUtilizado;
       this.equipoLaboratorio = equipoLaboratorio;
       this.practica = practica;
    }
   
    public int getIdEquipoUtilizado() {
        return this.idEquipoUtilizado;
    }
    
    public void setIdEquipoUtilizado(int idEquipoUtilizado) {
        this.idEquipoUtilizado = idEquipoUtilizado;
    }
    public EquipoLaboratorio getEquipoLaboratorio() {
        return this.equipoLaboratorio;
    }
    
    public void setEquipoLaboratorio(EquipoLaboratorio equipoLaboratorio) {
        this.equipoLaboratorio = equipoLaboratorio;
    }
    public Practica getPractica() {
        return this.practica;
    }
    
    public void setPractica(Practica practica) {
        this.practica = practica;
    }




}


