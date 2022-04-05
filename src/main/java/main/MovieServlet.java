package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {
    Movie movie = new Movie(4,
            "Gangs of New York",
            "Martin Scorsese",
            "https://m.media-amazon.com/images/M/MV5BNDg3MmI1ZDYtMDZjYi00ZWRlLTk4NzEtZjY4Y2U0NjE5YmRiXkEyXkFqcGdeQXVyNzAxMjE1NDg@._V1_SX300.jpg",
            "20 Dec 2002",
            2001,
            "Crime, Drama",
            "In 1862, Amsterdam Vallon returns to the Five Points area of New York City seeking revenge against Bill the Butcher, his " +
                    "father's killer.",
            "R",
            7.5,
            167,
            "Leonardo DiCaprio, Cameron Diaz, Daniel Day-Lewis");

    ArrayList<Movie> movies = new ArrayList<>();
    int movieId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);
        for (Movie movie : newMovies) {
            movie.setId(movieId++);
            movies.add(movie);
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("Added Movies");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        BufferedReader br = request.getReader();
        Movie editedMovie = new Gson().fromJson(br, Movie.class);
        try {
            PrintWriter out = response.getWriter();
            String [] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            for(Movie movie : movies) {
                if (targetId == movie.getId()){
                    movies.set(movies.indexOf(movie),editedMovie);
                }
            }
            out.println("Movie edited");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            movies.removeIf(movie -> movie.getId() == targetId);
            PrintWriter out = response.getWriter();
            out.println("Deleted Movie");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
