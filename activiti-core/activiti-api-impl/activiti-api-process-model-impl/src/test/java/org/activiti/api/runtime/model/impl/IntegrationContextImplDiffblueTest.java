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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntegrationContextImplDiffblueTest {
  /**
   * Test new {@link IntegrationContextImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link IntegrationContextImpl}
   */
  @Test
  @DisplayName("Test new IntegrationContextImpl (default constructor)")
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

  /**
   * Test {@link IntegrationContextImpl#addInBoundVariable(String, Object)}.
   * <p>
   * Method under test:
   * {@link IntegrationContextImpl#addInBoundVariable(String, Object)}
   */
  @Test
  @DisplayName("Test addInBoundVariable(String, Object)")
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
   * Test {@link IntegrationContextImpl#addOutBoundVariable(String, Object)}.
   * <p>
   * Method under test:
   * {@link IntegrationContextImpl#addOutBoundVariable(String, Object)}
   */
  @Test
  @DisplayName("Test addOutBoundVariable(String, Object)")
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
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
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
   * Test {@link IntegrationContextImpl#equals(Object)}, and
   * {@link IntegrationContextImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link IntegrationContextImpl#equals(Object)}
   *   <li>{@link IntegrationContextImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act and Assert
    assertEquals(integrationContextImpl, integrationContextImpl);
    int expectedHashCodeResult = integrationContextImpl.hashCode();
    assertEquals(expectedHashCodeResult, integrationContextImpl.hashCode());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientId("42");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setConnectorType("Connector Type");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientName("Dr Jane Doe");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setClientType("Client Type");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.setExecutionId("42");

    // Act and Assert
    assertNotEquals(integrationContextImpl, new IntegrationContextImpl());
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new IntegrationContextImpl(), null);
  }

  /**
   * Test {@link IntegrationContextImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new IntegrationContextImpl(), "Different type to IntegrationContextImpl");
  }

  /**
   * Test {@link IntegrationContextImpl#getInBoundVariable(String, Class)} with
   * {@code name}, {@code type}.
   * <p>
   * Method under test:
   * {@link IntegrationContextImpl#getInBoundVariable(String, Class)}
   */
  @Test
  @DisplayName("Test getInBoundVariable(String, Class) with 'name', 'type'")
  void testGetInBoundVariableWithNameType() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(integrationContextImpl.getInBoundVariable("Name", type));
  }

  /**
   * Test {@link IntegrationContextImpl#getInBoundVariable(String)} with
   * {@code name}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#getInBoundVariable(String)}
   */
  @Test
  @DisplayName("Test getInBoundVariable(String) with 'name'; given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetInBoundVariableWithName_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> inboundVariables = new HashMap<>();
    inboundVariables.computeIfPresent("foo", mock(BiFunction.class));

    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariables(inboundVariables);

    // Act and Assert
    assertNull(integrationContextImpl.getInBoundVariable("Name"));
  }

  /**
   * Test {@link IntegrationContextImpl#getInBoundVariable(String)} with
   * {@code name}.
   * <ul>
   *   <li>Given {@link IntegrationContextImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#getInBoundVariable(String)}
   */
  @Test
  @DisplayName("Test getInBoundVariable(String) with 'name'; given IntegrationContextImpl (default constructor)")
  void testGetInBoundVariableWithName_givenIntegrationContextImpl() {
    // Arrange, Act and Assert
    assertNull((new IntegrationContextImpl()).getInBoundVariable("Name"));
  }

  /**
   * Test {@link IntegrationContextImpl#getOutBoundVariable(String, Class)} with
   * {@code name}, {@code type}.
   * <p>
   * Method under test:
   * {@link IntegrationContextImpl#getOutBoundVariable(String, Class)}
   */
  @Test
  @DisplayName("Test getOutBoundVariable(String, Class) with 'name', 'type'")
  void testGetOutBoundVariableWithNameType() {
    // Arrange
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(integrationContextImpl.getOutBoundVariable("Name", type));
  }

  /**
   * Test {@link IntegrationContextImpl#getOutBoundVariable(String)} with
   * {@code name}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#getOutBoundVariable(String)}
   */
  @Test
  @DisplayName("Test getOutBoundVariable(String) with 'name'; given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetOutBoundVariableWithName_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> inboundVariables = new HashMap<>();
    inboundVariables.computeIfPresent("foo", mock(BiFunction.class));

    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariables(inboundVariables);

    // Act and Assert
    assertNull(integrationContextImpl.getOutBoundVariable("Name"));
  }

  /**
   * Test {@link IntegrationContextImpl#getOutBoundVariable(String)} with
   * {@code name}.
   * <ul>
   *   <li>Given {@link IntegrationContextImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextImpl#getOutBoundVariable(String)}
   */
  @Test
  @DisplayName("Test getOutBoundVariable(String) with 'name'; given IntegrationContextImpl (default constructor)")
  void testGetOutBoundVariableWithName_givenIntegrationContextImpl() {
    // Arrange, Act and Assert
    assertNull((new IntegrationContextImpl()).getOutBoundVariable("Name"));
  }
}
