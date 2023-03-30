package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws SQLException {
        ArrayList<String> lines = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(x -> lines.add(x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Movie> movies = parser(lines);
        saveDB(movies);
    }

    private ArrayList<Movie> parser(ArrayList<String> lines) {
        ArrayList<Movie> movies = new ArrayList<>();
        lines.remove(0);
        lines.remove(0);
        for (String line : lines) {
            char[] odj = line.toCharArray();
            ArrayList<String> liters = new ArrayList<>();
            StringBuilder liter = new StringBuilder();
            for (int i = 0; i < odj.length; i++) {
                if (odj[i] == ';') {
                    if (odj[i - 1] == ';') {
                        liters.add(null);
                    } else {
                        liters.add(liter.toString());
                        liter.setLength(0);
                    }
                } else {
                    liter.append(odj[i]);
                }
            }
            Movie movie = new Movie();

            movie.setYear(Integer.valueOf(liters.get(0)));
            if (liters.get(1) != null) {
                movie.setLength(Integer.valueOf(liters.get(1)));
            } else {
                movie.setLength(null);
            }
            movie.setTitle(liters.get(2));
            movie.setSubject(liters.get(3));
            movie.setActors(liters.get(4));
            movie.setActress(liters.get(5));
            movie.setDirector(liters.get(6));
            if (liters.get(7) != null) {
                movie.setPopularity(Integer.valueOf(liters.get(7)));
            } else {
                movie.setPopularity(null);
            }
            if (liters.get(8).equals("No")) {
                movie.setAwards(false);
            }
            if (liters.get(8).equals("Yes")) {
                movie.setAwards(true);
            }
            movies.add(movie);
        }
        return movies;
    }

    private void saveDB(ArrayList<Movie> movies) throws SQLException {
        String sqlInsert = "INSERT INTO movie (year, length, title, subject, actors, " +
                "actress, director, popularity, awards) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        for (int i = 0; i < movies.size(); i++) {
            preparedStatement.setInt(1, movies.get(i).getYear());
            if (movies.get(i).getLength() != null) {
                preparedStatement.setInt(2, movies.get(i).getLength());
            } else {
                preparedStatement.setNull(2, 0);
            }
            preparedStatement.setString(3, movies.get(i).getTitle());
            preparedStatement.setString(4, movies.get(i).getSubject());
            preparedStatement.setString(5, movies.get(i).getActors());
            preparedStatement.setString(6, movies.get(i).getActress());
            preparedStatement.setString(7, movies.get(i).getDirector());
            if (movies.get(i).getPopularity() != null) {
                preparedStatement.setInt(8, movies.get(i).getPopularity());
            } else {
                preparedStatement.setNull(8, 0);
            }
            preparedStatement.setBoolean(9, movies.get(i).getAwards());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        dataSource.getConnection().close();
    }

}
