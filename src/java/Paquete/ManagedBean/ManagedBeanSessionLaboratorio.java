package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.*;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.*;
import org.dom4j.DocumentException;
import org.hibernate.*;
import org.primefaces.model.DefaultStreamedContent;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Named("SessionLaboratorio")
@SessionScoped
public class ManagedBeanSessionLaboratorio implements Serializable {
    
    private SesionDeLaboratorio sesionLaboratorio = new SesionDeLaboratorio();
    private SesionDeLaboratorio sesionActiva = new SesionDeLaboratorio();
    private List<Reactivos> reactivosPractica = new ArrayList<>();
    private List<Material> materialPractica = new ArrayList<>();
    private List<EquipoLaboratorio> equipoPractica = new ArrayList<>();
    private int IdUnidadGrupo;
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
        String direccion = "Elementos\\PrestamoMaterial\\Archivos\\" + 
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
        String inputFile = "Elementos\\PrestamoMaterial\\Creacion\\" + 
                           equipoAlumnos.getNombre() + ".html"; 
        String url = new File(inputFile).toURI().toURL().toString(); 
        String outputFile = "Elementos\\PrestamoMaterial\\Archivos\\" + 
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
            File fileDir = new File("Elementos\\PrestamoMaterial\\Creacion\\" + 
                                    equipoAlumnos.getNombre() + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));
            out.append(ManagedBeanPractica.LeeArchivo("Elementos\\PrestamoMaterial\\Creacion\\inicioPrestamo.txt") + 
                       llenaContenido() + 
                       ManagedBeanPractica.LeeArchivo("Elementos\\PrestamoMaterial\\Creacion\\finPrestamo.txt"));
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
    
    public String llenaContenido(){
            String salida;
            
            salida = datosPrincipales();
            salida += "<table border=\"1\" style=\"width: 100%;font-size:14px\" cellpadding=\"5\">";
            salida += "<tr style=\"text-align:center;\">";
            salida += "<td width=\"20%\"><strong>TIPO</strong></td>";
            salida += "<td width=\"50%\"><strong>NOMBRE</strong></td>";
            salida += "<td width=\"15%\"><strong>CANTIDAD</strong></td>";
            salida += "<td width=\"15%\"><strong>MEDIDA</strong></td></tr>";
            
            salida += agregaReactivosPDF() + agregaMaterialPDF() + agregaEquipoPDF();
            
        return salida + "</table>";
    }
    
    public String agregaMaterialPDF(){
        String salida = "";
        
        for (Material item:materialPractica){
            salida += "<tr>\n" + "<td>Material</td>\n";
            salida += "<td>" + item.getNombre() + "</td>\n"; 
            salida += "<td>" + item.getCantidad() + "</td>\n";
            salida += "<td>Pieza</td>\n" + "</tr>\n";
        }
        
        return salida;
    }
    
    public String agregaEquipoPDF(){
        String salida = "";
        
        for (EquipoLaboratorio item:equipoPractica){
            salida += "<tr>\n" + "<td>Equipo</td>\n";
            salida += "<td>" + item.getNombre() + "</td>\n"; 
            salida += "<td>" + item.getCantidad() + "</td>\n";
            salida += "<td>Pieza</td>\n" + "</tr>\n";
        }
        
        return salida;
    }
    
    public String agregaReactivosPDF(){
        String salida = "";
        
        for (Reactivos item:reactivosPractica){
            salida += "<tr>\n" + "<td>Reactivo</td>\n";
            salida += "<td>" + item.getNombre() + "</td>\n"; 
            salida += "<td>" + item.getCantidad() + "</td>\n";
            salida += "<td>" + item.getMedida() + "</td>\n" + "</tr>\n";
        }
        
        return salida;
    }
    
    public String datosPrincipales(){
        String salida;
        String inicioTabla = "<tr style=\"text-align:left\"><td width=\"10%\"><strong>";
        String centroTabla = "</strong></td><td width=\"90%\">&nbsp;";
        String finTabla = "</td></tr>";
        Date fechaActual = new Date();
        
        salida = inicioTabla + "GRUPO:" + centroTabla + 
                 sesionActiva.getUnidadGrupo().getGrupo().getNombre() + finTabla;
        
        salida += inicioTabla + "NOMBRE:" + centroTabla + 
                  equipoAlumnos.getNombre() + finTabla;
        
        salida += inicioTabla + "FECHA:" + centroTabla + 
                  new SimpleDateFormat("dd/MM/yyyy").format(fechaActual) + finTabla;
        
        return salida + "</table><br/>";
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
            
            System.out.println("Aquí: Fecha -> " + sesionLaboratorio.getFecha());
            System.out.println("Aquí: idUnidadGrupo -> " + IdUnidadGrupo);
            System.out.println("Aquí: Docente auxiliar -> " + sesionLaboratorio.getDocenteAuxiliar());
            
            UnidadGrupo ua = new UnidadGrupo();
            ua = (UnidadGrupo) session.get(UnidadGrupo.class, IdUnidadGrupo);
            sesionLaboratorio.setUnidadGrupo(ua);
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
    
    public List<UnidadGrupo> obtenerUnidades() {
        Transaction tran = null;
        List<UnidadGrupo> grupos = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            grupos = session.createQuery("FROM UnidadGrupo").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return grupos;
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
    
    public void reactivosPracticas(double cantidad, String nombre) {
        Reactivos reactivo = new Reactivos();
        reactivo.setNombre(nombre);
        reactivo.setCantidad(cantidad);
        
        reactivosPractica.add(reactivo);
    }
    
    public void materialPracticas(Integer cantidad, String nombre) {
        Material material = new Material();
        material.setNombre(nombre);
        material.setCantidad(cantidad);
        
        materialPractica.add(material);
    }
    
    public void equipoPracticas(Integer cantidad, String nombre) {
        EquipoLaboratorio equipo = new EquipoLaboratorio();
        equipo.setNombre(nombre);
        equipo.setCantidad(cantidad);
        
        equipoPractica.add(equipo);
    }
    
    public void eliminarReactivosPracticas(int posicion) {
        reactivosPractica.remove(posicion);
    }
    
    public void eliminarMaterialPracticas(int posicion) {
        materialPractica.remove(posicion);
    }
    
    public void eliminarEquipoPracticas(int posicion) {
        equipoPractica.remove(posicion);
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

    public List<Reactivos> getReactivosPractica() {
        return reactivosPractica;
    }

    public void setReactivosPractica(List<Reactivos> reactivosPractica) {
        this.reactivosPractica = reactivosPractica;
    }

    public List<Material> getMaterialPractica() {
        return materialPractica;
    }

    public void setMaterialPractica(List<Material> materialPractica) {
        this.materialPractica = materialPractica;
    }

    public List<EquipoLaboratorio> getEquipoPractica() {
        return equipoPractica;
    }

    public void setEquipoPractica(List<EquipoLaboratorio> equipoPractica) {
        this.equipoPractica = equipoPractica;
    }
    
}
