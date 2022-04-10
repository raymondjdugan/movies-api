package main;

import com.google.gson.Gson;
import data.Movie;
import data.MoviesDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static data.MoviesDaoFactory.DAOType.MYSQL;
import static data.MoviesDaoFactory.getMoviesDao;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServletSingle extends HttpServlet {
    MoviesDao moviesDao = getMoviesDao(MYSQL);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        Movie movie = new Movie();
        try {
            movie = new Gson().fromJson(request.getReader(), Movie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            moviesDao.insert(movie);
        } catch (SQLException e) {
            outputMessage(response, "application/json", new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        }
        response.setStatus(200);
    }

    private void outputMessage(HttpServletResponse response, String contentType, String message) {

        try {
            response.setContentType(contentType);
            PrintWriter out = response.getWriter();
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
