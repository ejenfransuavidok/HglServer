package spu.hgl.emu.emulator;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GrpcServer {

  private int port;

  private String host;

  private String path;

  public GrpcServer(
      @Value("${dau.hgl.address}") String hostPort,
      @Value("${dau.hgl.pathToFile}") String path) throws IOException, InterruptedException {
    this.port = Integer.parseInt(hostPort.split(":")[1]);
    this.host = hostPort.split(":")[0];
    this.path = path;
    start(this.port, this.path);
  }

  private void start(int port, String path) throws IOException, InterruptedException {
    Server server = ServerBuilder
        .forPort(port)
        .addService(new GrpcGetConfigService(path)).build();

    server.start();
    server.awaitTermination();
  }

}
