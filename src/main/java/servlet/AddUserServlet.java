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

public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("AddUserPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> pageVariables = new HashMap<>();

        if(!req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
            pageVariables.put("message", "Entered passwords do not match!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (req.getParameter("firstName").equals("") ||
                req.getParameter("secondName").equals("") ||
                req.getParameter("userName").equals("") ||
                req.getParameter("password").equals("") ||
                req.getParameter("age").equals("") ||
                req.getParameter("gender") == null) {
            pageVariables.put("message", "All fields are required!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            User newUser = new User();
            newUser.setFirstName(req.getParameter("firstName"));
            newUser.setSecondName(req.getParameter("secondName"));
            newUser.setUserName(req.getParameter("userName"));
            newUser.setPassword(req.getParameter("password"));
            newUser.setAge(Long.parseLong(req.getParameter("age")));
            newUser.setGender(req.getParameter("gender"));

            if (UserService.getInstance().addUser(newUser)) {
                pageVariables.put("message", "User was added!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            }
        }
    }
}
