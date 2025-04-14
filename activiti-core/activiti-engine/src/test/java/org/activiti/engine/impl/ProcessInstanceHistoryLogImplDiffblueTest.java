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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import org.activiti.engine.history.HistoricData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessInstanceHistoryLogImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessInstanceHistoryLogImpl#ProcessInstanceHistoryLogImpl(HistoricProcessInstance)}
   *   <li>{@link ProcessInstanceHistoryLogImpl#getHistoricData()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.<init>(HistoricProcessInstance)",
      "java.util.List ProcessInstanceHistoryLogImpl.getHistoricData()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue(
        (new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl())).getHistoricData().isEmpty());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code historicEvents}.
   * <p>
   * Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents() {
    // Arrange
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl = new ProcessInstanceHistoryLogImpl(
        new HistoricProcessInstanceEntityImpl());

    // Act
    processInstanceHistoryLogImpl.addHistoricData(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(processInstanceHistoryLogImpl.getHistoricData().isEmpty());
  }

  /**
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code historicEvents}.
   * <p>
   * Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents2() {
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
   * Test {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)} with {@code historicEvents}.
   * <p>
   * Method under test: {@link ProcessInstanceHistoryLogImpl#addHistoricData(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessInstanceHistoryLogImpl.addHistoricData(Collection)"})
  public void testAddHistoricDataWithHistoricEvents3() {
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
}
