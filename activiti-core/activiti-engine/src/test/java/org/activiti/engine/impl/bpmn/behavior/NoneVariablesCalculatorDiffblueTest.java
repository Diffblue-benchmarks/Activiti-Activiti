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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class NoneVariablesCalculatorDiffblueTest {
  /**
   * Method under test:
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  public void testCalculateOutPutVariables() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  public void testCalculateOutPutVariables2() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    HashMap<String, Object> availableVariables = new HashMap<>();
    availableVariables.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateOutPutVariables(mappingExecutionContext, availableVariables).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}
   */
  @Test
  public void testCalculateInputVariables() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();

    // Act and Assert
    assertTrue(
        noneVariablesCalculator.calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .isEmpty());
  }

  /**
   * Method under test:
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}
   */
  @Test
  public void testCalculateInputVariables2() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateInputVariables(execution).isEmpty());
  }
}
