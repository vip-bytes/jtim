// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: define.proto

package cn.bytes.jtim.core.protocol.protobuf;

public final class Define {
  private Define() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DefineResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DefineResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AckRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AckRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014define.proto\"=\n\016DefineResponse\022\016\n\006resu" +
      "lt\030\001 \001(\010\022\n\n\002ts\030\002 \001(\003\022\017\n\007message\030\003 \001(\t\"\033\n" +
      "\nAckRequest\022\r\n\005msgId\030\001 \001(\tB(\n$cn.bytes.j" +
      "tim.core.protocol.protobufP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_DefineResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_DefineResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DefineResponse_descriptor,
        new java.lang.String[] { "Result", "Ts", "Message", });
    internal_static_AckRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_AckRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AckRequest_descriptor,
        new java.lang.String[] { "MsgId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
