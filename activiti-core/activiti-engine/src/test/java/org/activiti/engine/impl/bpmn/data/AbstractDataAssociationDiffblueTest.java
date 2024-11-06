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
import static org.mockito.Mockito.mock;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class AbstractDataAssociationDiffblueTest {
  /**
   * Test {@link AbstractDataAssociation#getSource()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getSource()}
   */
  @Test
  public void testGetSource_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull((new SimpleDataInputAssociation(
        new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"), "Target"))
        .getSource());
  }

  /**
   * Test {@link AbstractDataAssociation#getSource()}.
   * <ul>
   *   <li>Then return {@code Source}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getSource()}
   */
  @Test
  public void testGetSource_thenReturnSource() {
    // Arrange, Act and Assert
    assertEquals("Source", (new SimpleDataInputAssociation("Source", "Target")).getSource());
  }

  /**
   * Test {@link AbstractDataAssociation#getTarget()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getTarget()}
   */
  @Test
  public void testGetTarget_givenJavaLangObject() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Target",
        (new SimpleDataInputAssociation(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"),
            "Target")).getTarget());
  }

  /**
   * Test {@link AbstractDataAssociation#getTarget()}.
   * <ul>
   *   <li>Given
   * {@link SimpleDataInputAssociation#SimpleDataInputAssociation(String, String)}
   * with {@code Source} and {@code Target}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getTarget()}
   */
  @Test
  public void testGetTarget_givenSimpleDataInputAssociationWithSourceAndTarget() {
    // Arrange, Act and Assert
    assertEquals("Target", (new SimpleDataInputAssociation("Source", "Target")).getTarget());
  }

  /**
   * Test {@link AbstractDataAssociation#getSourceExpression()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@link JuelExpression}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getSourceExpression()}
   */
  @Test
  public void testGetSourceExpression_givenJavaLangObject_thenReturnJuelExpression() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    SimpleDataInputAssociation simpleDataInputAssociation = new SimpleDataInputAssociation(
        new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"), "Target");

    // Act
    Expression actualSourceExpression = simpleDataInputAssociation.getSourceExpression();

    // Assert
    assertTrue(actualSourceExpression instanceof JuelExpression);
    assertEquals("Expression Text", actualSourceExpression.getExpressionText());
    assertSame(simpleDataInputAssociation.sourceExpression, actualSourceExpression);
  }

  /**
   * Test {@link AbstractDataAssociation#getSourceExpression()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDataAssociation#getSourceExpression()}
   */
  @Test
  public void testGetSourceExpression_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new SimpleDataInputAssociation("Source", "Target")).getSourceExpression());
  }
}
