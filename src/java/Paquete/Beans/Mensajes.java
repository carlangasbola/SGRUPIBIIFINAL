package Paquete.Beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class Mensajes
{
  private String message;
  
  public Mensajes() {}
  
  public String getMessage()
  {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public void info()
  {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema", message));
  }
  
  public void warn()
  {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Alerta!", message));
  }
  
  public void error()
  {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Error!", message));
  }
  
  public void fatal()
  {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "¡Fatal!", message));
  }
}
