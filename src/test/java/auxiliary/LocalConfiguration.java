package auxiliary;

/**
 * Class where local configuration variables are assigned
 */
public class LocalConfiguration {
    public static class web {
        public final static boolean enableLocation = true;

        public final static double defaultLength = 127.15326;
        public final static double defaultLatitude = 40.213732;

        public final static boolean enableChangeableDisplay = false;
        public final static int width = 1360;
        public final static int height = 768;

        public final static boolean enableModifiableRandomDisplay = false;
        public final static int[][] resolutions = {{1360, 768}, {1024, 768}, {600, 768}};
    }
}