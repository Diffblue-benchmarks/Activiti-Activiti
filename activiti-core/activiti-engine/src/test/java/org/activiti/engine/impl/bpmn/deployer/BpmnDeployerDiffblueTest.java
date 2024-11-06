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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BpmnDeployerDiffblueTest {
  @InjectMocks
  private BpmnDeployer bpmnDeployer;

  /**
   * Test {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link DeploymentEntityImpl#getResources()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}
   */
  @Test
  public void testSetProcessDefinitionDiagramNames_givenHashMap_thenCallsGetResources() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    DeploymentEntityImpl entity = mock(DeploymentEntityImpl.class);
    when(entity.getResources()).thenReturn(new HashMap<>());
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    // Act
    bpmnDeployer.setProcessDefinitionDiagramNames(
        new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses, new HashMap<>()));

    // Assert that nothing has changed
    verify(entity).getResources();
  }

  /**
   * Test {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}.
   * <ul>
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}
   */
  @Test
  public void testSetProcessDefinitionDiagramNames_thenCallsGetAllProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(new DeploymentEntityImpl());

    // Act
    bpmnDeployer.setProcessDefinitionDiagramNames(parsedDeployment);

    // Assert that nothing has changed
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getDeployment();
  }

  /**
   * Test
   * {@link BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}
   */
  @Test
  public void testGetPreviousVersionsOfProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setIdGenerator(mock(IdGenerator.class));
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    // Act and Assert
    assertTrue(bpmnDeployer
        .getPreviousVersionsOfProcessDefinitions(
            new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses, new HashMap<>()))
        .isEmpty());
  }

  /**
   * Test
   * {@link BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}.
   * <ul>
   *   <li>Given {@link BpmnDeployer} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}
   */
  @Test
  public void testGetPreviousVersionsOfProcessDefinitions_givenBpmnDeployer_thenReturnEmpty() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    // Act and Assert
    assertTrue(bpmnDeployer
        .getPreviousVersionsOfProcessDefinitions(
            new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses, new HashMap<>()))
        .isEmpty());
  }

  /**
   * Test
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}.
   * <ul>
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}
   */
  @Test
  public void testSetProcessDefinitionVersionsAndIds_thenCallsGetAllProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(new DeploymentEntityImpl());

    // Act
    bpmnDeployer.setProcessDefinitionVersionsAndIds(parsedDeployment, new HashMap<>());

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getDeployment();
  }

  /**
   * Test
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}.
   * <ul>
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}
   */
  @Test
  public void testSetProcessDefinitionVersionsAndIds_thenCallsGetAllProcessDefinitions2() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getVersion()).thenReturn(1);
    when(deploymentEntity.getProjectReleaseVersion()).thenReturn("1.0.2");
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(deploymentEntity);

    // Act
    bpmnDeployer.setProcessDefinitionVersionsAndIds(parsedDeployment, new HashMap<>());

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment, atLeast(1)).getDeployment();
    verify(deploymentEntity).getProjectReleaseVersion();
    verify(deploymentEntity).getVersion();
  }

  /**
   * Test
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}.
   * <ul>
   *   <li>Then calls {@link DeploymentEntity#getProjectReleaseVersion()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}
   */
  @Test
  public void testSetProcessDefinitionVersionsAndIds_thenCallsGetProjectReleaseVersion() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    DeploymentEntity entity = mock(DeploymentEntity.class);
    when(entity.getVersion()).thenReturn(1);
    when(entity.getProjectReleaseVersion()).thenReturn("1.0.2");
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();
    ParsedDeployment parsedDeployment = new ParsedDeployment(entity, processDefinitions, mapProcessDefinitionsToParses,
        new HashMap<>());

    // Act
    bpmnDeployer.setProcessDefinitionVersionsAndIds(parsedDeployment, new HashMap<>());

    // Assert
    verify(entity).getProjectReleaseVersion();
    verify(entity).getVersion();
  }

  /**
   * Test {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}
   */
  @Test
  public void testUpdateTimersAndEvents_givenArrayList_thenCallsGetAllProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.updateTimersAndEvents(parsedDeployment, new HashMap<>());

    // Assert that nothing has changed
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}.
   * <ul>
   *   <li>Then calls
   * {@link BpmnDeploymentHelper#updateTimersAndEvents(ProcessDefinitionEntity, ProcessDefinitionEntity, ParsedDeployment)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}
   */
  @Test
  public void testUpdateTimersAndEvents_thenCallsUpdateTimersAndEvents() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    doNothing().when(bpmnDeploymentHelper)
        .updateTimersAndEvents(Mockito.<ProcessDefinitionEntity>any(), Mockito.<ProcessDefinitionEntity>any(),
            Mockito.<ParsedDeployment>any());

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.updateTimersAndEvents(parsedDeployment, new HashMap<>());

    // Assert that nothing has changed
    verify(bpmnDeploymentHelper).updateTimersAndEvents(isA(ProcessDefinitionEntity.class), isNull(),
        isA(ParsedDeployment.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}
   */
  @Test
  public void testDispatchProcessDefinitionEntityInitializedEvent() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.dispatchProcessDefinitionEntityInitializedEvent(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}
   */
  @Test
  public void testDispatchProcessDefinitionEntityInitializedEvent2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    Process process = new Process();
    bpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(process, new ProcessDefinitionEntityImpl());

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.dispatchProcessDefinitionEntityInitializedEvent(parsedDeployment);

    // Assert that nothing has changed
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#getIdForNewProcessDefinition(ProcessDefinitionEntity)}.
   * <ul>
   *   <li>Then return {@code null:0:42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#getIdForNewProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetIdForNewProcessDefinition_thenReturnNull042() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setIdGenerator(idGenerator);

    // Act
    String actualIdForNewProcessDefinition = bpmnDeployer
        .getIdForNewProcessDefinition(new ProcessDefinitionEntityImpl());

    // Assert
    verify(idGenerator).getNextId();
    assertEquals("null:0:42", actualIdForNewProcessDefinition);
  }

  /**
   * Test
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    when(bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert
    verify(bpmnDeploymentHelper).getPersistedInstanceOfProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    when(bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(null);

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert that nothing has changed
    verify(bpmnDeploymentHelper).getPersistedInstanceOfProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions_givenArrayList() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert that nothing has changed
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue() {
    // Arrange, Act and Assert
    assertFalse(bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue2() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path(eq("42"));
    verify(arrayNode2).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue5() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode2).path(eq("42"));
    verify(arrayNode).path(eq("Property Name"));
    verify(arrayNode3).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue6() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode2).path(eq("42"));
    verify(arrayNode).path(eq("Property Name"));
    verify(arrayNode3).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isMissingNode()} return
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodeIsMissingNodeReturnTrue() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.path(Mockito.<String>any())).thenReturn(arrayNode3);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).isMissingNode();
    verify(arrayNode3).path(eq("42"));
    verify(arrayNode2).path(eq("Property Name"));
    verify(arrayNode4).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnInstance() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnInstance2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path(eq("42"));
    verify(arrayNode2).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnInstance3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode2).path(eq("42"));
    verify(arrayNode).path(eq("Property Name"));
    verify(arrayNode3).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_givenInstance() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenReturn(false);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.path(Mockito.<String>any())).thenReturn(arrayNode3);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult = bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42",
        "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).isMissingNode();
    verify(arrayNode).isNull();
    verify(arrayNode3).path(eq("42"));
    verify(arrayNode2).path(eq("Property Name"));
    verify(arrayNode4).path(eq("en"));
    verify(infoNode).path(eq("localization"));
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testIsEqualToCurrentLocalizationValue_whenObjectNodeWithNcIsJsonNodeFactory() {
    // Arrange, Act and Assert
    assertFalse(bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42",
        new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnDeployer}
   *   <li>{@link BpmnDeployer#setBpmnDeploymentHelper(BpmnDeploymentHelper)}
   *   <li>
   * {@link BpmnDeployer#setCachingAndArtifactsManager(CachingAndArtifactsManager)}
   *   <li>{@link BpmnDeployer#setIdGenerator(IdGenerator)}
   *   <li>
   * {@link BpmnDeployer#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}
   *   <li>{@link BpmnDeployer#getBpmnDeploymentHelper()}
   *   <li>{@link BpmnDeployer#getCachingAndArtifcatsManager()}
   *   <li>{@link BpmnDeployer#getExParsedDeploymentBuilderFactory()}
   *   <li>{@link BpmnDeployer#getIdGenerator()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnDeployer actualBpmnDeployer = new BpmnDeployer();
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    actualBpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    CachingAndArtifactsManager manager = new CachingAndArtifactsManager();
    actualBpmnDeployer.setCachingAndArtifactsManager(manager);
    IdGenerator idGenerator = mock(IdGenerator.class);
    actualBpmnDeployer.setIdGenerator(idGenerator);
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());
    actualBpmnDeployer.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);
    BpmnDeploymentHelper actualBpmnDeploymentHelper = actualBpmnDeployer.getBpmnDeploymentHelper();
    CachingAndArtifactsManager actualCachingAndArtifcatsManager = actualBpmnDeployer.getCachingAndArtifcatsManager();
    ParsedDeploymentBuilderFactory actualExParsedDeploymentBuilderFactory = actualBpmnDeployer
        .getExParsedDeploymentBuilderFactory();

    // Assert that nothing has changed
    assertSame(bpmnDeploymentHelper, actualBpmnDeploymentHelper);
    assertSame(manager, actualCachingAndArtifcatsManager);
    assertSame(parsedDeploymentBuilderFactory, actualExParsedDeploymentBuilderFactory);
    assertSame(idGenerator, actualBpmnDeployer.getIdGenerator());
  }
}
