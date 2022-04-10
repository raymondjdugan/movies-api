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
public class MovieServlet extends HttpServlet {
    MoviesDao moviesDao = getMoviesDao(MYSQL);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            try {
                String[] uriParts = request.getRequestURI().split("/");
                int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
                outputMessage(response, "application/json", new Gson().toJson(
                        moviesDao.findOne(targetId)));
            } catch (NumberFormatException e) {
                outputMessage(response, "application/json", new Gson().toJson(
                        moviesDao.all()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Movie[] movies = new Movie[0];
        try {
            movies = new Gson().fromJson(request.getReader(), Movie[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            moviesDao.insertAll(movies);
        } catch (SQLException e) {
            outputMessage(response, "application/json", new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        }
        response.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String[] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            Movie movie = new Gson().fromJson(request.getReader(), Movie.class);
            movie.setId(targetId);
            moviesDao.update(movie);
        } catch (SQLException e) {
            outputMessage(response, "application/json", new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            outputMessage(response, "application/json", new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(400);
            e.printStackTrace();
            return;
        }
        response.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int targetId = 0;
            try {
                String[] uriParts = request.getRequestURI().split("/");
                targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            moviesDao.delete(targetId);
        } catch (SQLException e) {
            outputMessage(response, "application/json", new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
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
