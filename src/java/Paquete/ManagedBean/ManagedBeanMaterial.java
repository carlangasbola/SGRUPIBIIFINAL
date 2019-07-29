package Paquete.ManagedBean;

import Hibernate.Util.HibernateUtil;
import Paquete.Beans.Mensajes;
import Paquete.Pojos.Material;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("DatosMaterial")
@RequestScoped
public class ManagedBeanMaterial {

    private List<Material> datosMaterial;
    private Material material;

    @PostConstruct
    private void init() {
        datosMaterial = new ArrayList<>();
        material = new Material();
        ObtenerDatosMaterial();
        
    }
    
    public List<Material> ObtenerDatosMaterial() {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            tran = session.beginTransaction();
            datosMaterial = session.createQuery("FROM Material").list();
            tran.commit();
        } catch ( Exception e) {
            tran.rollback();
            throw e;
        }
        return datosMaterial;
    }
    
    public List<String> nombresMaterial() {
        List <String> nombres = new ArrayList<>();
        
        if(datosMaterial == null) {
            ObtenerDatosMaterial();
        }
        
        for(Material item:datosMaterial) {
            nombres.add(item.getNombre());
        }
        
        Collections.sort(nombres); 
        
        return nombres;
    }
    
    public String nombreMaterial(Integer elemento) {
        if(datosMaterial == null) {
            ObtenerDatosMaterial();
        }
        return datosMaterial.get(elemento).getNombre();
    }
    
    public Integer cantidadMaterial(Integer elemento) {
        if(datosMaterial == null) {
            ObtenerDatosMaterial();
        }
        return datosMaterial.get(elemento).getCantidad();
    }
    
    public String disponiblidad (int Existencia_Inventario) {
        if (Existencia_Inventario == 1) {
            return "Si";
        }
        return "No";
    }
    
    public void InsertDatosMaterial() {
        Mensajes mensaje = new Mensajes();
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            
            session.save(material);
            tx.commit();
            
            mensaje.setMessage("Material agregado");
            mensaje.info();
            
        } catch (RuntimeException e) {
            tx.rollback();
            mensaje.setMessage("No se puedo insertar el material, consulte el log para mas detalles");
            mensaje.fatal();
            throw e;
        }
    }
    
    public void EliminarMaterial(int idMaterial) {
        Mensajes mensaje = new Mensajes();
        Transaction transObj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transObj = session.beginTransaction();
            session.delete((Material) session.get(Material.class, idMaterial));

            transObj.commit();
            mensaje.setMessage("Material eliminado del sistema");
            mensaje.info();
        } catch (HibernateException exObj) {
            if (transObj != null) {
                transObj.rollback();
                mensaje.setMessage("No se pudo eliminar, contacte el log de errores");
                mensaje.warn();
            }
        }
    }
    
    public List<Material> getDatosMaterial() {
        if(datosMaterial == null) {
            ObtenerDatosMaterial();
        }
        return datosMaterial;
    }

    public void setDatosMaterial(List<Material> datosMaterial) {
        this.datosMaterial = datosMaterial;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    
}
