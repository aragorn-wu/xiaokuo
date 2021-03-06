package com.git.wuqf.xiaokuo.rpc.protocol.xiaokuo;

import com.git.wuqf.xiaokuo.common.URL;
import com.git.wuqf.xiaokuo.common.extension.ExtensionLoader;
import com.git.wuqf.xiaokuo.rpc.Invoker;
import com.git.wuqf.xiaokuo.rpc.Protocol;
import com.git.wuqf.xiaokuo.rpc.ProxyFactory;
import com.git.wuqf.xiaokuo.rpc.protocol.xiaokuo.support.DemoService;
import com.git.wuqf.xiaokuo.rpc.protocol.xiaokuo.support.DemoServiceImpl;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by wuqf on 17-3-25.
 */
public class XiaokuoProtocolTest {
    private Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
    private ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
    URL u = URL.valueOf("xiaokuo://127.0.0.1:9020/" + DemoService.class.getName() + "?codec=exchange");

    @Test
    public void testDemoProtocol_sayHello() throws Exception {
        DemoService service = new DemoServiceImpl();
        Invoker<DemoService> serviceInvoker = proxy.getInvoker(service, DemoService.class, u);
        protocol.export(serviceInvoker);

        Invoker<DemoService> clientInvoker = protocol.refer(DemoService.class,u);
        service = proxy.getProxy(clientInvoker);

        service.sayHello("jbwy");
    }

    @Test
    public void testDemoProtocol_getSize() throws Exception {
        DemoService service = new DemoServiceImpl();
        Invoker<DemoService> serviceInvoker = proxy.getInvoker(service, DemoService.class, u);
        protocol.export(serviceInvoker);

        Invoker<DemoService> clientInvoker = protocol.refer(DemoService.class,
                u);
        service = proxy.getProxy(clientInvoker);

        int size = service.getSize(new String[]{"a", "b", "c"});
        assertEquals(3, size);
    }
}
