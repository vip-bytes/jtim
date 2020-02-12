//package cn.bytes.jtim.test;
//
//import cn.bytes.jtim.core.protocol.protobuf.TestRequest;
//import cn.bytes.jtim.core.protocol.protobuf.TestResponse;
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import io.grpc.stub.StreamObserver;
//
///**
// * @author maliang@sioniov.com
// * @version 1.0
// * @date 2020/2/12 23:45
// */
//public class GrpcServer {
//    public static void main(String[] args) {
//        Server server
//                = ServerBuilder.forPort(9999)
//                .addService(new Test)
//                .build().start();
//
////        try {
////            server.awaitTermination();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
//        public void testSomeThing(TestRequest request, StreamObserver<TestResponse> responseObserver) {
//            TestResponse build = TestResponse.newBuilder().setMessage(request.getName()).build();
//            //onNext()方法向客户端返回结果
//            responseObserver.onNext(build);
//            //告诉客户端这次调用已经完成
//            responseObserver.onCompleted();
//        }
//    }
//}
