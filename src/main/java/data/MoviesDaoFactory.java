package data;
import data.InMemoryMoviesDao;
import data.MoviesDao;
import data.MySqlMoviesDao;

public class MoviesDaoFactory  {
    private static Config config = new Config();

    // private static Config config = new Config();

    public enum DAOType {MYSQL, IN_MEMORY}

     //Notice we have two values here

    public static MoviesDao getMoviesDao(DAOType daoType) {

        switch (daoType) {
            case IN_MEMORY: { //yet we have one switch case. We'll get to that!
                return new InMemoryMoviesDao();
            }
            case MYSQL:{ // <-- added this
                return new MySqlMoviesDao(config);
            }
        }
        return null;
    }

}
