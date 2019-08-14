package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Grupo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("Grupo")
@SessionScoped
public class ManagedBeanGrupo implements Serializable {

    private Grupo grupo = new Grupo();
    private List<Grupo> listaGrupo = new ArrayList<>();

    @PostConstruct
    public void init() {
        listaGrupo = obtenerGrupos();
    }

    public void eliminarGrupo(int idGrupo) {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.delete((Grupo) session.get(Grupo.class, idGrupo));
            tx.commit();

            listaGrupo = obtenerGrupos();
            mensaje.setMessage("Operación éxitosa");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se pudo completar la transacción");
            mensaje.fatal();

        }
    }

    public String crearGrupo() {
        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            tran = session.beginTransaction();
            session.save(this.grupo);
            tran.commit();
            listaGrupo = obtenerGrupos();
            mensaje.setMessage("Operación éxitosa");
            mensaje.info();
        } catch (Exception sqlException) {
            tran.rollback();
            mensaje.setMessage("No se creo el grupo");
            mensaje.error();
        }

        return "AgregarGrupo";
    }

    public List<Grupo> obtenerGrupos() {

        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Grupo> grupos = new ArrayList<>();

        try {
            tran = session.beginTransaction();
            Query query = session.createQuery("FROM Grupo");
            grupos = query.list();
            tran.commit();

        } catch (Exception sqlException) {
            tran.rollback();
        }

        return grupos;
    }

    public void actualizarGrupo() {
        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            tran = session.beginTransaction();

            Grupo grupo = new Grupo();
            grupo = (Grupo) session.get(Grupo.class, this.grupo.getIdGrupo());
            grupo.setNombre(this.grupo.getNombre());
            grupo.setCupo(this.grupo.getCupo());
            session.save(grupo);
            mensaje.setMessage("Grupo Actualizado");
            mensaje.info();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
            mensaje.setMessage("No se pudo actualizar el grupo");
            mensaje.error();
            System.out.print(ex);
        }
    }

    public Long cantidadUnidadesAprendizaje(int idGrupo) {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Long count = null;
        try {
            tran = session.beginTransaction();
            Query q = session.createQuery("SELECT COUNT(*) FROM UnidadGrupo WHERE grupo.idGrupo = :idGrupo");
            q.setParameter("idGrupo", idGrupo);
            count = (Long) q.uniqueResult();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
            System.out.print(ex);
        }
        return count;
    }

    public String redirecionarModificacionGrupo(Grupo grupo) {
        this.grupo = grupo;
        return "modificarGrupo";
    }

    public void insertarGrupo() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.save(grupo);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el usuario, consulte el log para mas detalles");
            mensaje.fatal();

        }

    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Grupo> getListaGrupo() {
        if (listaGrupo == null) {
            listaGrupo = obtenerGrupos();
        }
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> gruposSeleccionados) {
        this.listaGrupo = gruposSeleccionados;
    }
}
