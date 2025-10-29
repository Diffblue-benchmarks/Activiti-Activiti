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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractJobEntityImplDiffblueTest {
  @InjectMocks
  private DeadLetterJobEntityImpl deadLetterJobEntityImpl;

  /**
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new DeadLetterJobEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionMessage"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExceptionStacktrace(null);

    // Act
    Object actualPersistentState = deadLetterJobEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(4, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionByteArrayId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionMessage"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act
    Object actualPersistentState = deadLetterJobEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("exceptionMessage"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("retries"));
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertNull(deadLetterJobEntityImpl.getExecutionId());
    assertNull(deadLetterJobEntityImpl.getProcessDefinitionId());
    assertNull(deadLetterJobEntityImpl.getProcessInstanceId());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");

    // Act
    deadLetterJobEntityImpl.setExecution(execution);

    // Assert
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    assertEquals("42", deadLetterJobEntityImpl.getExecutionId());
    assertEquals("42", deadLetterJobEntityImpl.getProcessDefinitionId());
    assertEquals("42", deadLetterJobEntityImpl.getProcessInstanceId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getDuedate()}
   */
  @Test
  public void testGetDuedate() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getDuedate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getDuedate()}
   */
  @Test
  public void testGetDuedate2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(deadLetterJobEntityImpl.duedate, deadLetterJobEntityImpl.getDuedate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setDuedate(Date)}
   */
  @Test
  public void testSetDuedate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    java.util.Date duedate = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    deadLetterJobEntityImpl.setDuedate(duedate);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
    assertSame(duedate, deadLetterJobEntityImpl.getDuedate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setDuedate(java.util.Date)}
   */
  @Test
  public void testSetDuedate2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    java.sql.Date duedate = mock(java.sql.Date.class);

    // Act
    deadLetterJobEntityImpl.setDuedate(duedate);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
    assertSame(duedate, deadLetterJobEntityImpl.getDuedate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExecutionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExecutionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setExecutionId(String)}
   */
  @Test
  public void testSetExecutionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setExecutionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getExecutionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setExecutionId(String)}
   */
  @Test
  public void testSetExecutionId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setExecutionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getExecutionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getRetries()}
   */
  @Test
  public void testGetRetries() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getRetries());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getRetries()}
   */
  @Test
  public void testGetRetries2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals(0, deadLetterJobEntityImpl.getRetries());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setRetries(int)}
   */
  @Test
  public void testSetRetries() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setRetries(1);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertEquals(1, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(1, deadLetterJobEntityImpl.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setRetries(int)}
   */
  @Test
  public void testSetRetries2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl.setRetries(1);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(1, deadLetterJobEntityImpl.getRetries());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessInstanceId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getProcessInstanceId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setProcessInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessInstanceId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setProcessInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessInstanceId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive() {
    // Arrange, Act and Assert
    assertTrue((new DeadLetterJobEntityImpl()).isExclusive());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertTrue(deadLetterJobEntityImpl.isExclusive());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive3() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExclusive(false);

    // Act and Assert
    assertFalse(deadLetterJobEntityImpl.isExclusive());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessDefinitionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getProcessDefinitionId());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  public void testSetProcessDefinitionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessDefinitionId());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  public void testSetProcessDefinitionId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessDefinitionId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getRepeat()}
   */
  @Test
  public void testGetRepeat() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getRepeat());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getRepeat()}
   */
  @Test
  public void testGetRepeat2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getRepeat());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setRepeat(String)}
   */
  @Test
  public void testSetRepeat() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setRepeat("Repeat");

    // Assert
    assertEquals("Repeat", deadLetterJobEntityImpl2.getRepeat());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setRepeat(String)}
   */
  @Test
  public void testSetRepeat2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setRepeat("Repeat");

    // Assert
    assertEquals("Repeat", deadLetterJobEntityImpl2.getRepeat());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getEndDate()}
   */
  @Test
  public void testGetEndDate() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getEndDate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getEndDate()}
   */
  @Test
  public void testGetEndDate2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setEndDate(Date)}
   */
  @Test
  public void testSetEndDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    java.util.Date endDate = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    deadLetterJobEntityImpl.setEndDate(endDate);

    // Assert
    assertSame(endDate, deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setEndDate(java.util.Date)}
   */
  @Test
  public void testSetEndDate2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    java.sql.Date endDate = mock(java.sql.Date.class);

    // Act
    deadLetterJobEntityImpl.setEndDate(endDate);

    // Assert
    assertSame(endDate, deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getMaxIterations()}
   */
  @Test
  public void testGetMaxIterations() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getMaxIterations());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getMaxIterations()}
   */
  @Test
  public void testGetMaxIterations2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals(0, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setMaxIterations(int)}
   */
  @Test
  public void testSetMaxIterations() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setMaxIterations(3);

    // Assert
    assertEquals(3, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setMaxIterations(int)}
   */
  @Test
  public void testSetMaxIterations2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl.setMaxIterations(3);

    // Assert
    assertEquals(3, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerType()}
   */
  @Test
  public void testGetJobHandlerType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerType()}
   */
  @Test
  public void testGetJobHandlerType2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobHandlerType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerType(String)}
   */
  @Test
  public void testSetJobHandlerType() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobHandlerType("Job Handler Type");

    // Assert
    assertEquals("Job Handler Type", deadLetterJobEntityImpl2.getJobHandlerType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerType(String)}
   */
  @Test
  public void testSetJobHandlerType2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobHandlerType("Job Handler Type");

    // Assert
    assertEquals("Job Handler Type", deadLetterJobEntityImpl2.getJobHandlerType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}
   */
  @Test
  public void testGetJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerConfiguration());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}
   */
  @Test
  public void testGetJobHandlerConfiguration2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobHandlerConfiguration());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}
   */
  @Test
  public void testSetJobHandlerConfiguration() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobHandlerConfiguration("Job Handler Configuration");

    // Assert
    assertEquals("Job Handler Configuration", deadLetterJobEntityImpl2.getJobHandlerConfiguration());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}
   */
  @Test
  public void testSetJobHandlerConfiguration2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobHandlerConfiguration("Job Handler Configuration");

    // Assert
    assertEquals("Job Handler Configuration", deadLetterJobEntityImpl2.getJobHandlerConfiguration());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobType()}
   */
  @Test
  public void testGetJobType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getJobType()}
   */
  @Test
  public void testGetJobType2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setJobType(String)}
   */
  @Test
  public void testSetJobType() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobType("Job Type");

    // Assert
    assertEquals("Job Type", deadLetterJobEntityImpl2.getJobType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setJobType(String)}
   */
  @Test
  public void testSetJobType2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobType("Job Type");

    // Assert
    assertEquals("Job Type", deadLetterJobEntityImpl2.getJobType());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId() {
    // Arrange, Act and Assert
    assertEquals("", (new DeadLetterJobEntityImpl()).getTenantId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals("", deadLetterJobEntityImpl.getTenantId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setTenantId(String)}
   */
  @Test
  public void testSetTenantId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getTenantId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setTenantId(String)}
   */
  @Test
  public void testSetTenantId2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getTenantId());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionStacktrace());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExceptionStacktrace(null);

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionStacktrace());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace3() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionStacktrace());
  }

  /**
   * Method under test:
   * {@link AbstractJobEntityImpl#setExceptionStacktrace(String)}
   */
  @Test
  public void testSetExceptionStacktrace() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setExceptionStacktrace(null);

    // Assert
    Object persistentState = deadLetterJobEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    ByteArrayRef exceptionByteArrayRef = deadLetterJobEntityImpl2.getExceptionByteArrayRef();
    assertEquals("stacktrace", exceptionByteArrayRef.getName());
    assertNull(exceptionByteArrayRef.getBytes());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("exceptionByteArrayId"));
    assertNull(exceptionByteArrayRef.getId());
    assertNull(exceptionByteArrayRef.getEntity());
    assertFalse(exceptionByteArrayRef.isDeleted());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionMessage()}
   */
  @Test
  public void testGetExceptionMessage() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionMessage());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionMessage()}
   */
  @Test
  public void testGetExceptionMessage2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionMessage());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  public void testSetExceptionMessage() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setExceptionMessage("An error occurred");

    // Assert
    Object persistentState = deadLetterJobEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("An error occurred", deadLetterJobEntityImpl2.getExceptionMessage());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  public void testSetExceptionMessage2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setExceptionMessage("");

    // Assert
    Object persistentState = deadLetterJobEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("", deadLetterJobEntityImpl2.getExceptionMessage());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals("", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  public void testSetExceptionMessage3() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setExceptionMessage("An error occurred");

    // Assert
    Object persistentState = deadLetterJobEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("An error occurred", deadLetterJobEntityImpl2.getExceptionMessage());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}
   */
  @Test
  public void testGetExceptionByteArrayRef() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionByteArrayRef());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}
   */
  @Test
  public void testGetExceptionByteArrayRef2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionByteArrayRef());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes() throws UnsupportedEncodingException {
    // Arrange and Act
    byte[] actualUtf8Bytes = (new DeadLetterJobEntityImpl()).getUtf8Bytes("Str");

    // Assert
    assertArrayEquals("Str".getBytes("UTF-8"), actualUtf8Bytes);
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes2() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getUtf8Bytes(null));
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes3() throws UnsupportedEncodingException {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    byte[] actualUtf8Bytes = deadLetterJobEntityImpl2.getUtf8Bytes("Str");

    // Assert
    assertArrayEquals("Str".getBytes("UTF-8"), actualUtf8Bytes);
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("DeadLetterJobEntity [id=null]", (new DeadLetterJobEntityImpl()).toString());
  }

  /**
   * Method under test: {@link AbstractJobEntityImpl#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals("DeadLetterJobEntity [id=null]", deadLetterJobEntityImpl.toString());
  }
}
