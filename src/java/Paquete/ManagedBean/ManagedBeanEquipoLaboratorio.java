package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.EquipoLaboratorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("DatosEquipoLaboratorio")
@SessionScoped
public class ManagedBeanEquipoLaboratorio implements Serializable{
    
    private List<EquipoLaboratorio> datosEquipo;
    private EquipoLaboratorio equipo;

    @PostConstruct
    private void init() {
        datosEquipo = new ArrayList<>();
        equipo = new EquipoLaboratorio();
        ObtenerDatosEquipo();
    }
    
    public String obtenerEquipoLaboratorio(EquipoLaboratorio equipo){
        this.equipo = equipo;
        return "ActualizarEquipoLaboratorio";
    }
    
    public List<EquipoLaboratorio> ObtenerDatosEquipo() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            datosEquipo = session.createQuery("FROM EquipoLaboratorio").list();
            tran.commit();
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return datosEquipo;
    }
    
    public List <String> nombresEquipo() {
        List <String> nombres = new ArrayList<>();
        
        if(datosEquipo == null) {
            ObtenerDatosEquipo();
        }
        
        for(EquipoLaboratorio item:datosEquipo) {
            nombres.add(item.getNombre());
        }
        
        Collections.sort(nombres); 
        
        return nombres;
    }
    
    public String nombreEquipo(Integer elemento) {
        if(datosEquipo == null) {
            ObtenerDatosEquipo();
        }
        return datosEquipo.get(elemento).getNombre();
    }
    
    public Integer cantidadEquipo(Integer elemento) {
        if(datosEquipo == null) {
            ObtenerDatosEquipo();
        }
        return datosEquipo.get(elemento).getCantidad();
    }
    
    public String disponiblidad (int Existencia_Inventario) {
        if (Existencia_Inventario == 1) {
            return "Si";
        }
        return "No";
    }
    
    public void InsertDatosEquipo() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.save(equipo);
            tx.commit();
            
            mensaje.setMessage("Equipo agregado");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el material, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }
    
     public void ActualzarDatosEquipo() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.update(equipo);
            tx.commit();
            
            mensaje.setMessage("Equipo actualizado");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo actualizar el equipo, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }
    
    public void EliminarEquipo(int idEquipo) {
        Mensajes mensaje = new Mensajes();
        Transaction transObj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transObj = session.beginTransaction();
            session.delete((EquipoLaboratorio) session.get(EquipoLaboratorio.class, idEquipo));

            transObj.commit();
            mensaje.setMessage("Material eliminado del sistema");
            mensaje.info();
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
    }
    
    public List<EquipoLaboratorio> getDatosEquipo() {
        if(datosEquipo == null) {
            ObtenerDatosEquipo();
        }
        return datosEquipo;
    }

    public void setDatosEquipo(List<EquipoLaboratorio> datosEquipo) {
        this.datosEquipo = datosEquipo;
    }

    public EquipoLaboratorio getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoLaboratorio equipo) {
        this.equipo = equipo;
    }

}
