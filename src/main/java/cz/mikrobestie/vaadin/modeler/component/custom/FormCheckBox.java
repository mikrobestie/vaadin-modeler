package cz.mikrobestie.vaadin.modeler.component.custom;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;


public class FormCheckBox extends CustomField<Boolean> {


    private CheckBox checkBox = new CheckBox();

    @Override
    protected Component initContent() {
        setSizeUndefined();
        setImmediate(true);
        checkBox.setImmediate(true);
        checkBox.addValueChangeListener(e -> {
            setValue(checkBox.getValue());
            fireValueChange(false);
        });
        return checkBox;
    }

    @Override
    public Class<? extends Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public boolean isReadOnly() {
        return checkBox.isReadOnly();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        checkBox.setReadOnly(readOnly);
        super.setReadOnly(readOnly);
    }

    @Override
    protected void setInternalValue(Boolean newValue) {
        super.setInternalValue(newValue);
        checkBox.setValue(newValue);
    }

    @Override
    public void addValueChangeListener(ValueChangeListener listener) {
        checkBox.addValueChangeListener(listener);
    }
}