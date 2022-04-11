package data;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {

    public Connection connection = null;

    public MySqlMoviesDao(Config config) {
        //TODO: We will configure our connections here
        try {
            DriverManager.registerDriver(new Driver());
            this.connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!, e");
        }
    }

    @Override
    public List<Movie> all() throws SQLException {
        // TODO: Get ALL the movies
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM movies");

        List<Movie> movies = new ArrayList<>();

        while (resultSet.next()) {
            movies.add(new Movie(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("director"),
                    resultSet.getString("poster"),
                    resultSet.getString("dateReleased"),
                    resultSet.getInt("yearMade"),
                    resultSet.getString("genre"),
                    resultSet.getString("plot"),
                    resultSet.getString("rating"),
                    resultSet.getDouble("imdb"),
                    resultSet.getString("runtime"),
                    resultSet.getString("actors")));
        }
        return movies;
    }

    @Override
    public Movie findOne(int id) throws SQLException {
        // TODO: Get one movie by id
        Movie movie = null;

        String sql = "SELECT * FROM movies WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        movie = new Movie(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("director"),
                resultSet.getString("poster"),
                resultSet.getString("dateReleased"),
                resultSet.getInt("yearMade"),
                resultSet.getString("genre"),
                resultSet.getString("plot"),
                resultSet.getString("rating"),
                resultSet.getDouble("imdb"),
                resultSet.getString("runtime"),
                resultSet.getString("actors"));

        return movie;
    }

    @Override
    public void insert(Movie movie) throws SQLException {
        // TODO: Insert one movie
        // TODO: BUILD SQL TEMPLATE
        StringBuilder sql = new StringBuilder("INSERT INTO movies (title, director, poster, dateReleased, yearMade, genre, plot, rating, imdb, " +
                "runtime, actors)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        //TODO: USE THE SQL STRING TO CREATE A PREPARED STATMENT


        PreparedStatement statement = connection.prepareStatement(sql.toString());

        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setString(3, movie.getPoster());
        statement.setString(4, movie.getDateReleased());
        statement.setInt(5, movie.getYearMade());
        statement.setString(6, movie.getGenre());
        statement.setString(7, movie.getPlot());
        statement.setString(8, movie.getRating());
        statement.setDouble(9, movie.getImdb());
        statement.setString(10, movie.getRuntime());
        statement.setString(11, movie.getActors());

        statement.executeUpdate();
    }

    public void insertAll(Movie[] movies) throws SQLException {
        for (Movie movie : movies) {
            insert(movie);
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        //TODO: Update a movie here!
        // TODO: BUILD SQL TEMPLATE
        String query = ("UPDATE movies SET title = ?, director = ?, poster = ?, dateReleased = ?, yearMade = ?, genre = ?, plot = ?, rating = ?, " +
                "imdb = ?, runtime = ?, actors = ? WHERE id = ?");

        //TODO: USE THE SQL STRING TO CREATE A PREPARED STATMENT
        PreparedStatement statement = connection.prepareStatement(query.toString());

        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setString(3, movie.getPoster());
        statement.setString(4, movie.getDateReleased());
        statement.setInt(5, movie.getYearMade());
        statement.setString(6, movie.getGenre());
        statement.setString(7, movie.getPlot());
        statement.setString(8, movie.getRating());
        statement.setDouble(9, movie.getImdb());
        statement.setString(10, movie.getRuntime());
        statement.setString(11, movie.getActors());
        statement.setInt(12, movie.getId());


        statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie
        // TODO: BUILD SQL TEMPLATE
        String sql = "DELETE FROM movies WHERE id = ?";

        //TODO: USE THE SQL STRING TO CREATE A PREPARED STATMENT
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        statement.setInt(1, id);

        statement.execute();
    }
}
