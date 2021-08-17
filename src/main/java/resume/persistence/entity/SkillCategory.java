package resume.persistence.entity;

import javax.persistence.*;


@Entity
@Table(name = "skill_category")
public class SkillCategory extends AbstractEntity<Short> {
    public static final String ORDER_FIELD_NAME = "id";
    @Id
    @Column
    private Short id;

    @Column(nullable = false, length = 50)
    private String category;

    public SkillCategory() {
        super();
    }

    public SkillCategory(String category) {
        super();
        this.category = category;
    }

    public Short getId() {
        return id;
    }

    @Transient
    public Short getIdCategory() {
        return getId();
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}