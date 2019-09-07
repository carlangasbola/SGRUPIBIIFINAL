package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.*;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.*;
import org.dom4j.DocumentException;
import org.hibernate.*;
import org.primefaces.model.DefaultStreamedContent;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Named("SessionLaboratorio")
@RequestScoped

public class ManagedBeanSessionLaboratorio implements Serializable {
    
    private SesionDeLaboratorio sesionLaboratorio = new SesionDeLaboratorio();
    private SesionDeLaboratorio sesionActiva = new SesionDeLaboratorio();
    private int IdUnidadGrupo;
    private String docenteAux;
    private Equipo equipoAlumnos = new Equipo();
    private DefaultStreamedContent descarga;
    
    public List<Equipo> obtenerEquipos(int idUnidadGrupo){
        List<Equipo> listaEquipos = new ArrayList();
        
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            listaEquipos = session.createQuery("FROM Equipo WHERE unidadGrupo.idUnidadGrupo = " +
                                               idUnidadGrupo).list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
        }
        
        return listaEquipos;
    }
    
    public void preparaDescarga() throws Exception {
        imprimePDF();
        String direccion = "Elementos\\Practicas\\Archivos\\" + 
                           equipoAlumnos.getNombre() + ".pdf";
        File archivo = new File(direccion);
        InputStream entrada = new FileInputStream(archivo);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDescarga(new DefaultStreamedContent(entrada, externalContext.getMimeType(archivo.getName()), archivo.getName()));
    }
    
    
    
    public void imprimePDF() throws MalformedURLException, FileNotFoundException, 
                                    DocumentException, IOException, 
                                    com.lowagie.text.DocumentException{
        crea();
        String inputFile = "Elementos\\Practicas\\Creacion\\" + 
                           equipoAlumnos.getNombre() + ".html"; 
        String url = new File(inputFile).toURI().toURL().toString(); 
        String outputFile = "Elementos\\Practicas\\Archivos\\" + 
                            equipoAlumnos.getNombre() + ".pdf";; 
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer(); 
        renderer.setDocument(url); 
        renderer.layout(); 
        renderer.createPDF(os);

        os.close();  
    }
    
    public void crea(){
        try {
            File fileDir = new File("Elementos\\Practicas\\Creacion\\" + 
                                    equipoAlumnos.getNombre() + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));
            out.append(ManagedBeanPractica.LeeArchivo("Elementos\\Practicas\\Creacion\\inicio.txt") + 
                       LlenaContenido() + 
                       ManagedBeanPractica.LeeArchivo("Elementos\\Practicas\\Creacion\\fin.txt"));
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        } 
    }
    
    public String LlenaContenido(){
            String salida;
            salida = "HOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOLA";
            
        return salida;
    }
    
    public String prestamoDisponible(){
        Date fechaActual = new Date();
        List<SesionDeLaboratorio> sesion = new ArrayList();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            tran = session.beginTransaction();
            sesion = session.createQuery("FROM SesionDeLaboratorio WHERE fecha BETWEEN '" + 
                                         fecha.format(modificarDias(fechaActual, -1)) + 
                                         "' AND '" + fecha.format(fechaActual) + "'").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        for(SesionDeLaboratorio item:sesion){
            if(validaFecha(fechaActual, item.getFecha()) == true){
                sesionActiva = item;
                return "PrestamoMaterial.xhtml";
            }
        }
        
        return "PrestamoNoDisponible.xhtml";
    }
    
    public Boolean validaFecha(Date fechaActual, Date fechaLaboratorio){
        if (fechaLaboratorio.before(fechaActual) && fechaActual.before(modificarHoras(fechaLaboratorio, 2))){
            return true;
        }
        
        return false;
    }
    
    public Date modificarHoras(Date fecha, int horas){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        
        return calendar.getTime();
    }
    
    public Date modificarDias(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, dias);
        
        return calendar.getTime();
    }
    
    public List<SesionDeLaboratorio> obtenerSesionesDeLaboratorio(){
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<SesionDeLaboratorio> sesionesLab = new ArrayList<>();

        try {
            tran = session.beginTransaction();
            sesionesLab = session.createQuery("FROM SesionDeLaboratorio").list();
            tran.commit();
        } catch (Exception sqlException) {
            tran.rollback();
        }
        return sesionesLab;
    }

    public void crearSesionLaboratorio() {
        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            
            UnidadGrupo ua = new UnidadGrupo();
            ua = (UnidadGrupo) session.get(UnidadGrupo.class, IdUnidadGrupo);
            sesionLaboratorio.setUnidadGrupo(ua);
            sesionLaboratorio.setDocenteAuxiliar(docenteAux);
            session.save(this.sesionLaboratorio);
            tran.commit();
            mensaje.setMessage("Sesión de laboratorio creada");
            mensaje.info();
        } catch (Exception sqlException) {
            tran.rollback();
            mensaje.setMessage("No se pudo crear la sesión");
            mensaje.error();
        }
    }
    
    public List<SesionDeLaboratorio> ObtenerFechasLaboratorio() {
        Transaction tran = null;
        List<SesionDeLaboratorio> fechasLaboratorio = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            fechasLaboratorio = session.createQuery("FROM SesionDeLaboratorio").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return fechasLaboratorio;
    }
    
    public ManagedBeanSessionLaboratorio() {
    }
    
    public SesionDeLaboratorio getSesionLaboratorio() {
        return sesionLaboratorio;
    }
    
    public void setSesionLaboratorio(SesionDeLaboratorio sesionLaboratorio) {
        this.sesionLaboratorio = sesionLaboratorio;
    }
    
    public int getIdUnidadGrupo() {
        return IdUnidadGrupo;
    }
    
    public void setIdUnidadGrupo(int IdUnidadGrupo) {
        this.IdUnidadGrupo = IdUnidadGrupo;
    }
    
    public String getDocenteAux() {
        return docenteAux;
    }
    
    public void setDocenteAux(String DocenteAux) {
        this.docenteAux = DocenteAux;
    }

    public SesionDeLaboratorio getSesionActiva() {
        prestamoDisponible();
        return sesionActiva;
    }

    public void setSesionActiva(SesionDeLaboratorio sesionActiva) {
        this.sesionActiva = sesionActiva;
    }

    public Equipo getEquipoAlumnos() {
        return equipoAlumnos;
    }

    public void setEquipoAlumnos(Equipo equipoAlumnos) {
        this.equipoAlumnos = equipoAlumnos;
    }
    
    public DefaultStreamedContent getDescarga() {
        return descarga;
    }

    public void setDescarga(DefaultStreamedContent descarga) {
        this.descarga = descarga;
    }
    
}
