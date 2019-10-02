package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Residuos;
import Paquete.Pojos.SesionDeLaboratorio;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.dom4j.DocumentException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.optionconfig.elements.Elements;
import org.primefaces.model.charts.optionconfig.elements.ElementsLine;
import org.primefaces.model.charts.radar.RadarChartDataSet;
import org.primefaces.model.charts.radar.RadarChartModel;
import org.primefaces.model.charts.radar.RadarChartOptions;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Named("ResiduosLaboratorio")
@SessionScoped
public class ManagedBeanResiduos implements Serializable {
    private LineChartModel lineModel;
    private HorizontalBarChartModel graficaPrincipalHorizontal;
    private RadarChartModel radarModel;
    private Residuos residuos = new Residuos();
    private int sesion;
    private List<Residuos> todosLosRegistros = new ArrayList<>();
    private DefaultStreamedContent descarga;
    
    @PostConstruct
    public void init() {
        
    }
    
    public String redireccionaResiduo(String residuo){
        obtenerResiduosNombre(residuo);
        
        return "RegistroDeResiduo.xhtml";
    }
    
    public List<SesionDeLaboratorio> obteneSesionesRecientes(){
        List<SesionDeLaboratorio> sesiones = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            sesiones = session.createQuery("FROM SesionDeLaboratorio").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return sesiones;
    }
    
