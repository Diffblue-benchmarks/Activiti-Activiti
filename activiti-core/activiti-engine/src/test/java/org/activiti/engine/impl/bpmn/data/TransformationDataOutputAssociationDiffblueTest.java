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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TransformationDataOutputAssociationDiffblueTest {
  /**
   * Test {@link TransformationDataOutputAssociation#TransformationDataOutputAssociation(String,
   * String, Expression)}.
   *
   * <p>Method under test: {@link
   * TransformationDataOutputAssociation#TransformationDataOutputAssociation(String, String,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransformationDataOutputAssociation.<init>(String, String, Expression)"})
  public void testNewTransformationDataOutputAssociation() {
    // Arrange and Act
    TransformationDataOutputAssociation actualTransformationDataOutputAssociation =
        new TransformationDataOutputAssociation(
            "Source Ref", "Target Ref", new FixedValue(JSONObject.NULL));

    // Assert
    assertEquals("Source Ref", actualTransformationDataOutputAssociation.getSource());
    assertEquals("Target Ref", actualTransformationDataOutputAssociation.getTarget());
    assertNull(actualTransformationDataOutputAssociation.getSourceExpression());
  }

  /**
   * Test {@link TransformationDataOutputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link DelegateExecution} {@link DelegateExecution#setVariable(String, Object)} does
   *       nothing.
   *   <li>Then calls {@link DelegateExecution#setVariable(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link TransformationDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransformationDataOutputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_whenDelegateExecutionSetVariableDoesNothing_thenCallsSetVariable() {
    // Arrange
    TransformationDataOutputAssociation transformationDataOutputAssociation =
        new TransformationDataOutputAssociation(
            "Source Ref", "Target Ref", new FixedValue(JSONObject.NULL));

    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    transformationDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).setVariable(eq("Target Ref"), isA(Object.class));
  }
}
