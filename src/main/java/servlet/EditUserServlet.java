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
        User userToEdit = UserService.getInstance().findUserByID(id);
        pageVariables.put("id", id);
        pageVariables.put("firstName", userToEdit.getFirstName());
        pageVariables.put("secondName", userToEdit.getSecondName());
        pageVariables.put("userName", userToEdit.getUserName());
        pageVariables.put("age", userToEdit.getAge());
        pageVariables.put("gender", userToEdit.getGender());
        resp.getWriter().println(PageGenerator.getInstance().getPage("EditUserPage.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> pageVariables = new HashMap<>();

        User userForEdit = new User();
        userForEdit.setId(Long.parseLong(req.getParameter("id")));
        userForEdit.setFirstName(req.getParameter("firstName"));
        userForEdit.setSecondName(req.getParameter("secondName"));
        userForEdit.setUserName(req.getParameter("userName"));
        userForEdit.setPassword(req.getParameter("password"));
        userForEdit.setAge(Long.parseLong(req.getParameter("age")));
        userForEdit.setGender(req.getParameter("gender"));

        if (!userForEdit.getPassword().equals(req.getParameter("confirmPassword"))) {
            pageVariables.put("message", "Entered passwords do not match!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (UserService.getInstance().isExistUserName(userForEdit.getUserName())) {
            pageVariables.put("message", "Username is alredy exist!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (userForEdit.getFirstName().equals("") &&
                userForEdit.getSecondName().equals("") &&
                userForEdit.getUserName().equals("") &&
                userForEdit.getPassword().equals("") &&
                userForEdit.getAge().equals("") &&
                userForEdit.getGender() == null) {
            pageVariables.put("message", "At least one field is required for changes!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
