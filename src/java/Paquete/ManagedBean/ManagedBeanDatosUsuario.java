package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.DatosUsuario;
import Paquete.Pojos.Roles;
import Paquete.Pojos.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("DatosUsuario")
@SessionScoped
public class ManagedBeanDatosUsuario implements Serializable{

    private List<DatosUsuario> datosUsuarios = new ArrayList<>();
    private DatosUsuario datosUsuario = new DatosUsuario();
    private Roles roles = new Roles();

    @PostConstruct
    private void init() {
        ObtenerDatosUsuarios();
    }

    public String obtenerDatosUsuario(DatosUsuario du) {
        this.datosUsuario = du;
        return "EditarDatosUsuario";
    }
    

    public void actualizarDatosUsuario() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            Roles r = new Roles();
            r = (Roles) session.get(Roles.class, roles.getIdrol());

            Usuarios u = new Usuarios();
            u = (Usuarios) session.get(Usuarios.class, datosUsuario.getUsuarios().getIdUsuarios());
            u.setUserLogin(datosUsuario.getCorreo());
            u.setPasssword(datosUsuario.getIdentificador());
            u.setRoles(r);
            session.update(u);

            DatosUsuario du = new DatosUsuario();
            du = (DatosUsuario) session.get(DatosUsuario.class, datosUsuario.getIdUsuarios());
            du.setUsuarios(u);
            session.update(du);

            tx.commit();
            mensaje.setMessage("Información Actualizada");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se pudo actualizar la información del usuario, consulte el log para mas detalles");
            mensaje.fatal();
        }
    }

    public void InsertDatosUsuario() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            Usuarios u = new Usuarios();

            u.setUserLogin(datosUsuario.getCorreo());
            u.setPasssword(datosUsuario.getIdentificador());
            u.setRoles((Roles) session.get(Roles.class, roles.getIdrol()));
            session.save(u);

            datosUsuario.setUsuarios(u);
            session.save(datosUsuario);

            tx.commit();
            mensaje.setMessage("Usuario agregado");
            mensaje.info();
        } catch (HibernateException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el usuario, probablemente ya se encuentra registrado el correo.");
            mensaje.fatal();
        }
    }
    
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
            ObtenerDatosUsuarios();
            
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
     }

    public List<DatosUsuario> ObtenerDatosUsuarios() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            tran = session.beginTransaction();
            datosUsuarios = session.createQuery("FROM DatosUsuario").list();
            tran.commit();
        } catch (Exception e) {
            tran.rollback();
        }
        return datosUsuarios;
    }

    public List<DatosUsuario> obtenerDatosUsuarioRol(int idRol) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<DatosUsuario> datosUsuarios = new ArrayList();
        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM DatosUsuario WHERE usuarios.roles.idrol = :idRol");
            query.setParameter("idRol", idRol);
            datosUsuarios = query.list();
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            session.getTransaction().rollback();
        } 
        
        return datosUsuarios;
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<DatosUsuario> getDatosUsuarios() {
        if (datosUsuarios == null) {
            ObtenerDatosUsuarios();
        }
        return datosUsuarios;
    }

    public void setDatosUsuarios(List<DatosUsuario> datosUsuarios) {
        this.datosUsuarios = datosUsuarios;
    }

}
