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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.events.ProcessDefinitionEvent;
import org.activiti.api.process.model.events.ProcessDefinitionEvent.ProcessDefinitionEvents;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessDeployedEventImplDiffblueTest {
  /**
   * Test {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl()}.
   *
   * <p>Method under test: {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessDeployedEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDeployedEventImpl.<init>()"})
  void testNewProcessDeployedEventImpl() {
    // Arrange and Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl = new ProcessDeployedEventImpl();

    // Assert
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getEntity());
    assertEquals(
        ProcessDefinitionEvents.PROCESS_DEPLOYED, actualProcessDeployedEventImpl.getEventType());
  }

  /**
   * Test {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then Entity return {@link ProcessDefinitionImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition)}
   */
  @Test
  @DisplayName(
      "Test new ProcessDeployedEventImpl(ProcessDefinition); then Entity return ProcessDefinitionImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDeployedEventImpl.<init>(ProcessDefinition)"})
  void testNewProcessDeployedEventImpl_thenEntityReturnProcessDefinitionImpl() {
    // Arrange
    ProcessDefinitionImpl entity = new ProcessDefinitionImpl();

    // Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl = new ProcessDeployedEventImpl(entity);

    // Assert
    ProcessDefinition entity2 = actualProcessDeployedEventImpl.getEntity();
    assertTrue(entity2 instanceof ProcessDefinitionImpl);
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessDefinitionEvents.PROCESS_DEPLOYED, actualProcessDeployedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition, String)}.
   *
   * <ul>
   *   <li>Then Entity return {@link ProcessDefinitionImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition, String)}
   */
  @Test
  @DisplayName(
      "Test new ProcessDeployedEventImpl(ProcessDefinition, String); then Entity return ProcessDefinitionImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDeployedEventImpl.<init>(ProcessDefinition, String)"})
  void testNewProcessDeployedEventImpl_thenEntityReturnProcessDefinitionImpl2() {
    // Arrange
    ProcessDefinitionImpl entity = new ProcessDefinitionImpl();

    // Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl =
        new ProcessDeployedEventImpl(entity, "Not all who wander are lost");

    // Assert
    ProcessDefinition entity2 = actualProcessDeployedEventImpl.getEntity();
    assertTrue(entity2 instanceof ProcessDefinitionImpl);
    assertEquals(
        "Not all who wander are lost", actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessDefinitionEvents.PROCESS_DEPLOYED, actualProcessDeployedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link ProcessDeployedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link ProcessDeployedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionEvents ProcessDeployedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessDefinitionEvents.PROCESS_DEPLOYED, new ProcessDeployedEventImpl().getEventType());
  }

  /**
   * Test {@link ProcessDeployedEventImpl#getProcessModelContent()}.
   *
   * <p>Method under test: {@link ProcessDeployedEventImpl#getProcessModelContent()}
   */
  @Test
  @DisplayName("Test getProcessModelContent()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessDeployedEventImpl.getProcessModelContent()"})
  void testGetProcessModelContent() {
    // Arrange, Act and Assert
    assertNull(new ProcessDeployedEventImpl().getProcessModelContent());
  }
}
