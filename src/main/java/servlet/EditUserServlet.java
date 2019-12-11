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

public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> pageVariables = new HashMap<>();
        Long id = Long.parseLong(req.getParameter("id"));
        User userToEdit = UserService.getInstance().getUserByID(id);
        pageVariables.put("id", id);
        pageVariables.put("firstName", userToEdit.getFirstName());
        pageVariables.put("secondName", userToEdit.getSecondName());
        pageVariables.put("userName", userToEdit.getUserName());
        pageVariables.put("password", userToEdit.getPassword());
        pageVariables.put("age", userToEdit.getAge());
        pageVariables.put("gender", userToEdit.getGender());
        if (userToEdit.getGender().equals("male")) {
            pageVariables.put("agender", "female");
        } else {
            pageVariables.put("agender", "male");
        }

        resp.getWriter().println(PageGenerator.getInstance().getPage("EditUserPage.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap pageVariables = new HashMap();

        if(!req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
            pageVariables.put("message", "Error: Entered passwords do not match!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (req.getParameter("firstName").equals("") ||
                req.getParameter("secondName").equals("") ||
                req.getParameter("userName").equals("") ||
                req.getParameter("password").equals("") ||
                req.getParameter("age").equals("") ||
                req.getParameter("gender") == null) {
            pageVariables.put("message", "Error: All fields are required!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            User userForChange = new User();
            userForChange.setId(Long.parseLong(req.getParameter("id")));
            userForChange.setFirstName(req.getParameter("firstName"));
            userForChange.setSecondName(req.getParameter("secondName"));
            userForChange.setUserName(req.getParameter("userName"));
            userForChange.setPassword(req.getParameter("password"));
            userForChange.setAge(Long.parseLong(req.getParameter("age")));
            userForChange.setGender(req.getParameter("gender"));

            String result = UserService.getInstance().changeUser(userForChange);
            if (result.equals("Error: User does not exist!")) {
                pageVariables.put("message", result);
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                pageVariables.put("message", result);
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
