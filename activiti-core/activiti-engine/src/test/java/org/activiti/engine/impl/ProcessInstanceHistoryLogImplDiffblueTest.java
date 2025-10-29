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

public class ProcessInstanceHistoryLogImplDiffblueTest {
  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getId()}
   */
  @Test
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getId()}
   */
  @Test
  public void testGetId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getBusinessKey()}
   */
  @Test
  public void testGetBusinessKey() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getBusinessKey());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getBusinessKey()}
   */
  @Test
  public void testGetBusinessKey2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getBusinessKey());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getProcessDefinitionId());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getProcessDefinitionId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartTime()}
   */
  @Test
  public void testGetStartTime() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getStartTime());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartTime()}
   */
  @Test
  public void testGetStartTime2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getStartTime());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getEndTime()}
   */
  @Test
  public void testGetEndTime() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getEndTime());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getEndTime()}
   */
  @Test
  public void testGetEndTime2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getEndTime());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getDurationInMillis()}
   */
  @Test
  public void testGetDurationInMillis() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getDurationInMillis());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getDurationInMillis()}
   */
  @Test
  public void testGetDurationInMillis2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getDurationInMillis());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartUserId()}
   */
  @Test
  public void testGetStartUserId() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getStartUserId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartUserId()}
   */
  @Test
  public void testGetStartUserId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getStartUserId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartActivityId()}
   */
  @Test
  public void testGetStartActivityId() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getStartActivityId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getStartActivityId()}
   */
  @Test
  public void testGetStartActivityId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getStartActivityId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getDeleteReason()}
   */
  @Test
  public void testGetDeleteReason() {
    // Arrange, Act and Assert
    assertNull((new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getDeleteReason());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getDeleteReason()}
   */
  @Test
  public void testGetDeleteReason2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getDeleteReason());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getSuperProcessInstanceId()}
   */
  @Test
  public void testGetSuperProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull(
        (new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getSuperProcessInstanceId());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#getSuperProcessInstanceId()}
   */
  @Test
  public void testGetSuperProcessInstanceId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertNull(processInstanceHistoryLogImpl.getSuperProcessInstanceId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId() {
    // Arrange, Act and Assert
    assertEquals("", (new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getTenantId());
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#getTenantId()}
   */
  @Test
  public void testGetTenantId2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act and Assert
    assertEquals("", processInstanceHistoryLogImpl.getTenantId());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  public void testAddHistoricData() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());

    // Act
    processInstanceHistoryLogImpl.addHistoricData(new ArrayList<>());

    // Assert
    assertTrue(processInstanceHistoryLogImpl.getHistoricData().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  public void testAddHistoricData2() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(mock(HistoricData.class));

    // Act
    processInstanceHistoryLogImpl.addHistoricData(new ArrayList<>());

    // Assert
    assertEquals(1, processInstanceHistoryLogImpl.getHistoricData().size());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  public void testAddHistoricData3() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());

    ArrayList<HistoricData> historicEvents = new ArrayList<>();
    historicEvents.add(mock(HistoricData.class));

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvents);

    // Assert
    assertEquals(1, processInstanceHistoryLogImpl.getHistoricData().size());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  public void testAddHistoricData4() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());

    ArrayList<HistoricData> historicEvents = new ArrayList<>();
    historicEvents.add(mock(HistoricData.class));
    historicEvents.add(mock(HistoricData.class));

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvents);

    // Assert
    assertEquals(historicEvents, processInstanceHistoryLogImpl.getHistoricData());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceHistoryLogImpl#addHistoricData(HistoricData)}
   */
  @Test
  public void testAddHistoricData5() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    HistoricData historicEvent = mock(HistoricData.class);

    // Act
    processInstanceHistoryLogImpl.addHistoricData(historicEvent);

    // Assert
    List<HistoricData> historicData = processInstanceHistoryLogImpl.getHistoricData();
    assertEquals(1, historicData.size());
    assertSame(historicEvent, historicData.get(0));
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}
   */
  @Test
  public void testOrderHistoricData() {
    // Arrange
    HistoricData historicEvent = mock(HistoricData.class);
    when(historicEvent.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    HistoricData historicEvent2 = mock(HistoricData.class);
    when(historicEvent2.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
    processInstanceHistoryLogImpl.addHistoricData(historicEvent2);
    processInstanceHistoryLogImpl.addHistoricData(historicEvent);

    // Act
    processInstanceHistoryLogImpl.orderHistoricData();

    // Assert
    verify(historicEvent2).getTime();
    verify(historicEvent).getTime();
  }

  /**
   * Method under test: {@link ProcessInstanceHistoryLogImpl#orderHistoricData()}
   */
  @Test
  public void testOrderHistoricData2() {
    // Arrange
    HistoricData historicEvent = mock(HistoricData.class);
    when(historicEvent.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    HistoricData historicEvent2 = mock(HistoricData.class);
    when(historicEvent2.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    HistoricData historicEvent3 = mock(HistoricData.class);
    when(historicEvent3.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());
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

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessInstanceHistoryLogImpl#ProcessInstanceHistoryLogImpl(HistoricProcessInstance)}
   *   <li>{@link ProcessInstanceHistoryLogImpl#getHistoricData()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue(
        (new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getHistoricData().isEmpty());
  }
}
