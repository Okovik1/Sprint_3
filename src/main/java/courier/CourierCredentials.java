package courier;

public class CourierCredentials {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public CourierCredentials(){}

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password=password;
    }
}
