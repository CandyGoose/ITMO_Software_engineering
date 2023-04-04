package Client.util;

import Common.interfaces.Data;
import Common.interfaces.IOController;
import Common.util.Serializers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSocketChannelIO implements IOController {
    private final SocketChannel channel;

    public ClientSocketChannelIO(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Data data) throws IOException {
        ByteBuffer buffer = Serializers.serializeRequest(data);
        channel.write(buffer);
    }

    @Override
    public Data receive() throws IOException, ClassNotFoundException {
        ByteBuffer readBuffer = ByteBuffer.allocate(channel.socket().getReceiveBufferSize());
        channel.read(readBuffer);
        return Serializers.deSerializeResponse(readBuffer.array());
    }
}
