/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Grupo;
import Paquete.Pojos.UnidadAprendizaje;
import Paquete.Pojos.UnidadGrupo;
import Paquete.Pojos.Usuarios;
import java.io.Serializable;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "UnidadGrupo")
@SessionScoped
public class ManagedBeanUnidadGrupo implements Serializable {

    private UnidadGrupo unidadGrupo;
    private Usuarios docente;
    private UnidadAprendizaje unidadAprendizaje;
    private Grupo grupo;

    @PostConstruct
    private void init() {
        docente = new Usuarios();
        unidadGrupo = new UnidadGrupo();
        unidadAprendizaje = new UnidadAprendizaje();
        grupo = new Grupo();
    }

    public List<UnidadGrupo> obtenerUnidadesGrupo() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query q = null;
        try {
            session.beginTransaction();
            q = session.createQuery("FROM UnidadGrupo");
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            session.getTransaction().rollback();
            sqlException.printStackTrace();
        }

        return q.list();
    }

    public List<UnidadGrupo> obtenerUnidadesAprendizajeGrupo() {
        List<UnidadGrupo> unidadGrupo = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("FROM UnidadGrupo WHERE grupo.idGrupo = :id");
            q.setParameter("id", grupo.getIdGrupo());
            unidadGrupo = q.list();
            session.getTransaction().commit();
        } catch (Exception sqlException) {

            session.getTransaction().rollback();
            sqlException.printStackTrace();
        }
        return unidadGrupo;
    }

    public void insertUnidadGrupo() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        ///////////// ESTA ES LA UNICA FORMA EN LA QUE PUDE HACER ESTA SELECCIÓN
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        grupo.setIdGrupo((int) sessionMap.get("Id_Grupo"));
        //////////////////////////////////////
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            UnidadGrupo unidadGrupo = new UnidadGrupo();
            unidadGrupo.setUsuarios((Usuarios) session.get(Usuarios.class, docente.getIdUsuarios()));
            unidadGrupo.setUnidadAprendizaje((UnidadAprendizaje) session.get(UnidadAprendizaje.class, unidadAprendizaje.getIdUnidadAprendizaje()));
            unidadGrupo.setGrupo((Grupo) session.get(Grupo.class, grupo.getIdGrupo()));

            session.save(unidadGrupo);

            tx.commit();
            mensaje.setMessage("Agregado con exito");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar la unidad grupo, probablemente ya esta asignada");
            mensaje.fatal();
        }
    }

    public Long obtenerCantidadAlumnosUnidadGrupo(int Id_UnidadGrupo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Long count = null;
        try {
            session.beginTransaction();
            Query q = session.createQuery("SELECT COUNT(*) FROM ListaGrupo WHERE unidadGrupo.idUnidadGrupo = :idUnidadGrupo");
            q.setParameter("idUnidadGrupo", Id_UnidadGrupo);
            count = (Long) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            session.getTransaction().rollback();
            sqlException.printStackTrace();
        }

        return count;
    }

    public void eliminarUnidadGrupo(UnidadGrupo unidadGrupo) {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Mensajes message = new Mensajes();
        try {
            tran = session.beginTransaction();
            session.delete(unidadGrupo);
            tran.commit();
            message.setMessage("Eliminado correctamente");
            message.info();
        } catch (Exception sqlException) {
            tran.rollback();
            message.setMessage("No se pudo eliminar");
            message.info();
            sqlException.printStackTrace();
        }
    }

    // Guarda el Id de unidad grupo y redirecciona a la pagina Donde se leera ese ID
    public String redirectAlumnosUnidadAprendizaje(int id_UnidadGrupo) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove("Id_UnidadGrupo");
        sessionMap.put("Id_UnidadGrupo", id_UnidadGrupo);
        return "AlumnosUnidadAprendizaje?faces-redirect=true";
    }

    public String redirectRelacionarUnidadAprendizajeGrupo() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove("Id_Grupo");
        sessionMap.put("Id_Grupo", grupo.getIdGrupo());
        return "RelacionarUnidadAprendizajeGrupo";
    }

    public String redirectAgregarListaGrupo(int id_UnidadGrupo) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove("Id_UnidadGrupo");
        sessionMap.put("Id_UnidadGrupo", id_UnidadGrupo);
        this.unidadGrupo = devolverUnidadGrupo(id_UnidadGrupo);
        return "AgregarListaGrupo?faces-redirect=true";
    }

    public UnidadGrupo devolverUnidadGrupo(int idUnidadGrupo) {
        UnidadGrupo ua = new UnidadGrupo();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            tran = session.beginTransaction();
            ua = (UnidadGrupo) session.get(UnidadGrupo.class, idUnidadGrupo);
            tran.commit();
        } catch (Exception sqlException) {
            tran.rollback();
            sqlException.printStackTrace();
        }
        return ua;
    }

    // Getters y Setters
    public Usuarios getDocente() {
        return docente;
    }

    public void setDocente(Usuarios docente) {
        this.docente = docente;
    }

    public UnidadAprendizaje getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(UnidadAprendizaje unidadAprendizaje) {
        this.unidadAprendizaje = unidadAprendizaje;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public UnidadGrupo getUnidadGrupo() {
        return unidadGrupo;
    }

    public void setUnidadGrupo(UnidadGrupo unidadGrupo) {
        this.unidadGrupo = unidadGrupo;
    }

}
