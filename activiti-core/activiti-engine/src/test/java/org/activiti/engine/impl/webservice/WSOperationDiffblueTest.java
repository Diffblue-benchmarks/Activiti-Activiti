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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.bpmn.data.StructureInstance;
import org.activiti.engine.impl.bpmn.webservice.BpmnInterface;
import org.activiti.engine.impl.bpmn.webservice.MessageDefinition;
import org.activiti.engine.impl.bpmn.webservice.MessageInstance;
import org.activiti.engine.impl.bpmn.webservice.Operation;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class WSOperationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link WSOperation#WSOperation(String, String, WSService)}
   *   <li>{@link WSOperation#getId()}
   *   <li>{@link WSOperation#getName()}
   *   <li>{@link WSOperation#getService()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void WSOperation.<init>(String, String, WSService)",
    "String WSOperation.getId()",
    "String WSOperation.getName()",
    "WSService WSOperation.getService()"
  })
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
   *
   * <ul>
   *   <li>Given {@link SyncWebServiceClient} {@link SyncWebServiceClient#send(String, Object[],
   *       ConcurrentMap)} return {@code null}.
   *   <li>When {@link Operation#Operation()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageInstance WSOperation.sendFor(MessageInstance, Operation, ConcurrentMap)"
  })
  public void testSendFor_givenSyncWebServiceClientSendReturnNull_whenOperation_thenReturnNull()
      throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(
            Mockito.<String>any(),
            Mockito.<Object[]>any(),
            Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(null);
    WSService service =
        new WSService(
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            client);
    WSOperation wsOperation = new WSOperation("42", "Operation Name", service);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance message2 = new MessageInstance(message, item2);
    Operation operation = new Operation();

    // Act
    MessageInstance actualSendForResult =
        wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertNull(actualSendForResult);
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   *
   * <ul>
   *   <li>Given {@link SyncWebServiceClient} {@link SyncWebServiceClient#send(String, Object[],
   *       ConcurrentMap)} throw {@link Exception#Exception()}.
   *   <li>Then throw {@link Exception}.
   * </ul>
   *
   * <p>Method under test: {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageInstance WSOperation.sendFor(MessageInstance, Operation, ConcurrentMap)"
  })
  public void testSendFor_givenSyncWebServiceClientSendThrowException_thenThrowException()
      throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(
            Mockito.<String>any(),
            Mockito.<Object[]>any(),
            Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenThrow(new Exception());
    WSService service =
        new WSService(
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            client);
    WSOperation wsOperation = new WSOperation("42", "Operation Name", service);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance message2 = new MessageInstance(message, item2);
    Operation operation = new Operation();

    // Act and Assert
    assertThrows(
        Exception.class, () -> wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>()));
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageInstance WSOperation.sendFor(MessageInstance, Operation, ConcurrentMap)"
  })
  public void testSendFor_thenReturnNull() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(
            Mockito.<String>any(),
            Mockito.<Object[]>any(),
            Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[] {JSONObject.NULL});
    WSService service =
        new WSService(
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            client);
    WSOperation wsOperation = new WSOperation("42", "Operation Name", service);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance message2 = new MessageInstance(message, item2);
    Operation operation = new Operation();

    // Act
    MessageInstance actualSendForResult =
        wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertNull(actualSendForResult);
  }

  /**
   * Test {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}.
   *
   * <ul>
   *   <li>Then StructureInstance return {@link FieldBaseStructureInstance}.
   * </ul>
   *
   * <p>Method under test: {@link WSOperation#sendFor(MessageInstance, Operation, ConcurrentMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageInstance WSOperation.sendFor(MessageInstance, Operation, ConcurrentMap)"
  })
  public void testSendFor_thenStructureInstanceReturnFieldBaseStructureInstance() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(
            Mockito.<String>any(),
            Mockito.<Object[]>any(),
            Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[] {JSONObject.NULL});
    WSService service =
        new WSService(
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
            client);
    WSOperation wsOperation = new WSOperation("42", "Operation Name", service);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance message2 = new MessageInstance(message, item2);

    MessageDefinition outMessage = new MessageDefinition("42");
    ItemDefinition itemDefinition = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    outMessage.setItemDefinition(itemDefinition);
    BpmnInterface bpmnInterface = new BpmnInterface("42", "Name");

    Operation operation = new Operation("42", "Name", bpmnInterface, new MessageDefinition("42"));
    operation.setOutMessage(outMessage);

    // Act
    MessageInstance actualSendForResult =
        wsOperation.sendFor(message2, operation, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    StructureInstance structureInstance2 = actualSendForResult.getStructureInstance();
    assertTrue(structureInstance2 instanceof FieldBaseStructureInstance);
    assertEquals(0, ((FieldBaseStructureInstance) structureInstance2).getFieldSize());
    assertEquals(0, structureInstance2.toArray().length);
    assertSame(outMessage, actualSendForResult.getMessage());
  }
}
