package alexfillis.monitor;

public interface Monitor {
    Status status();

    void refresh();

    void disable();

    void mute();

    class Status {
        private final Result result;

        public Status(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        public enum Result {success, failure, disabled, muted, none}
    }
}
