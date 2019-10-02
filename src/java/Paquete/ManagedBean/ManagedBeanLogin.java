package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.DatosUsuario;
import Paquete.Pojos.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
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

        // Si el usuario no existe o si existe
        if (usuario.isEmpty()) {
            message.setMessage("Datos incorrectos");
            message.error();
            return "index.xhtml";
        } else {
            verPaginasWeb = true;
            GuardarDatosUsuarioSesion(usuario.get(0).getIdUsuarios());
            switch (((Usuarios) usuario.get(0)).getRoles().getIdrol()) {

                case 1:
                    pagina = "Administrador/TemplatePrincipal?faces-redirect=true";
            }
        }

        return pagina;
    }

    public void GuardarDatosUsuarioSesion(int idUsuario) {

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context2.getExternalContext().getSession(true);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;

        DatosUsuario du = new DatosUsuario();
        
        try {

            transaction = session.beginTransaction();
            String hql = "from DatosUsuario where idUsuarios = :idUser";
            du = (DatosUsuario) session.createQuery(hql).setParameter("idUser", idUsuario).uniqueResult();
           
            // Si el usuario tiene datos de usuario los guarda en session
            // y los muestra en Templates/BarraNavegacion solo los nombres
            if (du != null) {
                httpSession.setAttribute("idUsuarios", du.getUsuarios().getIdUsuarios() );
                httpSession.setAttribute("identificador", du.getIdentificador());
                httpSession.setAttribute("nombre", du.getNombre());
                httpSession.setAttribute("paterno", du.getApellidoPaterno());
                httpSession.setAttribute("materno", du.getApellidoMaterno());
                httpSession.setAttribute("telefono", du.getTelefono());
                httpSession.setAttribute("correo", du.getCorreo());
                httpSession.setAttribute("numero_seguro", du.getNumeroSeguro());
            }
            
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }

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
