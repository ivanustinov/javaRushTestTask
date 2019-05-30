package logic;

import dao.DaoAccess;
import entities.Component;
import lombok.Data;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 28.05.2019
 */
@Data
@Named
@ApplicationScoped
public class SortView implements Serializable {
    private List<Component> components;
    @Inject
    private DaoAccess daoAccess;

    @PostConstruct
    public void init() {
        components = daoAccess.getAll();
    }

    public void delete(Component component) {
        components.remove(component);
        daoAccess.delete(component);
    }
    public Component addComponent() {
        Component component = new Component("NEW", 0, false, 0);
        components.add(component);
        daoAccess.add(component);
        FacesMessage msg = new FacesMessage("New component added", "Unknown");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return component;
    }

    public int calculateComputers() {
        int c = 0;
        for (Component component : components) {
            if (component.isNeedForAssembly()) {
                int a = component.getCount();
                int b = component.getCountForAssembly();
                if (a < b) return 0;
                if (b != 0) {
                    int w = a / b;
                    if (c == 0) c = w;
                    c = c > w ? w : c;
                }
            }
        }
        return c;
    }
    public void onRowEdit(RowEditEvent event) {
        Component component = (Component) event.getObject();
        daoAccess.update(component);
        FacesMessage msg = new FacesMessage("Car Edited", component.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Component) event.getObject()).getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
