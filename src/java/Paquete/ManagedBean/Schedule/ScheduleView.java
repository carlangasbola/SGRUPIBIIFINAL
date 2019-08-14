package Paquete.ManagedBean.Schedule;

import Hibernate.Util.HibernateUtil;
import Paquete.Pojos.Eventos;
import Paquete.Pojos.SesionDeLaboratorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@ViewScoped
// Arreglar el calendario
public class ScheduleView implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        List<SesionDeLaboratorio> sesionesLab = new ArrayList<>();
        List<Eventos> eventos = new ArrayList<>();

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            sesionesLab = session.createQuery("FROM SesionDeLaboratorio").list();

            for (SesionDeLaboratorio sl : sesionesLab) {
                eventModel.addEvent(
                        new DefaultScheduleEvent("Grupo : "
                                + sl.getUnidadGrupo().getGrupo().getNombre() + "\n"
                                + "Unidad Aprendizaje: "
                                + sl.getUnidadGrupo().getUnidadAprendizaje().getNombre() + "\n",
                                sl.getFecha(),
                                sl.getFecha()));
            }

            eventos = session.createQuery("FROM Eventos").list();

            for (Eventos e : eventos) {
                eventModel
                        .addEvent(
                                new DefaultScheduleEvent("Entrega de práctica :" + "\n"
                                        + "Titulo :" + e.getNombre() + "\n"
                                        + "Grupo " + e.getUnidadGrupo().getGrupo().getNombre() + "\n"
                                        + e.getUnidadGrupo().getUnidadAprendizaje().getNombre(),
                                        e.getFecha(),
                                        e.getFecha()
                                ));
            }

            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }

    }

//    public String obtenerEventosAlumno() {
//
//        int idUnidadGrupo;
//        List<ListaGrupo> listaGrupo;
//        ConsultasHQL consulta = new ConsultasHQL();
//        int idAlumno = (int) consulta.obtenerDatosSesion("UserId");
//        consulta.crearListPair("idAlumno", idAlumno);
//        listaGrupo = consulta.crearSelectQuery("FROM ListaGrupo where usuarios.idUsuarios = :idAlumno");
//        // Obtenemos el id de Unidad Grupo (Este codigo solo busca 1 deberia buscar n)
//        idUnidadGrupo = listaGrupo.get(0).getUnidadGrupo().getIdUnidadGrupo();
//
//        // Aqui empieza lo de los eventos
//        eventModel = new DefaultScheduleModel();
//
//        consulta.crearListPair("idUnidadA", idUnidadGrupo);
//        List<SesionDeLaboratorio> sls = consulta.crearSelectQuery("FROM SesionDeLaboratorio WHERE unidadGrupo.idUnidadGrupo = :idUnidadA");
//
//        for (SesionDeLaboratorio sl : sls) {
//            eventModel
//                    .addEvent(
//                            new DefaultScheduleEvent("Grupo : "
//                                    + //sl.getGrupo().getNombre() + "\n" +
//                                    "Unidad Aprendizaje: "
//                                    + //sl.getUnidadAprendizaje().getNombre() + "\n"+
//                                    "Docente Auxiliar: " + sl.getDocenteAuxiliar(),
//                                    sl.getFecha(),
//                                    sl.getFecha()
//                            ));
//        }
//
//        consulta.crearListPair("idUnidadA", idUnidadGrupo);
//        List<Eventos> evs = consulta.crearSelectQuery("FROM Eventos WHERE unidadGrupo.idUnidadGrupo = :idUnidadA");
//
//        for (Eventos e : evs) {
//            eventModel
//                    .addEvent(
//                            new DefaultScheduleEvent("Entrega de práctica :" + "\n"
//                                    + "Titulo :" + e.getNombre() + "\n"
//                                    + "Grupo " + e.getUnidadGrupo().getGrupo().getNombre() + "\n"
//                                    + e.getUnidadGrupo().getUnidadAprendizaje().getNombre(),
//                                    e.getFecha(),
//                                    e.getFecha()
//                            ));
//        }
//        return "modEventos";
//    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
