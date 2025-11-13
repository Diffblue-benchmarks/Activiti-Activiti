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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RuntimeEventImplDiffblueTest {
  /**
   * Test {@link RuntimeEventImpl#getEntity()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getEntity()}
   */
  @Test
  @DisplayName("Test getEntity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RuntimeEventImpl.getEntity()"})
  void testGetEntity() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getEntity());
  }

  /**
   * Test {@link RuntimeEventImpl#getProcessInstanceId()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getProcessInstanceId()}
   */
  @Test
  @DisplayName("Test getProcessInstanceId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RuntimeEventImpl.getProcessInstanceId()"})
  void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getProcessInstanceId());
  }

  /**
   * Test {@link RuntimeEventImpl#getProcessDefinitionId()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getProcessDefinitionId()}
   */
  @Test
  @DisplayName("Test getProcessDefinitionId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RuntimeEventImpl.getProcessDefinitionId()"})
  void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getProcessDefinitionId());
  }

  /**
   * Test {@link RuntimeEventImpl#getProcessDefinitionKey()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getProcessDefinitionKey()}
   */
  @Test
  @DisplayName("Test getProcessDefinitionKey()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RuntimeEventImpl.getProcessDefinitionKey()"})
  void testGetProcessDefinitionKey() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getProcessDefinitionKey());
  }

  /**
   * Test {@link RuntimeEventImpl#getProcessDefinitionVersion()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getProcessDefinitionVersion()}
   */
  @Test
  @DisplayName("Test getProcessDefinitionVersion()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer RuntimeEventImpl.getProcessDefinitionVersion()"})
  void testGetProcessDefinitionVersion() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getProcessDefinitionVersion());
  }

  /**
   * Test {@link RuntimeEventImpl#getBusinessKey()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getBusinessKey()}
   */
  @Test
  @DisplayName("Test getBusinessKey()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RuntimeEventImpl.getBusinessKey()"})
  void testGetBusinessKey() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getBusinessKey());
  }

  /**
   * Test {@link RuntimeEventImpl#getParentProcessInstanceId()}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#getParentProcessInstanceId()}
   */
  @Test
  @DisplayName("Test getParentProcessInstanceId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RuntimeEventImpl.getParentProcessInstanceId()"})
  void testGetParentProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull(new VariableCreatedEventImpl().getParentProcessInstanceId());
  }

  /**
   * Test {@link RuntimeEventImpl#setParentProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setParentProcessInstanceId(String)}
   */
  @Test
  @DisplayName("Test setParentProcessInstanceId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setParentProcessInstanceId(String)"})
  void testSetParentProcessInstanceId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setParentProcessInstanceId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getParentProcessInstanceId());
  }

  /**
   * Test {@link RuntimeEventImpl#setProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setProcessDefinitionId(String)}
   */
  @Test
  @DisplayName("Test setProcessDefinitionId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setProcessDefinitionId(String)"})
  void testSetProcessDefinitionId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link RuntimeEventImpl#setProcessDefinitionKey(String)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName("Test setProcessDefinitionKey(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setProcessDefinitionKey(String)"})
  void testSetProcessDefinitionKey() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", variableCreatedEventImpl.getProcessDefinitionKey());
  }

  /**
   * Test {@link RuntimeEventImpl#setProcessDefinitionVersion(Integer)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setProcessDefinitionVersion(Integer)}
   */
  @Test
  @DisplayName("Test setProcessDefinitionVersion(Integer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setProcessDefinitionVersion(Integer)"})
  void testSetProcessDefinitionVersion() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionVersion(1);

    // Assert
    assertEquals(1, variableCreatedEventImpl.getProcessDefinitionVersion().intValue());
  }

  /**
   * Test {@link RuntimeEventImpl#setBusinessKey(String)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setBusinessKey(String)}
   */
  @Test
  @DisplayName("Test setBusinessKey(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setBusinessKey(String)"})
  void testSetBusinessKey() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", variableCreatedEventImpl.getBusinessKey());
  }

  /**
   * Test {@link RuntimeEventImpl#setProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setProcessInstanceId(String)}
   */
  @Test
  @DisplayName("Test setProcessInstanceId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setProcessInstanceId(String)"})
  void testSetProcessInstanceId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getProcessInstanceId());
  }

  /**
   * Test {@link RuntimeEventImpl#setEntity(Object)}.
   *
   * <p>Method under test: {@link RuntimeEventImpl#setEntity(Object)}
   */
  @Test
  @DisplayName("Test setEntity(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeEventImpl.setEntity(Object)"})
  void testSetEntity() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();
    VariableInstanceImpl<Object> variableInstanceImpl = new VariableInstanceImpl<>();

    // Act
    variableCreatedEventImpl.setEntity(variableInstanceImpl);

    // Assert
    assertSame(variableInstanceImpl, variableCreatedEventImpl.getEntity());
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}, and {@link RuntimeEventImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RuntimeEventImpl#equals(Object)}
   *   <li>{@link RuntimeEventImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act and Assert
    assertEquals(variableCreatedEventImpl, variableCreatedEventImpl);
    int expectedHashCodeResult = variableCreatedEventImpl.hashCode();
    assertEquals(expectedHashCodeResult, variableCreatedEventImpl.hashCode());
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl =
        new VariableCreatedEventImpl(new VariableInstanceImpl<>(), "42");

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();
    variableCreatedEventImpl.setBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableCreatedEventImpl(), null);
  }

  /**
   * Test {@link RuntimeEventImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeEventImpl.equals(Object)", "int RuntimeEventImpl.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableCreatedEventImpl(), "Different type to RuntimeEventImpl");
  }
}
