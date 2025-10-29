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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.junit.jupiter.api.Test;

class RuntimeEventImplDiffblueTest {
  /**
   * Method under test: {@link RuntimeEventImpl#getId()}
   */
  @Test
  void testGetId() {
    // Arrange
    VariableInstanceImpl<Object> entity = mock(VariableInstanceImpl.class);
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    (new VariableCreatedEventImpl(entity, "42")).getId();

    // Assert
    verify(entity).getProcessInstanceId();
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getEntity()}
   */
  @Test
  void testGetEntity() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getEntity());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getTimestamp()}
   */
  @Test
  void testGetTimestamp() {
    // Arrange
    VariableInstanceImpl<Object> entity = mock(VariableInstanceImpl.class);
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    (new VariableCreatedEventImpl(entity, "42")).getTimestamp();

    // Assert
    verify(entity).getProcessInstanceId();
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getProcessInstanceId()}
   */
  @Test
  void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getProcessInstanceId());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getProcessDefinitionId()}
   */
  @Test
  void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getProcessDefinitionId());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getProcessDefinitionKey()}
   */
  @Test
  void testGetProcessDefinitionKey() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getProcessDefinitionKey());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getProcessDefinitionVersion()}
   */
  @Test
  void testGetProcessDefinitionVersion() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getProcessDefinitionVersion());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getBusinessKey()}
   */
  @Test
  void testGetBusinessKey() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getBusinessKey());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#getParentProcessInstanceId()}
   */
  @Test
  void testGetParentProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull((new VariableCreatedEventImpl()).getParentProcessInstanceId());
  }

  /**
   * Method under test:
   * {@link RuntimeEventImpl#setParentProcessInstanceId(String)}
   */
  @Test
  void testSetParentProcessInstanceId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setParentProcessInstanceId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getParentProcessInstanceId());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#setProcessDefinitionId(String)}
   */
  @Test
  void testSetProcessDefinitionId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getProcessDefinitionId());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#setProcessDefinitionKey(String)}
   */
  @Test
  void testSetProcessDefinitionKey() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", variableCreatedEventImpl.getProcessDefinitionKey());
  }

  /**
   * Method under test:
   * {@link RuntimeEventImpl#setProcessDefinitionVersion(Integer)}
   */
  @Test
  void testSetProcessDefinitionVersion() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessDefinitionVersion(1);

    // Assert
    assertEquals(1, variableCreatedEventImpl.getProcessDefinitionVersion().intValue());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#setBusinessKey(String)}
   */
  @Test
  void testSetBusinessKey() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", variableCreatedEventImpl.getBusinessKey());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#setProcessInstanceId(String)}
   */
  @Test
  void testSetProcessInstanceId() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act
    variableCreatedEventImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", variableCreatedEventImpl.getProcessInstanceId());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#setEntity(Object)}
   */
  @Test
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
   * Method under test: {@link RuntimeEventImpl#toString()}
   */
  @Test
  void testToString() {
    // Arrange
    VariableInstanceImpl<Object> entity = mock(VariableInstanceImpl.class);
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    (new VariableCreatedEventImpl(entity, "42")).toString();

    // Assert
    verify(entity).getProcessInstanceId();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RuntimeEventImpl#equals(Object)}
   *   <li>{@link RuntimeEventImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act and Assert
    assertEquals(variableCreatedEventImpl, variableCreatedEventImpl);
    int expectedHashCodeResult = variableCreatedEventImpl.hashCode();
    assertEquals(expectedHashCodeResult, variableCreatedEventImpl.hashCode());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl(new VariableInstanceImpl<>(),
        "42");

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableCreatedEventImpl(), mock(VariableDeletedEventImpl.class));
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VariableCreatedEventImpl variableCreatedEventImpl = new VariableCreatedEventImpl();
    variableCreatedEventImpl.setBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(variableCreatedEventImpl, new VariableCreatedEventImpl());
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableCreatedEventImpl(), null);
  }

  /**
   * Method under test: {@link RuntimeEventImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableCreatedEventImpl(), "Different type to RuntimeEventImpl");
  }
}
