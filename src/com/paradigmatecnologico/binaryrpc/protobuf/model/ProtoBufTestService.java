package com.paradigmatecnologico.binaryrpc.protobuf.model;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.RpcUtil;
import com.google.protobuf.Service;
import com.google.protobuf.Message;
import com.google.protobuf.ServiceException;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Data;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Response;


public class ProtoBufTestService {

	
	/**
	   * Protobuf service {@code com.paradigmatecnologico.binaryrpc.protobuf.model.ProtobufTestService}
	   *
	   * <pre>
	   *Defines simple service
	   * </pre>
	   */
	  public static abstract class ProtobufTestService implements Service {
	    protected ProtobufTestService() {}

	    public interface Interface {
	      /**
	       * <code>rpc create(.com.paradigmatecnologico.binaryrpc.protobuf.model.Data) returns (.com.paradigmatecnologico.binaryrpc.protobuf.model.Response);</code>
	       */
	      public abstract void create(RpcController controller,Entity.Data request,RpcCallback<Entity.Response> done);

	    }

	    public static Service newReflectiveService(
	        final Interface impl) {
	      return new ProtobufTestService() {
	        @java.lang.Override
	        public  void create(RpcController controller,Entity.Data request,RpcCallback<Entity.Response> done) {
	          impl.create(controller, request, done);
	        }

	      };
	    }

	    public static BlockingService newReflectiveBlockingService(final BlockingInterface impl) {
	      return new BlockingService() {
	        public final Descriptors.ServiceDescriptor
	            getDescriptorForType() {
	          return getDescriptor();
	        }

	        public final Message callBlockingMethod(Descriptors.MethodDescriptor method,RpcController controller,Message request)
	            throws ServiceException {
	          if (method.getService() != getDescriptor()) {
	            throw new IllegalArgumentException("Service.callBlockingMethod() given method descriptor for " +
	              "wrong service type.");
	          }
	          switch(method.getIndex()) {
	            case 0:
	              return impl.create(controller, (Entity.Data)request);
	            default:
	              throw new java.lang.AssertionError("Can't get here.");
	          }
	        }

	        public final Message getRequestPrototype(Descriptors.MethodDescriptor method) {
	          if (method.getService() != getDescriptor()) {
	            throw new IllegalArgumentException(
	              "Service.getRequestPrototype() given method " +
	              "descriptor for wrong service type.");
	          }
	          switch(method.getIndex()) {
	            case 0:
	              return Entity.Data.getDefaultInstance();
	            default:
	              throw new AssertionError("Can't get here.");
	          }
	        }

	        public final Message getResponsePrototype(Descriptors.MethodDescriptor method) {
	          if (method.getService() != getDescriptor()) {
	            throw new IllegalArgumentException(
	              "Service.getResponsePrototype() given method " +
	              "descriptor for wrong service type.");
	          }
	          switch(method.getIndex()) {
	            case 0:
	              return Entity.Response.getDefaultInstance();
	            default:
	              throw new AssertionError("Can't get here.");
	          }
	        }

	      };
	    }

	    /**
	     * <code>rpc create(.com.paradigmatecnologico.binaryrpc.protobuf.model.Data) returns (.com.paradigmatecnologico.binaryrpc.protobuf.model.Response);</code>
	     */
	    public abstract void create(RpcController controller,Entity.Data request,RpcCallback<Entity.Response> done);

	    public static final ServiceDescriptor getDescriptor() {
	      return Entity.getDescriptor().getServices().get(0);
	    }
	    public final ServiceDescriptor getDescriptorForType() {
	      return getDescriptor();
	    }

	    public final void callMethod(MethodDescriptor method, RpcController controller,Message request,RpcCallback<Message> done) {
	      if (method.getService() != getDescriptor()) {
	        throw new IllegalArgumentException(
	          "Service.callMethod() given method descriptor for wrong " +
	          "service type.");
	      }
	      switch(method.getIndex()) {
	        case 0:
	          this.create(controller, (Entity.Data)request,RpcUtil.<Entity.Response>specializeCallback(done));
	          return;
	        default:
	          throw new AssertionError("Can't get here.");
	      }
	    }

	    public final Message getRequestPrototype(Descriptors.MethodDescriptor method) {
	      if (method.getService() != getDescriptor()) {
	        throw new IllegalArgumentException(
	          "Service.getRequestPrototype() given method " +
	          "descriptor for wrong service type.");
	      }
	      switch(method.getIndex()) {
	        case 0:
	          return Data.getDefaultInstance();
	        default:
	          throw new AssertionError("Can't get here.");
	      }
	    }

	    public final Message getResponsePrototype(MethodDescriptor method) {
	      if (method.getService() != getDescriptor()) {
	        throw new IllegalArgumentException(
	          "Service.getResponsePrototype() given method " +
	          "descriptor for wrong service type.");
	      }
	      switch(method.getIndex()) {
	        case 0:
	          return Response.getDefaultInstance();
	        default:
	          throw new java.lang.AssertionError("Can't get here.");
	      }
	    }

	    public static Stub newStub(RpcChannel channel) {
	      return new Stub(channel);
	    }

