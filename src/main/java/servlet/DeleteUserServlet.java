package servlet;

import service.UserServiceHQL;
import service.UserServiceSQL;
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

        if (req.getPathInfo().contains("HQL")) {
            pageVariables.put("QL", "HQL");
        }
        if (req.getPathInfo().contains("SQL")) {
            pageVariables.put("QL", "SQL");
        }

        if (req.getPathInfo().contains("all")) {
            pageVariables.put("message", "all users");
            pageVariables.put("id", "all");
            resp.getWriter().println(PageGenerator.getInstance().getPage("DeleteUserPage.html", pageVariables));
        } else if (req.getPathInfo().contains("user")) {
            String userName = UserServiceHQL.getInstance().getUserByID(Long.parseLong(req.getParameter("id"))).getUserName();
            pageVariables.put("message", "user " + userName);
            pageVariables.put("id", "user?id" + req.getParameter("id"));
            resp.getWriter().println(PageGenerator.getInstance().getPage("DeleteUserPage.html", pageVariables));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> pageVariables = new HashMap<>();

        if (req.getPathInfo().contains("HQL")) {
            if (req.getPathInfo().contains("all")) {
                UserServiceHQL.getInstance().deleteAllUsers();
                pageVariables.put("message", "Data Base is clear!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            if (req.getPathInfo().contains("user")) {
                Long userId = Long.parseLong(req.getParameter("id"));

                String result = UserServiceHQL.getInstance().deleteUserById(userId);
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

        if (req.getPathInfo().contains("SQL")) {
            if (req.getPathInfo().contains("all")) {
                new UserServiceSQL().deleteAllUsers();
                pageVariables.put("message", "Data Base is clear!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("ResultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            if (req.getPathInfo().contains("user")) {
                Long userId = Long.parseLong(req.getParameter("id"));

                String result = new UserServiceSQL().deleteUserById(userId);
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
}
