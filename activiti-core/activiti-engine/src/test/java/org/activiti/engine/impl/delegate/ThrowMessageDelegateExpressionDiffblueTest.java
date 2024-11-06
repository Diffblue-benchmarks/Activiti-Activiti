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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ThrowMessageDelegateExpressionDiffblueTest {
  /**
   * Test
   * {@link ThrowMessageDelegateExpression#send(DelegateExecution, ThrowMessage)}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ThrowMessageDelegateExpression#send(DelegateExecution, ThrowMessage)}
   */
  @Test
  public void testSend_givenFixedValueWithValueIsNull_thenReturnFalse() {
    // Arrange
    FixedValue delegateExpression = new FixedValue(JSONObject.NULL);
    ThrowMessageDelegateExpression throwMessageDelegateExpression = new ThrowMessageDelegateExpression(
        delegateExpression, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(throwMessageDelegateExpression.send(execution, new ThrowMessage()));
  }
}
