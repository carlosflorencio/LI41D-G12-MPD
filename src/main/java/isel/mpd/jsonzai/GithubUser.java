package isel.mpd.jsonzai;

/**
 * Created by Nuno on 05/04/2015.
 */
public class GithubUser {

    private String login;
    private int id;
    private String email;
    private String location;

    public GithubUser(){
        login = null;
        id = -1;
        email = null;
        location = null;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
