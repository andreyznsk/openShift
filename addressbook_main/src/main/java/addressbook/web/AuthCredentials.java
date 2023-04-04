package addressbook.web;

public class AuthCredentials {

    private final String login;
    private final String loginPdi;

    public AuthCredentials(String login, String loginPdi) {
        this.login = login;
        this.loginPdi = loginPdi;
    }

    public String getLogin() {
        return login;
    }

    public String getLoginPdi() {
        return loginPdi;
    }

    @Override
    public String toString() {
        return "AuthCredentials{" +
                "login='" + login + '\'' +
                ", sberPdi='" + loginPdi + '\'' +
                '}';
    }
}
