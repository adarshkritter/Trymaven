import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.StringWriter;

public class Trial {
   static void velocity(){
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template t = new Template();
       RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
       SimpleNode simpleNode = null;
       try {
           simpleNode = runtimeServices.parse("hello $name", "");
       } catch (ParseException e) {
           e.printStackTrace();
       }

       t.setRuntimeServices(runtimeServices);
       t.setData(simpleNode);
       t.initDocument();

        VelocityContext context = new VelocityContext();
        context.put("name", "World");

        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        System.out.println(writer);
    }

    public static void main(String[] args) {
        velocity();
    }
}
