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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.FunctionMapper;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ListResolverHelperDiffblueTest {
  /**
   * Test {@link ListResolverHelper#list(Object[])}.
   * <p>
   * Method under test: {@link ListResolverHelper#list(Object[])}
   */
  @Test
  @DisplayName("Test list(Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ListResolverHelper.list(Object[])"})
  void testList() {
    // Arrange and Act
    List<Object> actualListResult = ListResolverHelper.list("Objects");

    // Assert
    assertEquals(1, actualListResult.size());
    assertEquals("Objects", actualListResult.get(0));
  }

  /**
   * Test {@link ListResolverHelper#addListFunctions(ActivitiElContext)}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} FunctionMapper {@link ActivitiFunctionMapper}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListResolverHelper#addListFunctions(ActivitiElContext)}
   */
  @Test
  @DisplayName("Test addListFunctions(ActivitiElContext); then ActivitiElContext() FunctionMapper ActivitiFunctionMapper")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ListResolverHelper.addListFunctions(ActivitiElContext)"})
  void testAddListFunctions_thenActivitiElContextFunctionMapperActivitiFunctionMapper() throws NoSuchMethodException {
    // Arrange
    ActivitiElContext elContext = new ActivitiElContext();

    // Act
    ListResolverHelper.addListFunctions(elContext);

    // Assert
    FunctionMapper functionMapper = elContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = elContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertTrue(stringMethodMap.containsKey(":list"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }
}
