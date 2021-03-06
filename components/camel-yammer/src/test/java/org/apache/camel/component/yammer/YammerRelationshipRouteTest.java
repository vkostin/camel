/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.yammer;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.yammer.model.Relationships;
import org.junit.Test;

public class YammerRelationshipRouteTest extends YammerComponentTestSupport {

    @SuppressWarnings("unchecked")
    @Test
    public void testConsumeAllUsers() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        assertMockEndpointsSatisfied();
        
        Exchange exchange = mock.getExchanges().get(0);
        Relationships relationships = exchange.getIn().getBody(Relationships.class);

        assertEquals(1, relationships.getSuperiors().size());
        assertEquals("Joe Camel", relationships.getSuperiors().get(0).getFullName());        
    }

    @Override
    protected String jsonFile() {
        return "/relationships.json";
    }
       
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                // using dummy keys here since we are mocking out calls to yammer.com with static json; in a real app, please use your own keys!
                from("yammer:relationships?consumerKey=aConsumerKey&consumerSecret=aConsumerSecretKey&accessToken=aAccessToken").to("mock:result");
            }
        };
    }
}
