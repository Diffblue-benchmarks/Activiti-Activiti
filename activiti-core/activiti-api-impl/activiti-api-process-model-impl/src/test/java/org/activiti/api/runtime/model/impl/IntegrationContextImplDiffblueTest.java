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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class IntegrationContextImplDiffblueTest {
  /**
   * Method under test:
   * {@link IntegrationContextImpl#addInBoundVariable(String, Object)}
   */
  @Test
  void testAddInBoundVariable() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act
    integrationContextImpl.addInBoundVariable("Name", "Value");

    // Assert that nothing has changed
    Map<String, Object> inBoundVariables = integrationContextImpl.getInBoundVariables();
    assertEquals(1, inBoundVariables.size());
    assertEquals("Value", inBoundVariables.get("Name"));
  }

  /**
   * Method under test:
   * {@link IntegrationContextImpl#addOutBoundVariable(String, Object)}
   */
  @Test
  void testAddOutBoundVariable() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act
    integrationContextImpl.addOutBoundVariable("Name", "Value");

    // Assert that nothing has changed
    Map<String, Object> outBoundVariables = integrationContextImpl.getOutBoundVariables();
    assertEquals(1, outBoundVariables.size());
    assertEquals("Value", outBoundVariables.get("Name"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link IntegrationContextImpl#equals(Object)}
   *   <li>{@link IntegrationContextImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act and Assert
    assertEquals(integrationContextImpl, integrationContextImpl);
    int expectedHashCodeResult = integrationContextImpl.hashCode();
    assertEquals(expectedHashCodeResult, integrationContextImpl.hashCode());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#getInBoundVariable(String)}
   */
  @Test
  void testGetInBoundVariable() {
    // Arrange, Act and Assert
    assertNull((new IntegrationContextImpl()).getInBoundVariable("Name"));
  }

  /**
   * Method under test: {@link IntegrationContextImpl#getInBoundVariable(String)}
   */
  @Test
  void testGetInBoundVariable2() {
    // Arrange
    HashMap<String, Object> inboundVariables = new HashMap<>();
    inboundVariables.computeIfPresent("foo", mock(BiFunction.class));

    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariables(inboundVariables);

    // Act and Assert
    assertNull(integrationContextImpl.getInBoundVariable("Name"));
  }

  /**
   * Method under test:
   * {@link IntegrationContextImpl#getInBoundVariable(String, Class)}
   */
  @Test
  void testGetInBoundVariable3() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(integrationContextImpl.getInBoundVariable("Name", type));
  }

  /**
   * Method under test: {@link IntegrationContextImpl#getOutBoundVariable(String)}
   */
  @Test
  void testGetOutBoundVariable() {
    // Arrange, Act and Assert
    assertNull((new IntegrationContextImpl()).getOutBoundVariable("Name"));
  }

  /**
   * Method under test: {@link IntegrationContextImpl#getOutBoundVariable(String)}
   */
  @Test
  void testGetOutBoundVariable2() {
    // Arrange
    HashMap<String, Object> inboundVariables = new HashMap<>();
    inboundVariables.computeIfPresent("foo", mock(BiFunction.class));

    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariables(inboundVariables);

    // Act and Assert
    assertNull(integrationContextImpl.getOutBoundVariable("Name"));
  }

  /**
   * Method under test:
   * {@link IntegrationContextImpl#getOutBoundVariable(String, Class)}
   */
  @Test
  void testGetOutBoundVariable3() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(integrationContextImpl.getOutBoundVariable("Name", type));
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientId("42");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setConnectorType("Connector Type");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientName("Dr Jane Doe");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientType("Client Type");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setExecutionId("42");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new IntegrationContextImpl(), null);
  }

  /**
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new IntegrationContextImpl(), "Different type to IntegrationContextImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link IntegrationContextImpl#setAppVersion(String)}
   *   <li>{@link IntegrationContextImpl#setBusinessKey(String)}
   *   <li>{@link IntegrationContextImpl#setClientId(String)}
   *   <li>{@link IntegrationContextImpl#setClientName(String)}
   *   <li>{@link IntegrationContextImpl#setClientType(String)}
   *   <li>{@link IntegrationContextImpl#setConnectorType(String)}
   *   <li>{@link IntegrationContextImpl#setExecutionId(String)}
   *   <li>{@link IntegrationContextImpl#setId(String)}
   *   <li>{@link IntegrationContextImpl#setParentProcessInstanceId(String)}
   *   <li>{@link IntegrationContextImpl#setProcessDefinitionId(String)}
   *   <li>{@link IntegrationContextImpl#setProcessDefinitionKey(String)}
   *   <li>{@link IntegrationContextImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link IntegrationContextImpl#setProcessInstanceId(String)}
   *   <li>{@link IntegrationContextImpl#setRootProcessInstanceId(String)}
   *   <li>{@link IntegrationContextImpl#getAppVersion()}
   *   <li>{@link IntegrationContextImpl#getBusinessKey()}
   *   <li>{@link IntegrationContextImpl#getClientId()}
   *   <li>{@link IntegrationContextImpl#getClientName()}
   *   <li>{@link IntegrationContextImpl#getClientType()}
   *   <li>{@link IntegrationContextImpl#getConnectorType()}
   *   <li>{@link IntegrationContextImpl#getExecutionId()}
   *   <li>{@link IntegrationContextImpl#getId()}
   *   <li>{@link IntegrationContextImpl#getInBoundVariables()}
   *   <li>{@link IntegrationContextImpl#getOutBoundVariables()}
   *   <li>{@link IntegrationContextImpl#getParentProcessInstanceId()}
   *   <li>{@link IntegrationContextImpl#getProcessDefinitionId()}
   *   <li>{@link IntegrationContextImpl#getProcessDefinitionKey()}
   *   <li>{@link IntegrationContextImpl#getProcessDefinitionVersion()}
   *   <li>{@link IntegrationContextImpl#getProcessInstanceId()}
   *   <li>{@link IntegrationContextImpl#getRootProcessInstanceId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act
    integrationContextImpl.setAppVersion("1.0.2");
    integrationContextImpl.setBusinessKey("Business Key");
    integrationContextImpl.setClientId("42");
    integrationContextImpl.setClientName("Dr Jane Doe");
    integrationContextImpl.setClientType("Client Type");
    integrationContextImpl.setConnectorType("Connector Type");
    integrationContextImpl.setExecutionId("42");
    integrationContextImpl.setId("42");
    integrationContextImpl.setParentProcessInstanceId("42");
    integrationContextImpl.setProcessDefinitionId("42");
    integrationContextImpl.setProcessDefinitionKey("Process Definition Key");
    integrationContextImpl.setProcessDefinitionVersion(1);
    integrationContextImpl.setProcessInstanceId("42");
    integrationContextImpl.setRootProcessInstanceId("42");
    String actualAppVersion = integrationContextImpl.getAppVersion();
    String actualBusinessKey = integrationContextImpl.getBusinessKey();
    String actualClientId = integrationContextImpl.getClientId();
    String actualClientName = integrationContextImpl.getClientName();
    String actualClientType = integrationContextImpl.getClientType();
    String actualConnectorType = integrationContextImpl.getConnectorType();
    String actualExecutionId = integrationContextImpl.getExecutionId();
    String actualId = integrationContextImpl.getId();
    Map<String, Object> actualInBoundVariables = integrationContextImpl.getInBoundVariables();
    Map<String, Object> actualOutBoundVariables = integrationContextImpl.getOutBoundVariables();
    String actualParentProcessInstanceId = integrationContextImpl.getParentProcessInstanceId();
    String actualProcessDefinitionId = integrationContextImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = integrationContextImpl.getProcessDefinitionKey();
    Integer actualProcessDefinitionVersion = integrationContextImpl.getProcessDefinitionVersion();
    String actualProcessInstanceId = integrationContextImpl.getProcessInstanceId();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualAppVersion);
    assertEquals("42", actualClientId);
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualId);
    assertEquals("42", actualParentProcessInstanceId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", integrationContextImpl.getRootProcessInstanceId());
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Client Type", actualClientType);
    assertEquals("Connector Type", actualConnectorType);
    assertEquals("Dr Jane Doe", actualClientName);
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertTrue(actualInBoundVariables.isEmpty());
    assertTrue(actualOutBoundVariables.isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link IntegrationContextImpl}
   */
  @Test
  void testNewIntegrationContextImpl() {
    // Arrange and Act
    IntegrationContextImpl actualIntegrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertNull(actualIntegrationContextImpl.getProcessDefinitionVersion());
    assertNull(actualIntegrationContextImpl.getAppVersion());
    assertNull(actualIntegrationContextImpl.getBusinessKey());
    assertNull(actualIntegrationContextImpl.getClientId());
    assertNull(actualIntegrationContextImpl.getClientName());
    assertNull(actualIntegrationContextImpl.getClientType());
    assertNull(actualIntegrationContextImpl.getConnectorType());
    assertNull(actualIntegrationContextImpl.getExecutionId());
    assertNull(actualIntegrationContextImpl.getParentProcessInstanceId());
    assertNull(actualIntegrationContextImpl.getProcessDefinitionId());
    assertNull(actualIntegrationContextImpl.getProcessDefinitionKey());
    assertNull(actualIntegrationContextImpl.getProcessInstanceId());
    assertNull(actualIntegrationContextImpl.getRootProcessInstanceId());
    assertTrue(actualIntegrationContextImpl.getInBoundVariables().isEmpty());
    assertTrue(actualIntegrationContextImpl.getOutBoundVariables().isEmpty());
  }
}
