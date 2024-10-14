package _10_SealedClasses;

public class Api {
    public abstract class Response {
        abstract String getData();
    }

    public class Success extends Response {
        private final String data;

        public Success(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    public class Error extends Response {
        private final String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getData() {
            return errorMessage;
        }
    }

    public static String toMessage(Response response) throws Exception {
        String result = "";
        if (response instanceof Success) {
            result += 200;
        } else if (response instanceof Error) {
            result += 200;
        } else throw new Exception("unknown!");

        return result + response.getData();
    }
}
