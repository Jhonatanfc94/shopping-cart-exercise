package auxiliary;

public class Data {
    public static class downloads {
        public final static String downloadDirectory = System.getProperty("user.dir")+"\\downloads\\";
    }

    public static class emailTesting{
        public final static String user = "email";
        public final static String password = "password";
        //https://myaccount.google.com/u/0/apppasswords requires 2FA
        public final static String userRegression = "regressionEmail";
    }
    public static class time{
        public final static String timeZone = "America/Guatemala";
    }
    public static class variables {
        public final static String environment = "QA";
        public final static String link;
        static {
            if (environment.equals("UAT")) {
                link = "https:stage.com/";
            } else if (environment.equals("QA")) {
                link = "https:qa.com/";
            } else {
                link = "https:dev.com/";
            }
        }
    }
}
