package com.jd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

/**
 * @author wangyingjie1
 */
public class SayHelloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Application application = ApplicationManager.getApplication();

        MyComponent myComponent = application.getComponent(MyComponent.class);

        myComponent.sayHello();
    }
}
