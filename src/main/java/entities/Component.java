package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.*;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 26.05.2019
 */
@Data
@Entity
@Table(name = "components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private int count;
    private boolean needForAssembly;
    private int countForAssembly;

    public Component(String name, int count, boolean needForAssembly, int countForAssembly) {
        this.description = name;
        this.count = count;
        this.needForAssembly = needForAssembly;
        this.countForAssembly = countForAssembly;
    }

    public Component() {
    }
}

