package servlet;

import com.google.gson.Gson;
import model.User;
import service.UserServiceHQL;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> allUsers = UserServiceHQL.getInstance().getAllUsers();
        Gson gson = new Gson();
        String json = gson.toJson(allUsers);
        HashMap<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("table", json);
        pageVariables.put("rows", allUsers.size());
        resp.getWriter().println(PageGenerator.getInstance().getPage("MainPage.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}