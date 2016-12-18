package alexfillis.monitor;

public interface Monitor {
    Status status();

    class Status {
        private final Result result;

        public Status(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        public enum Result {none}
    }
}
