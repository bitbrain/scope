package nl.fontys.scope.ui.validation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ValidationTextField extends TextField {

    public ValidationTextField(final String text, TextFieldStyle style) {
        super(text, style);
        addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text.equals(ValidationTextField.this.getText())) {
                    ValidationTextField.this.setText("");
                }
            }
        });
    }
}
