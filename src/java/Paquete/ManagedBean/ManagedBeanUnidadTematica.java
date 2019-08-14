package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.UnidadTematica;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("UnidadTematica")
@Dependent
public class ManagedBeanUnidadTematica {

    public ManagedBeanUnidadTematica() {
    }

    public List<UnidadTematica> obtenerUnidadTematica(int Id_UnidadAprendizaje) {
        Transaction tran = null;
        Session session = null;
        List<UnidadTematica> unidadesTematicas = new ArrayList<>();
        Mensajes mensaje = new Mensajes();

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            Query q = session.createQuery("FROM UnidadTematica WHERE unidadAprendizaje.idUnidadAprendizaje = :id");
            q.setParameter("id", Id_UnidadAprendizaje);
            unidadesTematicas = q.list();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
            mensaje.setMessage("Sin acceso a la base de datos. Espere unos minutos e intentelo nuevamente.");
            mensaje.error();
        }

        return unidadesTematicas;
    }
}
