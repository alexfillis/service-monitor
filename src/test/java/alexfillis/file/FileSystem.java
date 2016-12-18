package alexfillis.file;

import java.io.IOException;

public interface FileSystem {
    boolean exists(String path) throws IOException;
}
