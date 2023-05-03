package Common.interfaces;

import java.io.IOException;

public interface IOController {
    void send(Data data) throws IOException;

    Data receive() throws IOException, ClassNotFoundException;
}
