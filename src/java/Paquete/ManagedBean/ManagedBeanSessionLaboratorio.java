/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.SesionDeLaboratorio;
import Paquete.Pojos.UnidadGrupo;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author iron1
 */
@Named(value = "SessionLaboratorio")
@RequestScoped
public class ManagedBeanSessionLaboratorio {

    
    private SesionDeLaboratorio sesionLaboratorio = new SesionDeLaboratorio();
    // Esto no se debe hacer pero no tuve tiempo de hacer un conver
    // al select one menu
    private int IdUnidadGrupo;
    private String docenteAux;
    
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

}
