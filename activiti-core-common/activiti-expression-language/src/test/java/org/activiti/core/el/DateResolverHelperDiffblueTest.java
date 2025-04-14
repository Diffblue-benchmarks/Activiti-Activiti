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
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DateResolverHelperDiffblueTest {
  /**
   * Test {@link DateResolverHelper#addDateFunctions(ActivitiElContext)}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} FunctionMapper {@link ActivitiFunctionMapper}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateResolverHelper#addDateFunctions(ActivitiElContext)}
   */
  @Test
  @DisplayName("Test addDateFunctions(ActivitiElContext); then ActivitiElContext() FunctionMapper ActivitiFunctionMapper")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DateResolverHelper.addDateFunctions(ActivitiElContext)"})
  void testAddDateFunctions_thenActivitiElContextFunctionMapperActivitiFunctionMapper() throws NoSuchMethodException {
    // Arrange
    ActivitiElContext elContext = new ActivitiElContext();

    // Act
    DateResolverHelper.addDateFunctions(elContext);

    // Assert
    FunctionMapper functionMapper = elContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = elContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertTrue(stringMethodMap.containsKey(":now"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }
}
