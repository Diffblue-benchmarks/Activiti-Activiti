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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SuspendedJobQueryImplDiffblueTest {
  /**
   * Test {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobQueryImpl.<init>()"})
  public void testNewSuspendedJobQueryImpl() {
    // Arrange and Act
    SuspendedJobQueryImpl actualSuspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualSuspendedJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualSuspendedJobQueryImpl.getOrderByColumns());
    assertNull(actualSuspendedJobQueryImpl.getDatabaseType());
    assertNull(actualSuspendedJobQueryImpl.getExceptionMessage());
    assertNull(actualSuspendedJobQueryImpl.getExecutionId());
    assertNull(actualSuspendedJobQueryImpl.getId());
    assertNull(actualSuspendedJobQueryImpl.getProcessDefinitionId());
    assertNull(actualSuspendedJobQueryImpl.getProcessInstanceId());
    assertNull(actualSuspendedJobQueryImpl.getTenantId());
    assertNull(actualSuspendedJobQueryImpl.getTenantIdLike());
    assertNull(actualSuspendedJobQueryImpl.orderBy);
    assertNull(actualSuspendedJobQueryImpl.getDuedateHigherThan());
    assertNull(actualSuspendedJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualSuspendedJobQueryImpl.getDuedateLowerThan());
    assertNull(actualSuspendedJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualSuspendedJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualSuspendedJobQueryImpl.resultType);
    assertNull(actualSuspendedJobQueryImpl.commandContext);
    assertNull(actualSuspendedJobQueryImpl.commandExecutor);
    assertNull(actualSuspendedJobQueryImpl.orderProperty);
    assertEquals(0, actualSuspendedJobQueryImpl.getFirstResult());
    assertEquals(1, actualSuspendedJobQueryImpl.getFirstRow());
    assertFalse(actualSuspendedJobQueryImpl.getExecutable());
    assertFalse(actualSuspendedJobQueryImpl.getRetriesLeft());
    assertFalse(actualSuspendedJobQueryImpl.isNoRetriesLeft());
    assertFalse(actualSuspendedJobQueryImpl.isOnlyMessages());
    assertFalse(actualSuspendedJobQueryImpl.isOnlyTimers());
    assertFalse(actualSuspendedJobQueryImpl.isWithException());
    assertFalse(actualSuspendedJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualSuspendedJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualSuspendedJobQueryImpl.getMaxResults());
    assertSame(actualSuspendedJobQueryImpl, actualSuspendedJobQueryImpl.getParameter());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobId(String)"})
  public void testJobId_when42_thenSuspendedJobQueryImplIdIs42() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualJobIdResult = suspendedJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getId());
    assertSame(suspendedJobQueryImpl, actualJobIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobId(String)"})
  public void testJobId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).jobId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} ProcessInstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_when42_thenSuspendedJobQueryImplProcessInstanceIdIs42() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualProcessInstanceIdResult = suspendedJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getProcessInstanceId());
    assertSame(suspendedJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).processInstanceId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_when42_thenSuspendedJobQueryImplProcessDefinitionIdIs42() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualProcessDefinitionIdResult = suspendedJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getProcessDefinitionId());
    assertSame(suspendedJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).processDefinitionId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.executionId(String)"})
  public void testExecutionId_when42_thenSuspendedJobQueryImplExecutionIdIs42() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualExecutionIdResult = suspendedJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getExecutionId());
    assertSame(suspendedJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.executionId(String)"})
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).executionId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#withRetriesLeft()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#withRetriesLeft()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.withRetriesLeft()"})
  public void testWithRetriesLeft() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualWithRetriesLeftResult = suspendedJobQueryImpl.withRetriesLeft();

    // Assert
    assertTrue(suspendedJobQueryImpl.getRetriesLeft());
    assertSame(suspendedJobQueryImpl, actualWithRetriesLeftResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executable()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executable()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.executable()"})
  public void testExecutable() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualExecutableResult = suspendedJobQueryImpl.executable();

    // Assert
    assertTrue(suspendedJobQueryImpl.getExecutable());
    assertSame(suspendedJobQueryImpl, actualExecutableResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#timers()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#timers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.timers()"})
  public void testTimers() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualTimersResult = suspendedJobQueryImpl.timers();

    // Assert
    assertTrue(suspendedJobQueryImpl.isOnlyTimers());
    assertSame(suspendedJobQueryImpl, actualTimersResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#messages()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#messages()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.messages()"})
  public void testMessages() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualMessagesResult = suspendedJobQueryImpl.messages();

    // Assert
    assertTrue(suspendedJobQueryImpl.isOnlyMessages());
    assertSame(suspendedJobQueryImpl, actualMessagesResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThan(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThan(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThen(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThenOrEquals(Date)"})
  public void testDuedateHigherThenOrEquals_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateHigherThenOrEquals(Date)"})
  public void testDuedateHigherThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new SuspendedJobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThen(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThenOrEquals(Date)"})
  public void testDuedateLowerThenOrEquals_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.duedateLowerThenOrEquals(Date)"})
  public void testDuedateLowerThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new SuspendedJobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#noRetriesLeft()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#noRetriesLeft()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.noRetriesLeft()"})
  public void testNoRetriesLeft() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualNoRetriesLeftResult = suspendedJobQueryImpl.noRetriesLeft();

    // Assert
    assertTrue(suspendedJobQueryImpl.isNoRetriesLeft());
    assertSame(suspendedJobQueryImpl, actualNoRetriesLeftResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#withException()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#withException()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.withException()"})
  public void testWithException() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualWithExceptionResult = suspendedJobQueryImpl.withException();

    // Assert
    assertTrue(suspendedJobQueryImpl.isWithException());
    assertSame(suspendedJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} ExceptionMessage is {@code Exception Message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage_thenSuspendedJobQueryImplExceptionMessageIsExceptionMessage() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualExceptionMessageResult = suspendedJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", suspendedJobQueryImpl.getExceptionMessage());
    assertSame(suspendedJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).exceptionMessage(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_when42_thenSuspendedJobQueryImplTenantIdIs42() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualJobTenantIdResult = suspendedJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getTenantId());
    assertSame(suspendedJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).jobTenantId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()} TenantIdLike is {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_thenSuspendedJobQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualJobTenantIdLikeResult = suspendedJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", suspendedJobQueryImpl.getTenantIdLike());
    assertSame(suspendedJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).jobTenantIdLike(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobWithoutTenantId()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobQueryImpl SuspendedJobQueryImpl.jobWithoutTenantId()"})
  public void testJobWithoutTenantId() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualJobWithoutTenantIdResult = suspendedJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(suspendedJobQueryImpl.isWithoutTenantId());
    assertSame(suspendedJobQueryImpl, actualJobWithoutTenantIdResult);
  }
}
