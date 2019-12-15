package servlet;

import com.google.gson.Gson;
import exception.DBException;
import model.User;
import service.UserServiceHQL;
import service.UserServiceSQL;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> allUsersHQL = UserServiceHQL.getInstance().getAllUsers();
        List<User> allUsersSQL = new UserServiceSQL().getAllUsers();
        Gson gson = new Gson();
        String jsonHQL = gson.toJson(allUsersHQL);
        String jsonSQL = gson.toJson(allUsersSQL);

        HashMap<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("tableHQL", jsonHQL);
        pageVariables.put("rowsHQL", allUsersHQL.size());
        pageVariables.put("tableSQL", jsonSQL);
        pageVariables.put("rowsSQL", allUsersSQL.size());
        resp.getWriter().println(PageGenerator.getInstance().getPage("MainPage.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}