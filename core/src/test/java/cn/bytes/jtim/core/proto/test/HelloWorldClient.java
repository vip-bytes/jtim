//package cn.bytes.jtim.core.proto.test;
//
//import cn.bytes.jtim.core.protocol.protobuf.HelloRequest;
//import cn.bytes.jtim.core.protocol.protobuf.TestRequest;
//import cn.bytes.jtim.core.protocol.protobuf.TestResponse;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.StatusRuntimeException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomUtils;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//public class HelloWorldClient {
//
//    private final ManagedChannel channel;
//    private final GreeterGrpc.GreeterBlockingStub blockingStub;
//
//    public HelloWorldClient(String host, int port) {
//        channel = ManagedChannelBuilder.forAddress(host, port)
//                .usePlaintext()
//                .build();
//
//        blockingStub = GreeterGrpc.newBlockingStub(channel);
//    }
//
//    public void shutdown() throws InterruptedException {
//        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
//    }
//
//    public void greet(String name) {
//        TestRequest request = TestRequest.newBuilder().setName(name).build();
//        TestResponse response;
//        try {
//            response = blockingStub.testSomeThing(request);
//        } catch (StatusRuntimeException e) {
//            log.error("RPC failed: {}", e.getStatus());
//            return;
//        }
//        log.info("Greeting: " + response.getMessage());
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
//        String user = "world";
//        if (args.length > 0) {
//            user = args[0];
//        }
//
//        while (true) {
//
//            client.greet(RandomUtils.nextFloat(0, 100) + "");
//
//            TimeUnit.SECONDS.sleep(1);
//        }
//    }
//}