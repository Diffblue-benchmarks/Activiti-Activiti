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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ShellActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    ShellActivityBehavior shellActivityBehavior = new ShellActivityBehavior();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", shellActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull2() {
    // Arrange
    ShellActivityBehavior shellActivityBehavior = new ShellActivityBehavior();
    FixedValue expression = new FixedValue(null);

    // Act and Assert
    assertNull(shellActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShellActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenNull_thenReturnNull() {
    // Arrange
    ShellActivityBehavior shellActivityBehavior = new ShellActivityBehavior();

    // Act and Assert
    assertNull(
        shellActivityBehavior.getStringFromField(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test new {@link ShellActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ShellActivityBehavior}
   */
  @Test
  public void testNewShellActivityBehavior() {
    // Arrange and Act
    ShellActivityBehavior actualShellActivityBehavior = new ShellActivityBehavior();

    // Assert
    assertNull(actualShellActivityBehavior.cleanEnvBoolean);
    assertNull(actualShellActivityBehavior.redirectErrorFlag);
    assertNull(actualShellActivityBehavior.waitFlag);
    assertNull(actualShellActivityBehavior.arg1Str);
    assertNull(actualShellActivityBehavior.arg2Str);
    assertNull(actualShellActivityBehavior.arg3Str);
    assertNull(actualShellActivityBehavior.arg4Str);
    assertNull(actualShellActivityBehavior.arg5Str);
    assertNull(actualShellActivityBehavior.commandStr);
    assertNull(actualShellActivityBehavior.directoryStr);
    assertNull(actualShellActivityBehavior.errorCodeVariableStr);
    assertNull(actualShellActivityBehavior.resultVariableStr);
    assertNull(actualShellActivityBehavior.waitStr);
    assertNull(actualShellActivityBehavior.arg1);
    assertNull(actualShellActivityBehavior.arg2);
    assertNull(actualShellActivityBehavior.arg3);
    assertNull(actualShellActivityBehavior.arg4);
    assertNull(actualShellActivityBehavior.arg5);
    assertNull(actualShellActivityBehavior.cleanEnv);
    assertNull(actualShellActivityBehavior.command);
    assertNull(actualShellActivityBehavior.directory);
    assertNull(actualShellActivityBehavior.errorCodeVariable);
    assertNull(actualShellActivityBehavior.outputVariable);
    assertNull(actualShellActivityBehavior.redirectError);
    assertNull(actualShellActivityBehavior.wait);
    assertNull(actualShellActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualShellActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualShellActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
