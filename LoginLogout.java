public class LoginLogout {
    private String date;
    private String time;
    private String log;

    LoginLogout(boolean log, String time, String date) {
        //if log==true => log=login.....else log=logout.
        if (log) {
            this.log = "login";
        } else {
            this.log = "logout";
        }
        this.date = date;
        this.time = time;
    }
}