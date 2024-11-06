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
package org.activiti.engine.impl.bpmn.deployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceNameUtilDiffblueTest {
  @InjectMocks
  private ResourceNameUtil resourceNameUtil;

  /**
   * Test {@link ResourceNameUtil#stripBpmnFileSuffix(String)}.
   * <p>
   * Method under test: {@link ResourceNameUtil#stripBpmnFileSuffix(String)}
   */
  @Test
  public void testStripBpmnFileSuffix() {
    // Arrange, Act and Assert
    assertEquals("Bpmn File Resource", ResourceNameUtil.stripBpmnFileSuffix("Bpmn File Resource"));
  }

  /**
   * Test
   * {@link ResourceNameUtil#getProcessDiagramResourceName(String, String, String)}.
   * <p>
   * Method under test:
   * {@link ResourceNameUtil#getProcessDiagramResourceName(String, String, String)}
   */
  @Test
  public void testGetProcessDiagramResourceName() {
    // Arrange, Act and Assert
    assertEquals("Bpmn File ResourceProcess Key.Diagram Suffix",
        ResourceNameUtil.getProcessDiagramResourceName("Bpmn File Resource", "Process Key", "Diagram Suffix"));
  }

  /**
   * Test
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}
   */
  @Test
  public void testGetProcessDiagramResourceNameFromDeployment_givenEmptyString() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setResourceName("");

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> ResourceNameUtil.getProcessDiagramResourceNameFromDeployment(processDefinition, new HashMap<>()));
  }

  /**
   * Test
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}
   */
  @Test
  public void testGetProcessDiagramResourceNameFromDeployment_thenReturnNull() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setResourceName("Process Definition");

    // Act and Assert
    assertNull(ResourceNameUtil.getProcessDiagramResourceNameFromDeployment(processDefinition, new HashMap<>()));
  }

  /**
   * Test
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}.
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceNameUtil#getProcessDiagramResourceNameFromDeployment(ProcessDefinitionEntity, Map)}
   */
  @Test
  public void testGetProcessDiagramResourceNameFromDeployment_whenProcessDefinitionEntityImpl() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> ResourceNameUtil.getProcessDiagramResourceNameFromDeployment(processDefinition, new HashMap<>()));
  }
}
