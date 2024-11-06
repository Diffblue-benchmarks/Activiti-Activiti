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
import static org.mockito.Mockito.mock;
import org.activiti.engine.impl.webservice.WSOperation;
import org.activiti.engine.impl.webservice.WSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OperationDiffblueTest {
  @InjectMocks
  private Operation operation;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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
  public void testGettersAndSetters() {
    // Arrange and Act
    Operation actualOperation = new Operation();
    actualOperation.setId("42");
    WSOperation implementation = new WSOperation("42", "Operation Name",
        new WSService("Name", "Location", "Wsdl Location"));

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

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Name", actualName);
    assertSame(bpmnInterface, actualInterface);
    assertSame(inMessage, actualInMessage);
    assertSame(outMessage, actualOperation.getOutMessage());
    assertSame(implementation, actualImplementation);
  }

  /**
   * Test
   * {@link Operation#Operation(String, String, BpmnInterface, MessageDefinition)}.
   * <ul>
   *   <li>Then return Interface is
   * {@link BpmnInterface#BpmnInterface(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Operation#Operation(String, String, BpmnInterface, MessageDefinition)}
   */
  @Test
  public void testNewOperation_thenReturnInterfaceIsBpmnInterfaceWithIdIs42AndName() {
    // Arrange
    BpmnInterface bpmnInterface = new BpmnInterface("42", "Name");

    // Act and Assert
    assertSame(bpmnInterface, (new Operation("42", "Name", bpmnInterface, new MessageDefinition("42"))).getInterface());
  }

  /**
   * Test
   * {@link Operation#Operation(String, String, BpmnInterface, MessageDefinition)}.
   * <ul>
   *   <li>When {@link BpmnInterface}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Operation#Operation(String, String, BpmnInterface, MessageDefinition)}
   */
  @Test
  public void testNewOperation_whenBpmnInterface_thenReturnIdIs42() {
    // Arrange
    BpmnInterface bpmnInterface = mock(BpmnInterface.class);
    MessageDefinition inMessage = new MessageDefinition("42");

    // Act
    Operation actualOperation = new Operation("42", "Name", bpmnInterface, inMessage);

    // Assert
    assertEquals("42", actualOperation.getId());
    assertEquals("Name", actualOperation.getName());
    assertNull(actualOperation.getOutMessage());
    assertNull(actualOperation.getImplementation());
    assertSame(inMessage, actualOperation.getInMessage());
    assertSame(bpmnInterface, actualOperation.getInterface());
  }
}
