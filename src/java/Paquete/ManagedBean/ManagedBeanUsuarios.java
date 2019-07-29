package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Usuarios;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("Usuarios")
@RequestScoped
public class ManagedBeanUsuarios {

    
    public void EliminarUsuario(int idUsuario) {
        Mensajes mensaje = new Mensajes();
        Transaction transObj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transObj = session.beginTransaction();
            session.delete((Usuarios) session.get(Usuarios.class, idUsuario));

            transObj.commit();
            mensaje.setMessage("Eliminado del sistema");
            mensaje.info();
            
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
        
    }

    
}
