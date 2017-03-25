/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.git.wuqf.remoting.support.header;


import com.git.wuqf.remoting.ExchangeClient;
import com.git.wuqf.remoting.ExchangeServer;
import com.git.wuqf.remoting.RemotingException;
import com.git.wuqf.remoting.Transporters;
import com.git.wuqf.remoting.exchange.ExchangeHandler;
import com.git.wuqf.remoting.exchange.Exchanger;
import com.git.wuqf.remoting.transport.DecodeHandler;
import com.git.wuqf.xiaokuo.common.URL;

/**
 * DefaultMessenger
 * 
 * @author william.liangf
 */
public class HeaderExchanger implements Exchanger {
    
    public static final String NAME = "header";

    public ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeClient(Transporters.connect(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }

    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }

}