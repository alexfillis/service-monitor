package alexfillis.monitor;

import java.util.Objects;

public interface Monitor {
    Status status();

    void refresh();

    void disable();

    void mute();

    void enable();

    void unmute();

    class Status {
        private final Result result;

        public Status(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Status status = (Status) o;
            return result == status.result;
        }

        @Override
        public int hashCode() {
            return Objects.hash(result);
        }

        public enum Result {success, failure, disabled, muted, none}
    }
}
