import servlet.MainServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        MainServlet mainServlet = new MainServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(mainServlet), "/");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
