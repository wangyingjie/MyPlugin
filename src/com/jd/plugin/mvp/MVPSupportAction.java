package com.jd.plugin.mvp;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;


/**
 * @author: wangyingjie1
 * @version: 1.0
 * @createdate: 2017-11-20 15:35
 */
public class MVPSupportAction extends AnAction {

    /**
     * 执行插件的入口，相当于java中的main方法
     *
     * @param anActionEvent
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

//        String currentEditorFileName = currentEditorFile.getName();
//        /**
//         * 文件名的前缀
//         */
//        String prefixOfGenerateFileName = currentEditorFileName;
//        if (currentEditorFileName.endsWith(AppConstant.ACTIVITY)) {
//            prefixOfGenerateFileName = StringUtil.cutLastSubContent(currentEditorFileName, AppConstant.ACTIVITY);
//        } else if (currentEditorFileName.endsWith(AppConstant.FRAGMENT)) {
//            prefixOfGenerateFileName = StringUtil.cutLastSubContent(currentEditorFileName, AppConstant.FRAGMENT);
//        }
//
//        showHintDialog(currentEditorFile, prefixOfGenerateFileName, project);

    }

    /**
     * 显示提示对话框
     *
     * @param file
     * @param prefix
     * @param project
     */
    private void showHintDialog(PsiFile file, String prefix, Project project) {
//        HintDialog dialog = new HintDialog(file, prefix, project);
//        dialog.setSize(600, 400);
//        dialog.setLocationRelativeTo(null);
//        dialog.setVisible(true);
//        dialog.requestFocus();
    }

//    private void createFileInWriteCommandAction() {
//        new WriteCommandAction.Simple(project, mPsiFile) {
//            @Override
//            protected void run() throws Throwable {
//                createFile();
//            }
//        }.execute();
//    }


}

