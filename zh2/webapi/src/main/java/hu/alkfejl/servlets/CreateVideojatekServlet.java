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
import java.util.Arrays;

@WebServlet("/api/add")
public class CreateVideojatekServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setStatus(HttpStatus.CREATED.value());

        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .map(ck -> new Cookie(ck.getName(), "modified" + ck.getValue()))
                    .forEach(resp::addCookie);
        }

        var gson = new Gson();
        var db = VideojatekController.getInstance(this.getClass());

        Videojatek game;
        try {
            game = gson.fromJson(req.getReader(), Videojatek.class);
        } catch (Exception ex) {
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        if (!db.add(game)) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


    }
}
