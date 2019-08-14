package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.*;
import javax.inject.Named;
import org.dom4j.DocumentException;
import org.hibernate.*;
import org.primefaces.model.DefaultStreamedContent;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Named("ListaPracticas")
@SessionScoped
public class ManagedBeanPractica implements Serializable {
    
    private List<Practica> listaPracticas = new ArrayList<>();
    private Practica practica = new Practica();
    private Integer idUnidadTematica;
    private DatosPractica datosPractica = new DatosPractica();
    private List<String> listaAutores = new ArrayList<>();
    private List<Reactivos> reactivosPractica = new ArrayList<>();
    private List<Material> materialPractica = new ArrayList<>();
    private List<EquipoLaboratorio> equipoPractica = new ArrayList<>();
    private DefaultStreamedContent descarga;
    
    @PostConstruct
    private void init() {
        ObtenerPracticas();
    }
    
    public String obtenerDatosPractica(int id){
        List<Practica> practicaAux = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            practicaAux = session.createQuery("FROM Practica WHERE idPractica = " +
                                              id).list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        practica = practicaAux.get(0);
        
        return "DatosPractica.xhtml";
    }
    
    public void insertarPracticaCompleta() {
        UnidadTematica unidadTematica = recuperaUnidad(idUnidadTematica);
        Usuarios usuario = recuperaUsuario(128);
        Date fecha = new Date();
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        practica.setUnidadTematica(unidadTematica);
        practica.setUsuarios(usuario);
        practica.setCreacion(fecha);
        insertarPractica(practica);
        datosPractica.setPractica(practica);
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.save(datosPractica);
            tx.commit();
            
            mensaje.setMessage("Practica agregado");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el material, consulte el log "
                                + "para mas detalles");
            mensaje.fatal();
            throw e;
        }
        
    }
    
    public void insertarPractica(Practica practica) {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.save(practica);
            tx.commit();
            
            mensaje.setMessage("Practica agregada");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el material, consulte el log "
                                + "para mas detalles");
            mensaje.fatal();
            throw e;
        }
        
    }
    
    public Usuarios recuperaUsuario(Integer id) {
        List<Usuarios> usuario = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            usuario = session.createQuery("FROM Usuarios WHERE idUsuarios = " + 
                                          id).list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return usuario.get(0);
    }
    
    public UnidadTematica recuperaUnidad(Integer id) {
        List<UnidadTematica> unidades = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            unidades = session.createQuery("FROM UnidadTematica WHERE idUnidadTematica = " 
                                           + id).list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return unidades.get(0);
    }
    
    public List<UnidadTematica> unidadesTematicas() {
        List<UnidadTematica> unidades = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            unidades = session.createQuery("FROM UnidadTematica").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return unidades;
    }
    
    public void eliminarPractica(int idPractica) {
        Mensajes mensaje = new Mensajes();
        Transaction transObj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transObj = session.beginTransaction();
            session.delete((Practica) session.get(Practica.class, idPractica));
            transObj.commit();
            mensaje.setMessage("Práctica eliminada del sistema");
            mensaje.info();
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
    }
    
    public void eliminarEquipoPracticas(int posicion) {
        equipoPractica.remove(posicion);
    }
    
    public void equipoPracticas(Integer cantidad, String nombre) {
        EquipoLaboratorio equipo = new EquipoLaboratorio();
        equipo.setNombre(nombre);
        equipo.setCantidad(cantidad);
        
        equipoPractica.add(equipo);
    }
    
    public void eliminarMaterialPracticas(int posicion) {
        materialPractica.remove(posicion);
    }
    
    public void materialPracticas(Integer cantidad, String nombre) {
        Material material = new Material();
        material.setNombre(nombre);
        material.setCantidad(cantidad);
        
        materialPractica.add(material);
    }
    
    public void eliminarReactivosPracticas(int posicion) {
        reactivosPractica.remove(posicion);
    }
    
    public void reactivosPracticas(double cantidad, String nombre) {
        Reactivos reactivo = new Reactivos();
        reactivo.setNombre(nombre);
        reactivo.setCantidad(cantidad);
        
        reactivosPractica.add(reactivo);
    }
    
    public List<Practica> ObtenerPracticas(){
        
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            listaPracticas = session.createQuery("FROM Practica").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return listaPracticas;
    }
    
    public String fechaPractica(String fecha) {
        return fecha.substring(0, 10);
    }
    
    public String nivelRiesgo(String semaforo){
        if(semaforo.equals("Riesgo alto")){
            return "alert alert-danger";
        }
        else if(semaforo.equals("Riesgo medio")){
            return "alert alert-warning";
        }
        else{
            return "alert alert-success";
        }
    }
    
    public String textoSemaforo(String nivel){
        if(nivel.equals("Riesgo alto")){
            return "skillbar alto";
        }
        else if(nivel.equals("Riesgo medio")){
            return "skillbar medio";
        }
        else{
            return "skillbar bajo";
        }
    }
    
    public String nivelSemaforo(String nivel){
        if(nivel.equals("Riesgo alto")){
            return "ALTO";
        }
        else if(nivel.equals("Riesgo medio")){
            return "MEDIO";
        }
        else{
            return "BAJO";
        }
    }
    
    public String porcentajeSemaforo(String nivel){
        if(nivel.equals("Riesgo alto")){
            return "90%";
        }
        else if(nivel.equals("Riesgo medio")){
            return "50%";
        }
        else{
            return "25%";
        }
    }
    
    public void agregarAutor(String nuevoAutor){
        listaAutores.add(nuevoAutor);
    }
    
    public void eliminarAutor(int posicion){
        listaAutores.remove(posicion);
    }

    public List<Practica> getListaPracticas() {
        if(listaPracticas == null) {
            ObtenerPracticas();
        }
        return listaPracticas;
    }
    
    public void preparaDescarga() throws Exception {
        imprimePDF();
        String direccion = "Elementos\\Practicas\\Archivos\\" + 
                           datosPractica.getNombre() + ".pdf";
        File archivo = new File(direccion);
        InputStream entrada = new FileInputStream(archivo);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDescarga(new DefaultStreamedContent(entrada, externalContext.getMimeType(archivo.getName()), archivo.getName()));
    }
    
    public void buscaDescarga() throws Exception {
        String direccion = "Elementos\\Practicas\\Archivos\\" + practica.getDatosPractica().getNombre() + ".pdf";
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
                           datosPractica.getNombre() + ".html"; 
        String url = new File(inputFile).toURI().toURL().toString(); 
        String outputFile = "Elementos\\Practicas\\Archivos\\" + 
                            datosPractica.getNombre() + ".pdf";; 
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
                                    datosPractica.getNombre() + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));
            out.append(LeeArchivo("Elementos\\Practicas\\Creacion\\inicio.txt") + 
                       LlenaContenido() + 
                       LeeArchivo("Elementos\\Practicas\\Creacion\\fin.txt"));
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
    
    public String LeeArchivo(String archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
    public String estructura(String titulo, String contenido){
        return "<br/><br/><left><span style=\"font-size:16px\"><strong>" + 
               titulo + "</strong></span></left><br/><br/>" + contenido;
    }
    
    public String LlenaContenido(){
            String salida;
            //Requisitos
            salida = "<br/><center><span style=\"font-size:16px\"><strong>" + 
                     datosPractica.getNombre() + "</strong></span></center><br/><br/>";
            salida += estructura("OBJETIVOS", datosPractica.getObjetivos());
            salida += estructura("ACTIVIDADES PREVIAS", datosPractica.getActividadesPrevias());
            salida += estructura("COMPETENCIAS", datosPractica.getCompetencias());
            salida += estructura("INTRODUCCIÓN", datosPractica.getIntroducion());
            //Selección de material, equipo y reactivo
            //Desarrollo de la práctica
            salida += estructura("DESARROLLO EXPERIMENTAL", datosPractica.getDesarrollo());
            salida += estructura("REGISTRO DE DATOS", datosPractica.getRegistroDatos());
            salida += estructura("RESULTADOS", datosPractica.getResultados());
            salida += estructura("ANÁLISIS DE RESULTADOS", datosPractica.getAnalisisResultados());
            //Desarrollo de la práctica
            salida += estructura("REFERENCIAS", datosPractica.getReferencias());
            salida += estructura("NOMENCLATURA", datosPractica.getNomeclantura());
            salida += estructura("ANEXOS", datosPractica.getAnexos());
            salida += estructura("RECOMENDACIONES", datosPractica.getRecomendaciones());
            salida += estructura("PROTOCOLOS", datosPractica.getProtocolos());
            
        return salida;
    }

    public Practica getPractica() {
        return practica;
    }

    public void setPractica(Practica practica) {
        this.practica = practica;
    }

    public Integer getIdUnidadTematica() {
        return idUnidadTematica;
    }

    public void setIdUnidadTematica(Integer idUnidadTematica) {
        this.idUnidadTematica = idUnidadTematica;
    }

    public DatosPractica getDatosPractica() {
        return datosPractica;
    }

    public void setDatosPractica(DatosPractica datosPractica) {
        this.datosPractica = datosPractica;
    }

    public List<String> getListaAutores() {
        return listaAutores;
    }

    public void setListaAutores(List<String> listaAutores) {
        this.listaAutores = listaAutores;
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

    public DefaultStreamedContent getDescarga() {
        return descarga;
    }

    public void setDescarga(DefaultStreamedContent descarga) {
        this.descarga = descarga;
    }

}
