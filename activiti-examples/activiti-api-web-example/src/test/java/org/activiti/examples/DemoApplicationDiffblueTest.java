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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#processFile(String)}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#processFile(String)}
   */
  @Test
  @DisplayName("Test processFile(String); then return a string")
  void testProcessFile_thenReturnAString() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());

    // Act
    String actualProcessFileResult = (new DemoApplication(processRuntime)).processFile("Not all who wander are lost");

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    assertEquals(
        ">>> Created Process Instance: ProcessInstance{id='null', name='null', processDefinitionId='null',"
            + " processDefinitionKey='null', parentId='null', initiator='null', startDate=null, completedDate=null,"
            + " businessKey='null', status=null, processDefinitionVersion='null', processDefinitionName='null'}",
        actualProcessFileResult);
  }

  /**
   * Test {@link DemoApplication#getProcessDefinition()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#getProcessDefinition()}
   */
  @Test
  @DisplayName("Test getProcessDefinition(); then return Empty")
  void testGetProcessDefinition_thenReturnEmpty() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    // Act
    List<ProcessDefinition> actualProcessDefinition = (new DemoApplication(processRuntime)).getProcessDefinition();

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    assertTrue(actualProcessDefinition.isEmpty());
  }
}
