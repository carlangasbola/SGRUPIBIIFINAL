/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Eventos;
import Paquete.Pojos.UnidadGrupo;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author iron1
 */
@Named(value = "Evento")
@RequestScoped
public class ManagedBeanEvento {

    private Eventos evento = new Eventos();
    private UnidadGrupo unidadGrupo = new UnidadGrupo();
    private String tipo;
    private int idUnidadGrupo;

    public ManagedBeanEvento() {
    }

    public void crearEvento() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            UnidadGrupo ua = (UnidadGrupo) session.get(UnidadGrupo.class, idUnidadGrupo);
            evento.setTipo(tipo);
            evento.setUnidadGrupo(ua);

            session.save(evento);
            tx.commit();
            mensaje.setMessage("Evento creado");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se creo el evento");
            mensaje.fatal();

        }
    }

    public List<UnidadGrupo> obtenerUnidadesGrupo() {
        List<UnidadGrupo> ug = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query q = null;
        try {
            session.beginTransaction();
            q = session.createQuery("FROM UnidadGrupo");
            ug = q.list();
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            session.getTransaction().rollback();
            sqlException.printStackTrace();
        }

        return ug;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public UnidadGrupo getUnidadGrupo() {
        return unidadGrupo;
    }

    public void setUnidadGrupo(UnidadGrupo unidadGrupo) {
        this.unidadGrupo = unidadGrupo;
    }
    
    public int getIdUnidadGrupo() {
        return idUnidadGrupo;
    }

    public void setIdUnidadGrupo(int idUnidadGrupo) {
        this.idUnidadGrupo = idUnidadGrupo;
    }
   
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
