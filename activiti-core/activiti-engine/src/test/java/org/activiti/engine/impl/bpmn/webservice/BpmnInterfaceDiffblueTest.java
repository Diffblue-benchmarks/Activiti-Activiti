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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BpmnInterfaceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link BpmnInterface#BpmnInterface()}
   *   <li>{@link BpmnInterface#setId(String)}
   *   <li>{@link BpmnInterface#setImplementation(BpmnInterfaceImplementation)}
   *   <li>{@link BpmnInterface#setName(String)}
   *   <li>{@link BpmnInterface#getId()}
   *   <li>{@link BpmnInterface#getImplementation()}
   *   <li>{@link BpmnInterface#getName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnInterface.<init>()",
    "String BpmnInterface.getId()",
    "BpmnInterfaceImplementation BpmnInterface.getImplementation()",
    "String BpmnInterface.getName()",
    "void BpmnInterface.setId(String)",
    "void BpmnInterface.setImplementation(BpmnInterfaceImplementation)",
    "void BpmnInterface.setName(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnInterface actualBpmnInterface = new BpmnInterface();
    actualBpmnInterface.setId("42");
    BpmnInterfaceImplementation implementation = mock(BpmnInterfaceImplementation.class);
    actualBpmnInterface.setImplementation(implementation);
    actualBpmnInterface.setName("Name");
    String actualId = actualBpmnInterface.getId();
    BpmnInterfaceImplementation actualImplementation = actualBpmnInterface.getImplementation();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualBpmnInterface.getName());
    assertTrue(actualBpmnInterface.operations.isEmpty());
    assertSame(implementation, actualImplementation);
  }

  /**
   * Test {@link BpmnInterface#BpmnInterface(String, String)}.
   *
   * <p>Method under test: {@link BpmnInterface#BpmnInterface(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnInterface.<init>(String, String)"})
  public void testNewBpmnInterface() {
    // Arrange and Act
    BpmnInterface actualBpmnInterface = new BpmnInterface("42", "Name");

    // Assert
    assertEquals("42", actualBpmnInterface.getId());
    assertEquals("Name", actualBpmnInterface.getName());
    assertNull(actualBpmnInterface.getImplementation());
    assertTrue(actualBpmnInterface.getOperations().isEmpty());
    assertTrue(actualBpmnInterface.operations.isEmpty());
  }

  /**
   * Test {@link BpmnInterface#addOperation(Operation)}.
   *
   * <ul>
   *   <li>Then {@link BpmnInterface#BpmnInterface(String, String)} with id is {@code 42} and {@code
   *       Name} Operations size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnInterface#addOperation(Operation)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnInterface.addOperation(Operation)"})
  public void testAddOperation_thenBpmnInterfaceWithIdIs42AndNameOperationsSizeIsOne() {
    // Arrange
    BpmnInterface bpmnInterface = new BpmnInterface("42", "Name");
    Operation operation = new Operation();

    // Act
    bpmnInterface.addOperation(operation);

    // Assert
    assertEquals(1, bpmnInterface.getOperations().size());
    Map<String, Operation> stringOperationMap = bpmnInterface.operations;
    assertEquals(1, stringOperationMap.size());
    assertSame(operation, stringOperationMap.get(null));
  }

  /**
   * Test {@link BpmnInterface#getOperation(String)}.
   *
   * <p>Method under test: {@link BpmnInterface#getOperation(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation BpmnInterface.getOperation(String)"})
  public void testGetOperation() {
    // Arrange, Act and Assert
    assertNull(new BpmnInterface("42", "Name").getOperation("42"));
  }

  /**
   * Test {@link BpmnInterface#getOperations()}.
   *
   * <p>Method under test: {@link BpmnInterface#getOperations()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Collection BpmnInterface.getOperations()"})
  public void testGetOperations() {
    // Arrange, Act and Assert
    assertTrue(new BpmnInterface("42", "Name").getOperations().isEmpty());
  }
}
