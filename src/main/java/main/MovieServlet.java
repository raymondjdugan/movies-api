package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        try {
            Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);
            for (Movie movie : newMovies) {
                movie.setId(movieId++);
                movies.add(movie);
            }
            PrintWriter out = response.getWriter();
            out.println("Added Movies");
        } catch (IOException e) {
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Movie editedMovie = new Gson().fromJson(request.getReader(), Movie.class);
        try {
            PrintWriter out = response.getWriter();
            String[] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            for (Movie movie : movies) {
                if (targetId == movie.getId()) {
                    if (editedMovie.getTitle() != null) {
                        movie.setTitle(editedMovie.getTitle());
                    }
                    if (editedMovie.getPlot() != null) {
                        movie.setPlot(editedMovie.getPlot());
                    }
                    if (editedMovie.getRating() != null) {
                        movie.setRating(editedMovie.getRating());
                    }
                    if (editedMovie.getPoster() != null) {
                        movie.setPoster(editedMovie.getPoster());
                    }
                    if (editedMovie.getYearMade() != 0) {
                        movie.setYearMade(editedMovie.getYearMade());
                    }
                    if (editedMovie.getGenre() != null) {
                        movie.setGenre(editedMovie.getGenre());
                    }
                    if (editedMovie.getDirector() != null) {
                        movie.setDirector(editedMovie.getDirector());
                    }
                    if (editedMovie.getActors() != null) {
                        movie.setActors(editedMovie.getActors());
                    }
                    if (editedMovie.getDateReleased() != null) {
                        movie.setDateReleased(editedMovie.getDateReleased());
                    }
                    if (editedMovie.getImdb() > 0) {
                        movie.setImdb(editedMovie.getImdb());
                    }
                    if (editedMovie.getRuntime() > 0) {
                        movie.setRuntime(editedMovie.getRuntime());
                    }
                }
            }
            out.println("Movie edited");
        } catch (IOException e) {
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        int targetId = 0;
        try {
            try {
                String [] uriParts = request.getRequestURI().split("/");
                targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            } catch (Exception e) {}
            int finalTargetId = targetId;
            movies.removeIf(movie -> movie.getId() == finalTargetId);
            PrintWriter out = response.getWriter();
            out.println("Deleted Movie");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
