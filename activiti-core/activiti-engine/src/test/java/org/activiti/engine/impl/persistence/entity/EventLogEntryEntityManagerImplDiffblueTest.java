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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.EventLogEntryDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventLogEntryDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventLogEntryEntityManagerImplDiffblueTest {
  @Mock
  private EventLogEntryDataManager eventLogEntryDataManager;

  @InjectMocks
  private EventLogEntryEntityManagerImpl eventLogEntryEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test:
   * {@link EventLogEntryEntityManagerImpl#findAllEventLogEntries()}
   */
  @Test
  public void testFindAllEventLogEntries() {
    // Arrange
    EventLogEntryDataManager eventLogEntryDataManager = mock(EventLogEntryDataManager.class);
    ArrayList<EventLogEntry> eventLogEntryList = new ArrayList<>();
    when(eventLogEntryDataManager.findAllEventLogEntries()).thenReturn(eventLogEntryList);

    // Act
    List<EventLogEntry> actualFindAllEventLogEntriesResult = (new EventLogEntryEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventLogEntryDataManager)).findAllEventLogEntries();

    // Assert
    verify(eventLogEntryDataManager).findAllEventLogEntries();
    assertTrue(actualFindAllEventLogEntriesResult.isEmpty());
    assertSame(eventLogEntryList, actualFindAllEventLogEntriesResult);
  }

  /**
   * Method under test:
   * {@link EventLogEntryEntityManagerImpl#findEventLogEntries(long, long)}
   */
  @Test
  public void testFindEventLogEntries() {
    // Arrange
    EventLogEntryDataManager eventLogEntryDataManager = mock(EventLogEntryDataManager.class);
    ArrayList<EventLogEntry> eventLogEntryList = new ArrayList<>();
    when(eventLogEntryDataManager.findEventLogEntries(anyLong(), anyLong())).thenReturn(eventLogEntryList);

    // Act
    List<EventLogEntry> actualFindEventLogEntriesResult = (new EventLogEntryEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventLogEntryDataManager)).findEventLogEntries(1L, 3L);

    // Assert
    verify(eventLogEntryDataManager).findEventLogEntries(eq(1L), eq(3L));
    assertTrue(actualFindEventLogEntriesResult.isEmpty());
    assertSame(eventLogEntryList, actualFindEventLogEntriesResult);
  }

  /**
   * Method under test:
   * {@link EventLogEntryEntityManagerImpl#findEventLogEntriesByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventLogEntriesByProcessInstanceId() {
    // Arrange
    ArrayList<EventLogEntry> eventLogEntryList = new ArrayList<>();
    when(eventLogEntryDataManager.findEventLogEntriesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(eventLogEntryList);

    // Act
    List<EventLogEntry> actualFindEventLogEntriesByProcessInstanceIdResult = eventLogEntryEntityManagerImpl
        .findEventLogEntriesByProcessInstanceId("42");

    // Assert
    verify(eventLogEntryDataManager).findEventLogEntriesByProcessInstanceId(eq("42"));
    assertTrue(actualFindEventLogEntriesByProcessInstanceIdResult.isEmpty());
    assertSame(eventLogEntryList, actualFindEventLogEntriesByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link EventLogEntryEntityManagerImpl#deleteEventLogEntry(long)}
   */
  @Test
  public void testDeleteEventLogEntry() {
    // Arrange
    EventLogEntryDataManager eventLogEntryDataManager = mock(EventLogEntryDataManager.class);
    doNothing().when(eventLogEntryDataManager).deleteEventLogEntry(anyLong());

    // Act
    (new EventLogEntryEntityManagerImpl(new JtaProcessEngineConfiguration(), eventLogEntryDataManager))
        .deleteEventLogEntry(1L);

    // Assert
    verify(eventLogEntryDataManager).deleteEventLogEntry(eq(1L));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link EventLogEntryEntityManagerImpl#EventLogEntryEntityManagerImpl(ProcessEngineConfigurationImpl, EventLogEntryDataManager)}
   *   <li>
   * {@link EventLogEntryEntityManagerImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}
   *   <li>{@link EventLogEntryEntityManagerImpl#getDataManager()}
   *   <li>{@link EventLogEntryEntityManagerImpl#getEventLogEntryDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    EventLogEntryEntityManagerImpl actualEventLogEntryEntityManagerImpl = new EventLogEntryEntityManagerImpl(
        processEngineConfiguration, new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()));
    MybatisEventLogEntryDataManager eventLogEntryDataManager = new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration());
    actualEventLogEntryEntityManagerImpl.setEventLogEntryDataManager(eventLogEntryDataManager);
    DataManager<EventLogEntryEntity> actualDataManager = actualEventLogEntryEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(eventLogEntryDataManager, actualDataManager);
    assertSame(eventLogEntryDataManager, actualEventLogEntryEntityManagerImpl.getEventLogEntryDataManager());
  }
}
