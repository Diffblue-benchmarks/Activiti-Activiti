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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleDataInputAssociationDiffblueTest {
  /**
   * Test
   * {@link SimpleDataInputAssociation#SimpleDataInputAssociation(Expression, String)}.
   * <ul>
   *   <li>Then SourceExpression return {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleDataInputAssociation#SimpleDataInputAssociation(Expression, String)}
   */
  @Test
  public void testNewSimpleDataInputAssociation_thenSourceExpressionReturnFixedValue() {
    // Arrange
    FixedValue sourceExpression = new FixedValue(JSONObject.NULL);

    // Act
    SimpleDataInputAssociation actualSimpleDataInputAssociation = new SimpleDataInputAssociation(sourceExpression,
        "Target");

    // Assert
    Expression sourceExpression2 = actualSimpleDataInputAssociation.getSourceExpression();
    assertTrue(sourceExpression2 instanceof FixedValue);
    assertEquals("Target", actualSimpleDataInputAssociation.getTarget());
    assertNull(actualSimpleDataInputAssociation.getSource());
    assertTrue(actualSimpleDataInputAssociation.assignments.isEmpty());
    assertSame(sourceExpression, sourceExpression2);
  }

  /**
   * Test
   * {@link SimpleDataInputAssociation#SimpleDataInputAssociation(String, String)}.
   * <ul>
   *   <li>When {@code Source}.</li>
   *   <li>Then return {@code Source}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleDataInputAssociation#SimpleDataInputAssociation(String, String)}
   */
  @Test
  public void testNewSimpleDataInputAssociation_whenSource_thenReturnSource() {
    // Arrange and Act
    SimpleDataInputAssociation actualSimpleDataInputAssociation = new SimpleDataInputAssociation("Source", "Target");

    // Assert
    assertEquals("Source", actualSimpleDataInputAssociation.getSource());
    assertEquals("Target", actualSimpleDataInputAssociation.getTarget());
    assertNull(actualSimpleDataInputAssociation.getSourceExpression());
    assertTrue(actualSimpleDataInputAssociation.assignments.isEmpty());
  }

  /**
   * Test {@link SimpleDataInputAssociation#addAssignment(Assignment)}.
   * <p>
   * Method under test:
   * {@link SimpleDataInputAssociation#addAssignment(Assignment)}
   */
  @Test
  public void testAddAssignment() {
    // Arrange
    SimpleDataInputAssociation simpleDataInputAssociation = new SimpleDataInputAssociation("Source", "Target");
    FixedValue fromExpression = new FixedValue(JSONObject.NULL);
    Assignment assignment = new Assignment(fromExpression, new FixedValue(JSONObject.NULL));

    // Act
    simpleDataInputAssociation.addAssignment(assignment);

    // Assert
    List<Assignment> assignmentList = simpleDataInputAssociation.assignments;
    assertEquals(1, assignmentList.size());
    assertSame(assignment, assignmentList.get(0));
  }

  /**
   * Test {@link SimpleDataInputAssociation#addAssignment(Assignment)}.
   * <p>
   * Method under test:
   * {@link SimpleDataInputAssociation#addAssignment(Assignment)}
   */
  @Test
  public void testAddAssignment2() {
    // Arrange
    SimpleDataInputAssociation simpleDataInputAssociation = new SimpleDataInputAssociation("Source", "Target");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression fromExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    Assignment assignment = new Assignment(fromExpression, new FixedValue(JSONObject.NULL));

    // Act
    simpleDataInputAssociation.addAssignment(assignment);

    // Assert
    List<Assignment> assignmentList = simpleDataInputAssociation.assignments;
    assertEquals(1, assignmentList.size());
    assertSame(assignment, assignmentList.get(0));
  }

  /**
   * Test {@link SimpleDataInputAssociation#evaluate(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link Expression}
   * {@link Expression#setValue(Object, VariableScope)} does nothing.</li>
   *   <li>Then calls {@link Expression#setValue(Object, VariableScope)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate_givenExpressionSetValueDoesNothing_thenCallsSetValue() {
    // Arrange
    Expression toExpression = mock(Expression.class);
    doNothing().when(toExpression).setValue(Mockito.<Object>any(), Mockito.<VariableScope>any());
    Assignment assignment = new Assignment(new FixedValue(JSONObject.NULL), toExpression);

    SimpleDataInputAssociation simpleDataInputAssociation = new SimpleDataInputAssociation("Source", "Target");
    simpleDataInputAssociation.addAssignment(assignment);

    // Act
    simpleDataInputAssociation.evaluate(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(toExpression).setValue(isA(Object.class), isA(VariableScope.class));
  }
}
