package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Grupo;
import Paquete.Pojos.UnidadAprendizaje;
import Paquete.Pojos.UnidadGrupo;
import Paquete.Pojos.UnidadTematica;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "UnidadAprendizaje")
@javax.faces.bean.ViewScoped
public class ManagedBeanUnidadAprendizaje implements java.io.Serializable {

    private UnidadAprendizaje unidadAprendizaje;
    private List<UnidadTematica> unidadesTematicas;
    private List<UnidadAprendizaje> unidadAprendizajeSeleccionada;

    @PostConstruct
    public void init() {
        unidadesTematicas = new java.util.ArrayList();
        unidadAprendizaje = new UnidadAprendizaje();
        unidadesTematicas.add(new UnidadTematica());
    }

    public void addInput() {
        unidadesTematicas.add(new UnidadTematica());
    }

    public void deleteInput() {
        Mensajes mensaje = new Mensajes();
        if (unidadesTematicas.size() - 1 == 0) {
            mensaje.setMessage("No hay datos que eliminar. Debe existir minímo una unidad temática en cada unidad de aprendizaje.");
            mensaje.warn();
        } else {
            unidadesTematicas.remove(unidadesTematicas.size() - 1);
            mensaje.setMessage("Eliminado.");
            mensaje.info();
        }
    }
    
    public List<UnidadGrupo> unidadGrupoPorUnidadAprendizaje(int idUnidadAprendizaje){
        List<UnidadGrupo> unidadGrupo = new ArrayList<>();
        
        Session session = null;
        Transaction tran = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            Query q = session.createQuery("FROM UnidadGrupo WHERE unidadAprendizaje.idUnidadAprendizaje = :Id");
            q.setParameter("Id", idUnidadAprendizaje);
            unidadGrupo = q.list();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        }
        
        return unidadGrupo;
    }

    public List<UnidadAprendizaje> obtenerUnidadesAprendizaje() {

        List<UnidadAprendizaje> unidadesAprendizajeTematicas = new ArrayList<>();
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tran = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            Query q = session.createQuery("FROM UnidadAprendizaje");
            unidadesAprendizajeTematicas = q.list();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
            mensaje.setMessage("Sin acceso a la base de datos. Espere unos minutos e intentelo nuevamente.");
            mensaje.error();
        }

        return unidadesAprendizajeTematicas;
    }

    public void insertUnidadAprendizajeYTematica() {
        Mensajes mensaje = new Mensajes();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(unidadAprendizaje);

            for (UnidadTematica unidadT : unidadesTematicas) {
                UnidadTematica unidadTematica = new UnidadTematica();
                unidadTematica.setNombre(unidadT.getNombre());
                unidadTematica.setUnidadAprendizaje(unidadAprendizaje);
                session.save(unidadTematica);
            }

            tx.commit();
            mensaje.setMessage("Guardado con éxito");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el usuario, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }

    public void EliminarUnidadAprendizaje(int id_UnidadAprendizaje) {
        Mensajes mensaje = new Mensajes();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete((UnidadAprendizaje) session.get(UnidadAprendizaje.class, id_UnidadAprendizaje));
            tx.commit();
            mensaje.setMessage("Eliminado del sistema");
            mensaje.info();
        } catch (Exception e) {
                tx.rollback();
                mensaje.setMessage("No se pudo eliminar, probablemente esta ligada a un grupo");
                mensaje.warn();
        } 
    }
    
    public String returnGrupos(){
        return "AgregarGrupo";
    }

    //Getters y Setters
    public UnidadAprendizaje getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(UnidadAprendizaje unidadAprendizaje) {
        this.unidadAprendizaje = unidadAprendizaje;
    }

    public List<UnidadTematica> getUnidadesTematicas() {
        return unidadesTematicas;
    }

    public void setUnidadesTematicas(List<UnidadTematica> unidadesTematicas) {
        this.unidadesTematicas = unidadesTematicas;
    }

    public List<UnidadAprendizaje> getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    public void setUnidadAprendizajeSeleccionada(List<UnidadAprendizaje> unidadAprendizajeSeleccionada) {
        this.unidadAprendizajeSeleccionada = unidadAprendizajeSeleccionada;
    }
}
