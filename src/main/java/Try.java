//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.velocity.*;
import org.apache.log4j.*;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

public class Try {

    //public static Logger logger = null;
    public static void loadlog4j(){
        String logpath=System.getProperty("user.dir")+"/log4j.properties";
        PropertyConfigurator.configure(logpath);
    }


   /*void slf4jPrint() {
        Logger logger = LoggerFactory.getLogger("Try");
        //Logging the information
        logger.info("info using slf4j");
    }*/

    void log4jPrint(){
        Logger log = Logger.getLogger(Try.class.getName());
        log.info("This is from log4j");
    }
void volicity(){
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.init();

    Template t = velocityEngine.getTemplate("index.vm");

    VelocityContext context = new VelocityContext();
    context.put("name", "World");

    StringWriter writer = new StringWriter();
    t.merge( context, writer );
}

    public static void main(String[] args) {
       //BasicConfigurator.configure();
        Try t1 = new Try();
        //t1.slf4jPrint();
        loadlog4j();
        t1.log4jPrint();
        //t1.volicity();
    }
}
