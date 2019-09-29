package Paquete.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.axes.radial.RadialScales;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearAngleLines;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearPointLabels;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.bubble.BubbleChartDataSet;
import org.primefaces.model.charts.bubble.BubbleChartModel;
import org.primefaces.model.charts.bubble.BubblePoint;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.elements.Elements;
import org.primefaces.model.charts.optionconfig.elements.ElementsLine;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;
import org.primefaces.model.charts.radar.RadarChartDataSet;
import org.primefaces.model.charts.radar.RadarChartModel;
import org.primefaces.model.charts.radar.RadarChartOptions;

@Named("Residuos")
@SessionScoped
public class ManagedBeanResiduos implements Serializable {
    private PieChartModel pieModel;
    private PolarAreaChartModel polarAreaModel;
    private LineChartModel lineModel;
    private LineChartModel cartesianLinerModel;
    private BarChartModel graficaTodosLosResiduos;
    private BarChartModel barModel2;
    private HorizontalBarChartModel graficaPrincipalHorizontal;
    private BarChartModel stackedBarModel;
    private BarChartModel stackedGroupBarModel;
    private RadarChartModel radarModel;
    private RadarChartModel radarModel2;
    private BubbleChartModel bubbleModel;
    private BarChartModel mixedModel;
    private DonutChartModel donutModel;
 
    @PostConstruct
    public void init() {
        createPieModel();
        createPolarAreaModel();
        createLineModel();
        createCartesianLinerModel();
        createBarModel();
        createBarModel2();
        createHorizontalBarModel();
        createStackedBarModel();
        createStackedGroupBarModel();
        createRadarModel();
        createRadarModel2();
        createBubbleModel();
        createMixedModel();
        createDonutModel();
    }
     
    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(300);
        values.add(50);
        values.add(100);
        dataSet.setData(values);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Red");
        labels.add("Blue");
        labels.add("Yellow");
        data.setLabels(labels);
         
