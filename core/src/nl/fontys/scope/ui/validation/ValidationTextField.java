package nl.fontys.scope.ui.validation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ValidationTextField extends TextField implements Validable {

    private String previewText;

    private String errorMessage;

    public ValidationTextField(final String text, TextFieldStyle style, final ValidationContext context) {
        super(text, style);
        this.errorMessage = "Text should not be empty";
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

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    @Override
    public void checkValidity() throws ValidationException {
        if (isDefaultText() || this.getText().isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private boolean isDefaultText() {
        return previewText.equals(ValidationTextField.this.getText());
    }
}
