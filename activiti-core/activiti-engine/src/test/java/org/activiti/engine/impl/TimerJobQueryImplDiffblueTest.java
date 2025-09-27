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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TimerJobQueryImplDiffblueTest {
  /**
   * Test {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#TimerJobQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobQueryImpl.<init>()"})
  public void testNewTimerJobQueryImpl() {
    // Arrange and Act
    TimerJobQueryImpl actualTimerJobQueryImpl = new TimerJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualTimerJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualTimerJobQueryImpl.getOrderByColumns());
    assertNull(actualTimerJobQueryImpl.getDatabaseType());
    assertNull(actualTimerJobQueryImpl.getExceptionMessage());
    assertNull(actualTimerJobQueryImpl.getExecutionId());
    assertNull(actualTimerJobQueryImpl.getId());
    assertNull(actualTimerJobQueryImpl.getProcessDefinitionId());
    assertNull(actualTimerJobQueryImpl.getProcessInstanceId());
    assertNull(actualTimerJobQueryImpl.getTenantId());
    assertNull(actualTimerJobQueryImpl.getTenantIdLike());
    assertNull(actualTimerJobQueryImpl.orderBy);
    assertNull(actualTimerJobQueryImpl.getDuedateHigherThan());
    assertNull(actualTimerJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualTimerJobQueryImpl.getDuedateLowerThan());
    assertNull(actualTimerJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualTimerJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualTimerJobQueryImpl.resultType);
    assertNull(actualTimerJobQueryImpl.commandContext);
    assertNull(actualTimerJobQueryImpl.commandExecutor);
    assertNull(actualTimerJobQueryImpl.orderProperty);
    assertEquals(0, actualTimerJobQueryImpl.getFirstResult());
    assertEquals(1, actualTimerJobQueryImpl.getFirstRow());
    assertFalse(actualTimerJobQueryImpl.getExecutable());
    assertFalse(actualTimerJobQueryImpl.getRetriesLeft());
    assertFalse(actualTimerJobQueryImpl.isNoRetriesLeft());
    assertFalse(actualTimerJobQueryImpl.isOnlyMessages());
    assertFalse(actualTimerJobQueryImpl.isOnlyTimers());
    assertFalse(actualTimerJobQueryImpl.isWithException());
    assertFalse(actualTimerJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualTimerJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualTimerJobQueryImpl.getMaxResults());
    Object actualParameter = actualTimerJobQueryImpl.getParameter();
    assertSame(actualTimerJobQueryImpl, actualParameter);
  }

  /**
   * Test {@link TimerJobQueryImpl#jobId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobId(String)"})
  public void testJobId_when42_thenTimerJobQueryImplIdIs42() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualJobIdResult = timerJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getId());
    assertSame(timerJobQueryImpl, actualJobIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#jobId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobId(String)"})
  public void testJobId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new TimerJobQueryImpl().jobId(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} ProcessInstanceId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_when42_thenTimerJobQueryImplProcessInstanceIdIs42() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualProcessInstanceIdResult = timerJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getProcessInstanceId());
    assertSame(timerJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().processInstanceId(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} ProcessDefinitionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_when42_thenTimerJobQueryImplProcessDefinitionIdIs42() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualProcessDefinitionIdResult = timerJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getProcessDefinitionId());
    assertSame(timerJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().processDefinitionId(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} ExecutionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.executionId(String)"})
  public void testExecutionId_when42_thenTimerJobQueryImplExecutionIdIs42() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualExecutionIdResult = timerJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getExecutionId());
    assertSame(timerJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.executionId(String)"})
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new TimerJobQueryImpl().executionId(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#executable()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#executable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.executable()"})
  public void testExecutable() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualExecutableResult = timerJobQueryImpl.executable();

    // Assert
    assertTrue(timerJobQueryImpl.getExecutable());
    assertSame(timerJobQueryImpl, actualExecutableResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#timers()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#timers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.timers()"})
  public void testTimers() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualTimersResult = timerJobQueryImpl.timers();

    // Assert
    assertTrue(timerJobQueryImpl.isOnlyTimers());
    assertSame(timerJobQueryImpl, actualTimersResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#messages()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#messages()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.messages()"})
  public void testMessages() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualMessagesResult = timerJobQueryImpl.messages();

    // Assert
    assertTrue(timerJobQueryImpl.isOnlyMessages());
    assertSame(timerJobQueryImpl, actualMessagesResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThan(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateHigherThanResult = timerJobQueryImpl.duedateHigherThan(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateHigherThanResult);
    assertSame(date, timerJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThan(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateHigherThan(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThan(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateLowerThanResult = timerJobQueryImpl.duedateLowerThan(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateLowerThanResult);
    assertSame(date, timerJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThan(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateLowerThan(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThen(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateHigherThenResult = timerJobQueryImpl.duedateHigherThen(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateHigherThenResult);
    assertSame(date, timerJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThen(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateHigherThen(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThenOrEquals(Date)"})
  public void testDuedateHigherThenOrEquals_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateHigherThenOrEqualsResult =
        timerJobQueryImpl.duedateHigherThenOrEquals(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateHigherThenOrEqualsResult);
    assertSame(date, timerJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateHigherThenOrEquals(Date)"})
  public void testDuedateHigherThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateHigherThenOrEquals(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThen(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateLowerThenResult = timerJobQueryImpl.duedateLowerThen(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateLowerThenResult);
    assertSame(date, timerJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThen(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateLowerThen(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>Then return {@link TimerJobQueryImpl#TimerJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThenOrEquals(Date)"})
  public void testDuedateLowerThenOrEquals_thenReturnTimerJobQueryImpl() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    TimerJobQueryImpl actualDuedateLowerThenOrEqualsResult =
        timerJobQueryImpl.duedateLowerThenOrEquals(date);

    // Assert
    assertSame(timerJobQueryImpl, actualDuedateLowerThenOrEqualsResult);
    assertSame(date, timerJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Test {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.duedateLowerThenOrEquals(Date)"})
  public void testDuedateLowerThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().duedateLowerThenOrEquals(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#withException()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#withException()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.withException()"})
  public void testWithException() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualWithExceptionResult = timerJobQueryImpl.withException();

    // Assert
    assertTrue(timerJobQueryImpl.isWithException());
    assertSame(timerJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#exceptionMessage(String)}.
   *
   * <ul>
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} ExceptionMessage is {@code Exception
   *       Message}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage_thenTimerJobQueryImplExceptionMessageIsExceptionMessage() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualExceptionMessageResult =
        timerJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", timerJobQueryImpl.getExceptionMessage());
    assertSame(timerJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#exceptionMessage(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().exceptionMessage(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#jobTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} TenantId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_when42_thenTimerJobQueryImplTenantIdIs42() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualJobTenantIdResult = timerJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getTenantId());
    assertSame(timerJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#jobTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new TimerJobQueryImpl().jobTenantId(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#jobTenantIdLike(String)}.
   *
   * <ul>
   *   <li>Then {@link TimerJobQueryImpl#TimerJobQueryImpl()} TenantIdLike is {@code Tenant Id
   *       Like}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_thenTimerJobQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualJobTenantIdLikeResult =
        timerJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", timerJobQueryImpl.getTenantIdLike());
    assertSame(timerJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#jobTenantIdLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new TimerJobQueryImpl().jobTenantIdLike(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#jobWithoutTenantId()}.
   *
   * <p>Method under test: {@link TimerJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQueryImpl TimerJobQueryImpl.jobWithoutTenantId()"})
  public void testJobWithoutTenantId() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualJobWithoutTenantIdResult = timerJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(timerJobQueryImpl.isWithoutTenantId());
    assertSame(timerJobQueryImpl, actualJobWithoutTenantIdResult);
  }

  /**
   * Test {@link TimerJobQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long TimerJobQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    timerJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.executeCount(null));
  }

  /**
   * Test {@link TimerJobQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobQueryImpl#executeList(CommandContext, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List TimerJobQueryImpl.executeList(CommandContext, Page)"})
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    timerJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> timerJobQueryImpl.executeList(null, new Page(1, 3)));
  }
}
