package Paquete.ManagedBean.Login;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.DatosUsuario;
import Paquete.Pojos.Roles;
import Paquete.Pojos.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("DatosUsuario")
@RequestScoped
public class ManagedBeanDatosUsuario {

    private List<DatosUsuario> datosUsuarios;
    private DatosUsuario datosUsuario;
    private Roles roles;

    @PostConstruct
    private void init() {
        datosUsuarios = new ArrayList<>();
        datosUsuario = new DatosUsuario();
        roles = new Roles();
        ObtenerDatosUsuarios();
    }

    public String obtenerDatosUsuario(int idUsuario) {
        Transaction tran = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            datosUsuario = ((DatosUsuario) session.get(DatosUsuario.class, Integer.valueOf(idUsuario)));
            tran.commit();
        } catch (Exception e) {
            tran.rollback();
        }

        return "EditarDatosUsuario";
    }

    public void actualizarDatosUsuario() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            Usuarios u = new Usuarios();
            u = (Usuarios) session.get(Usuarios.class, datosUsuario.getUsuarios().getIdUsuarios());
            u.setUserLogin(datosUsuario.getCorreo());
            u.setPasssword(datosUsuario.getIdentificador());
            u.setRoles((Roles) session.get(Roles.class, roles.getIdrol()));
            session.update(u);

            datosUsuario.setUsuarios(u);
            session.update(datosUsuario);

            tx.commit();
            mensaje.setMessage("Usuario agregado");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el usuario, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
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
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el usuario, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }

    public List<DatosUsuario> ObtenerDatosUsuarios() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            datosUsuarios = session.createQuery("FROM DatosUsuario").list();
            tran.commit();
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return datosUsuarios;
    }

    public List<DatosUsuario> obtenerDatosUsuarioRol(int idRol) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<DatosUsuario> datosUsuarios = new ArrayList();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM DatosUsuario WHERE usuarios.roles.idRol = :idRol");
            query.setParameter("idRol", idRol);
            datosUsuarios = query.list();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
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
        return datosUsuarios;
    }

    public void setDatosUsuarios(List<DatosUsuario> datosUsuarios) {
        this.datosUsuarios = datosUsuarios;
    }

}
