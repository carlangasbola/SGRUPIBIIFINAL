package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Residuos;
import Paquete.Pojos.SesionDeLaboratorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

@Named("ResiduosLaboratorio")
@SessionScoped
public class ManagedBeanResiduos implements Serializable {
    private LineChartModel lineModel;
    private HorizontalBarChartModel graficaPrincipalHorizontal;
    private RadarChartModel radarModel;
    private Residuos residuos = new Residuos();
    private int sesion;
    
    @PostConstruct
    public void init() {
        
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
     
    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
         
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Residuos en este semestre");
        dataSet.setBorderColor("rgb(128, 0, 168)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add("Enero");
        labels.add("Febrero");
        labels.add("Marzo");
        labels.add("Abril");
        labels.add("Mayo");
        labels.add("Junio");
        labels.add("Julio");
        data.setLabels(labels);
        
        lineModel.setData(data);
    }
    
    public void createHorizontalBarModel() {
        graficaPrincipalHorizontal = new HorizontalBarChartModel();
        ChartData data = new ChartData();
        List<Residuos> residuos = new ArrayList<>();
        
        residuos = obtenerResiduos();
         
        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("Cantidades");
         
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
        radarModel = new RadarChartModel();
        ChartData data = new ChartData();
        
        RadarChartDataSet radarDataSet1 = new RadarChartDataSet();
        radarDataSet1.setLabel("Julio");
        radarDataSet1.setFill(true);
        radarDataSet1.setBackgroundColor("rgba(8, 168, 17, 0.2)");
        radarDataSet1.setBorderColor("rgb(8, 168, 17)");
        radarDataSet1.setPointBackgroundColor("rgb(8, 168, 17)");
        radarDataSet1.setPointBorderColor("#fff");
        radarDataSet1.setPointHoverBackgroundColor("#fff");
        radarDataSet1.setPointHoverBorderColor("rgb(8, 168, 17)");
        List<Number> dataVal1 = new ArrayList<>();
        dataVal1.add(33);
        dataVal1.add(89);
        dataVal1.add(59);
        dataVal1.add(77);
        dataVal1.add(90);
        dataVal1.add(60);
        dataVal1.add(45);
        dataVal1.add(75);
        dataVal1.add(93);
        radarDataSet1.setData(dataVal1);
         
        RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
        radarDataSet2.setLabel("Agosto");
        radarDataSet2.setFill(true);
        radarDataSet2.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        radarDataSet2.setBorderColor("rgb(255, 99, 132)");
        radarDataSet2.setPointBackgroundColor("rgb(255, 99, 132)");
        radarDataSet2.setPointBorderColor("#fff");
        radarDataSet2.setPointHoverBackgroundColor("#fff");
        radarDataSet2.setPointHoverBorderColor("rgb(255, 99, 132)");
        List<Number> dataVal2 = new ArrayList<>();
        dataVal2.add(65);
        dataVal2.add(59);
        dataVal2.add(90);
        dataVal2.add(81);
        dataVal2.add(56);
        dataVal2.add(55);
        dataVal2.add(40);
        dataVal2.add(64);
        dataVal2.add(80);
        radarDataSet2.setData(dataVal2);
         
        RadarChartDataSet radarDataSet3 = new RadarChartDataSet();
        radarDataSet3.setLabel("Septiembre");
        radarDataSet3.setFill(true);
        radarDataSet3.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        radarDataSet3.setBorderColor("rgb(54, 162, 235)");
        radarDataSet3.setPointBackgroundColor("rgb(54, 162, 235)");
        radarDataSet3.setPointBorderColor("#fff");
        radarDataSet3.setPointHoverBackgroundColor("#fff");
        radarDataSet3.setPointHoverBorderColor("rgb(54, 162, 235)");
        List<Number> dataVal3 = new ArrayList<>();
        dataVal3.add(28);
        dataVal3.add(48);
        dataVal3.add(40);
        dataVal3.add(19);
        dataVal3.add(96);
        dataVal3.add(27);
        dataVal3.add(100);
        dataVal3.add(13);
        dataVal3.add(54);
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
    
    public int cantidadResiduos(List<Residuos> listaResiduos, String tipo) {
        int cantidad = 0;
        
        for(Residuos item:listaResiduos){
            if(item.getTipo().equals(tipo)){
                cantidad++;
            }
        }
        
        return cantidad;
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
    
}
