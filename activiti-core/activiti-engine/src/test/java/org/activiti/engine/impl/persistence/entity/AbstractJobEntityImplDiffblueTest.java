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
   * Test {@link AbstractJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>Then return size is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenDeadLetterJobEntityImpl_thenReturnSizeIsThree() {
    // Arrange and Act
    Object actualPersistentState = (new DeadLetterJobEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("exceptionMessage"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code exceptionMessage} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnExceptionMessageIsNull() {
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
   * Test {@link AbstractJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnSizeIsFour() {
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
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("exceptionMessage"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) ExecutionId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution_given42_thenDeadLetterJobEntityImplExecutionIdIs42() {
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
   * Test {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) ExecutionId is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution_thenDeadLetterJobEntityImplExecutionIdIsNull() {
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
   * Test {@link AbstractJobEntityImpl#getDuedate()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getDuedate()}
   */
  @Test
  public void testGetDuedate_givenDeadLetterJobEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getDuedate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getDuedate()}.
   * <ul>
   *   <li>Then return {@link DeadLetterJobEntityImpl} (default constructor)
   * {@link AbstractJobEntityImpl#duedate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getDuedate()}
   */
  @Test
  public void testGetDuedate_thenReturnDeadLetterJobEntityImplDuedate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(deadLetterJobEntityImpl.duedate, deadLetterJobEntityImpl.getDuedate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setDuedate(Date)}.
   * <p>
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
   * Test {@link AbstractJobEntityImpl#setDuedate(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor)
   * PersistentState {@code duedate} is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setDuedate(java.util.Date)}
   */
  @Test
  public void testSetDuedate_whenDate_thenDeadLetterJobEntityImplPersistentStateDuedateIsDate() {
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
   * Test {@link AbstractJobEntityImpl#getExecutionId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExecutionId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExecutionId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExecutionId(String)}
   */
  @Test
  public void testSetExecutionId_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setExecutionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExecutionId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExecutionId(String)}
   */
  @Test
  public void testSetExecutionId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setExecutionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRetries()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRetries()}
   */
  @Test
  public void testGetRetries_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getRetries());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRetries()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRetries()}
   */
  @Test
  public void testGetRetries_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals(0, deadLetterJobEntityImpl.getRetries());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRetries(int)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRetries(int)}
   */
  @Test
  public void testSetRetries_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl.setRetries(1);

    // Assert
    assertEquals(1, deadLetterJobEntityImpl.getRetries());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRetries(int)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor)
   * PersistentState {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRetries(int)}
   */
  @Test
  public void testSetRetries_thenDeadLetterJobEntityImplPersistentStateMap() {
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
   * Test {@link AbstractJobEntityImpl#getProcessInstanceId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getProcessInstanceId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessInstanceId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setProcessInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessInstanceId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setProcessInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#isExclusive()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive_givenDeadLetterJobEntityImplDuedateIsDate_thenReturnTrue() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertTrue(deadLetterJobEntityImpl.isExclusive());
  }

  /**
   * Test {@link AbstractJobEntityImpl#isExclusive()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Exclusive is
   * {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive_givenDeadLetterJobEntityImplExclusiveIsFalse_thenReturnFalse() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExclusive(false);

    // Act and Assert
    assertFalse(deadLetterJobEntityImpl.isExclusive());
  }

  /**
   * Test {@link AbstractJobEntityImpl#isExclusive()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  public void testIsExclusive_givenDeadLetterJobEntityImpl_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new DeadLetterJobEntityImpl()).isExclusive());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getProcessDefinitionId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getProcessDefinitionId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  public void testSetProcessDefinitionId_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  public void testSetProcessDefinitionId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRepeat()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRepeat()}
   */
  @Test
  public void testGetRepeat_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRepeat()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRepeat()}
   */
  @Test
  public void testGetRepeat_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRepeat(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRepeat(String)}
   */
  @Test
  public void testSetRepeat_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setRepeat("Repeat");

    // Assert
    assertEquals("Repeat", deadLetterJobEntityImpl2.getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRepeat(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRepeat(String)}
   */
  @Test
  public void testSetRepeat_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setRepeat("Repeat");

    // Assert
    assertEquals("Repeat", deadLetterJobEntityImpl2.getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getEndDate()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getEndDate()}
   */
  @Test
  public void testGetEndDate_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getEndDate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getEndDate()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getEndDate()}
   */
  @Test
  public void testGetEndDate_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setEndDate(Date)}.
   * <p>
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
   * Test {@link AbstractJobEntityImpl#setEndDate(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) EndDate is
   * {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setEndDate(java.util.Date)}
   */
  @Test
  public void testSetEndDate_whenDate_thenDeadLetterJobEntityImplEndDateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    java.sql.Date endDate = mock(java.sql.Date.class);

    // Act
    deadLetterJobEntityImpl.setEndDate(endDate);

    // Assert
    assertSame(endDate, deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getMaxIterations()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getMaxIterations()}
   */
  @Test
  public void testGetMaxIterations_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getMaxIterations()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getMaxIterations()}
   */
  @Test
  public void testGetMaxIterations_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals(0, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setMaxIterations(int)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setMaxIterations(int)}
   */
  @Test
  public void testSetMaxIterations_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setMaxIterations(3);

    // Assert
    assertEquals(3, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setMaxIterations(int)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setMaxIterations(int)}
   */
  @Test
  public void testSetMaxIterations_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl.setMaxIterations(3);

    // Assert
    assertEquals(3, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerType()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerType()}
   */
  @Test
  public void testGetJobHandlerType_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerType()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerType()}
   */
  @Test
  public void testGetJobHandlerType_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerType(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerType(String)}
   */
  @Test
  public void testSetJobHandlerType_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobHandlerType("Job Handler Type");

    // Assert
    assertEquals("Job Handler Type", deadLetterJobEntityImpl2.getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerType(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerType(String)}
   */
  @Test
  public void testSetJobHandlerType_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobHandlerType("Job Handler Type");

    // Assert
    assertEquals("Job Handler Type", deadLetterJobEntityImpl2.getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}
   */
  @Test
  public void testGetJobHandlerConfiguration_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}
   */
  @Test
  public void testGetJobHandlerConfiguration_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}
   */
  @Test
  public void testSetJobHandlerConfiguration_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobHandlerConfiguration("Job Handler Configuration");

    // Assert
    assertEquals("Job Handler Configuration", deadLetterJobEntityImpl2.getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}
   */
  @Test
  public void testSetJobHandlerConfiguration_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobHandlerConfiguration("Job Handler Configuration");

    // Assert
    assertEquals("Job Handler Configuration", deadLetterJobEntityImpl2.getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobType()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobType()}
   */
  @Test
  public void testGetJobType_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobType()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobType()}
   */
  @Test
  public void testGetJobType_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobType(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobType(String)}
   */
  @Test
  public void testSetJobType_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setJobType("Job Type");

    // Assert
    assertEquals("Job Type", deadLetterJobEntityImpl2.getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobType(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobType(String)}
   */
  @Test
  public void testSetJobType_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setJobType("Job Type");

    // Assert
    assertEquals("Job Type", deadLetterJobEntityImpl2.getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getTenantId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertEquals("", (new DeadLetterJobEntityImpl()).getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getTenantId()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals("", deadLetterJobEntityImpl.getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setTenantId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setTenantId(String)}
   */
  @Test
  public void testSetTenantId_givenDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl2.setTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setTenantId(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setTenantId(String)}
   */
  @Test
  public void testSetTenantId_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    deadLetterJobEntityImpl2.setTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl2.getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionStacktrace()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionStacktrace());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionStacktrace()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionStacktrace());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionStacktrace()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor)
   * ExceptionStacktrace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  public void testGetExceptionStacktrace_givenDeadLetterJobEntityImplExceptionStacktraceIsNull() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExceptionStacktrace(null);

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionStacktrace());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExceptionStacktrace(String)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor)
   * PersistentState {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractJobEntityImpl#setExceptionStacktrace(String)}
   */
  @Test
  public void testSetExceptionStacktrace_thenDeadLetterJobEntityImplPersistentStateMap() {
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
   * Test {@link AbstractJobEntityImpl#getExceptionMessage()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionMessage()}
   */
  @Test
  public void testGetExceptionMessage_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionMessage());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionMessage()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionMessage()}
   */
  @Test
  public void testGetExceptionMessage_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionMessage());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <p>
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
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  public void testSetExceptionMessage_givenDeadLetterJobEntityImplDuedateIsDate() {
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
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor)
   * ExceptionMessage is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  public void testSetExceptionMessage_thenDeadLetterJobEntityImplExceptionMessageIsEmptyString() {
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
   * Test {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}
   */
  @Test
  public void testGetExceptionByteArrayRef_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionByteArrayRef());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}
   */
  @Test
  public void testGetExceptionByteArrayRef_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionByteArrayRef());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getUtf8Bytes(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes_givenDeadLetterJobEntityImplDuedateIsDate() throws UnsupportedEncodingException {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl2 = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl2.setDuedate(mock(Date.class));

    // Act
    byte[] actualUtf8Bytes = deadLetterJobEntityImpl2.getUtf8Bytes("Str");

    // Assert
    assertArrayEquals("Str".getBytes("UTF-8"), actualUtf8Bytes);
  }

  /**
   * Test {@link AbstractJobEntityImpl#getUtf8Bytes(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes_givenDeadLetterJobEntityImpl_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getUtf8Bytes(null));
  }

  /**
   * Test {@link AbstractJobEntityImpl#getUtf8Bytes(String)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code Str} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  public void testGetUtf8Bytes_givenDeadLetterJobEntityImpl_whenStr_thenReturnStrBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange and Act
    byte[] actualUtf8Bytes = (new DeadLetterJobEntityImpl()).getUtf8Bytes("Str");

    // Assert
    assertArrayEquals("Str".getBytes("UTF-8"), actualUtf8Bytes);
  }

  /**
   * Test {@link AbstractJobEntityImpl#toString()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#toString()}
   */
  @Test
  public void testToString_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertEquals("DeadLetterJobEntity [id=null]", (new DeadLetterJobEntityImpl()).toString());
  }

  /**
   * Test {@link AbstractJobEntityImpl#toString()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Duedate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#toString()}
   */
  @Test
  public void testToString_givenDeadLetterJobEntityImplDuedateIsDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDuedate(mock(Date.class));

    // Act and Assert
    assertEquals("DeadLetterJobEntity [id=null]", deadLetterJobEntityImpl.toString());
  }
}
