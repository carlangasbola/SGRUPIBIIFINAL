package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.ReporteIncidencia;
import Paquete.Pojos.SesionDeLaboratorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author iron1
 */
@Named(value = "Incidencias")
@RequestScoped
public class ManagedBeanIncidencias {

    private ReporteIncidencia reporteIncidencia = new ReporteIncidencia();
    private SesionDeLaboratorio sesionLaboratorio = new SesionDeLaboratorio();

    public ManagedBeanIncidencias() {
    }
    
    public String obtenerReporte(ReporteIncidencia ri){
        this.reporteIncidencia = ri;
        return "verIncidencia";
    }
    
    public String verReporte(ReporteIncidencia ri){
        this.reporteIncidencia = ri;
        return "verIncidencia";
    }
    
    public void eliminarReporte(ReporteIncidencia ri){
        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {

            tran = session.beginTransaction();
            session.delete(ri);
            tran.commit();

            mensaje.setMessage("Reporte elaiminado");
            mensaje.info();
        } catch (Exception sqlException) {
            tran.rollback();

            mensaje.setMessage("No se elimino el reprote");
            mensaje.error();
        }
    }

    public List<ReporteIncidencia> obtenerReportesIncidencia() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<ReporteIncidencia> reportes = new ArrayList<>();
        
        try {

            tran = session.beginTransaction();
            reportes = session.createQuery("FROM ReporteIncidencia").list();
            tran.commit();

        } catch (Exception sqlException) {
            tran.rollback();
        }

        return reportes;
    }

    public void guardarReporteIncidencia() {

        Mensajes mensaje = new Mensajes();
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {

            tran = session.beginTransaction();
            SesionDeLaboratorio sesionesLab = (SesionDeLaboratorio) session.load(SesionDeLaboratorio.class, sesionLaboratorio.getIdSesion());
            reporteIncidencia.setSesionDeLaboratorio(sesionLaboratorio);
            reporteIncidencia.setFecha(new Date());
            reporteIncidencia.setEstado("Abierto");
            reporteIncidencia.setActualizacion(new Date());
            session.save(reporteIncidencia);
            tran.commit();

            mensaje.setMessage("Reporte creado");
            mensaje.info();
        } catch (Exception sqlException) {
            tran.rollback();

            mensaje.setMessage("No se creo el reprote");
            mensaje.error();
        }

    }

    // GETTERS Y SETTERS
    public ReporteIncidencia getReporteIncidencia() {
        return reporteIncidencia;
    }

    public void setReporteIncidencia(ReporteIncidencia reporteIncidencia) {
        this.reporteIncidencia = reporteIncidencia;
    }

    public SesionDeLaboratorio getSesionLaboratorio() {
        return sesionLaboratorio;
    }

    public void setSesionLaboratorio(SesionDeLaboratorio sesionLaboratorio) {
        this.sesionLaboratorio = sesionLaboratorio;
    }

}
