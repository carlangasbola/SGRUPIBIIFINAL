package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Pojos.SesionDeLaboratorio;
import java.io.Serializable;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("ManejoLaboratorio")
@SessionScoped

public class ManagedBeanLaboratorio implements Serializable {
    
    public String prestamoDisponible(){
        Date fechaActual = new Date();
        List<SesionDeLaboratorio> sesion = new ArrayList();
            
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            sesion = session.createQuery("FROM SesionDeLaboratorio").list();
            tran.commit();
            
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        
        for(SesionDeLaboratorio item:sesion){
            
            System.out.println("Aquí: " + item.getFecha());
            System.out.println("Aquí 2: " + modificarHoras(item.getFecha(), 2));
            if(validaFecha(fechaActual, item.getFecha()) == true){
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
}
