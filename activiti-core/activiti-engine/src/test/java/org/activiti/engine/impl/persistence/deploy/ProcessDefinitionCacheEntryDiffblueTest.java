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
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class ProcessDefinitionCacheEntryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessDefinitionCacheEntry#ProcessDefinitionCacheEntry(ProcessDefinition, BpmnModel, Process)}
   *   <li>{@link ProcessDefinitionCacheEntry#setBpmnModel(BpmnModel)}
   *   <li>{@link ProcessDefinitionCacheEntry#setProcess(Process)}
   *   <li>
   * {@link ProcessDefinitionCacheEntry#setProcessDefinition(ProcessDefinition)}
   *   <li>{@link ProcessDefinitionCacheEntry#getBpmnModel()}
   *   <li>{@link ProcessDefinitionCacheEntry#getProcess()}
   *   <li>{@link ProcessDefinitionCacheEntry#getProcessDefinition()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    ProcessDefinitionCacheEntry actualProcessDefinitionCacheEntry = new ProcessDefinitionCacheEntry(processDefinition,
        bpmnModel, new Process());
    BpmnModel bpmnModel2 = new BpmnModel();
    actualProcessDefinitionCacheEntry.setBpmnModel(bpmnModel2);
    Process process = new Process();
    actualProcessDefinitionCacheEntry.setProcess(process);
    ProcessDefinitionEntityImpl processDefinition2 = new ProcessDefinitionEntityImpl();
    actualProcessDefinitionCacheEntry.setProcessDefinition(processDefinition2);
    BpmnModel actualBpmnModel = actualProcessDefinitionCacheEntry.getBpmnModel();
    Process actualProcess = actualProcessDefinitionCacheEntry.getProcess();

    // Assert that nothing has changed
    assertSame(bpmnModel2, actualBpmnModel);
    assertSame(process, actualProcess);
    assertSame(processDefinition2, actualProcessDefinitionCacheEntry.getProcessDefinition());
  }
}
