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
   * Test
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  public void testCalculateOutPutVariables_givenFoo() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    HashMap<String, Object> availableVariables = new HashMap<>();
    availableVariables.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateOutPutVariables(mappingExecutionContext, availableVariables).isEmpty());
  }

  /**
   * Test
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NoneVariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  public void testCalculateOutPutVariables_whenHashMap() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()).isEmpty());
  }

  /**
   * Test
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}
   */
  @Test
  public void testCalculateInputVariables_givenDate() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertTrue(noneVariablesCalculator.calculateInputVariables(execution).isEmpty());
  }

  /**
   * Test
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NoneVariablesCalculator#calculateInputVariables(DelegateExecution)}
   */
  @Test
  public void testCalculateInputVariables_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    NoneVariablesCalculator noneVariablesCalculator = new NoneVariablesCalculator();

    // Act and Assert
    assertTrue(
        noneVariablesCalculator.calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .isEmpty());
  }
}
