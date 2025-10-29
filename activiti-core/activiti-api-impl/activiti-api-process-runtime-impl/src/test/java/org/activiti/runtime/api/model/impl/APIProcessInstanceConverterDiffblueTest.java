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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIProcessInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class APIProcessInstanceConverterDiffblueTest {
  @Autowired
  private APIProcessInstanceConverter aPIProcessInstanceConverter;

  /**
   * Method under test:
   * {@link APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  void testFrom() {
    // Arrange and Act
    org.activiti.api.process.model.ProcessInstance actualFromResult = aPIProcessInstanceConverter
        .from(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertNull(actualFromResult.getProcessDefinitionVersion());
    assertNull(actualFromResult.getAppVersion());
    assertNull(actualFromResult.getBusinessKey());
    assertNull(actualFromResult.getId());
    assertNull(actualFromResult.getInitiator());
    assertNull(actualFromResult.getName());
    assertNull(actualFromResult.getParentId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessDefinitionKey());
    assertNull(actualFromResult.getProcessDefinitionName());
    assertNull(actualFromResult.getCompletedDate());
    assertNull(actualFromResult.getStartDate());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.CREATED,
        actualFromResult.getStatus());
  }

  /**
   * Method under test:
   * {@link APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ExecutionEntityImpl internalProcessInstance = mock(ExecutionEntityImpl.class);
    when(internalProcessInstance.isSuspended()).thenReturn(true);
    when(internalProcessInstance.getAppVersion()).thenReturn(1);
    when(internalProcessInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(internalProcessInstance.getId()).thenReturn("42");
    when(internalProcessInstance.getBusinessKey()).thenReturn("Business Key");
    when(internalProcessInstance.getName()).thenReturn("Name");
    when(internalProcessInstance.getParentProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(internalProcessInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(internalProcessInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(internalProcessInstance.getStartTime()).thenReturn(fromResult);

    // Act
    org.activiti.api.process.model.ProcessInstance actualFromResult = aPIProcessInstanceConverter
        .from(internalProcessInstance);

    // Assert
    verify(internalProcessInstance).getId();
    verify(internalProcessInstance).getAppVersion();
    verify(internalProcessInstance).getBusinessKey();
    verify(internalProcessInstance).getName();
    verify(internalProcessInstance).getParentProcessInstanceId();
    verify(internalProcessInstance).getProcessDefinitionId();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionKey();
    verify(internalProcessInstance).getProcessDefinitionName();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(internalProcessInstance).getStartTime();
    verify(internalProcessInstance).getStartUserId();
    verify(internalProcessInstance).isSuspended();
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertEquals("1", actualFromResult.getAppVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("42", actualFromResult.getInitiator());
    assertEquals("42", actualFromResult.getParentId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("Business Key", actualFromResult.getBusinessKey());
    assertEquals("Name", actualFromResult.getName());
    assertEquals("Process Definition Key", actualFromResult.getProcessDefinitionKey());
    assertEquals("Process Definition Name", actualFromResult.getProcessDefinitionName());
    assertNull(actualFromResult.getCompletedDate());
    assertEquals(1, actualFromResult.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.SUSPENDED,
        actualFromResult.getStatus());
    assertSame(fromResult, actualFromResult.getStartDate());
  }

  /**
   * Method under test:
   * {@link APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  void testFrom3() {
    // Arrange
    ExecutionEntityImpl internalProcessInstance = mock(ExecutionEntityImpl.class);
    when(internalProcessInstance.isEnded()).thenReturn(true);
    when(internalProcessInstance.isSuspended()).thenReturn(false);
    when(internalProcessInstance.getAppVersion()).thenReturn(1);
    when(internalProcessInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(internalProcessInstance.getId()).thenReturn("42");
    when(internalProcessInstance.getBusinessKey()).thenReturn("Business Key");
    when(internalProcessInstance.getName()).thenReturn("Name");
    when(internalProcessInstance.getParentProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(internalProcessInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(internalProcessInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(internalProcessInstance.getStartTime()).thenReturn(fromResult);

    // Act
    org.activiti.api.process.model.ProcessInstance actualFromResult = aPIProcessInstanceConverter
        .from(internalProcessInstance);

    // Assert
    verify(internalProcessInstance).getId();
    verify(internalProcessInstance).getAppVersion();
    verify(internalProcessInstance).getBusinessKey();
    verify(internalProcessInstance).getName();
    verify(internalProcessInstance).getParentProcessInstanceId();
    verify(internalProcessInstance).getProcessDefinitionId();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionKey();
    verify(internalProcessInstance).getProcessDefinitionName();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(internalProcessInstance).getStartTime();
    verify(internalProcessInstance).getStartUserId();
    verify(internalProcessInstance).isEnded();
    verify(internalProcessInstance).isSuspended();
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertEquals("1", actualFromResult.getAppVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("42", actualFromResult.getInitiator());
    assertEquals("42", actualFromResult.getParentId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("Business Key", actualFromResult.getBusinessKey());
    assertEquals("Name", actualFromResult.getName());
    assertEquals("Process Definition Key", actualFromResult.getProcessDefinitionKey());
    assertEquals("Process Definition Name", actualFromResult.getProcessDefinitionName());
    assertNull(actualFromResult.getCompletedDate());
    assertEquals(1, actualFromResult.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.COMPLETED,
        actualFromResult.getStatus());
    assertSame(fromResult, actualFromResult.getStartDate());
  }

  /**
   * Method under test:
   * {@link APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  void testFrom4() {
    // Arrange
    ExecutionEntityImpl internalProcessInstance = mock(ExecutionEntityImpl.class);
    when(internalProcessInstance.isEnded()).thenReturn(false);
    when(internalProcessInstance.isSuspended()).thenReturn(false);
    when(internalProcessInstance.getAppVersion()).thenReturn(1);
    when(internalProcessInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(internalProcessInstance.getId()).thenReturn("42");
    when(internalProcessInstance.getBusinessKey()).thenReturn("Business Key");
    when(internalProcessInstance.getName()).thenReturn("Name");
    when(internalProcessInstance.getParentProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(internalProcessInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(internalProcessInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(internalProcessInstance.getStartTime()).thenReturn(fromResult);

    // Act
    org.activiti.api.process.model.ProcessInstance actualFromResult = aPIProcessInstanceConverter
        .from(internalProcessInstance);

    // Assert
    verify(internalProcessInstance).getId();
    verify(internalProcessInstance).getAppVersion();
    verify(internalProcessInstance).getBusinessKey();
    verify(internalProcessInstance).getName();
    verify(internalProcessInstance).getParentProcessInstanceId();
    verify(internalProcessInstance).getProcessDefinitionId();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionKey();
    verify(internalProcessInstance).getProcessDefinitionName();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(internalProcessInstance, atLeast(1)).getStartTime();
    verify(internalProcessInstance).getStartUserId();
    verify(internalProcessInstance).isEnded();
    verify(internalProcessInstance).isSuspended();
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertEquals("1", actualFromResult.getAppVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("42", actualFromResult.getInitiator());
    assertEquals("42", actualFromResult.getParentId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("Business Key", actualFromResult.getBusinessKey());
    assertEquals("Name", actualFromResult.getName());
    assertEquals("Process Definition Key", actualFromResult.getProcessDefinitionKey());
    assertEquals("Process Definition Name", actualFromResult.getProcessDefinitionName());
    assertNull(actualFromResult.getCompletedDate());
    assertEquals(1, actualFromResult.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.RUNNING,
        actualFromResult.getStatus());
    assertSame(fromResult, actualFromResult.getStartDate());
  }
}
