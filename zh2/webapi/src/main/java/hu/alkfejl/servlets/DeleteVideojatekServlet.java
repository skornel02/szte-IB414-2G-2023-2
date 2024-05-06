package hu.alkfejl.servlets;

import com.google.gson.Gson;
import hu.alkfejl.controller.VideojatekController;
import hu.alkfejl.model.Videojatek;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/delete/*")
public class DeleteVideojatekServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setStatus(HttpStatus.OK.value());

        var name = req.getPathInfo();

        if (name == null || name.isEmpty()) {
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        name = name.substring(1);

        var db = VideojatekController.getInstance(this.getClass());

        var game = new Videojatek();
        game.setCim(name);

        if (!db.delete(game)) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
