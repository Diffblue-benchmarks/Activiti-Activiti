/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class WSServiceDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link SyncWebServiceClient}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WSService#WSService(String, String, SyncWebServiceClient)}
   *   <li>{@link WSService#getLocation()}
   *   <li>{@link WSService#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenSyncWebServiceClient() {
    // Arrange and Act
    WSService actualWsService = new WSService("Name", "Location", mock(SyncWebServiceClient.class));
    String actualLocation = actualWsService.getLocation();

    // Assert
    assertEquals("Location", actualLocation);
    assertEquals("Name", actualWsService.getName());
    assertTrue(actualWsService.operations.isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Wsdl Location}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WSService#WSService(String, String, String)}
   *   <li>{@link WSService#getLocation()}
   *   <li>{@link WSService#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenWsdlLocation() {
    // Arrange and Act
    WSService actualWsService = new WSService("Name", "Location", "Wsdl Location");
    String actualLocation = actualWsService.getLocation();

    // Assert
    assertEquals("Location", actualLocation);
    assertEquals("Name", actualWsService.getName());
    assertTrue(actualWsService.operations.isEmpty());
  }

  /**
   * Test {@link WSService#addOperation(WSOperation)}.
   * <p>
   * Method under test: {@link WSService#addOperation(WSOperation)}
   */
  @Test
  public void testAddOperation() {
    // Arrange
    WSService wsService = new WSService("Name", "Location", "Wsdl Location");
    WSOperation operation = new WSOperation("42", "Operation Name", new WSService("Name", "Location", "Wsdl Location"));

    // Act
    wsService.addOperation(operation);

    // Assert
    Map<String, WSOperation> stringWsOperationMap = wsService.operations;
    assertEquals(1, stringWsOperationMap.size());
    assertSame(operation, stringWsOperationMap.get("Operation Name"));
  }

  /**
   * Test {@link WSService#addOperation(WSOperation)}.
   * <p>
   * Method under test: {@link WSService#addOperation(WSOperation)}
   */
  @Test
  public void testAddOperation2() {
    // Arrange
    WSService wsService = new WSService("Name", "Location", mock(SyncWebServiceClient.class));
    WSOperation operation = new WSOperation("42", "Operation Name", new WSService("Name", "Location", "Wsdl Location"));

    // Act
    wsService.addOperation(operation);

    // Assert
    Map<String, WSOperation> stringWsOperationMap = wsService.operations;
    assertEquals(1, stringWsOperationMap.size());
    assertSame(operation, stringWsOperationMap.get("Operation Name"));
  }

  /**
   * Test {@link WSService#getClient()}.
   * <ul>
   *   <li>Then return array length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link WSService#getClient()}
   */
  @Test
  public void testGetClient_thenReturnArrayLengthIsOne() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[]{JSONObject.NULL});

    // Act
    SyncWebServiceClient actualClient = (new WSService("Name", "Location", client)).getClient();
    Object[] actualSendResult = actualClient.send("Method Name", new Object[]{JSONObject.NULL},
        new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Method Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertEquals(1, actualSendResult.length);
  }
}
