/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete.Service;

import Hibernate.Util.HibernateUtil;
import Paquete.Pojos.Grupo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceGrupo {

    public List<Grupo> obtenerGrupos() {

        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Grupo> grupos = new ArrayList<>();

        try {
            tran = session.beginTransaction();
            Query query = session.createQuery("FROM Grupo");
            grupos = query.list();
            tran.commit();

        } catch (Exception sqlException) {
            tran.rollback();
        }

        return grupos;
    }

}
