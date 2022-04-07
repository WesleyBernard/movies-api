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
        // TODO: Get ALL the movies
    }

    @Override
    public Movie findOne(int id) {
        // TODO: Get one movie by id
    }

    @Override
    public void insert(Movie movie) {
        // TODO: Insert one movie
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // Build sql template
        StringBuilder sql = new StringBuilder("INSERT INTO movies (" +
                "title, year, director, actors, rating, poster, genre, plot) " +
                "VALUES ");


        // Add a interpolation template for each element in movies list
        sql.append("(?, ?, ?, ?, ?, ?, ?, ?), ".repeat(movies.length));

        // Create a new String and take off the last comma and whitespace
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));

        // Use the sql string to create a prepared statement
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        // Add each movie to the prepared statement using the index of each sql param: '?'
        // This is done by using a counter
        // You could use a for loop to do this as well and use its incrementor
        int counter = 0;
        for (Movie movie : movies) {
            statement.setString((counter * 8) + 1, movie.getTitle());
            statement.setInt((counter * 8) + 2, movie.getYear());
            statement.setString((counter * 8) + 3, movie.getDirector());
            statement.setString((counter * 8) + 4, movie.getActors());
            statement.setInt((counter * 8) + 5, movie.getRating());
            statement.setString((counter * 8) + 6, movie.getPoster());
            statement.setString((counter * 8) + 7, movie.getGenre());
            statement.setString((counter * 8) + 8, movie.getPlot());
            counter++;
        }
        statement.executeUpdate();
    }

    @Override
    public void update(Movie movie) throws SQLException {
        StringBuilder sql = new StringBuilder("update movies" +
                "set title = ?, " +
                "year = ?, " +
                "director = ?, " +
                "actors = ?, " +
                "rating = ?, "+
                "poster = ?, " +
                "genre = ?, " +
                "plot = ?" +
                "where id = ?");

        PreparedStatement statement = connection.prepareStatement(sql.toString());
        statement.setString(1, movie.getTitle());
        statement.setInt( 2, movie.getYear());
        statement.setString(3, movie.getDirector());
        statement.setString( 4, movie.getActors());
        statement.setInt(5, movie.getRating());
        statement.setString( 6, movie.getPoster());
        statement.setString(7, movie.getGenre());
        statement.setString( 8, movie.getPlot());
        statement.setInt(9, movie.getId());

        statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie
    }

}
