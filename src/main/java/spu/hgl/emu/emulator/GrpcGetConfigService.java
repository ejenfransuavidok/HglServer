package spu.hgl.emu.emulator;

import com.google.protobuf.ByteString;
import com.hgldynamics.acquisition.api.ConfigurationFile;
import com.hgldynamics.acquisition.api.ControlServiceGrpc;
import com.hgldynamics.acquisition.api.Void;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class GrpcGetConfigService extends ControlServiceGrpc.ControlServiceImplBase {

  private final String path;

  public GrpcGetConfigService(String path) {
    this.path = path;
  }

  @Override
  public void getConfig(
      final Void request, final StreamObserver<ConfigurationFile> responseObserver
  ) {
    try {
      ConfigurationFile data = ConfigurationFile
          .newBuilder()
          .setXmlBuffer(ByteString.copyFrom(Files.readAllBytes(Paths.get(path))))
          .build();
      responseObserver.onNext(data);
      responseObserver.onCompleted();
    } catch (Exception e) {
      log.error("error protobuf error", e);
    }
  }

}
