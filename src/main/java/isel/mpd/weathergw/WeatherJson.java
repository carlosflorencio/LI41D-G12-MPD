package isel.mpd.weathergw;


import java.util.stream.Stream;

public class WeatherJson {

    public static String response = "{\"login\":\"achiu\",\"id\":24772,\"avatar_url\":\"https://avatars.githubusercontent.com/u/24772?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/achiu\",\"html_url\":\"https://github.com/achiu\",\"followers_url\":\"https://api.github.com/users/achiu/followers\",\"following_url\":\"https://api.github.com/users/achiu/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/achiu/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/achiu/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/achiu/subscriptions\",\"organizations_url\":\"https://api.github.com/users/achiu/orgs\",\"repos_url\":\"https://api.github.com/users/achiu/repos\",\"events_url\":\"https://api.github.com/users/achiu/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/achiu/received_events\",\"type\":\"User\",\"site_admin\":true,\"name\":\"Arthur Chiu\",\"company\":\"GitHub\",\"blog\":\"\",\"location\":\"San Francisco, CA\",\"email\":\"achiu@github.com\",\"hireable\":false,\"bio\":null,\"public_repos\":51,\"public_gists\":37,\"followers\":200,\"following\":38,\"created_at\":\"2008-09-16T03:24:44Z\",\"updated_at\":\"2015-04-09T13:59:15Z\"}";

    public static void main(String[] args) {

        Stream.of(response.substring(0, response.length() - 1)
                          .split("[{|,]\""))
                .filter(s -> !s.isEmpty())
                .forEach(WeatherJson::printData);
    }

    public static void printData(String row) {
        System.out.print("chave=" + getKey(row));
        System.out.println("|valor=" + getValue(row));
    }

    public static String getKey(String row) {
        return row.trim().substring(0, row.indexOf(':')-1);
    }

    public static String getValue(String row) {
        return row.substring(row.indexOf(':') + 1, row.length()).trim();
    }
}
