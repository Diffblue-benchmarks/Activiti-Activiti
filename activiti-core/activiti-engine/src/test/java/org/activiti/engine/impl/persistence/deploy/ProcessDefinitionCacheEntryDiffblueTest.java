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
package org.activiti.engine.impl.persistence.deploy;

import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessDefinitionCacheEntryDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessDefinitionCacheEntry#ProcessDefinitionCacheEntry(ProcessDefinition,
   *       BpmnModel, Process)}
   *   <li>{@link ProcessDefinitionCacheEntry#setBpmnModel(BpmnModel)}
   *   <li>{@link ProcessDefinitionCacheEntry#setProcess(Process)}
   *   <li>{@link ProcessDefinitionCacheEntry#setProcessDefinition(ProcessDefinition)}
   *   <li>{@link ProcessDefinitionCacheEntry#getBpmnModel()}
   *   <li>{@link ProcessDefinitionCacheEntry#getProcess()}
   *   <li>{@link ProcessDefinitionCacheEntry#getProcessDefinition()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionCacheEntry.<init>(ProcessDefinition, BpmnModel, Process)",
    "BpmnModel ProcessDefinitionCacheEntry.getBpmnModel()",
    "Process ProcessDefinitionCacheEntry.getProcess()",
    "ProcessDefinition ProcessDefinitionCacheEntry.getProcessDefinition()",
    "void ProcessDefinitionCacheEntry.setBpmnModel(BpmnModel)",
    "void ProcessDefinitionCacheEntry.setProcess(Process)",
    "void ProcessDefinitionCacheEntry.setProcessDefinition(ProcessDefinition)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();

    // Act
    ProcessDefinitionCacheEntry actualProcessDefinitionCacheEntry =
        new ProcessDefinitionCacheEntry(
            processDefinition, bpmnModel, TestProcessUtil.createOneTaskProcessWithId("42"));
    BpmnModel bpmnModel2 = TestProcessUtil.createOneTaskBpmnModel();
    actualProcessDefinitionCacheEntry.setBpmnModel(bpmnModel2);
    Process process = TestProcessUtil.createOneTaskProcessWithId("42");
    actualProcessDefinitionCacheEntry.setProcess(process);
    ProcessDefinitionEntityImpl processDefinition2 = new ProcessDefinitionEntityImpl();
    actualProcessDefinitionCacheEntry.setProcessDefinition(processDefinition2);
    BpmnModel actualBpmnModel = actualProcessDefinitionCacheEntry.getBpmnModel();
    Process actualProcess = actualProcessDefinitionCacheEntry.getProcess();

    // Assert
    assertSame(processDefinition2, actualProcessDefinitionCacheEntry.getProcessDefinition());
    assertSame(bpmnModel2, actualBpmnModel);
    assertSame(process, actualProcess);
  }
}
