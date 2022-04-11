package data;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao{

    private Connection connection = null;

    public MySqlMoviesDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());

            this.connection = DriverManager.getConnection(
                    config.getUrl(), // <-- WHERE IS THE DB?
                    config.getUser(), // <-- WHO IS ACCESSING?
                    config.getPassword() // <-- WHAT IS THEIR PASSWORD?
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

    @Override
    public List<Movie> all() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM wesley.movies");

        List<Movie> movies = new ArrayList<>();

        while (rs.next()) {
            movies.add(new Movie(
                    rs.getString("title"),
                    rs.getInt("rating"),
                    rs.getString("poster"),
                    rs.getInt("year"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("plot"),
                    rs.getString("actors"),
                    rs.getInt("id")
            ));
        }
        return movies;
    }

    @Override
    public Movie findOne(int id){
        String sql = "select * from wesley.movies where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Movie movie = new Movie();
            movie.setTitle(rs.getString("title"));
            movie.setId(rs.getInt("id"));
            movie.setActors(rs.getString("actors"));
            movie.setDirector(rs.getString("director"));
            movie.setGenre(rs.getString("genre"));
            movie.setPlot(rs.getString("plot"));
            movie.setPoster(rs.getString("poster"));
            movie.setRating(rs.getInt("rating"));
            movie.setYear(rs.getInt("year"));
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Movie movie) {
        String sql = "insert into wesley.movies (title, year, director, actors, rating, poster, genre, plot)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getActors());
            statement.setInt(5, movie.getRating());
            statement.setString(6, movie.getPoster());
            statement.setString(7, movie.getGenre());
            statement.setString(8, movie.getPlot());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // Build sql template
        for (Movie movie: movies) {
            insert(movie);
        }
    }

    @Override
    public void update(Movie movie){
        try {
            Movie oldData = findOne(movie.getId());
            String sql = "update wesley.movies " +
                    "set title = ?, " +
                    "year = ?, " +
                    "director = ?, " +
                    "actors = ?, " +
                    "rating = ?, " +
                    "poster = ?, " +
                    "genre = ?, " +
                    "plot = ?" +
                    "where id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            if (movie.getTitle() != null) {
                statement.setString(1, movie.getTitle());
            } else {
                statement.setString(1, oldData.getTitle());
            }
            if (movie.getYear() != 0) {
                statement.setInt(2, movie.getYear());
            } else {
                statement.setInt(2, oldData.getYear());
            }
            if (movie.getDirector() != null) {
                statement.setString(3, movie.getDirector());
            } else {
                statement.setString(3, oldData.getDirector());
            }
            if (movie.getActors() != null) {
                statement.setString(4, movie.getActors());
            } else {
                statement.setString(4, oldData.getActors());
            }
            if (movie.getRating() != 0) {
                statement.setInt(5, movie.getRating());
            } else {
                statement.setInt(5, oldData.getRating());
            }
            if (movie.getPoster() != null) {
                statement.setString(6, movie.getPoster());
            } else {
                statement.setString(6, oldData.getPoster());
            }
            if (movie.getGenre() != null) {
                statement.setString(7, movie.getGenre());
            } else {
                statement.setString(7, oldData.getGenre());
            }
            if (movie.getPlot() != null) {
                statement.setString(8, movie.getPlot());
            } else {
                statement.setString(8, oldData.getPlot());
            }
            if (movie.getId() != 0) {
                statement.setInt(9, movie.getId());
            } else {
                statement.setInt(9, oldData.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql =
                "DELETE FROM wesley.movies " +
                        "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.execute();
    }

}
