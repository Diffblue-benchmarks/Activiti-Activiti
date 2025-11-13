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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.activiti.engine.history.HistoricData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessInstanceHistoryLogImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ProcessInstanceHistoryLogImpl#ProcessInstanceHistoryLogImpl(HistoricProcessInstance)}
   *   <li>{@link ProcessInstanceHistoryLogImpl#getHistoricData()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessInstanceHistoryLogImpl.<init>(HistoricProcessInstance)",
    "List ProcessInstanceHistoryLogImpl.getHistoricData()"
  })
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getHistoricData()
            .isEmpty());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getId()"})
  public void testGetId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl()).getId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getBusinessKey()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getBusinessKey()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getBusinessKey()"})
  public void testGetBusinessKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getBusinessKey());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getProcessDefinitionId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getProcessDefinitionId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getProcessDefinitionId()"})
  public void testGetProcessDefinitionId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getProcessDefinitionId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getStartTime()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getStartTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date ProcessInstanceHistoryLogImpl.getStartTime()"})
  public void testGetStartTime_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl()).getStartTime());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getEndTime()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getEndTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date ProcessInstanceHistoryLogImpl.getEndTime()"})
  public void testGetEndTime_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl()).getEndTime());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getDurationInMillis()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getDurationInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Long ProcessInstanceHistoryLogImpl.getDurationInMillis()"})
  public void testGetDurationInMillis_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getDurationInMillis());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getStartUserId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getStartUserId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getStartUserId()"})
  public void testGetStartUserId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getStartUserId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getStartActivityId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getStartActivityId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getStartActivityId()"})
  public void testGetStartActivityId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getStartActivityId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getDeleteReason()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getDeleteReason()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getDeleteReason()"})
  public void testGetDeleteReason_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getDeleteReason());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getSuperProcessInstanceId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getSuperProcessInstanceId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getSuperProcessInstanceId()"})
  public void testGetSuperProcessInstanceId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())
            .getSuperProcessInstanceId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#getTenantId()}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#getTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ProcessInstanceHistoryLogImpl.getTenantId()"})
  public void testGetTenantId_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals(
        "",
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl()).getTenantId());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(HistoricData)} with {@code
   * historicEvent}.
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(HistoricData)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(HistoricData)"})
  public void testAddHistoricDataWithHistoricEvent() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());
    HistoricData historicEvent = mock(HistoricData.class);

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvent);

    // Assert
    List<HistoricData> historicData = processInstanceHistoryLogImpl.getHistoricData();
    assertEquals(1, historicData.size());
    assertSame(historicEvent, historicData.get(0));
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code
   * historicEvents}.
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());

    // Act
    processInstanceHistoryLogImpl.addHistoricData(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(processInstanceHistoryLogImpl.getHistoricData().isEmpty());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code
   * historicEvents}.
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());

    ArrayList<HistoricData> historicEvents = new ArrayList<>();
    historicEvents.add(mock(HistoricData.class));

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvents);

    // Assert
    assertEquals(1, processInstanceHistoryLogImpl.getHistoricData().size());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code
   * historicEvents}.
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents3() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());

    ArrayList<HistoricData> historicEvents = new ArrayList<>();
    historicEvents.add(mock(HistoricData.class));
    historicEvents.add(mock(HistoricData.class));

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvents);

    // Assert
    assertEquals(historicEvents, processInstanceHistoryLogImpl.getHistoricData());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricData#getTime()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.orderHistoricData()"})
  public void testOrderHistoricData_thenCallsGetTime() {
    // Arrange
    HistoricData historicEvent = mock(HistoricData.class);
    when(historicEvent.getTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    HistoricData historicEvent2 = mock(HistoricData.class);
    when(historicEvent2.getTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(historicEvent2);
    processInstanceHistoryLogImpl.addHistoricData(historicEvent);

    // Act
    processInstanceHistoryLogImpl.orderHistoricData();

    // Assert
    verify(historicEvent2).getTime();
    verify(historicEvent).getTime();
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricData#getTime()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.orderHistoricData()"})
  public void testOrderHistoricData_thenCallsGetTime2() {
    // Arrange
    HistoricData historicEvent = mock(HistoricData.class);
    when(historicEvent.getTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    HistoricData historicEvent2 = mock(HistoricData.class);
    when(historicEvent2.getTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    HistoricData historicEvent3 = mock(HistoricData.class);
    when(historicEvent3.getTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(historicEvent3);
    processInstanceHistoryLogImpl.addHistoricData(historicEvent2);
    processInstanceHistoryLogImpl.addHistoricData(historicEvent);

    // Act
    processInstanceHistoryLogImpl.orderHistoricData();

    // Assert
    verify(historicEvent3).getTime();
    verify(historicEvent).getTime();
    verify(historicEvent2, atLeast(1)).getTime();
  }
}
