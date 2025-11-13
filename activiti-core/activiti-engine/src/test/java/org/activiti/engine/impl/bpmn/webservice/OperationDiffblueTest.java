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
package org.activiti.engine.impl.bpmn.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.webservice.SyncWebServiceClient;
import org.activiti.engine.impl.webservice.WSOperation;
import org.activiti.engine.impl.webservice.WSService;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class OperationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Operation#Operation()}
   *   <li>{@link Operation#setId(String)}
   *   <li>{@link Operation#setImplementation(OperationImplementation)}
   *   <li>{@link Operation#setInMessage(MessageDefinition)}
   *   <li>{@link Operation#setInterface(BpmnInterface)}
   *   <li>{@link Operation#setName(String)}
   *   <li>{@link Operation#setOutMessage(MessageDefinition)}
   *   <li>{@link Operation#getId()}
   *   <li>{@link Operation#getImplementation()}
   *   <li>{@link Operation#getInMessage()}
   *   <li>{@link Operation#getInterface()}
   *   <li>{@link Operation#getName()}
   *   <li>{@link Operation#getOutMessage()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Operation.<init>()",
    "String Operation.getId()",
    "OperationImplementation Operation.getImplementation()",
    "MessageDefinition Operation.getInMessage()",
    "BpmnInterface Operation.getInterface()",
    "String Operation.getName()",
    "MessageDefinition Operation.getOutMessage()",
    "void Operation.setId(String)",
    "void Operation.setImplementation(OperationImplementation)",
    "void Operation.setInMessage(MessageDefinition)",
    "void Operation.setInterface(BpmnInterface)",
    "void Operation.setName(String)",
    "void Operation.setOutMessage(MessageDefinition)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Operation actualOperation = new Operation();
    actualOperation.setId("42");
    WSService service = new WSService("Name", "Location", "Wsdl Location");
    WSOperation implementation = new WSOperation("42", "Operation Name", service);
    actualOperation.setImplementation(implementation);
    MessageDefinition inMessage = new MessageDefinition("42");
    actualOperation.setInMessage(inMessage);
    BpmnInterface bpmnInterface = new BpmnInterface("42", "Name");
    actualOperation.setInterface(bpmnInterface);
    actualOperation.setName("Name");
    MessageDefinition outMessage = new MessageDefinition("42");
    actualOperation.setOutMessage(outMessage);
    String actualId = actualOperation.getId();
    OperationImplementation actualImplementation = actualOperation.getImplementation();
    MessageDefinition actualInMessage = actualOperation.getInMessage();
    BpmnInterface actualInterface = actualOperation.getInterface();
    String actualName = actualOperation.getName();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualName);
    assertSame(bpmnInterface, actualInterface);
    assertSame(inMessage, actualInMessage);
    assertSame(outMessage, actualOperation.getOutMessage());
    assertSame(implementation, actualImplementation);
  }

  /**
   * Test {@link Operation#Operation(String, String, BpmnInterface, MessageDefinition)}.
   *
   * <p>Method under test: {@link Operation#Operation(String, String, BpmnInterface,
   * MessageDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Operation.<init>(String, String, BpmnInterface, MessageDefinition)"})
  public void testNewOperation() {
    // Arrange
    BpmnInterface bpmnInterface = new BpmnInterface("42", "Name");
    MessageDefinition inMessage = new MessageDefinition("42");

    // Act
    Operation actualOperation = new Operation("42", "Name", bpmnInterface, inMessage);

    // Assert
    assertEquals("42", actualOperation.getId());
    assertEquals("Name", actualOperation.getName());
    assertNull(actualOperation.getOutMessage());
    assertNull(actualOperation.getImplementation());
    assertSame(bpmnInterface, actualOperation.getInterface());
    assertSame(inMessage, actualOperation.getInMessage());
  }

  /**
   * Test {@link Operation#sendMessage(MessageInstance, ConcurrentMap)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#sendMessage(MessageInstance, ConcurrentMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MessageInstance Operation.sendMessage(MessageInstance, ConcurrentMap)"})
  public void testSendMessage_thenReturnNull() throws Exception {
    // Arrange
    SyncWebServiceClient client = mock(SyncWebServiceClient.class);
    when(client.send(
            Mockito.<String>any(),
            Mockito.<Object[]>any(),
            Mockito.<ConcurrentMap<QName, URL>>any()))
        .thenReturn(new Object[] {JSONObject.NULL});
    WSService service = new WSService("Name", "Location", client);
    WSOperation implementation = new WSOperation("42", "Operation Name", service);

    Operation operation = new Operation();
    operation.setImplementation(implementation);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance message2 = new MessageInstance(message, item2);

    // Act
    MessageInstance actualSendMessageResult =
        operation.sendMessage(message2, new ConcurrentHashMap<>());

    // Assert
    verify(client).send(eq("Operation Name"), isA(Object[].class), isA(ConcurrentMap.class));
    assertNull(actualSendMessageResult);
  }
}
