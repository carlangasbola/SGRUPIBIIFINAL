package Paquete.Pojos;
// Generated 1/07/2019 07:23:53 PM by Hibernate Tools 4.3.1



/**
 * ReactivosUtilizadoPractica generated by hbm2java
 */
public class ReactivosUtilizadoPractica  implements java.io.Serializable {


     private int idReactivosUtilizado;
     private Practica practica;
     private Reactivos reactivos;
     private int idDatos;

    public ReactivosUtilizadoPractica() {
    }

    public ReactivosUtilizadoPractica(int idReactivosUtilizado, Practica practica, Reactivos reactivos, int idDatos) {
       this.idReactivosUtilizado = idReactivosUtilizado;
       this.practica = practica;
       this.reactivos = reactivos;
       this.idDatos = idDatos;
    }
   
    public int getIdReactivosUtilizado() {
        return this.idReactivosUtilizado;
    }
    
    public void setIdReactivosUtilizado(int idReactivosUtilizado) {
        this.idReactivosUtilizado = idReactivosUtilizado;
    }
    public Practica getPractica() {
        return this.practica;
    }
    
    public void setPractica(Practica practica) {
        this.practica = practica;
    }
    public Reactivos getReactivos() {
        return this.reactivos;
    }
    
    public void setReactivos(Reactivos reactivos) {
        this.reactivos = reactivos;
    }
    public int getIdDatos() {
        return this.idDatos;
    }
    
    public void setIdDatos(int idDatos) {
        this.idDatos = idDatos;
    }




}


