package Paquete.Pojos;
// Generated 1/07/2019 07:23:53 PM by Hibernate Tools 4.3.1



/**
 * NotificacionesPractica generated by hbm2java
 */
public class NotificacionesPractica  implements java.io.Serializable {


     private int idNotificacion;
     private Practica practica;
     private byte[] descripcion;
     private short estado;

    public NotificacionesPractica() {
    }

    public NotificacionesPractica(int idNotificacion, Practica practica, byte[] descripcion, short estado) {
       this.idNotificacion = idNotificacion;
       this.practica = practica;
       this.descripcion = descripcion;
       this.estado = estado;
    }
   
    public int getIdNotificacion() {
        return this.idNotificacion;
    }
    
    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    public Practica getPractica() {
        return this.practica;
    }
    
    public void setPractica(Practica practica) {
        this.practica = practica;
    }
    public byte[] getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(byte[] descripcion) {
        this.descripcion = descripcion;
    }
    public short getEstado() {
        return this.estado;
    }
    
    public void setEstado(short estado) {
        this.estado = estado;
    }




}


