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
package org.activiti.engine.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.engine.test.api.deletereason.DeleteReasonTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractActivitiTestCaseDiffblueTest {
  /**
   * Test {@link AbstractActivitiTestCase#createOneTaskTestProcess()}.
   *
   * <p>Method under test: {@link AbstractActivitiTestCase#createOneTaskTestProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel AbstractActivitiTestCase.createOneTaskTestProcess()"})
  public void testCreateOneTaskTestProcess() {
    // Arrange and Act
    BpmnModel actualCreateOneTaskTestProcessResult =
        new DeleteReasonTest().createOneTaskTestProcess();

    // Assert
    Collection<Resource> resources = actualCreateOneTaskTestProcessResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = actualCreateOneTaskTestProcessResult.getSignals();
    assertTrue(signals instanceof List);
    assertNull(actualCreateOneTaskTestProcessResult.getEventSupport());
    assertNull(actualCreateOneTaskTestProcessResult.getSourceSystemId());
    assertNull(actualCreateOneTaskTestProcessResult.getTargetNamespace());
    assertNull(actualCreateOneTaskTestProcessResult.getStartEventFormTypes());
    assertNull(actualCreateOneTaskTestProcessResult.getUserTaskFormTypes());
    assertEquals(1, actualCreateOneTaskTestProcessResult.getProcesses().size());
    assertFalse(actualCreateOneTaskTestProcessResult.hasDiagramInterchangeInfo());
    assertTrue(actualCreateOneTaskTestProcessResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getGlobalArtifacts().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getImports().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getInterfaces().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getPools().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getDataStores().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getDefinitionsAttributes().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getErrors().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getFlowLocationMap().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getItemDefinitions().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getLabelLocationMap().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getLocationMap().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getMessageFlows().isEmpty());
    assertTrue(actualCreateOneTaskTestProcessResult.getNamespaces().isEmpty());
  }

  /**
   * Test {@link AbstractActivitiTestCase#createTwoTasksTestProcess()}.
   *
   * <p>Method under test: {@link AbstractActivitiTestCase#createTwoTasksTestProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel AbstractActivitiTestCase.createTwoTasksTestProcess()"})
  public void testCreateTwoTasksTestProcess() {
    // Arrange and Act
    BpmnModel actualCreateTwoTasksTestProcessResult =
        new DeleteReasonTest().createTwoTasksTestProcess();

    // Assert
    Collection<Resource> resources = actualCreateTwoTasksTestProcessResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = actualCreateTwoTasksTestProcessResult.getSignals();
    assertTrue(signals instanceof List);
    assertNull(actualCreateTwoTasksTestProcessResult.getEventSupport());
    assertNull(actualCreateTwoTasksTestProcessResult.getSourceSystemId());
    assertNull(actualCreateTwoTasksTestProcessResult.getTargetNamespace());
    assertNull(actualCreateTwoTasksTestProcessResult.getStartEventFormTypes());
    assertNull(actualCreateTwoTasksTestProcessResult.getUserTaskFormTypes());
    assertEquals(1, actualCreateTwoTasksTestProcessResult.getProcesses().size());
    assertFalse(actualCreateTwoTasksTestProcessResult.hasDiagramInterchangeInfo());
    assertTrue(actualCreateTwoTasksTestProcessResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getGlobalArtifacts().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getImports().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getInterfaces().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getPools().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getDataStores().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getDefinitionsAttributes().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getErrors().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getFlowLocationMap().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getItemDefinitions().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getLabelLocationMap().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getLocationMap().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getMessageFlows().isEmpty());
    assertTrue(actualCreateTwoTasksTestProcessResult.getNamespaces().isEmpty());
  }

  /**
   * Test {@link AbstractActivitiTestCase#createOneTaskAndStartEventWithFormKeyProcess()}.
   *
   * <p>Method under test: {@link
   * AbstractActivitiTestCase#createOneTaskAndStartEventWithFormKeyProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel AbstractActivitiTestCase.createOneTaskAndStartEventWithFormKeyProcess()"
  })
  public void testCreateOneTaskAndStartEventWithFormKeyProcess() {
    // Arrange and Act
    BpmnModel actualCreateOneTaskAndStartEventWithFormKeyProcessResult =
        new DeleteReasonTest().createOneTaskAndStartEventWithFormKeyProcess();

    // Assert
    Collection<Resource> resources =
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals =
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getSignals();
    assertTrue(signals instanceof List);
    assertNull(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getEventSupport());
    assertNull(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getSourceSystemId());
    assertNull(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getTargetNamespace());
    assertNull(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getStartEventFormTypes());
    assertNull(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getUserTaskFormTypes());
    assertEquals(1, actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getProcesses().size());
    assertFalse(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.hasDiagramInterchangeInfo());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getGlobalArtifacts().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getImports().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getInterfaces().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getPools().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getDataStores().isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult
            .getDefinitionsAttributes()
            .isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getErrors().isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getFlowLocationMap().isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getItemDefinitions().isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getLabelLocationMap().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getLocationMap().isEmpty());
    assertTrue(
        actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getMessageFlows().isEmpty());
    assertTrue(actualCreateOneTaskAndStartEventWithFormKeyProcessResult.getNamespaces().isEmpty());
  }
}