        pieModel.setData(data);
    }
     
    private void createPolarAreaModel() {
        polarAreaModel = new PolarAreaChartModel();
        ChartData data = new ChartData();
         
        PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(11);
        values.add(16);
        values.add(7);
        values.add(3);
        values.add(14);
        dataSet.setData(values);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(201, 203, 207)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Red");
        labels.add("Green");
        labels.add("Yellow");
        labels.add("Grey");
        labels.add("Blue");
        data.setLabels(labels);
         
        polarAreaModel.setData(data);
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
     
    public void createCartesianLinerModel() {
        cartesianLinerModel = new LineChartModel();
        ChartData data = new ChartData();
         
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(20);
        values.add(50);
        values.add(100);
        values.add(75);
        values.add(25);
        values.add(0);
        dataSet.setData(values);
        dataSet.setLabel("Left Dataset");
        dataSet.setYaxisID("left-y-axis");
         
        LineChartDataSet dataSet2 = new LineChartDataSet();
        List<Number> values2 = new ArrayList<>();
        values2.add(0.1);
        values2.add(0.5);
        values2.add(1.0);
        values2.add(2.0);
        values2.add(1.5);
        values2.add(0);
        dataSet2.setData(values2);
        dataSet2.setLabel("Right Dataset");
        dataSet2.setYaxisID("right-y-axis");
         
        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);
         
        List<String> labels = new ArrayList<>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        data.setLabels(labels);
        cartesianLinerModel.setData(data);
         
        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");
        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("right-y-axis");
        linearAxes2.setPosition("right");
         
        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);
        options.setScales(cScales);    
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Cartesian Linear Chart");
        options.setTitle(title);
         
        cartesianLinerModel.setOptions(options);
    }
     
    public void createBarModel() {
        graficaTodosLosResiduos = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Residuos");
         
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        values.add(10);
        values.add(73);
        barDataSet.setData(values);
         
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

        barDataSet.setBackgroundColor(bgColor);
         
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
        
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
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
        graficaTodosLosResiduos.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
 
        graficaTodosLosResiduos.setOptions(options);
    }
     
    public void createBarModel2() {
        barModel2 = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("My First Dataset");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        barDataSet.setData(values);
         
        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("My Second Dataset");
        barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
        barDataSet2.setBorderColor("rgb(255, 159, 64)");
        barDataSet2.setBorderWidth(1);
        List<Number> values2 = new ArrayList<>();
        values2.add(85);
        values2.add(69);
        values2.add(20);
        values2.add(51);
        values2.add(76);
        values2.add(75);
        values2.add(10);
        barDataSet2.setData(values2);
 
        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
        barModel2.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);
         
        barModel2.setOptions(options);
    }
     
    public void createHorizontalBarModel() {
        graficaPrincipalHorizontal = new HorizontalBarChartModel();
        ChartData data = new ChartData();
         
        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("Cantidades");
         
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        values.add(10);
        values.add(73);
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
     
    public void createStackedBarModel() {
        stackedBarModel = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Dataset 1");
        barDataSet.setBackgroundColor("rgb(255, 99, 132)");
        List<Number> dataVal = new ArrayList<>();
        dataVal.add(62);
        dataVal.add(-58);
        dataVal.add(-49);
        dataVal.add(25);
        dataVal.add(4);
        dataVal.add(77);
        dataVal.add(-41);
        barDataSet.setData(dataVal);
         
        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("Dataset 2");
        barDataSet2.setBackgroundColor("rgb(54, 162, 235)");
        List<Number> dataVal2 = new ArrayList<>();
        dataVal2.add(-1);
        dataVal2.add(32);
        dataVal2.add(-52);
        dataVal2.add(11);
        dataVal2.add(97);
        dataVal2.add(76);
        dataVal2.add(-78);
        barDataSet2.setData(dataVal2);
         
        BarChartDataSet barDataSet3 = new BarChartDataSet();
        barDataSet3.setLabel("Dataset 3");
        barDataSet3.setBackgroundColor("rgb(75, 192, 192)");
        List<Number> dataVal3 = new ArrayList<>();
        dataVal3.add(-44);
        dataVal3.add(25);
        dataVal3.add(15);
        dataVal3.add(92);
        dataVal3.add(80);
        dataVal3.add(-25);
        dataVal3.add(-11);
        barDataSet3.setData(dataVal3);
         
        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
        data.addChartDataSet(barDataSet3);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
        stackedBarModel.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setStacked(true);    
        cScales.addXAxesData(linearAxes);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart - Stacked");
        options.setTitle(title);
         
        Tooltip tooltip = new Tooltip();
        tooltip.setMode("index");
        tooltip.setIntersect(false);
        options.setTooltip(tooltip);  
         
        stackedBarModel.setOptions(options);
    }
     
    public void createStackedGroupBarModel() {
        stackedGroupBarModel = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Dataset 1");
        barDataSet.setBackgroundColor("rgb(255, 99, 132)");
        barDataSet.setStack("Stack 0");
        List<Number> dataVal = new ArrayList<>();
        dataVal.add(-32);
        dataVal.add(-70);
        dataVal.add(-33);
        dataVal.add(30);
        dataVal.add(-49);
        dataVal.add(23);
        dataVal.add(-92);
        barDataSet.setData(dataVal);
         
        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("Dataset 2");
        barDataSet2.setBackgroundColor("rgb(54, 162, 235)");
        barDataSet2.setStack("Stack 0");
        List<Number> dataVal2 = new ArrayList<>();
        dataVal2.add(83);
        dataVal2.add(18);
        dataVal2.add(86);
        dataVal2.add(8);
        dataVal2.add(34);
        dataVal2.add(46);
        dataVal2.add(11);
        barDataSet2.setData(dataVal2);
         
        BarChartDataSet barDataSet3 = new BarChartDataSet();
        barDataSet3.setLabel("Dataset 3");
        barDataSet3.setBackgroundColor("rgb(75, 192, 192)");
        barDataSet3.setStack("Stack 1");
        List<Number> dataVal3 = new ArrayList<>();
        dataVal3.add(-45);
        dataVal3.add(73);
        dataVal3.add(-25);
        dataVal3.add(65);
        dataVal3.add(49);
        dataVal3.add(-18);
        dataVal3.add(46);
        barDataSet3.setData(dataVal3);
         
        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
        data.addChartDataSet(barDataSet3);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
        stackedGroupBarModel.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setStacked(true);    
        cScales.addXAxesData(linearAxes);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart - Stacked Group");
        options.setTitle(title);
         
        Tooltip tooltip = new Tooltip();
        tooltip.setMode("index");
        tooltip.setIntersect(false);
        options.setTooltip(tooltip);  
         
        stackedGroupBarModel.setOptions(options);
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
     
    public void createRadarModel2() {
        radarModel2 = new RadarChartModel();
        ChartData data = new ChartData();
         
        RadarChartDataSet radarDataSet = new RadarChartDataSet();
        radarDataSet.setLabel("P.Practitioner");
        radarDataSet.setLineTension(0.1);
        radarDataSet.setBackgroundColor("rgba(102, 153, 204, 0.2)");
        radarDataSet.setBorderColor("rgba(102, 153, 204, 1)");
        radarDataSet.setPointBackgroundColor("rgba(102, 153, 204, 1)");
        radarDataSet.setPointBorderColor("#fff");
        radarDataSet.setPointHoverRadius(5);
        radarDataSet.setPointHoverBackgroundColor("#fff");
        radarDataSet.setPointHoverBorderColor("rgba(102, 153, 204, 1)");
        List<Number> dataVal = new ArrayList<>();
        dataVal.add(2);
        dataVal.add(3);
        dataVal.add(2);
        dataVal.add(1);
        dataVal.add(3);
        radarDataSet.setData(dataVal);
 
        RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
        radarDataSet2.setLabel("P.Manager");
        radarDataSet2.setLineTension(0.1);
        radarDataSet2.setBackgroundColor("rgba(255, 204, 102, 0.2)");
        radarDataSet2.setBorderColor("rgba(255, 204, 102, 1)");
        radarDataSet2.setPointBackgroundColor("rgba(255, 204, 102, 1)");
        radarDataSet2.setPointBorderColor("#fff");
        radarDataSet2.setPointHoverRadius(5);
        radarDataSet2.setPointHoverBackgroundColor("#fff");
        radarDataSet2.setPointHoverBorderColor("rgba(255, 204, 102, 1)");
        List<Number> dataVal2 = new ArrayList<>();
        dataVal2.add(2);
        dataVal2.add(3);
        dataVal2.add(3);
        dataVal2.add(2);
        dataVal2.add(3);
        radarDataSet2.setData(dataVal2);
         
        data.addChartDataSet(radarDataSet);
        data.addChartDataSet(radarDataSet2);
         
        List<List<String>> labels = new ArrayList<>();
        labels.add(new ArrayList(Arrays.asList("Process","Excellence")));
        labels.add(new ArrayList(Arrays.asList("Problem","Solving")));
        labels.add(new ArrayList(Arrays.asList("Facilitation")));
        labels.add(new ArrayList(Arrays.asList("Project","Mgmt")));
        labels.add(new ArrayList(Arrays.asList("Change","Mgmt")));
        data.setLabels(labels);
 
        /* Options */
        RadarChartOptions options = new RadarChartOptions();
        RadialScales rScales = new RadialScales();
         
        RadialLinearAngleLines angelLines = new RadialLinearAngleLines();
        angelLines.setDisplay(true);
        angelLines.setLineWidth(0.5);
        angelLines.setColor("rgba(128, 128, 128, 0.2)");
        rScales.setAngelLines(angelLines);
         
        RadialLinearPointLabels pointLabels = new RadialLinearPointLabels();
        pointLabels.setFontSize(14);
        pointLabels.setFontStyle("300");
        pointLabels.setFontColor("rgba(204, 204, 204, 1)");
        pointLabels.setFontFamily("Lato, sans-serif");
         
        RadialLinearTicks ticks = new RadialLinearTicks();
        ticks.setBeginAtZero(true);
        ticks.setMaxTicksLimit(3);
        ticks.setMin(0);
        ticks.setMax(3);
        ticks.setDisplay(false);
 
        options.setScales(rScales);
         
        radarModel2.setOptions(options);
        radarModel2.setData(data);
        radarModel2.setExtender("skinRadarChart");
    }
     
    public void createBubbleModel() {
        bubbleModel = new BubbleChartModel();
        ChartData data = new ChartData();
         
        BubbleChartDataSet dataSet = new BubbleChartDataSet();
        List<BubblePoint> values = new ArrayList<>();
        values.add(new BubblePoint(20, 30, 15));
        values.add(new BubblePoint(40, 10, 10));
        dataSet.setData(values);
        dataSet.setBackgroundColor("rgb(255, 99, 132)");
        dataSet.setLabel("First Dataset");
        data.addChartDataSet(dataSet);
        bubbleModel.setData(data);
    }
     
    public void createMixedModel() {
        mixedModel = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet dataSet = new BarChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(10);
        values.add(20);
        values.add(30);
        values.add(40);
        dataSet.setData(values);
        dataSet.setLabel("Bar Dataset");
        dataSet.setBorderColor("rgb(255, 99, 132)");
        dataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
         
        LineChartDataSet dataSet2 = new LineChartDataSet();
        List<Number> values2 = new ArrayList<>();
        values2.add(50);
        values2.add(50);
        values2.add(50);
        values2.add(50);
        dataSet2.setData(values2);
        dataSet2.setLabel("Line Dataset");
        dataSet2.setFill(false);
        dataSet2.setBorderColor("rgb(54, 162, 235)");
         
        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        data.setLabels(labels);
         
        mixedModel.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
         
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        mixedModel.setOptions(options);
    }
     
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
         
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(300);
        values.add(50);
        values.add(100);
        dataSet.setData(values);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Red");
        labels.add("Blue");
        labels.add("Yellow");
        data.setLabels(labels);
         
        donutModel.setData(data);
    }
 
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());
 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public PieChartModel getPieModel() {
        return pieModel;
    }
 
    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
 
    public PolarAreaChartModel getPolarAreaModel() {
        return polarAreaModel;
    }
 
    public void setPolarAreaModel(PolarAreaChartModel polarAreaModel) {
        this.polarAreaModel = polarAreaModel;
    }
 
    public LineChartModel getLineModel() {
        return lineModel;
    }
 
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
 
    public LineChartModel getCartesianLinerModel() {
        return cartesianLinerModel;
    }
 
    public void setCartesianLinerModel(LineChartModel cartesianLinerModel) {
        this.cartesianLinerModel = cartesianLinerModel;
    }

    public BarChartModel getGraficaTodosLosResiduos() {
        return graficaTodosLosResiduos;
    }

    public void setGraficaTodosLosResiduos(BarChartModel graficaTodosLosResiduos) {
        this.graficaTodosLosResiduos = graficaTodosLosResiduos;
    }
 
    public BarChartModel getBarModel2() {
        return barModel2;
    }
 
    public void setBarModel2(BarChartModel barModel2) {
        this.barModel2 = barModel2;
    }

    public HorizontalBarChartModel getGraficaPrincipalHorizontal() {
        return graficaPrincipalHorizontal;
    }

    public void setGraficaPrincipalHorizontal(HorizontalBarChartModel graficaPrincipalHorizontal) {
        this.graficaPrincipalHorizontal = graficaPrincipalHorizontal;
    }
 
    public BarChartModel getStackedBarModel() {
        return stackedBarModel;
    }
 
    public void setStackedBarModel(BarChartModel stackedBarModel) {
        this.stackedBarModel = stackedBarModel;
    }
 
    public BarChartModel getStackedGroupBarModel() {
        return stackedGroupBarModel;
    }
 
    public void setStackedGroupBarModel(BarChartModel stackedGroupBarModel) {
        this.stackedGroupBarModel = stackedGroupBarModel;
    }
 
    public RadarChartModel getRadarModel() {
        return radarModel;
    }
 
    public void setRadarModel(RadarChartModel radarModel) {
        this.radarModel = radarModel;
    }
 
    public RadarChartModel getRadarModel2() {
        return radarModel2;
    }
 
    public void setRadarModel2(RadarChartModel radarModel2) {
        this.radarModel2 = radarModel2;
    }
 
    public BubbleChartModel getBubbleModel() {
        return bubbleModel;
    }
 
    public void setBubbleModel(BubbleChartModel bubbleModel) {
        this.bubbleModel = bubbleModel;
    }
 
    public BarChartModel getMixedModel() {
        return mixedModel;
    }
 
    public void setMixedModel(BarChartModel mixedModel) {
        this.mixedModel = mixedModel;
    }
 
    public DonutChartModel getDonutModel() {
        return donutModel;
    }
 
    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }
    
}
