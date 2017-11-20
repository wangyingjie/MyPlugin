package com.jd.plugin.set;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author: wangyingjie1
 * @version: 1.0
 * @createdate: 2017-11-20 14:16
 */
public class FormatConfigurable implements SearchableConfigurable {

    private FormatForm formatForm;
    private FormatSetting formatSetting = FormatSetting.getInstance();

    public FormatConfigurable() {
    }

    @NotNull
    public String getId() {
        return "DocFormat";
    }

    @Nls
    public String getDisplayName() {
        return this.getId();
    }

    @Nullable
    public String getHelpTopic() {
        return this.getId();
    }

    @Nullable
    public JComponent createComponent() {
        if(null == this.formatForm) {
            this.formatForm = new FormatForm();
        }

        return this.formatForm.mainPanel;
    }

    public boolean isModified() {
        return !this.formatSetting.getGetFormat().equals(this.formatForm.getFormatTextArea.getText()) || !this.formatSetting.getSetFormat().equals(this.formatForm.setFormatTextArea.getText());
    }

    public void apply() throws ConfigurationException {
        this.formatSetting.setGetFormat(this.formatForm.getFormatTextArea.getText());
        this.formatSetting.setSetFormat(this.formatForm.setFormatTextArea.getText());
    }

    public void reset() {
        this.formatForm.getFormatTextArea.setText(this.formatSetting.getGetFormat());
        this.formatForm.setFormatTextArea.setText(this.formatSetting.getSetFormat());
    }

}