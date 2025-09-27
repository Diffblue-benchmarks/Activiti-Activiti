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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessParseHandlerDiffblueTest {
  /**
   * Test {@link ProcessParseHandler#executeParse(BpmnParse, Process)} with {@code BpmnParse},
   * {@code Process}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link BpmnParse}.
   *   <li>Then calls {@link Process#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessParseHandler#executeParse(BpmnParse, Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessParseHandler.executeParse(BpmnParse, Process)"})
  public void testExecuteParseWithBpmnParseProcess_givenFalse_whenBpmnParse_thenCallsGetId() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);

    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(false);
    when(process.getId()).thenReturn("42");

    // Act
    processParseHandler.executeParse(bpmnParse, process);

    // Assert
    verify(process).getId();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessParseHandler#getEventSupport(BpmnModel)}.
   *
   * <ul>
   *   <li>When createOneTaskBpmnModel.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessParseHandler#getEventSupport(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.event.impl.ActivitiEventSupport ProcessParseHandler.getEventSupport(BpmnModel)"
  })
  public void testGetEventSupport_whenCreateOneTaskBpmnModel_thenReturnNull() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();

    // Act and Assert
    assertNull(processParseHandler.getEventSupport(TestProcessUtil.createOneTaskBpmnModel()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessParseHandler}
   *   <li>{@link ProcessParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessParseHandler.<init>()",
    "Class ProcessParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = new ProcessParseHandler().getHandledType();

    // Assert
    Class<Process> expectedHandledType = Process.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
