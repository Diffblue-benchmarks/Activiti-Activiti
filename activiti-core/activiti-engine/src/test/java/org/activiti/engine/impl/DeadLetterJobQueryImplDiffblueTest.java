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
public class DeadLetterJobQueryImplDiffblueTest {
  /**
   * Test {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobQueryImpl.<init>()"})
  public void testNewDeadLetterJobQueryImpl() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualDeadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualDeadLetterJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualDeadLetterJobQueryImpl.getOrderByColumns());
    assertNull(actualDeadLetterJobQueryImpl.getDatabaseType());
    assertNull(actualDeadLetterJobQueryImpl.getExceptionMessage());
    assertNull(actualDeadLetterJobQueryImpl.getExecutionId());
    assertNull(actualDeadLetterJobQueryImpl.getId());
    assertNull(actualDeadLetterJobQueryImpl.getProcessDefinitionId());
    assertNull(actualDeadLetterJobQueryImpl.getProcessInstanceId());
    assertNull(actualDeadLetterJobQueryImpl.getTenantId());
    assertNull(actualDeadLetterJobQueryImpl.getTenantIdLike());
    assertNull(actualDeadLetterJobQueryImpl.orderBy);
    assertNull(actualDeadLetterJobQueryImpl.getDuedateHigherThan());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateLowerThan());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualDeadLetterJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualDeadLetterJobQueryImpl.resultType);
    assertNull(actualDeadLetterJobQueryImpl.commandContext);
    assertNull(actualDeadLetterJobQueryImpl.commandExecutor);
    assertNull(actualDeadLetterJobQueryImpl.orderProperty);
    assertEquals(0, actualDeadLetterJobQueryImpl.getFirstResult());
    assertEquals(1, actualDeadLetterJobQueryImpl.getFirstRow());
    assertFalse(actualDeadLetterJobQueryImpl.getExecutable());
    assertFalse(actualDeadLetterJobQueryImpl.isOnlyMessages());
    assertFalse(actualDeadLetterJobQueryImpl.isOnlyTimers());
    assertFalse(actualDeadLetterJobQueryImpl.isWithException());
    assertFalse(actualDeadLetterJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualDeadLetterJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualDeadLetterJobQueryImpl.getMaxResults());
    Object actualParameter = actualDeadLetterJobQueryImpl.getParameter();
    assertSame(actualDeadLetterJobQueryImpl, actualParameter);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobId(String)"})
  public void testJobId_when42_thenDeadLetterJobQueryImplIdIs42() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualJobIdResult = deadLetterJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getId());
    assertSame(deadLetterJobQueryImpl, actualJobIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobId(String)"})
  public void testJobId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new DeadLetterJobQueryImpl().jobId(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} ProcessInstanceId is {@code
   *       42}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_when42_thenDeadLetterJobQueryImplProcessInstanceIdIs42() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualProcessInstanceIdResult =
        deadLetterJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getProcessInstanceId());
    assertSame(deadLetterJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().processInstanceId(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} ProcessDefinitionId is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_when42_thenDeadLetterJobQueryImplProcessDefinitionIdIs42() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualProcessDefinitionIdResult =
        deadLetterJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getProcessDefinitionId());
    assertSame(deadLetterJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().processDefinitionId(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} ExecutionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.executionId(String)"})
  public void testExecutionId_when42_thenDeadLetterJobQueryImplExecutionIdIs42() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualExecutionIdResult = deadLetterJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getExecutionId());
    assertSame(deadLetterJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.executionId(String)"})
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().executionId(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#executable()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#executable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.executable()"})
  public void testExecutable() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualExecutableResult = deadLetterJobQueryImpl.executable();

    // Assert
    assertTrue(deadLetterJobQueryImpl.getExecutable());
    assertSame(deadLetterJobQueryImpl, actualExecutableResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#timers()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#timers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.timers()"})
  public void testTimers() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualTimersResult = deadLetterJobQueryImpl.timers();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isOnlyTimers());
    assertSame(deadLetterJobQueryImpl, actualTimersResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#messages()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#messages()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.messages()"})
  public void testMessages() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualMessagesResult = deadLetterJobQueryImpl.messages();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isOnlyMessages());
    assertSame(deadLetterJobQueryImpl, actualMessagesResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateHigherThanResult =
        deadLetterJobQueryImpl.duedateHigherThan(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateHigherThanResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThan(Date)"})
  public void testDuedateHigherThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateHigherThan(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateLowerThanResult =
        deadLetterJobQueryImpl.duedateLowerThan(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateLowerThanResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThan(Date)"})
  public void testDuedateLowerThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateLowerThan(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateHigherThenResult =
        deadLetterJobQueryImpl.duedateHigherThen(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateHigherThenResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThen(Date)"})
  public void testDuedateHigherThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateHigherThen(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThenOrEquals(Date)"
  })
  public void testDuedateHigherThenOrEquals_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateHigherThenOrEqualsResult =
        deadLetterJobQueryImpl.duedateHigherThenOrEquals(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateHigherThenOrEqualsResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateHigherThenOrEquals(Date)"
  })
  public void testDuedateHigherThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateHigherThenOrEquals(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateLowerThenResult =
        deadLetterJobQueryImpl.duedateLowerThen(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateLowerThenResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThen(Date)"})
  public void testDuedateLowerThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateLowerThen(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThenOrEquals(Date)"
  })
  public void testDuedateLowerThenOrEquals_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    DeadLetterJobQueryImpl actualDuedateLowerThenOrEqualsResult =
        deadLetterJobQueryImpl.duedateLowerThenOrEquals(date);

    // Assert
    assertSame(deadLetterJobQueryImpl, actualDuedateLowerThenOrEqualsResult);
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobQueryImpl DeadLetterJobQueryImpl.duedateLowerThenOrEquals(Date)"
  })
  public void testDuedateLowerThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().duedateLowerThenOrEquals(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#withException()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#withException()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.withException()"})
  public void testWithException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualWithExceptionResult = deadLetterJobQueryImpl.withException();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isWithException());
    assertSame(deadLetterJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#exceptionMessage(String)}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualExceptionMessageResult =
        deadLetterJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", deadLetterJobQueryImpl.getExceptionMessage());
    assertSame(deadLetterJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#exceptionMessage(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.exceptionMessage(String)"})
  public void testExceptionMessage_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().exceptionMessage(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} TenantId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_when42_thenDeadLetterJobQueryImplTenantIdIs42() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualJobTenantIdResult = deadLetterJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getTenantId());
    assertSame(deadLetterJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobTenantId(String)"})
  public void testJobTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().jobTenantId(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}.
   *
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} TenantIdLike is {@code
   *       Tenant Id Like}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_thenDeadLetterJobQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualJobTenantIdLikeResult =
        deadLetterJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", deadLetterJobQueryImpl.getTenantIdLike());
    assertSame(deadLetterJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobTenantIdLike(String)"})
  public void testJobTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeadLetterJobQueryImpl().jobTenantIdLike(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#jobWithoutTenantId()}.
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQueryImpl DeadLetterJobQueryImpl.jobWithoutTenantId()"})
  public void testJobWithoutTenantId() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualJobWithoutTenantIdResult =
        deadLetterJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isWithoutTenantId());
    assertSame(deadLetterJobQueryImpl, actualJobWithoutTenantIdResult);
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long DeadLetterJobQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.executeCount(null));
  }

  /**
   * Test {@link DeadLetterJobQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobQueryImpl#executeList(CommandContext, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DeadLetterJobQueryImpl.executeList(CommandContext, Page)"})
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deadLetterJobQueryImpl.executeList(null, new Page(1, 3)));
  }
}
