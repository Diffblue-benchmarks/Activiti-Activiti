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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessDefinitionUtilDiffblueTest {
  /**
   * Test {@link ProcessDefinitionUtil#getProcessDefinitionHelper()}.
   * <p>
   * Method under test: {@link ProcessDefinitionUtil#getProcessDefinitionHelper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.ProcessDefinitionHelper ProcessDefinitionUtil.getProcessDefinitionHelper()"})
  public void testGetProcessDefinitionHelper() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ProcessDefinitionUtil.getProcessDefinitionHelper());
  }

  /**
   * Test {@link ProcessDefinitionUtil#getProcess(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionUtil#getProcess(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.bpmn.model.Process ProcessDefinitionUtil.getProcess(String)"})
  public void testGetProcess() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ProcessDefinitionUtil.getProcess("42"));
  }

  /**
   * Test {@link ProcessDefinitionUtil#getBpmnModel(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionUtil#getBpmnModel(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.bpmn.model.BpmnModel ProcessDefinitionUtil.getBpmnModel(String)"})
  public void testGetBpmnModel() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ProcessDefinitionUtil.getBpmnModel("42"));
  }
}
