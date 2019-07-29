/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Beans.UtilPath;
import Paquete.Pojos.DatosUsuario;
import Paquete.Pojos.Grupo;
import Paquete.Pojos.ListaGrupo;
import Paquete.Pojos.Roles;
import Paquete.Pojos.UnidadAprendizaje;
import Paquete.Pojos.UnidadGrupo;
import Paquete.Pojos.Usuarios;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jxl.Sheet;
import jxl.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author iron1
 */
@Named(value = "ListaGrupo")
@RequestScoped
public class ManagedBeanListaGrupo {

    //Parametros para la creación de un usuario    
    private String nombre;
    private String paterno;
    private String materno;
    private String identificador;

    private int tipousuario = 4;
    private int rol = 4;
    private int idDatos = 4;
    private String filename = ManagedBeanUploadExcel.fileName;

    private ListaGrupo alumnoListaGrupo;

    @PostConstruct
    public void init() {
        alumnoListaGrupo = new ListaGrupo();
    }

    public void LeerArchivosExcel() {

        String usuario = "";
        Session hibernateSession = null;
        ////// Forma de asignar o recuperar el Id GRUPO
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        int idUnidadGrupo = (int) sessionMap.get("Id_UnidadGrupo");
        if (idUnidadGrupo == 0) {
            return;
        }
        ////// 
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String realPath = UtilPath.getUrlDefinida(ec.getRealPath("/"));
        System.out.println(filename);
        String archivoDestino = realPath + File.separator + "web" + File.separator + "ExcelUpload" + File.separator + filename;
        int contador = 1;

        try {
            hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
            hibernateSession.beginTransaction();

            UnidadGrupo ug = new UnidadGrupo();
            ug = (UnidadGrupo) hibernateSession.get(UnidadGrupo.class, idUnidadGrupo);
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            
            if ( archivoExcel.getNumberOfSheets() >= ug.getGrupo().getCupo()){
                Mensajes message = new Mensajes();
                message.setMessage("La cantidad de alumnos a ingresar es mas grande que el cupo del grupo");
                message.warn();
                return;
            }
            //Recorre cada hoja
            for (int hojas = 0; hojas < archivoExcel.getNumberOfSheets(); hojas++) {
                Sheet hoja = archivoExcel.getSheet(hojas);
                int x = 0;
                int ideq = 0;
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                String dato;
                //Recorre cada fila de la hoja
                for (int fila = 1; fila < numFilas; fila++) {
                    for (int columna = 1; columna < numColumnas; columna++) {
                        dato = hoja.getCell(columna, fila).getContents();
                        System.out.print(dato + " ");
                        //Intruccion switch que evalua la variable contador
                        switch (contador) {
                            case 1:
                                identificador = dato;
                                contador++;
                                break;
                            case 2:
                                nombre = dato;
                                contador++;
                                break;
                            case 3:
                                paterno = dato;
                                contador++;
                                break;
                            case 4:
                                materno = dato;
                                contador = 1;
                                break;
                            //case 5:
                            //num_Equipo = dato;
                            //contador=1;
                            //break;
                            //case 6:
                            //password = dato;
                            //contador++;
                            //break;
                            //case 7:
                            //numeroSeguro = dato;
                            //contador++;
                            //break;
                            //case 8:
                            //identificador = dato;
                            //contador=1;
                            //break;    
                        }

                    }
                    usuario = identificador + " " + nombre + " " + paterno + " " + materno;
                    ListaGrupo listgroup = new ListaGrupo();
                    Usuarios user = new Usuarios();
                    DatosUsuario datauser = new DatosUsuario();

                    Roles roles = new Roles();

                    //List<UnidadGrupo> ug = consulta.crearSelectidUnidadGrupo("FROM UnidadGrupo WHERE unidadAprendizaje = " + unidad_aprendizaje +" AND grupo = " + grupo) ;
                    int idug = idUnidadGrupo;
                    //System.out.println("idunidadgrupo" + idug);
                    // Obetener el rol
                    roles.setIdrol(rol);
                    //El login y el password sera con el identificador
                    user.setUserLogin(identificador);
                    user.setPasssword(identificador);
                    user.setRoles(roles);
                    //Guardamos el usuario en la base de datos
                    hibernateSession.save(user);

                    //Llenamos la tabala de datos de usuario
                    datauser.setNombre(nombre);
                    datauser.setApellidoPaterno(paterno);
                    datauser.setApellidoMaterno(materno);
                    //datauser.setTelefono(telefono);
                    //datauser.setCorreo(correo);
                    //datauser.setNumeroSeguro(numeroSeguro);
                    datauser.setIdentificador(identificador);
                    // Hacemos la relacion de los datos de usuario con la tabla usuario
                    datauser.setUsuarios(user);
                    //Guardamos los datos del usuario
                    hibernateSession.save(datauser);

                    listgroup.setUsuarios(user);
                    listgroup.setUnidadGrupo(ug);

                    hibernateSession.save(listgroup);

                }
                hibernateSession.getTransaction().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Agregados con éxito"));

            }
        } catch (Exception e) {
            hibernateSession.getTransaction().rollback();
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "Mensaje del sistema",
                                    "No se completo la acción,"
                                    + " inténtelo más tarde,Probablemente se intento insertar un usuario que ya existe \n"
                                    + usuario));
            System.out.println("ExepcionAlumno : " + e);
        }
    }

    public void eliminarAlumno() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            session.delete(alumnoListaGrupo);

            tx.commit();
            mensaje.setMessage("Eliminado con éxito");
            mensaje.info();
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se pudo eliminar el usuario");
            mensaje.fatal();
        }

    }

    public List<ListaGrupo> obtenerListaGrupo() {
        ///////////// Este Id se manda desde el metodo redirectAlumnos que esta en el managedbean Unidadgrupo
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        int idUnidadGrupo = (int) sessionMap.get("Id_UnidadGrupo");
        //////////////////////////////////////
        Session session = null;
        Transaction tran = null;
         List<ListaGrupo> lg = new ArrayList<>();
        try{
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tran = session.beginTransaction();
        Query q = session.createQuery("FROM ListaGrupo WHERE unidadGrupo.idUnidadGrupo = :idUnidadGrupo");
        q.setParameter("idUnidadGrupo", idUnidadGrupo);
        lg = q.list();
        tran.commit();
        }catch(HibernateException ex){
            tran.rollback();
        }
        return lg;
    }

    // Getters and Setters
    public ListaGrupo getAlumnoListaGrupo() {
        return alumnoListaGrupo;
    }

    public void setAlumnoListaGrupo(ListaGrupo alumnoListaGrupo) {
        this.alumnoListaGrupo = alumnoListaGrupo;
    }

}