	    public static final class Stub extends ProtobufTestService implements Interface {
	      private Stub(RpcChannel channel) {
	        this.channel = channel;
	      }

	      private final RpcChannel channel;

	      public RpcChannel getChannel() {
	        return channel;
	      }

	      public void create(RpcController controller,Data request,RpcCallback<Response> done) {
	    	  MethodDescriptor methodDescriptor = getDescriptor().getMethods().get(0);
	    	  Message response = Response.getDefaultInstance();
	    	  RpcCallback<Message> callback = RpcUtil.generalizeCallback(done,Response.class,Response.getDefaultInstance());
	    	  channel.callMethod(methodDescriptor,controller,request,response,callback);
	      }
	    }

	    public static BlockingInterface newBlockingStub(
	        BlockingRpcChannel channel) {
	      return new BlockingStub(channel);
	    }

	    public interface BlockingInterface {
	      public Response create(RpcController controller,Data request)
	          throws ServiceException;
	    }

	    private static final class BlockingStub implements BlockingInterface {
	      private BlockingStub(BlockingRpcChannel channel) {
	        this.channel = channel;
	      }

	      private final BlockingRpcChannel channel;

	      public Response create(RpcController controller,Data request)
	          throws ServiceException {
	        return (Response) channel.callBlockingMethod(getDescriptor().getMethods().get(0), controller, request, Response.getDefaultInstance());
	      }

	    }
	  }

	  private static Descriptor
	    internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_descriptor;
	  private static FieldAccessorTable
	      internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_fieldAccessorTable;
	  private static Descriptor
	    internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_Telephone_descriptor;
	  private static FieldAccessorTable
	      internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_Telephone_fieldAccessorTable;
	  private static Descriptor
	    internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Response_descriptor;
	  private static FieldAccessorTable
	      internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Response_fieldAccessorTable;

	  public static FileDescriptor
	      getDescriptor() {
	    return descriptor;
	  }
	  private static com.google.protobuf.Descriptors.FileDescriptor
	      descriptor;
	  static {
	    java.lang.String[] descriptorData = {
	      "\n\025protobuf/entity.proto\0221com.paradigmate" +
	      "cnologico.binaryrpc.protobuf.model\"\345\002\n\004D" +
	      "ata\022\n\n\002ID\030\001 \002(\001\022\014\n\004name\030\002 \002(\t\022W\n\005state\030\003" +
	      " \001(\0162=.com.paradigmatecnologico.binaryrp" +
	      "c.protobuf.model.Data.State:\tUNDEFINED\022U" +
	      "\n\ntelephones\030\004 \003(\0132A.com.paradigmatecnol" +
	      "ogico.binaryrpc.protobuf.model.Data.Tele" +
	      "phone\022\021\n\taddresses\030\005 \003(\t\022\016\n\006member\030\006 \001(\010" +
	      "\022\023\n\013description\030\007 \001(\t\032*\n\tTelephone\022\r\n\005al" +
	      "ias\030\001 \002(\t\022\016\n\006number\030\002 \003(\003\"/\n\005State\022\013\n\007MA",
	      "RRIED\020\000\022\n\n\006SINGLE\020\001\022\r\n\tUNDEFINED\020\002\"x\n\010Re" +
	      "sponse\022\014\n\004code\030\001 \002(\005\022\017\n\007message\030\002 \002(\t\022M\n" +
	      "\014responseData\030\003 \001(\01327.com.paradigmatecno" +
	      "logico.binaryrpc.protobuf.model.Data2\225\001\n" +
	      "\023ProtobufTestService\022~\n\006create\0227.com.par" +
	      "adigmatecnologico.binaryrpc.protobuf.mod" +
	      "el.Data\032;.com.paradigmatecnologico.binar" +
	      "yrpc.protobuf.model.ResponseB\003\210\001\001"
	    };
	    FileDescriptor.InternalDescriptorAssigner assigner =
	      new FileDescriptor.InternalDescriptorAssigner() {
	        public ExtensionRegistry assignDescriptors(
	            FileDescriptor root) {
	          descriptor = root;
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_descriptor =
	            getDescriptor().getMessageTypes().get(0);
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_fieldAccessorTable = new
	            FieldAccessorTable(
	              internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_descriptor,
	              new java.lang.String[] { "ID", "Name", "State", "Telephones", "Addresses", "Member", "Description", });
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_Telephone_descriptor =
	            internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_descriptor.getNestedTypes().get(0);
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_Telephone_fieldAccessorTable = new
	            FieldAccessorTable(
	              internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Data_Telephone_descriptor,
	              new String[] { "Alias", "Number", });
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Response_descriptor =
	            getDescriptor().getMessageTypes().get(1);
	          internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Response_fieldAccessorTable = new
	            FieldAccessorTable(
	              internal_static_com_paradigmatecnologico_binaryrpc_protobuf_model_Response_descriptor,
	              new java.lang.String[] { "Code", "Message", "ResponseData", });
	          return null;
	        }
	      };
	    FileDescriptor
	      .internalBuildGeneratedFileFrom(descriptorData,
	        new FileDescriptor[] {}, assigner);
	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
