import com.google.gson.Gson;
import com.google.gson.JsonArray;
import data.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="MovieServlet", urlPatterns="/movies/*")
public class MovieServlet extends HttpServlet{

    ArrayList<Movie> movies = new ArrayList<>();
    int nextID = 0;


    Movie treasurePlanet = new Movie("Treasure Planet", 10, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fmovies.disney.com%2Ftreasure-planet&psig=AOvVaw2kUHT7WcGgMTP9PLJ0uSga&ust=1649184789124000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCIDi4puK-_YCFQAAAAAdAAAAABAD", 2002, "fantasy", "Jhon Musker", "A really cool movie", "actors", 0);
    String movieString = new Gson().toJson(treasurePlanet);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);
            for (Movie movie : newMovies) {
                movie.setId(nextID++);
                movies.add(movie);
            }
        } catch (IOException e) {
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
        response.setContentType("application/json");
        String [] uriParts = request.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        for (Movie movie: movies) {
            if(movie.getId() == targetId) {
                try {
                    Movie editedMovie = new Gson().fromJson(request.getReader(), Movie.class);
                    if(editedMovie.getTitle() != null) {
                        movie.setTitle(editedMovie.getTitle());
                    }
                    if(editedMovie.getPlot() != null) {
                        movie.setPlot(editedMovie.getPlot());
                    }
                    if(editedMovie.getRating() != 0) {
                        movie.setRating(editedMovie.getRating());
                    }
                    if(editedMovie.getPoster()!= null) {
                        movie.setPoster(editedMovie.getPoster());
                    }
                    if(editedMovie.getYear() != 0) {
                        movie.setYear(editedMovie.getYear());
                    }

                    if(editedMovie.getGenre()!= null) {
                        movie.setGenre(editedMovie.getGenre());
                    }
                    if(editedMovie.getDirector()!= null) {
                        movie.setDirector(editedMovie.getDirector());
                    }
                    if(editedMovie.getActors()!= null) {
                        movie.setActors(editedMovie.getActors());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("Movie edited");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
