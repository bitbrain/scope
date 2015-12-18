package nl.fontys.scope.ui.validation;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;

import nl.fontys.scope.ui.Styles;

public class ValidationContext {

    private Button submit;

    private List<Validable> targets = new ArrayList<Validable>();

    private Label label;

    public ValidationContext(Button submit) {
        this.submit = submit;
        this.submit.setDisabled(true);
        label = new Label("", Styles.LABEL_VALIDATION);
    }

    public void register(Validable validable) {
        this.targets.add(validable);
    }

    public Label getLabel() {
        return label;
    }

    public void update() {
        int validCounter = 0;
        for (Validable target : targets) {
            try {
                target.checkValidity();
                validCounter++;
            } catch (ValidationException e) {
                label.setText(e.getMessage());
            }
        }
        label.setVisible(validCounter != targets.size());
        submit.setDisabled(validCounter != targets.size());
    }
}
