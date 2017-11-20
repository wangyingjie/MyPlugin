package com.jd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.ui.CollectionListModel;
import com.jd.plugin.set.FormatSetting;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class GenerateGSAction extends AnAction {

    private FormatSetting formatSetting;
    private static final String GET = "get";
    private static final String SET = "set";

    public GenerateGSAction() {
    }

    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

        this.formatSetting = FormatSetting.getInstance();
        this.generateGSMethod(this.getPsiMethodFromContext(anActionEvent));
    }

    private void generateGSMethod(final PsiClass psiMethod) {
        (new WriteCommandAction.Simple(psiMethod.getProject(), new PsiFile[]{psiMethod.getContainingFile()}) {
            protected void run() throws Throwable {
                GenerateGSAction.this.createGetSet(psiMethod);
            }
        }).execute();
    }

    private void createGetSet(PsiClass psiClass) {
        List fields = (new CollectionListModel(psiClass.getFields())).getItems();
        if (fields != null) {
            List list = (new CollectionListModel(psiClass.getMethods())).getItems();
            HashSet methodSet = new HashSet();
            Iterator elementFactory = list.iterator();

            while (elementFactory.hasNext()) {
                PsiMethod m = (PsiMethod) elementFactory.next();
                methodSet.add(m.getName());
            }

            PsiElementFactory elementFactory1 = JavaPsiFacade.getElementFactory(psiClass.getProject());
            Iterator m1 = fields.iterator();

            while (m1.hasNext()) {
                PsiField field = (PsiField) m1.next();
                if (!field.getModifierList().hasModifierProperty("final")) {
                    String methodText = this.buildGet(field);
                    PsiMethod toMethod = elementFactory1.createMethodFromText(methodText, psiClass);
                    if (!methodSet.contains(toMethod.getName())) {
                        psiClass.add(toMethod);
                        methodText = this.buildSet(field);
                        elementFactory1 = JavaPsiFacade.getElementFactory(psiClass.getProject());
                        toMethod = elementFactory1.createMethodFromText(methodText, psiClass);
                        if (!methodSet.contains(toMethod)) {
                            psiClass.add(toMethod);
                        }
                    }
                }
            }

        }
    }

    private String buildGet(PsiField field) {
        StringBuilder sb = new StringBuilder();
        String doc = this.format("get", field);
        if (doc != null) {
            sb.append(doc);
        }

        sb.append("public ");
        if (field.getModifierList().hasModifierProperty("static")) {
            sb.append("static ");
        }

        sb.append(field.getType().getPresentableText() + " ");
        if (field.getType().getPresentableText().equals("boolean")) {
            sb.append("is");
        } else {
            sb.append("get");
        }

        sb.append(this.getFirstUpperCase(field.getName()));
        sb.append("(){\n");
        sb.append(" return this." + field.getName() + ";}\n");
        return sb.toString();
    }

    private String buildSet(PsiField field) {
        StringBuilder sb = new StringBuilder();
        String doc = this.format("set", field);
        if (doc != null) {
            sb.append(doc);
        }

        sb.append("public ");
        if (field.getModifierList().hasModifierProperty("static")) {
            sb.append("static ");
        }

        sb.append("void ");
        sb.append("set" + this.getFirstUpperCase(field.getName()));
        sb.append("(" + field.getType().getPresentableText() + " " + field.getName() + "){\n");
        sb.append("this." + field.getName() + " = " + field.getName() + ";");
        sb.append("}");
        return sb.toString();
    }

    private String getFirstUpperCase(String oldStr) {
        return oldStr.substring(0, 1).toUpperCase() + oldStr.substring(1);
    }

    private PsiClass getPsiMethodFromContext(AnActionEvent e) {
        PsiElement elementAt = this.getPsiElement(e);
        return elementAt == null ? null : (PsiClass) PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
    }

    private PsiElement getPsiElement(AnActionEvent e) {
        PsiFile psiFile = (PsiFile) e.getData(LangDataKeys.PSI_FILE);
        Editor editor = (Editor) e.getData(PlatformDataKeys.EDITOR);
        if (psiFile != null && editor != null) {
            int offset = editor.getCaretModel().getOffset();
            return psiFile.findElementAt(offset);
        } else {
            e.getPresentation().setEnabled(false);
            return null;
        }
    }

    private String format(String string, PsiField field) {
        String oldContent;
        if (field.getDocComment() == null) {
            oldContent = field.getText().substring(0, field.getText().lastIndexOf("\n") + 1);
        } else {
            oldContent = field.getDocComment().getText();
        }

        oldContent = oldContent.substring(0, oldContent.length()).replaceAll("[\n,\r,*,/,\t]", "").trim();
        if ("get".equals(string)) {
            oldContent = this.formatSetting.getGetFormat().replaceAll("#\\{bare_field_comment}", oldContent).replaceAll("\\$\\{field.name}", field.getName());
        } else if ("set".equals(string)) {
            oldContent = this.formatSetting.getSetFormat().replaceAll("#\\{bare_field_comment}", oldContent).replaceAll("\\$\\{field.name}", field.getName());
        }

        return oldContent;
    }
}
