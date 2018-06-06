package logic.utils;

import launchers.Application;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("ApplicationWrapper")
public class ApplicationWrapper {

    public AnnotationConfigApplicationContext getWrappedContext(){
        return Application.getApplicationContext();
    }
}
