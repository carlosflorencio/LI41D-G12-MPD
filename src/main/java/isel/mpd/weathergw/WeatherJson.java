package isel.mpd.weathergw;


import isel.mpd.jsonzai.JsonParser;
import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.entities.GithubUser;
import isel.mpd.weather.data.HttpUrlStreamSupplier;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WeatherJson {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        testGetUser();
        testGetUserRepos();
    }

    private static void testGetUser() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        HttpUrlStreamSupplier url = new HttpUrlStreamSupplier("https://api.github.com/users/achiu");

        JsonParser parser = new JsonParser();

        GithubUser user = parser.toObject(new SimpleStringSupplierFromStream(url).get(), GithubUser.class);

        System.out.println("User Email = " + user.email);
    }

    private static void testGetUserRepos() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        HttpUrlStreamSupplier url = new HttpUrlStreamSupplier("https://api.github.com/users/achiu/repos");

        JsonParser parser = new JsonParser();

        List<GithubRepo> repos = parser.<GithubRepo>toList(new SimpleStringSupplierFromStream(url).get(), GithubRepo.class);

        System.out.println("Total repositories:" + repos.size());
    }

}
