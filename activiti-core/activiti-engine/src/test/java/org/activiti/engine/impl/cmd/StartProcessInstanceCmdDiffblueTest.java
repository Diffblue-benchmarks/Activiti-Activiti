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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StartProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String, String, Map)}.
   *
   * <p>Method under test: {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String,
   * String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartProcessInstanceCmd.<init>(String, String, String, Map)",
    "void StartProcessInstanceCmd.<init>(String, String, String, Map, String)"
  })
  public void testNewStartProcessInstanceCmd() {
    // Arrange and Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd =
        new StartProcessInstanceCmd<>(
            "Process Definition Key", "42", "Business Key", new HashMap<>());

    // Assert
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Test {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String, String, Map,
   * String)}.
   *
   * <p>Method under test: {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String,
   * String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartProcessInstanceCmd.<init>(String, String, String, Map)",
    "void StartProcessInstanceCmd.<init>(String, String, String, Map, String)"
  })
  public void testNewStartProcessInstanceCmd2() {
    // Arrange and Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd =
        new StartProcessInstanceCmd<>(
            "Process Definition Key", "42", "Business Key", new HashMap<>(), "42");

    // Assert
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Test {@link StartProcessInstanceCmd#StartProcessInstanceCmd(ProcessInstanceBuilderImpl)}.
   *
   * <ul>
   *   <li>Then return {@link StartProcessInstanceCmd#businessKey} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * StartProcessInstanceCmd#StartProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartProcessInstanceCmd.<init>(ProcessInstanceBuilderImpl)"})
  public void testNewStartProcessInstanceCmd_thenReturnBusinessKeyIsNull() {
    // Arrange and Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd =
        new StartProcessInstanceCmd<>(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Assert
    assertNull(actualStartProcessInstanceCmd.businessKey);
    assertNull(actualStartProcessInstanceCmd.processDefinitionId);
    assertNull(actualStartProcessInstanceCmd.processDefinitionKey);
    assertNull(actualStartProcessInstanceCmd.processInstanceName);
    assertNull(actualStartProcessInstanceCmd.tenantId);
    assertNull(actualStartProcessInstanceCmd.transientVariables);
    assertNull(actualStartProcessInstanceCmd.variables);
    assertNull(actualStartProcessInstanceCmd.processInstanceHelper);
  }

  /**
   * Test {@link StartProcessInstanceCmd#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map StartProcessInstanceCmd.processDataObjects(Collection)"})
  public void testProcessDataObjects_givenBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd =
        new StartProcessInstanceCmd<>(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult =
        startProcessInstanceCmd.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link StartProcessInstanceCmd#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map StartProcessInstanceCmd.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd =
        new StartProcessInstanceCmd<>(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Act and Assert
    assertTrue(startProcessInstanceCmd.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link StartProcessInstanceCmd#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map StartProcessInstanceCmd.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd =
        new StartProcessInstanceCmd<>(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Act and Assert
    assertTrue(startProcessInstanceCmd.processDataObjects(null).isEmpty());
  }
}
