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
        User changedUser = UserService.getInstance().getUserByID(id);

        pageVariables.put("id", id);
        pageVariables.put("firstName", changedUser.getFirstName());
        pageVariables.put("secondName", changedUser.getSecondName());
        pageVariables.put("userName", changedUser.getUserName());
        pageVariables.put("password", changedUser.getPassword());
        pageVariables.put("age", changedUser.getAge());
        pageVariables.put("gender", changedUser.getGender());
        if (changedUser.getGender().equals("male")) {
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

        Long id = Long.parseLong(req.getParameter("id"));
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String age = req.getParameter("age");
        String gender = req.getParameter("gender");

        if (!password.equals(confirmPassword)) {
            pageVariables.put("message", "Error: Entered passwords do not match!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (firstName.equals("") ||
                secondName.equals("") ||
                userName.equals("") ||
                password.equals("") ||
                age.equals("") ||
                gender.equals("")) {
            pageVariables.put("message", "Error: All fields are required!");
            resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {

            User changedUser = new User(id, firstName, secondName, userName, password, Long.parseLong(age), gender);

            String result = UserService.getInstance().changeUser(changedUser);
            if (result.contains("Error:")) {
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
