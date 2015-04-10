package isel.mpd.weathergw;


import java.util.stream.Stream;

public class WeatherJson {

    public static String response = "{\"login\":\"achiu\",\"id\":24772,\"avatar_url\":\"https://avatars.githubusercontent.com/u/24772?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/achiu\",\"html_url\":\"https://github.com/achiu\",\"followers_url\":\"https://api.github.com/users/achiu/followers\",\"following_url\":\"https://api.github.com/users/achiu/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/achiu/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/achiu/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/achiu/subscriptions\",\"organizations_url\":\"https://api.github.com/users/achiu/orgs\",\"repos_url\":\"https://api.github.com/users/achiu/repos\",\"events_url\":\"https://api.github.com/users/achiu/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/achiu/received_events\",\"type\":\"User\",\"site_admin\":true,\"name\":\"Arthur Chiu\",\"company\":\"GitHub\",\"blog\":\"\",\"location\":\"San Francisco, CA\",\"email\":\"achiu@github.com\",\"hireable\":false,\"bio\":null,\"public_repos\":51,\"public_gists\":37,\"followers\":200,\"following\":38,\"created_at\":\"2008-09-16T03:24:44Z\",\"updated_at\":\"2015-04-09T13:59:15Z\"}";

    public static String response1 = "{\n" +
            "  \"login\": \"achiu\",\n" +
            "  \"id\": 24772,\n" +
            "  \"avatar_url\": \"https://avatars.githubusercontent.com/u/24772?v=3\",\n" +
            "  \"gravatar_id\": \"\",\n" +
            "  \"url\": \"https://api.github.com/users/achiu\",\n" +
            "  \"html_url\": \"https://github.com/achiu\",\n" +
            "  \"followers_url\": \"https://api.github.com/users/achiu/followers\",\n" +
            "  \"following_url\": \"https://api.github.com/users/achiu/following{/other_user}\",\n" +
            "  \"gists_url\": \"https://api.github.com/users/achiu/gists{/gist_id}\",\n" +
            "  \"starred_url\": \"https://api.github.com/users/achiu/starred{/owner}{/repo}\",\n" +
            "  \"subscriptions_url\": \"https://api.github.com/users/achiu/subscriptions\",\n" +
            "  \"organizations_url\": \"https://api.github.com/users/achiu/orgs\",\n" +
            "  \"repos_url\": \"https://api.github.com/users/achiu/repos\",\n" +
            "  \"events_url\": \"https://api.github.com/users/achiu/events{/privacy}\",\n" +
            "  \"received_events_url\": \"https://api.github.com/users/achiu/received_events\",\n" +
            "  \"type\": \"User\",\n" +
            "  \"site_admin\": true,\n" +
            "  \"name\": \"Arthur Chiu\",\n" +
            "  \"company\": \"GitHub\",\n" +
            "  \"blog\": \"\",\n" +
            "  \"location\": \"San Francisco, CA\",\n" +
            "  \"email\": \"achiu@github.com\",\n" +
            "  \"hireable\": false,\n" +
            "  \"bio\": null,\n" +
            "  \"public_repos\": 51,\n" +
            "  \"public_gists\": 37,\n" +
            "  \"followers\": 200,\n" +
            "  \"following\": 38,\n" +
            "  \"created_at\": \"2008-09-16T03:24:44Z\",\n" +
            "  \"updated_at\": \"2015-04-09T13:59:15Z\"\n" +
            "}";

    public static void main(String[] args) {

        //remove all whitespaces (tabs, spaces, new lines) that are not between quotes
        response1 = response1.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
        Stream.of(response1.substring(0, response.length() - 1)
                          .split("[{|,]\""))
                .filter(s -> !s.isEmpty())
                .forEach(s -> System.out.println(s));


    }


}
