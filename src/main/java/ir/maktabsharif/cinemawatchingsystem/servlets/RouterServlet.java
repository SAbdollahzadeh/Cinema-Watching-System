package ir.maktabsharif.cinemawatchingsystem.servlets;

import ir.maktabsharif.cinemawatchingsystem.controller.Controller;
import ir.maktabsharif.cinemawatchingsystem.controller.IndexController;
import ir.maktabsharif.cinemawatchingsystem.controller.MovieController;
import ir.maktabsharif.cinemawatchingsystem.controller.UserController;
import ir.maktabsharif.cinemawatchingsystem.exceptions.RouterNotFound;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/app/*")
@MultipartConfig
public class RouterServlet extends HttpServlet {

    Map<String, Controller> controllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        controllers.put("/user", new UserController());
        controllers.put("/movie", new MovieController());
        controllers.put("/index", new IndexController());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String rawPathInfo = req.getPathInfo();

        String pathInfo =
                (rawPathInfo == null || rawPathInfo.trim().isEmpty() || rawPathInfo.equals("/")) ?
                        "/user/index" :
                        rawPathInfo;

        Controller controller = controllers.entrySet()
                .stream()
                .filter(es -> pathInfo.startsWith(es.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().orElseThrow(() -> new RouterNotFound("Invalid URL"));


        String jsp = controller.handle(req, resp);
        if(jsp.startsWith("redirect:")) {
            String redirectPath = jsp.substring("redirect:".length());
            resp.sendRedirect(req.getContextPath().concat(redirectPath));
            return;
        } else {
            String uri = "/".concat(jsp).concat(".jsp");
            req.getRequestDispatcher(uri).forward(req, resp);
        }
    }
}
