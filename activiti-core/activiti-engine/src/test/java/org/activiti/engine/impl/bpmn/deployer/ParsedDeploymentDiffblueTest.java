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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;

public class ParsedDeploymentDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ParsedDeployment#ParsedDeployment(DeploymentEntity, List, Map, Map)}
   *   <li>{@link ParsedDeployment#getAllProcessDefinitions()}
   *   <li>{@link ParsedDeployment#getDeployment()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    // Act
    ParsedDeployment actualParsedDeployment = new ParsedDeployment(entity, processDefinitions,
        mapProcessDefinitionsToParses, new HashMap<>());
    List<ProcessDefinitionEntity> actualAllProcessDefinitions = actualParsedDeployment.getAllProcessDefinitions();
    DeploymentEntity actualDeployment = actualParsedDeployment.getDeployment();

    // Assert
    assertTrue(actualAllProcessDefinitions.isEmpty());
    assertTrue(actualParsedDeployment.mapProcessDefinitionsToParses.isEmpty());
    assertTrue(actualParsedDeployment.mapProcessDefinitionsToResources.isEmpty());
    assertSame(processDefinitions, actualAllProcessDefinitions);
    assertSame(entity, actualDeployment);
  }

  /**
   * Test
   * {@link ParsedDeployment#getResourceForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getResourceForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetResourceForProcessDefinition() {
    // Arrange
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getResourceForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getResourceForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getResourceForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetResourceForProcessDefinition2() {
    // Arrange
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    mapProcessDefinitionsToParses.computeIfPresent(new ProcessDefinitionEntityImpl(), mock(BiFunction.class));
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getResourceForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getBpmnParseForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getBpmnParseForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetBpmnParseForProcessDefinition() {
    // Arrange
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getBpmnParseForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getBpmnParseForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getBpmnParseForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetBpmnParseForProcessDefinition2() {
    // Arrange
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    mapProcessDefinitionsToParses.computeIfPresent(new ProcessDefinitionEntityImpl(), mock(BiFunction.class));
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getBpmnParseForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getBpmnModelForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getBpmnModelForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetBpmnModelForProcessDefinition() {
    // Arrange
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getBpmnModelForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getBpmnModelForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getBpmnModelForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetBpmnModelForProcessDefinition2() {
    // Arrange
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    mapProcessDefinitionsToParses.computeIfPresent(new ProcessDefinitionEntityImpl(), mock(BiFunction.class));
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getBpmnModelForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getProcessModelForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getProcessModelForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetProcessModelForProcessDefinition() {
    // Arrange
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getProcessModelForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test
   * {@link ParsedDeployment#getProcessModelForProcessDefinition(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link ParsedDeployment#getProcessModelForProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetProcessModelForProcessDefinition2() {
    // Arrange
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    mapProcessDefinitionsToParses.computeIfPresent(new ProcessDefinitionEntityImpl(), mock(BiFunction.class));
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act and Assert
    assertNull(parsedDeployment.getProcessModelForProcessDefinition(new ProcessDefinitionEntityImpl()));
  }
}
