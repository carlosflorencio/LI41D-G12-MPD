package isel.mpd.weathergw;

import isel.mpd.weather.data.HttpUrlStreamSupplier;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;


public class WeatherJson {


    public static void main(String[] args) {
        String url = "https://api.github.com/users/achiu";
        HttpUrlStreamSupplier is = new HttpUrlStreamSupplier(url);
        String response = new SimpleStringSupplierFromStream(() -> is.get()).get();

        System.out.println(response);
    }
}
