package Paquete.ManagedBean.Login;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Roles;
import Paquete.Pojos.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Named("Login")
@RequestScoped
public class ManagedBeanLogin {

    private String user;
    private String pass;
    private boolean verPaginasWeb = true;

    public ManagedBeanLogin() {
    }

    public String validarUsuario() {
        String pagina = "";
        List<Usuarios> usuario = new ArrayList();
        Mensajes message = new Mensajes();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String hql = "from Usuarios where userLogin = :username and passsword = :pass";

            usuario = session.createQuery(hql).setParameter("username", user.trim()).setParameter("pass", pass).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }

        if (usuario.isEmpty()) {
            message.setMessage("Datos incorrectos");
            message.error();
            return "index.xhtml";
        } else {

            verPaginasWeb = true;

            switch (((Usuarios) usuario.get(0)).getRoles().getIdrol()) {

                case 1:
                    pagina = "Administrador/TemplatePrincipal?faces-redirect=true";
            }
        }

        return pagina;
    }

    public String logout() {
        verPaginasWeb = true;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