    public void InsertResiduos() {
        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            SesionDeLaboratorio s = new SesionDeLaboratorio();
            Date fechaActual = new Date();
            tran = session.beginTransaction();
            
            s = (SesionDeLaboratorio) session.get(SesionDeLaboratorio.class, sesion);
            residuos.setFechaDeIngreso(fechaActual);
            residuos.setSesionDeLaboratorio(s);
            
            session.save(this.residuos);
            tran.commit();
            mensaje.setMessage("Residuo agregado");
            mensaje.info();
        } catch (Exception sqlException) {
            tran.rollback();
            mensaje.setMessage("No se pudo crear agregar el residuo " + sqlException);
            mensaje.error();
        }
    }
    
    public List<Residuos> obtenerResiduos(){
        List<Residuos> residuos = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            residuos = session.createQuery("FROM Residuos").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return residuos;
    }
    
    public List<Residuos> obtenerResiduosNombre(String residuo){
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            if ("Todos".equals(residuo)){
                todosLosRegistros = session.createQuery("FROM Residuos").list();
            }
            else {
                todosLosRegistros = session.createQuery("FROM Residuos WHERE tipo = '" + residuo + "'").list();
            }
            
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return todosLosRegistros;
    }
    
    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
        int mesActual = Calendar.getInstance().get(Calendar.MONTH);
        int aux = 0;
         
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Registro de residuos en este semestre");
        dataSet.setBorderColor("rgb(128, 0, 168)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
         
        List<String> labels = new ArrayList<>();
        if (mesActual < 7 ){
            aux = mesActual - 12;
            labels.add("Enero");
            labels.add("Febrero");
            labels.add("Marzo");
            labels.add("Abril");
            labels.add("Mayo");
            labels.add("Junio");
        }
        else {
            aux = mesActual - 12;
            labels.add("Julio");
            labels.add("Agosto");
            labels.add("Septiembre");
            labels.add("Octubre");
            labels.add("Noviembre");
            labels.add("Diciembre");
        }
        
        for (int i = 0; i < 6; i++){
            values.add(residuosMes(aux).size());
            aux++;
        }
        
        data.setLabels(labels);
        
        lineModel.setData(data);
    }
    
    public void createHorizontalBarModel() {
        graficaPrincipalHorizontal = new HorizontalBarChartModel();
        ChartData data = new ChartData();
        List<Residuos> residuos = new ArrayList<>();
        
        residuos = obtenerResiduos();
         
        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("Registros");
         
        List<Number> values = new ArrayList<>();
        values.add(cantidadResiduos(residuos, "Explosivo"));
        values.add(cantidadResiduos(residuos, "Inflamable"));
        values.add(cantidadResiduos(residuos, "Gas a presión"));
        values.add(cantidadResiduos(residuos, "Sustancia comburente"));
        values.add(cantidadResiduos(residuos, "Sustancia perjudicial para la salud"));
        values.add(cantidadResiduos(residuos, "Sustancia corrosiva"));
        values.add(cantidadResiduos(residuos, "Sustancia nosiva"));
        values.add(cantidadResiduos(residuos, "Sustancia tóxica"));
        values.add(cantidadResiduos(residuos, "Sustancia peligrosas para el medio ambiente"));
        hbarDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(166, 19, 8, 0.2)");
        bgColor.add("rgba(62, 149, 205, 0.2)");
        bgColor.add("rgba(166, 134, 8, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(8, 168, 17, 0.2)");
        bgColor.add("rgba(0, 168, 151, 0.2)");
        bgColor.add("rgba(0, 92, 168, 0.2)");
        bgColor.add("rgba(172, 174, 176, 0.2)");
        bgColor.add("rgba(128, 0, 168, 0.2)");
        hbarDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(166, 19, 8)");
        borderColor.add("rgb(62, 149, 205)");
        borderColor.add("rgb(166, 134, 8)");
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(8, 168, 17)");
        borderColor.add("rgb(0, 168, 151)");
        borderColor.add("rgb(0, 92, 168)");
        borderColor.add("rgb(172, 174, 176)");
        borderColor.add("rgb(128, 0, 168)");
        hbarDataSet.setBorderColor(borderColor);
        hbarDataSet.setBorderWidth(1);
         
        data.addChartDataSet(hbarDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add("Explosivos");
        labels.add("Inflamables");
        labels.add("Gases a presión");
        labels.add("Comburentes");
        labels.add("Perjudiciales");
        labels.add("Corrosivas");
        labels.add("Nosivas");
        labels.add("Tóxicas");
        labels.add("Peligrosas");
        data.setLabels(labels);
        graficaPrincipalHorizontal.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);
         
        graficaPrincipalHorizontal.setOptions(options);
    }
    
    public void createRadarModel() {
        int r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0, r7 = 0, r8 = 0, r9 = 0;
        radarModel = new RadarChartModel();
        ChartData data = new ChartData();
        
        RadarChartDataSet radarDataSet1 = new RadarChartDataSet();
        radarDataSet1.setLabel(numeroAMes(Calendar.getInstance().get(Calendar.MONTH) - 2));
        radarDataSet1.setFill(true);
        radarDataSet1.setBackgroundColor("rgba(8, 168, 17, 0.2)");
        radarDataSet1.setBorderColor("rgb(8, 168, 17)");
        radarDataSet1.setPointBackgroundColor("rgb(8, 168, 17)");
        radarDataSet1.setPointBorderColor("#fff");
        radarDataSet1.setPointHoverBackgroundColor("#fff");
        radarDataSet1.setPointHoverBorderColor("rgb(8, 168, 17)");
        List<Number> dataVal1 = new ArrayList<>();
        for (Residuos item:residuosMes(-2)){
            if ("Explosivo".equals(item.getTipo())){ r1++;}
            else if("Inflamable".equals(item.getTipo())){ r2++;}
            else if("Gas a presión".equals(item.getTipo())){ r3++;}
            else if("Sustancia comburente".equals(item.getTipo())){ r4++;}
            else if("Sustancia perjudicial para la salud".equals(item.getTipo())){ r5++;}
            else if("Sustancia corrosiva".equals(item.getTipo())){ r6++;}
            else if("Sustancia nosiva".equals(item.getTipo())){ r7++;}
            else if("Sustancia tóxica".equals(item.getTipo())){ r8++;}
            else { r9++;}
        }
        dataVal1.add(r1);
        dataVal1.add(r2);
        dataVal1.add(r3);
        dataVal1.add(r4);
        dataVal1.add(r5);
        dataVal1.add(r6);
        dataVal1.add(r7);
        dataVal1.add(r8);
        dataVal1.add(r9);
        radarDataSet1.setData(dataVal1);
         
        RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
        radarDataSet2.setLabel(numeroAMes(Calendar.getInstance().get(Calendar.MONTH) - 1));
        radarDataSet2.setFill(true);
        radarDataSet2.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        radarDataSet2.setBorderColor("rgb(255, 99, 132)");
        radarDataSet2.setPointBackgroundColor("rgb(255, 99, 132)");
        radarDataSet2.setPointBorderColor("#fff");
        radarDataSet2.setPointHoverBackgroundColor("#fff");
        radarDataSet2.setPointHoverBorderColor("rgb(255, 99, 132)");
        List<Number> dataVal2 = new ArrayList<>();
        r1 = 0; r2 = 0; r3 = 0; r4 = 0; r5 = 0; r6 = 0; r7 = 0; r8 = 0; r9 = 0;
        for (Residuos item:residuosMes(-1)){
            if ("Explosivo".equals(item.getTipo())){ r1++;}
            else if("Inflamable".equals(item.getTipo())){ r2++;}
            else if("Gas a presión".equals(item.getTipo())){ r3++;}
            else if("Sustancia comburente".equals(item.getTipo())){ r4++;}
            else if("Sustancia perjudicial para la salud".equals(item.getTipo())){ r5++;}
            else if("Sustancia corrosiva".equals(item.getTipo())){ r6++;}
            else if("Sustancia nosiva".equals(item.getTipo())){ r7++;}
            else if("Sustancia tóxica".equals(item.getTipo())){ r8++;}
            else { r9++;}
        }
        dataVal2.add(r1);
        dataVal2.add(r2);
        dataVal2.add(r3);
        dataVal2.add(r4);
        dataVal2.add(r5);
        dataVal2.add(r6);
        dataVal2.add(r7);
        dataVal2.add(r8);
        dataVal2.add(r9);
        radarDataSet2.setData(dataVal2);
        
        RadarChartDataSet radarDataSet3 = new RadarChartDataSet();
        radarDataSet3.setLabel(numeroAMes(Calendar.getInstance().get(Calendar.MONTH)));
        radarDataSet3.setFill(true);
        radarDataSet3.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        radarDataSet3.setBorderColor("rgb(54, 162, 235)");
        radarDataSet3.setPointBackgroundColor("rgb(54, 162, 235)");
        radarDataSet3.setPointBorderColor("#fff");
        radarDataSet3.setPointHoverBackgroundColor("#fff");
        radarDataSet3.setPointHoverBorderColor("rgb(54, 162, 235)");
        List<Number> dataVal3 = new ArrayList<>();
        r1 = 0; r2 = 0; r3 = 0; r4 = 0; r5 = 0; r6 = 0; r7 = 0; r8 = 0; r9 = 0;
        for (Residuos item:residuosMes(0)){
            if ("Explosivo".equals(item.getTipo())){ r1++;}
            else if("Inflamable".equals(item.getTipo())){ r2++;}
            else if("Gas a presión".equals(item.getTipo())){ r3++;}
            else if("Sustancia comburente".equals(item.getTipo())){ r4++;}
            else if("Sustancia perjudicial para la salud".equals(item.getTipo())){ r5++;}
            else if("Sustancia corrosiva".equals(item.getTipo())){ r6++;}
            else if("Sustancia nosiva".equals(item.getTipo())){ r7++;}
            else if("Sustancia tóxica".equals(item.getTipo())){ r8++;}
            else { r9++;}
        }
        dataVal3.add(r1);
        dataVal3.add(r2);
        dataVal3.add(r3);
        dataVal3.add(r4);
        dataVal3.add(r5);
        dataVal3.add(r6);
        dataVal3.add(r7);
        dataVal3.add(r8);
        dataVal3.add(r9);
        radarDataSet3.setData(dataVal3);
        
        data.addChartDataSet(radarDataSet3);
        data.addChartDataSet(radarDataSet2);
        data.addChartDataSet(radarDataSet1);
         
        List<String> labels = new ArrayList<>();
        labels.add("Explosivos");
        labels.add("Inflamables");
        labels.add("Gases a presión");
        labels.add("Comburentes");
        labels.add("Perjudiciales");
        labels.add("Corrosivas");
        labels.add("Nosivas");
        labels.add("Tóxicas");
        labels.add("Peligrosas");
        data.setLabels(labels);
         
        /* Options */
        RadarChartOptions options = new RadarChartOptions();
        Elements elements = new Elements();
        ElementsLine elementsLine = new ElementsLine();
        elementsLine.setTension(0);
        elementsLine.setBorderWidth(3);
        elements.setLine(elementsLine);
        options.setElements(elements);
         
        radarModel.setOptions(options);
        radarModel.setData(data);
    }
    
    public List<Residuos> residuosMes(int mes){
        Date fechaActual = new Date();
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int horas = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutos = Calendar.getInstance().get(Calendar.MINUTE);
        
        fechaActual = modificarHoras(modificarDias(modificarMeses(fechaActual, mes), -(dia - 1)), -horas);
        fechaActual = modificarMinutos(fechaActual, -minutos);
        Date fechaFin = calculaDiaFinal(fechaActual);
        List<Residuos> residuos = new ArrayList();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            tran = session.beginTransaction();
            residuos = session.createQuery("FROM Residuos WHERE fechaDeIngreso BETWEEN '" + 
                                           fecha.format(fechaActual) + "' AND '" + fecha.format(fechaFin) + 
                                           "'").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        return residuos;
    }
    
    public String numeroAMes(int numeroMes){
        if (numeroMes == 1) { return "Enero"; }
        else if (numeroMes == 2) { return "Febrero"; }
        else if (numeroMes == 3) { return "Marzo"; }
        else if (numeroMes == 4) { return "Abril"; }
        else if (numeroMes == 5) { return "Mayo"; }
        else if (numeroMes == 6) { return "Junio"; }
        else if (numeroMes == 7) { return "Julio"; }
        else if (numeroMes == 8) { return "Agosto"; }
        else if (numeroMes == 9) { return "Septiembre"; }
        else if (numeroMes == 10) { return "Octubre"; }
        else if (numeroMes == 11) { return "Noviembre"; }
        else { return "Diciembre"; }
    }
    
    public Date modificarMinutos(Date fecha, int minutos){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        
        return calendar.getTime();
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
    
    public Date modificarMeses(Date fecha, int meses){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        
        return calendar.getTime();
    }
    
    public Date calculaDiaFinal(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        
        int mes = calendar.get(Calendar.MONTH) + 1;
        int horas = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        int minutoActual = calendar.get(Calendar.MINUTE);
        int diasMes;
        
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
            diasMes = 31;
        }
        else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            diasMes = 30;
        }
        else{
            diasMes = 28;
        }
        
        calendar.add(Calendar.DATE, diasMes - diaActual);
        calendar.add(Calendar.HOUR_OF_DAY, + (24 - horas));
        calendar.add(Calendar.MINUTE, + (59 - minutoActual));
        
        return calendar.getTime();
    }
    
    public int cantidadResiduos(List<Residuos> listaResiduos, String tipo) {
        int cantidad = 0;
        
        for(Residuos item:listaResiduos){
            if(item.getTipo().equals(tipo)){
                cantidad++;
            }
        }
        
        return cantidad;
    }
    
    public void preparaDescarga() throws Exception {
        imprimePDF();
        String direccion = "Elementos\\ReporteResiduos\\Archivos\\" + 
                           "Reporte general de residuos" + ".pdf";
        File archivo = new File(direccion);
        InputStream entrada = new FileInputStream(archivo);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDescarga(new DefaultStreamedContent(entrada, externalContext.getMimeType(archivo.getName()), archivo.getName()));
        
    }
    
    public void imprimePDF() throws MalformedURLException, FileNotFoundException, 
                                    DocumentException, IOException, 
                                    com.lowagie.text.DocumentException{
        crea();
        String inputFile = "Elementos\\ReporteResiduos\\Creacion\\" + 
                           "Reporte general de residuos" + ".html"; 
        String url = new File(inputFile).toURI().toURL().toString(); 
        String outputFile = "Elementos\\ReporteResiduos\\Archivos\\" + 
                            "Reporte general de residuos" + ".pdf";; 
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer(); 
        renderer.setDocument(url); 
        renderer.layout(); 
        renderer.createPDF(os);

        os.close();
    }
    
    public void crea(){
        try {
            File fileDir = new File("Elementos\\ReporteResiduos\\Creacion\\" + 
                                    "Reporte general de residuos" + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));
            out.append(ManagedBeanPractica.LeeArchivo("Elementos\\ReporteResiduos\\Creacion\\inicioReporte.txt") + 
                       llenaContenido() + 
                       ManagedBeanPractica.LeeArchivo("Elementos\\ReporteResiduos\\Creacion\\finReporte.txt"));
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
            salida += "<td width=\"25%\"><strong>NOMBRE</strong></td>";
            salida += "<td width=\"39%\"><strong>TIPO</strong></td>";
            salida += "<td width=\"15%\"><strong>CANTIDAD</strong></td>";
            salida += "<td width=\"21%\"><strong>FECHA</strong></td></tr>";
            
            salida += agregaResiduosPDF();
            
        return salida + "</table>";
    }
    
    public String agregaResiduosPDF(){
        String salida = "";
                
        for (Residuos item:obtenerResiduos()){
            salida += "<tr>\n <td>" + item.getNombre() + "</td>\n";
            salida += "<td>" + item.getTipo() + "</td>\n"; 
            salida += "<td>" + item.getCantidad() + "</td>\n";
            salida += "<td>" + item.getFechaDeIngreso().toString().substring(0, 19) + "</td>\n</tr>\n";
        }
        
        return salida;
    }
    
    public String datosPrincipales(){
        String salida;
        String inicioTabla = "<tr style=\"text-align:left\"><td width=\"10%\"><strong>";
        String centroTabla = "</strong></td><td width=\"90%\">&nbsp;";
        String finTabla = "</td></tr>";
        Date fechaActual = new Date();
        
        salida = inicioTabla + "TIPO:" + centroTabla + "Reporte general de residuos" + finTabla;
        salida += inicioTabla + "FECHA:" + centroTabla + 
                  new SimpleDateFormat("dd/MM/yyyy").format(fechaActual) + finTabla;
        
        return salida + "</table><br/>";
    }

    public LineChartModel getLineModel() {
        createLineModel();
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public HorizontalBarChartModel getGraficaPrincipalHorizontal() {
        createHorizontalBarModel();
        return graficaPrincipalHorizontal;
    }

    public void setGraficaPrincipalHorizontal(HorizontalBarChartModel graficaPrincipalHorizontal) {
        this.graficaPrincipalHorizontal = graficaPrincipalHorizontal;
    }

    public RadarChartModel getRadarModel() {
        createRadarModel();
        return radarModel;
    }

    public void setRadarModel(RadarChartModel radarModel) {
        this.radarModel = radarModel;
    }

    public Residuos getResiduos() {
        return residuos;
    }

    public void setResiduos(Residuos residuos) {
        this.residuos = residuos;
    }

    public int getSesion() {
        return sesion;
    }

    public void setSesion(int sesion) {
        this.sesion = sesion;
    }

    public List<Residuos> getTodosLosRegistros() {
        return todosLosRegistros;
    }

    public void setTodosLosRegistros(List<Residuos> todosLosRegistros) {
        this.todosLosRegistros = todosLosRegistros;
    }

    public DefaultStreamedContent getDescarga() {
        return descarga;
    }

    public void setDescarga(DefaultStreamedContent descarga) {
        this.descarga = descarga;
    }
    
}
