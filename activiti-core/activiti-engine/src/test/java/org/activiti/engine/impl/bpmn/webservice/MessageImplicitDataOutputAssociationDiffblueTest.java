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
package org.activiti.engine.impl.bpmn.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class MessageImplicitDataOutputAssociationDiffblueTest {
  /**
   * Method under test:
   * {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation = new MessageImplicitDataOutputAssociation(
        "Target Ref", "Source Ref");
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(execution.getVariable(Mockito.<String>any())).thenReturn(new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42")))));

    // Act
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).getVariable(eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"));
    verify(execution).setVariable(eq("Target Ref"), isNull());
  }

  /**
   * Method under test:
   * {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate2() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation = new MessageImplicitDataOutputAssociation(
        "Target Ref", "Source Ref");
    DelegateExecution execution = mock(DelegateExecution.class);
    MessageDefinition message = new MessageDefinition("42");
    when(execution.getVariable(Mockito.<String>any())).thenReturn(new MessageInstance(message,
        new ItemInstance(new ItemDefinition("42", new SimpleStructureDefinition("42")), null)));

    // Act
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).getVariable(eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"));
  }

  /**
   * Method under test:
   * {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate3() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation = new MessageImplicitDataOutputAssociation(
        "Target Ref", "Source Ref");
    FieldBaseStructureInstance structureInstance = mock(FieldBaseStructureInstance.class);
    when(structureInstance.getFieldValue(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    ItemInstance item = new ItemInstance(new ItemDefinition("42", new SimpleStructureDefinition("42")),
        structureInstance);

    MessageInstance messageInstance = new MessageInstance(new MessageDefinition("42"), item);

    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);

    // Act
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).getVariable(eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"));
    verify(execution).setVariable(eq("Target Ref"), isA(Object.class));
    verify(structureInstance).getFieldValue(eq("Source Ref"));
  }

  /**
   * Method under test:
   * {@link MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String, String)}
   */
  @Test
  public void testNewMessageImplicitDataOutputAssociation() {
    // Arrange and Act
    MessageImplicitDataOutputAssociation actualMessageImplicitDataOutputAssociation = new MessageImplicitDataOutputAssociation(
        "Target Ref", "Source Ref");

    // Assert
    assertEquals("Source Ref", actualMessageImplicitDataOutputAssociation.getSource());
    assertEquals("Target Ref", actualMessageImplicitDataOutputAssociation.getTarget());
    assertNull(actualMessageImplicitDataOutputAssociation.getSourceExpression());
  }

  /**
   * Method under test:
   * {@link MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String, Expression)}
   */
  @Test
  public void testNewMessageImplicitDataOutputAssociation2() {
    // Arrange
    FixedValue sourceExpression = new FixedValue(JSONObject.NULL);

    // Act
    MessageImplicitDataOutputAssociation actualMessageImplicitDataOutputAssociation = new MessageImplicitDataOutputAssociation(
        "Target Ref", sourceExpression);

    // Assert
    Expression sourceExpression2 = actualMessageImplicitDataOutputAssociation.getSourceExpression();
    assertTrue(sourceExpression2 instanceof FixedValue);
    assertEquals("Target Ref", actualMessageImplicitDataOutputAssociation.getTarget());
    assertNull(actualMessageImplicitDataOutputAssociation.getSource());
    assertSame(sourceExpression, sourceExpression2);
  }
}
