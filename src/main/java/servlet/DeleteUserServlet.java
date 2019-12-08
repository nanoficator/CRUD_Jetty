package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> pageVariables = new HashMap<>();
        if (req.getPathInfo().contains("all")) {
            resp.getWriter().println(PageGenerator.getInstance().getPage("DeleteAllPage.html", new HashMap<>()));
        } else if (req.getPathInfo().contains("user")) {
            pageVariables.put("id", req.getParameter("id"));
            resp.getWriter().println(PageGenerator.getInstance().getPage("DeleteUserPage.html", pageVariables));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> pageVariables = new HashMap<>();
        if (req.getPathInfo().contains("all")) {
            UserService.getInstance().deleteAllUsers();
            pageVariables.put("message", "Data is clear!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else if (req.getPathInfo().contains("user")) {
            Long userId = Long.parseLong(req.getParameter("id"));
            if (UserService.getInstance().deleteUserById(userId)) {
                pageVariables.put("message", "Success!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
