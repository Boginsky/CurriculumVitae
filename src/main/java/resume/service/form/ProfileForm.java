package resume.service.form;

import org.springframework.context.annotation.Profile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class ProfileForm {
    @Valid
    private List<Profile> items = new ArrayList<>();

    public ProfileForm() {
        super();
    }

    public ProfileForm(List<Profile> items) {
        super();
        this.items = items;
    }

    public List<Profile> getItems() {
        return items;
    }

    public void setItems(List<Profile> items) {
        this.items = items;
    }
}