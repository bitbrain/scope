package nl.fontys.scope.ui.validation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ValidationTextField extends TextField implements Validable {

    private String previewText;

    public ValidationTextField(final String text, TextFieldStyle style, final ValidationContext context) {
        super(text, style);
        this.previewText = text;
        context.register(this);
        addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isDefaultText()) {
                    ValidationTextField.this.setText("");
                }
            }
        });
        setTextFieldListener(new TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                context.update();
            }
        });

    }

    @Override
    public void checkValidity() throws ValidationException {
        if (isDefaultText() || this.getText().isEmpty()) {
            throw new ValidationException("Text should not be empty");
        }
    }

    private boolean isDefaultText() {
        return previewText.equals(ValidationTextField.this.getText());
    }
}
