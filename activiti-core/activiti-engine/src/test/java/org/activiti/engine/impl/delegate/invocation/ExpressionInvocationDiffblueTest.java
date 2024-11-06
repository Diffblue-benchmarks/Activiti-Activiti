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
package org.activiti.engine.impl.delegate.invocation;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ExpressionInvocationDiffblueTest {
  /**
   * Test {@link ExpressionInvocation#getTarget()}.
   * <p>
   * Method under test: {@link ExpressionInvocation#getTarget()}
   */
  @Test
  public void testGetTarget() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression valueExpression = new ObjectValueExpression(converter, JSONObject.NULL, type);

    ExpressionGetInvocation expressionGetInvocation = new ExpressionGetInvocation(valueExpression,
        new ParsingElContext());

    // Act and Assert
    assertSame(expressionGetInvocation.valueExpression, expressionGetInvocation.getTarget());
  }
}
