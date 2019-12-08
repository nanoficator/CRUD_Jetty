import servlet.AddUserServlet;
import servlet.DeleteUserServlet;
import servlet.MainServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        MainServlet mainServlet = new MainServlet();
        AddUserServlet addUserServlet = new AddUserServlet();
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(mainServlet), "/");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/add");
        servletContextHandler.addServlet(new ServletHolder(deleteUserServlet), "/delete/*");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
