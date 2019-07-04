package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Grupo;
import Paquete.Service.ServiceGrupo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("Grupo")
@RequestScoped
public class ManagedBeanGrupo {

    private Grupo grupo ;
    private List<Grupo> listaGrupo ;
    private ServiceGrupo service;
    
    @PostConstruct
    private void init() {
        service = new ServiceGrupo();
        grupo = new Grupo();
        listaGrupo = new ArrayList<>();
        listaGrupo = service.obtenerGrupos();
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

            mensaje.setMessage("Operación éxitosa");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se pudo completar la transacción");
            mensaje.fatal();

        } 
    }

    public Long cantidadUnidadesAprendizaje(int idGrupo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Long count = null;
        try {
            session.beginTransaction();
            Query q = session.createQuery("SELECT COUNT(*) FROM UnidadGrupo WHERE grupo.idGrupo = :idGrupo");
            q.setParameter("idGrupo", idGrupo);
            count = (Long) q.uniqueResult();
        } catch (Exception ex) {
            System.out.print(ex);
        } 
        return count;
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
        if (listaGrupo == null)
            listaGrupo = service.obtenerGrupos();
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> gruposSeleccionados) {
        this.listaGrupo = gruposSeleccionados;
    }
}
