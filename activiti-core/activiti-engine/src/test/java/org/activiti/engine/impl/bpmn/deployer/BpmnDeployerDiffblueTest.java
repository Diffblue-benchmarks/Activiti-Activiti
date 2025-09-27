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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BpmnDeployerDiffblueTest {
  /**
   * Test {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.setProcessDefinitionDiagramNames(ParsedDeployment)"})
  public void testSetProcessDefinitionDiagramNames_givenArrayList() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(new DeploymentEntityImpl());

    // Act
    bpmnDeployer.setProcessDefinitionDiagramNames(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getDeployment();
  }

  /**
   * Test {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}.
   *
   * <ul>
   *   <li>Then calls {@link DeploymentEntityImpl#getResources()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#setProcessDefinitionDiagramNames(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.setProcessDefinitionDiagramNames(ParsedDeployment)"})
  public void testSetProcessDefinitionDiagramNames_thenCallsGetResources() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    DeploymentEntityImpl deploymentEntityImpl = mock(DeploymentEntityImpl.class);
    when(deploymentEntityImpl.getResources()).thenReturn(new HashMap<>());

    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    processDefinitionEntityImpl.setResourceName("All Process Definitions");

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(processDefinitionEntityImpl);

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);
    when(parsedDeployment.getDeployment()).thenReturn(deploymentEntityImpl);

    // Act
    bpmnDeployer.setProcessDefinitionDiagramNames(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getDeployment();
    verify(deploymentEntityImpl).getResources();
  }

  /**
   * Test {@link BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}.
   *
   * <ul>
   *   <li>Given {@link BpmnDeployer} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeployer#getPreviousVersionsOfProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map BpmnDeployer.getPreviousVersionsOfProcessDefinitions(ParsedDeployment)"})
  public void testGetPreviousVersionsOfProcessDefinitions_givenBpmnDeployer_thenReturnEmpty() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    ParsedDeployment parsedDeployment =
        new ParsedDeployment(
            entity, processDefinitions, mapProcessDefinitionsToParses, new HashMap<>());

    // Act and Assert
    assertTrue(bpmnDeployer.getPreviousVersionsOfProcessDefinitions(parsedDeployment).isEmpty());
  }

  /**
   * Test {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}.
   *
   * <p>Method under test: {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)"})
  public void testSetProcessDefinitionVersionsAndIds() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.setProjectReleaseVersion("1.0.2");

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(deploymentEntityImpl);

    // Act
    bpmnDeployer.setProcessDefinitionVersionsAndIds(parsedDeployment, new HashMap<>());

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment, atLeast(1)).getDeployment();
  }

  /**
   * Test {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#setProcessDefinitionVersionsAndIds(ParsedDeployment,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.setProcessDefinitionVersionsAndIds(ParsedDeployment, Map)"})
  public void testSetProcessDefinitionVersionsAndIds_thenCallsGetAllProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.setProjectReleaseVersion(null);

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());
    when(parsedDeployment.getDeployment()).thenReturn(deploymentEntityImpl);

    // Act
    bpmnDeployer.setProcessDefinitionVersionsAndIds(parsedDeployment, new HashMap<>());

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getDeployment();
  }

  /**
   * Test {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link ParsedDeployment#getAllProcessDefinitions()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.updateTimersAndEvents(ParsedDeployment, Map)"})
  public void testUpdateTimersAndEvents_givenArrayList_thenCallsGetAllProcessDefinitions() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.updateTimersAndEvents(parsedDeployment, new HashMap<>());

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnDeploymentHelper#updateTimersAndEvents(ProcessDefinitionEntity,
   *       ProcessDefinitionEntity, ParsedDeployment)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#updateTimersAndEvents(ParsedDeployment, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnDeployer.updateTimersAndEvents(ParsedDeployment, Map)"})
  public void testUpdateTimersAndEvents_thenCallsUpdateTimersAndEvents() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    doNothing()
        .when(bpmnDeploymentHelper)
        .updateTimersAndEvents(
            Mockito.<ProcessDefinitionEntity>any(),
            Mockito.<ProcessDefinitionEntity>any(),
            Mockito.<ParsedDeployment>any());

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.updateTimersAndEvents(parsedDeployment, new HashMap<>());

    // Assert
    verify(bpmnDeploymentHelper)
        .updateTimersAndEvents(
            isA(ProcessDefinitionEntity.class), isNull(), isA(ParsedDeployment.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}.
   *
   * <p>Method under test: {@link
   * BpmnDeployer#dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeployer.dispatchProcessDefinitionEntityInitializedEvent(ParsedDeployment)"
  })
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
   * Test {@link BpmnDeployer#getIdForNewProcessDefinition(ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>Then return {@code null:0:42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeployer#getIdForNewProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnDeployer.getIdForNewProcessDefinition(ProcessDefinitionEntity)"})
  public void testGetIdForNewProcessDefinition_thenReturnNull042() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setIdGenerator(idGenerator);

    // Act
    String actualIdForNewProcessDefinition =
        bpmnDeployer.getIdForNewProcessDefinition(new ProcessDefinitionEntityImpl());

    // Assert
    verify(idGenerator).getNextId();
    assertEquals("null:0:42", actualIdForNewProcessDefinition);
  }

  /**
   * Test {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   *
   * <p>Method under test: {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)"
  })
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    when(bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(
            Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    processDefinitionEntityImpl.setDeploymentId("");
    processDefinitionEntityImpl.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(processDefinitionEntityImpl);

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert
    verify(bpmnDeploymentHelper)
        .getPersistedInstanceOfProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   *
   * <p>Method under test: {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)"
  })
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    when(bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(
            Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(null);

    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    bpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);

    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    processDefinitionEntityImpl.setDeploymentId("");
    processDefinitionEntityImpl.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(processDefinitionEntityImpl);

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert
    verify(bpmnDeploymentHelper)
        .getPersistedInstanceOfProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeployer#makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(ParsedDeployment)"
  })
  public void testMakeProcessDefinitionsConsistentWithPersistedVersions_givenArrayList() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeployer.makeProcessDefinitionsConsistentWithPersistedVersions(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link BpmnDeployer#localizeFlowElements(Collection, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code localization}.
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#localizeFlowElements(Collection, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnDeployer.localizeFlowElements(Collection, ObjectNode)"})
  public void testLocalizeFlowElements_givenLocalization_whenNull_thenReturnFalse() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    infoNode.put("localization", (JsonNode) null);

    // Act and Assert
    assertFalse(bpmnDeployer.localizeFlowElements(null, infoNode));
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertFalse(
        bpmnDeployer.isEqualToCurrentLocalizationValue(
            "en", "42", "Property Name", "42", new ObjectNode(nc)));
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodeIsNullReturnTrue() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

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
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).isMissingNode();
    verify(arrayNode).isNull();
    verify(arrayNode3).path("42");
    verify(arrayNode2).path("Property Name");
    verify(arrayNode4).path("en");
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnValueOfTen() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path("en");
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnValueOfTen2() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).path("42");
    verify(arrayNode2).path("en");
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#path(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenArrayNodePathReturnValueOfTen3() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.path(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode2).path("42");
    verify(arrayNode).path("Property Name");
    verify(arrayNode3).path("en");
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code localization}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenLocalization() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    infoNode.put("localization", (JsonNode) null);

    // Act and Assert
    assertFalse(
        bpmnDeployer.isEqualToCurrentLocalizationValue(
            "en", "42", "Property Name", "42", infoNode));
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_givenValueOfTen() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_thenCallsAsText() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenReturn(false);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.path(Mockito.<String>any())).thenReturn(arrayNode3);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(arrayNode).isMissingNode();
    verify(arrayNode).isNull();
    verify(arrayNode3).path("42");
    verify(arrayNode2).path("Property Name");
    verify(arrayNode4).path("en");
    verify(arrayNode).asText();
    verify(infoNode).path("localization");
    assertFalse(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>When {@code As Text}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeployer#isEqualToCurrentLocalizationValue(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnDeployer.isEqualToCurrentLocalizationValue(String, String, String, String, ObjectNode)"
  })
  public void testIsEqualToCurrentLocalizationValue_whenAsText_thenReturnTrue() {
    // Arrange
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenReturn(false);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.path(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.path(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.path(Mockito.<String>any())).thenReturn(arrayNode3);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.path(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    boolean actualIsEqualToCurrentLocalizationValueResult =
        bpmnDeployer.isEqualToCurrentLocalizationValue(
            "en", "42", "Property Name", "As Text", infoNode);

    // Assert
    verify(arrayNode).isMissingNode();
    verify(arrayNode).isNull();
    verify(arrayNode3).path("42");
    verify(arrayNode2).path("Property Name");
    verify(arrayNode4).path("en");
    verify(arrayNode).asText();
    verify(infoNode).path("localization");
    assertTrue(actualIsEqualToCurrentLocalizationValueResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnDeployer}
   *   <li>{@link BpmnDeployer#setBpmnDeploymentHelper(BpmnDeploymentHelper)}
   *   <li>{@link BpmnDeployer#setCachingAndArtifactsManager(CachingAndArtifactsManager)}
   *   <li>{@link BpmnDeployer#setIdGenerator(IdGenerator)}
   *   <li>{@link BpmnDeployer#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}
   *   <li>{@link BpmnDeployer#getBpmnDeploymentHelper()}
   *   <li>{@link BpmnDeployer#getCachingAndArtifcatsManager()}
   *   <li>{@link BpmnDeployer#getExParsedDeploymentBuilderFactory()}
   *   <li>{@link BpmnDeployer#getIdGenerator()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeployer.<init>()",
    "BpmnDeploymentHelper BpmnDeployer.getBpmnDeploymentHelper()",
    "CachingAndArtifactsManager BpmnDeployer.getCachingAndArtifcatsManager()",
    "ParsedDeploymentBuilderFactory BpmnDeployer.getExParsedDeploymentBuilderFactory()",
    "IdGenerator BpmnDeployer.getIdGenerator()",
    "void BpmnDeployer.setBpmnDeploymentHelper(BpmnDeploymentHelper)",
    "void BpmnDeployer.setCachingAndArtifactsManager(CachingAndArtifactsManager)",
    "void BpmnDeployer.setIdGenerator(IdGenerator)",
    "void BpmnDeployer.setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnDeployer actualBpmnDeployer = new BpmnDeployer();
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    actualBpmnDeployer.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    CachingAndArtifactsManager manager = new CachingAndArtifactsManager();
    actualBpmnDeployer.setCachingAndArtifactsManager(manager);
    IdGenerator idGenerator = mock(IdGenerator.class);
    actualBpmnDeployer.setIdGenerator(idGenerator);
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());
    actualBpmnDeployer.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);
    BpmnDeploymentHelper actualBpmnDeploymentHelper = actualBpmnDeployer.getBpmnDeploymentHelper();
    CachingAndArtifactsManager actualCachingAndArtifcatsManager =
        actualBpmnDeployer.getCachingAndArtifcatsManager();
    ParsedDeploymentBuilderFactory actualExParsedDeploymentBuilderFactory =
        actualBpmnDeployer.getExParsedDeploymentBuilderFactory();

    // Assert
    assertSame(bpmnDeploymentHelper, actualBpmnDeploymentHelper);
    assertSame(manager, actualCachingAndArtifcatsManager);
    assertSame(parsedDeploymentBuilderFactory, actualExParsedDeploymentBuilderFactory);
    assertSame(idGenerator, actualBpmnDeployer.getIdGenerator());
  }
}
