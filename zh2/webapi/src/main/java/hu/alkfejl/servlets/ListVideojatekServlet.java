package hu.alkfejl.servlets;

import com.google.gson.Gson;
import hu.alkfejl.controller.VideojatekController;
import hu.alkfejl.model.Videojatek;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = {"/api/list", "/alista"})
public class ListVideojatekServlet extends HttpServlet {

    private static int count = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setStatus(HttpStatus.OK.value());

        var fejleszto = req.getParameter("fejleszto");
        var besorolasStr = req.getParameter("besorolas");

        var gson = new Gson();
        var db = VideojatekController.getInstance(this.getClass());
        var filter = new Videojatek();
        filter.setFejleszto(fejleszto);
        try {
            var besorolas = Integer.parseInt(besorolasStr);
            filter.setBesorolas(besorolas);
        } catch (Exception ignored) {}

        var games = db.find(filter);

        var json = gson.toJson(games);

        resp.getWriter().print(json);

        resp.addCookie(new Cookie("request-number", String.valueOf(++count)));
    }
}
