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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FixedValueDiffblueTest {
  /**
   * Test {@link FixedValue#FixedValue(Object)}.
   *
   * <p>Method under test: {@link FixedValue#FixedValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FixedValue.<init>(Object)"})
  public void testNewFixedValue() {
    // Arrange, Act and Assert
    assertEquals("null", new FixedValue(JSONObject.NULL).getExpressionText());
  }

  /**
   * Test {@link FixedValue#setValue(Object, VariableScope)}.
   *
   * <p>Method under test: {@link FixedValue#setValue(Object, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FixedValue.setValue(Object, VariableScope)"})
  public void testSetValue() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new FixedValue(JSONObject.NULL)
                .setValue(JSONObject.NULL, NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link FixedValue#getExpressionText()}.
   *
   * <p>Method under test: {@link FixedValue#getExpressionText()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String FixedValue.getExpressionText()"})
  public void testGetExpressionText() {
    // Arrange, Act and Assert
    assertEquals("null", new FixedValue(JSONObject.NULL).getExpressionText());
  }
}
