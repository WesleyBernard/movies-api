import com.google.gson.Gson;
import com.google.gson.JsonArray;
import data.Movie;
import data.MoviesDao;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import static data.MoviesDaoFactory.DAOType.IN_MEMORY;
import static data.MoviesDaoFactory.DAOType.MYSQL;
import static data.MoviesDaoFactory.getMoviesDao;

@WebServlet(name="MovieServlet", urlPatterns="/movies/*")
public class MovieServlet extends HttpServlet{


    MoviesDao moviesDao = getMoviesDao(MYSQL);



    Movie treasurePlanet = new Movie("Treasure Planet", 10, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fmovies.disney.com%2Ftreasure-planet&psig=AOvVaw2kUHT7WcGgMTP9PLJ0uSga&ust=1649184789124000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCIDi4puK-_YCFQAAAAAdAAAAABAD", 2002, "fantasy", "Jhon Musker", "A really cool movie", "actors", 0);
    String movieString = new Gson().toJson(treasurePlanet);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(moviesDao.all());
            out.println(movieString);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);
                moviesDao.insertAll(newMovies);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter out = response.getWriter();
            out.println("Movie(s) added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            Movie movie = new Gson().fromJson(request.getReader(), Movie.class);
            movie.setId(targetId);
            moviesDao.update(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        int targetId;
        try {
            String [] uriParts = request.getRequestURI().split("/");
            targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
            moviesDao.delete(targetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PrintWriter out = response.getWriter();
            out.println("Deleted Movie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}