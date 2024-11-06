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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.bpmn.data.StructureInstance;
import org.activiti.engine.impl.bpmn.webservice.MessageDefinition;
import org.activiti.engine.impl.bpmn.webservice.MessageInstance;
import org.activiti.engine.impl.bpmn.webservice.Operation;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class WSOperationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WSOperation#WSOperation(String, String, WSService)}
   *   <li>{@link WSOperation#getId()}
   *   <li>{@link WSOperation#getName()}
   *   <li>{@link WSOperation#getService()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    WSService service = new WSService("Name", "Location", "Wsdl Location");

    // Act
    WSOperation actualWsOperation = new WSOperation("42", "Operation Name", service);
    String actualId = actualWsOperation.getId();
    String actualName = actualWsOperation.getName();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Operation Name", actualName);
    assertSame(service, actualWsOperation.getService());
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   * <ul>
   *   <li>Given {@link SyncWebServiceClient}
   * {@link SyncWebServiceClient#send(String, Object[], ConcurrentMap)} return
   * {@code null}.</li>
   *   <li>When {@link Operation#Operation()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  public void testSendFor_givenSyncWebServiceClientSendReturnNull_whenOperation_thenReturnNull() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(null);
    WSOperation wsOperation = new WSOperation("42", "Operation Name",
        new WSService("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory", client));
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    MessageInstance message2 = new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));

    Operation operation = new Operation();

    // Act
    MessageInstance actualSendForResult = wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertNull(actualSendForResult);
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  public void testSendFor_thenReturnNull() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[]{JSONObject.NULL});
    WSOperation wsOperation = new WSOperation("42", "Operation Name",
        new WSService("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory", client));
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    MessageInstance message2 = new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));

    Operation operation = new Operation();

    // Act
    MessageInstance actualSendForResult = wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertNull(actualSendForResult);
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   * <ul>
   *   <li>Then StructureInstance return {@link FieldBaseStructureInstance}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  public void testSendFor_thenStructureInstanceReturnFieldBaseStructureInstance() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[]{JSONObject.NULL});
    WSOperation wsOperation = new WSOperation("42", "Operation Name",
        new WSService("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory", client));
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    MessageInstance message2 = new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));

    MessageDefinition outMessage = new MessageDefinition("42");
    outMessage.setItemDefinition(new ItemDefinition("42", new SimpleStructureDefinition("42")));

    Operation operation = new Operation();
    operation.setOutMessage(outMessage);

    // Act
    MessageInstance actualSendForResult = wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    StructureInstance structureInstance = actualSendForResult.getStructureInstance();
    assertTrue(structureInstance instanceof FieldBaseStructureInstance);
    assertEquals(0, ((FieldBaseStructureInstance) structureInstance).getFieldSize());
    assertEquals(0, structureInstance.toArray().length);
    assertSame(outMessage, actualSendForResult.getMessage());
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   * <ul>
   *   <li>Then throw {@link Exception}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  public void testSendFor_thenThrowException() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenThrow(new Exception("foo"));
    WSOperation wsOperation = new WSOperation("42", "Operation Name",
        new WSService("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory", client));
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    MessageInstance message2 = new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));

    Operation operation = new Operation();

    // Act and Assert
    assertThrows(Exception.class, () -> wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>()));
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
  }
}
