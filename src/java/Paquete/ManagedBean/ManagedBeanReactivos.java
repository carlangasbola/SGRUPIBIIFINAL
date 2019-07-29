package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.*;
import Paquete.Pojos.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.*;

@Named("DatosReactivos")
@RequestScoped
public class ManagedBeanReactivos {

    private List<Reactivos> datosReactivo;
    private Reactivos reactivo;
    private List<Integer> cantidadReactivos;
    private int[] tv={0,0,0,0,0,0,0,0,0};
    
    @PostConstruct
    private void init() {
        datosReactivo = new ArrayList<>();
        reactivo = new Reactivos();
        ObtenerDatosReactivo();
    }
    
    public String porcentajeReactivo(String tipoReactivo){
        Integer cantidad = 0;
        String salida;
        if (datosReactivo == null) {
            ObtenerDatosReactivo();
        }
        for (Reactivos item:datosReactivo) {
            if (item.getTipo().equals(tipoReactivo)){
                cantidad++;
            }
        }
        
        cantidad = (cantidad * 100) / datosReactivo.size();
        salida = cantidad.toString();
        
        if (salida.length() == 1) {
            salida = "0" + salida;
        }
        
        return salida;
    }
    
    public List<Reactivos> ObtenerDatosReactivo() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            datosReactivo = session.createQuery("FROM Reactivos").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return datosReactivo;
    }
    
    public int cantidadReactivos(String tipo) {
        int cantidad = 0;
        
        if(datosReactivo == null) {
            ObtenerDatosReactivo();
        }
        
        for(Reactivos item:datosReactivo){
            if(item.getTipo().equals(tipo)){
                cantidad++;
            }
        }
        
        return cantidad;
    }
    
    public List<String> nombresReactivos() {
        List <String> nombres = new ArrayList<>();
        
        if(datosReactivo == null) {
            ObtenerDatosReactivo();
        }
        
        for(Reactivos item:datosReactivo) {
            nombres.add(item.getNombre());
        }
        
        Collections.sort(nombres); 
        
        return nombres;
    }
    
    public String tipoReactivo(String nombreReactivo) {
        if(datosReactivo == null) {
            ObtenerDatosReactivo();
        }
        
        for(Reactivos item:datosReactivo){
            if(item.getNombre().equals(nombreReactivo)){
                return item.getTipo();
            }
        }
        return "Tipo";
    }
    
    public void InsertDatosReactivo() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.save(reactivo);
            tx.commit();
            
            mensaje.setMessage("Reactivo agregado");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el material, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }
    
    public void EliminarReactivo(int idReactivo) {
        Mensajes mensaje = new Mensajes();
        Transaction transObj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transObj = session.beginTransaction();
            session.delete((Reactivos) session.get(Reactivos.class, idReactivo));

            transObj.commit();
            mensaje.setMessage("Reactivo eliminado del sistema");
            mensaje.info();
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
    }
    
    public String disponiblidad (int Existencia_Inventario) {
        if (Existencia_Inventario == 1) {
            return "Si";
        }
        return "No";
    }
    
    public List<Reactivos> getDatosReactivo() {
        if(datosReactivo == null) {
            ObtenerDatosReactivo();
        }
        return datosReactivo;
    }
    
    public void datosCalculo(List<Reactivos> reactivosPractica, DatosPractica datosPractica){
        
        for(Reactivos item:reactivosPractica){
            String tipo = tipoReactivo(item.getNombre());
            
            if("Explosivo".equals(tipo))
                tv[0] = 1;
            else if("Inflamable".equals(tipo))
                tv[1] = 1;
            else if("Gas a presión".equals(tipo))
                tv[2] = 1;
            else if("Sustancia comburente".equals(tipo))
                tv[3] = 1;
            else if("Sustancia perjudicial para la salud".equals(tipo))
                tv[4] = 1;
            else if("Sustancia corrosiva".equals(tipo))
                tv[5] = 1;
            else if("Sustancia nosiva".equals(tipo))
                tv[6] = 1;
            else if("Sustancia tóxica".equals(tipo))
                tv[7] = 1;
            else if("Sustancia peligrosas para el medio ambiente".equals(tipo))
                tv[8] = 1;
        }
        
        calcularRiesgo(reactivosPractica, datosPractica);
    }
    
    public void calcularRiesgo(List<Reactivos> reactivosPractica, DatosPractica datosPractica){
        ConexionNeurona conexion = new ConexionNeurona();
        double networkOutput;
        double networkOutput1;
        double networkOutput2;
        
        conexion.variables(tv[0], tv[1], tv[2], tv[3], tv[4], tv[5], tv[6], tv[7], tv[8]);
        conexion.RedNeuronal();
        networkOutput = conexion.networkOutput[0];
        networkOutput1 = conexion.networkOutput[1];
        networkOutput2 = conexion.networkOutput[2];
        
        if(networkOutput>.8){
            String rs[] = {"","","","","","","","",""};
            
            for(Reactivos item:reactivosPractica){
                String tipo = tipoReactivo(item.getNombre());
                
                if("Explosivo".equals(tipo))
                    rs[0] = tipo;
                else if("Inflamable".equals(tipo))
                    rs[1] = tipo;
                else if("Gas a presión".equals(tipo))
                    rs[2] = tipo;
                else if("Sustancia comburente".equals(tipo))
                    rs[3] = tipo;
                else if("Sustancia perjudicial para la salud".equals(tipo))
                    rs[4] = tipo;
                else if("Sustancia corrosiva".equals(tipo))
                    rs[5] = tipo;
                else if("Sustancia nosiva".equals(tipo))
                    rs[6] = tipo;
                else if("Sustancia tóxica".equals(tipo))
                    rs[7] = tipo;
                else if("Sustancia peligrosas para el medio ambiente".equals(item.getTipo()))
                    rs[8] = tipo;
            }
            
            datosPractica.setRecomendaciones("Práctica con riesgo minimo: <br/> Sustancias:" + 
                                              rs[0] + " " + rs[1] + " " + rs[2] + " " + rs[3] +
                                              " " + rs[4] + " " + rs[5] + " " + rs[6] + " " + 
                                              rs[7] + " " + rs[8] + "<br/>Residuo: Se puede almacenar juntos.");
            datosPractica.setProtocolos("<a href=\"http://dof.gob.mx/nota_detalle.php?" + 
                                        "codigo=5072773\">NOM-017-STPS-2008</a> <br/> " + 
                                        "<a href=\"http://www.dof.gob.mx/nota_detalle_" + 
                                        "popup.php?codigo=5070081\">NOM- 026-STPS-2008</a>");
            datosPractica.setSemaforo("Riesgo bajo");
        }
        
        else{
            if(networkOutput1>.8){
                String rs[] = {"","","","","","","","",""};
                
                for(Reactivos item:reactivosPractica){
                    String tipo = tipoReactivo(item.getNombre());

                    if("Explosivo".equals(tipo))
                        rs[0] = tipo;
                    else if("Inflamable".equals(tipo))
                        rs[1] = tipo;
                    else if("Gas a presión".equals(tipo))
                        rs[2] = tipo;
                    else if("Sustancia comburente".equals(tipo))
                        rs[3] = tipo;
                    else if("Sustancia perjudicial para la salud".equals(tipo))
                        rs[4] = tipo;
                    else if("Sustancia corrosiva".equals(tipo))
                        rs[5] = tipo;
                    else if("Sustancia nosiva".equals(tipo))
                        rs[6] = tipo;
                    else if("Sustancia tóxica".equals(tipo))
                        rs[7] = tipo;
                    else if("Sustancia peligrosas para el medio ambiente".equals(tipo))
                        rs[8] = tipo;
                }
                
                datosPractica.setRecomendaciones("Práctica con riesgo medio: <br/> Sustancias:" + 
                                                  rs[0] + " " + rs[1] + " " + rs[2] + " " + rs[3] + 
                                                  " " + rs[4] + " " + rs[5] + " " + rs[6] + " " + 
                                                  rs[7] + " " + rs[8] + "<br/>Residuo: " + 
                                                  "Colocar en distintos compartimientos" + 
                                                  ". Puede requeririse una separacion l" + 
                                                  "ongitudinal o vertical constituida p" + 
                                                  "or un compartimiento intermedio completo.");
                datosPractica.setProtocolos("<a href=\"http://dof.gob.mx/nota_detalle.p" + 
                                            "hp?codigo=5072773\">NOM-017-STPS-2008</a> " + 
                                            "<br/> <a href=\"http://www.dof.gob.mx/nota" + 
                                            "_detalle_popup.php?codigo=5070081\">NOM- 026-STPS-2008</a>");
                datosPractica.setSemaforo("Riesgo medio");
            }
        
            if(networkOutput2>.8){
                String rs[] = {"","","","","","","","",""};
                
                for(Reactivos item:reactivosPractica){
                    String tipo = tipoReactivo(item.getNombre());
                    
                    if("Explosivo".equals(tipo))
                        rs[0]=item.getTipo();
                    else if("Inflamable".equals(tipo))
                        rs[1]=item.getTipo();
                    else if("Gas a presión".equals(tipo))
                        rs[2]=item.getTipo();
                    else if("Sustancia comburente".equals(tipo))
                        rs[3]=item.getTipo();
                    else if("Sustancia perjudicial para la salud".equals(tipo))
                        rs[4]=item.getTipo();
                    else if("Sustancia corrosiva".equals(tipo))
                        rs[5]=item.getTipo();
                    else if("Sustancia nosiva".equals(tipo))
                        rs[6]=item.getTipo();
                    else if("Sustancia tóxica".equals(tipo))
                        rs[7]=item.getTipo();
                    else if("Sustancia peligrosas para el medio ambiente".equals(tipo))
                        rs[8]=item.getTipo();
                }
                
                datosPractica.setRecomendaciones("Práctica con riesgo Alto: <br/> Sustancias:" + 
                                                 rs[0] + " " + rs[1] + " " + rs[2] + " " + rs[3] + 
                                                 " " + rs[4] + " " + rs[5] + " " + rs[6] + " " + 
                                                 rs[7] + " " + rs[8] + "<br/>Residuo: Colocar" + 
                                                 " en compartimientos separados o bodega aparte.");
                datosPractica.setProtocolos("<a href=\"http://dof.gob.mx/nota_detalle.p" + 
                                            "hp?codigo=5072773\">NOM-017-STPS-2008</a> " + 
                                            "<br/> <a href=\"http://www.dof.gob.mx/nota" + 
                                            "_detalle_popup.php?codigo=5070081\">NOM- 026-STPS-2008</a>");            
                datosPractica.setSemaforo("Riesgo alto");
            }
        }
    }

    public void setDatosReactivo(List<Reactivos> datosReactivo) {
        this.datosReactivo = datosReactivo;
    }

    public Reactivos getReactivo() {
        return reactivo;
    }

    public void setReactivo(Reactivos reactivo) {
        this.reactivo = reactivo;
    }

    public List<Integer> getCantidadReactivos() {
        if(cantidadReactivos == null) {
            ObtenerDatosReactivo();
        }
        return cantidadReactivos;
    }

    public void setCantidadReactivos(List<Integer> cantidadReactivos) {
        this.cantidadReactivos = cantidadReactivos;
    }
    
}
