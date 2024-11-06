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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.junit.Test;
import org.mockito.Mockito;

public class MessageImplicitDataInputAssociationDiffblueTest {
  /**
   * Test
   * {@link MessageImplicitDataInputAssociation#MessageImplicitDataInputAssociation(String, String)}.
   * <p>
   * Method under test:
   * {@link MessageImplicitDataInputAssociation#MessageImplicitDataInputAssociation(String, String)}
   */
  @Test
  public void testNewMessageImplicitDataInputAssociation() {
    // Arrange and Act
    MessageImplicitDataInputAssociation actualMessageImplicitDataInputAssociation = new MessageImplicitDataInputAssociation(
        "Source", "Target");

    // Assert
    assertEquals("Source", actualMessageImplicitDataInputAssociation.getSource());
    assertEquals("Target", actualMessageImplicitDataInputAssociation.getTarget());
    assertNull(actualMessageImplicitDataInputAssociation.getSourceExpression());
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link MessageDefinition#MessageDefinition(String)} with id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate_givenMessageDefinitionWithIdIs42() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation = new MessageImplicitDataInputAssociation(
        "Source", "Target");
    DelegateExecution execution = mock(DelegateExecution.class);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(execution.getVariable(Mockito.<String>any())).thenReturn(new MessageInstance(message,
        new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42")))));

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(execution, atLeast(1)).getVariable(Mockito.<String>any());
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link MessageInstance}
   * {@link MessageInstance#getStructureInstance()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate_givenMessageInstanceGetStructureInstanceReturnNull() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation = new MessageImplicitDataInputAssociation(
        "Source", "Target");
    MessageInstance messageInstance = mock(MessageInstance.class);
    when(messageInstance.getStructureInstance()).thenReturn(null);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(execution, atLeast(1)).getVariable(Mockito.<String>any());
    verify(messageInstance).getStructureInstance();
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   * <ul>
   *   <li>Then calls
   * {@link FieldBaseStructureInstance#setFieldValue(String, Object)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate_thenCallsSetFieldValue() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation = new MessageImplicitDataInputAssociation(
        "Source", "Target");
    FieldBaseStructureInstance fieldBaseStructureInstance = mock(FieldBaseStructureInstance.class);
    doNothing().when(fieldBaseStructureInstance).setFieldValue(Mockito.<String>any(), Mockito.<Object>any());
    MessageInstance messageInstance = mock(MessageInstance.class);
    when(messageInstance.getStructureInstance()).thenReturn(fieldBaseStructureInstance);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(execution, atLeast(1)).getVariable(Mockito.<String>any());
    verify(fieldBaseStructureInstance).setFieldValue(eq("Target"), isA(Object.class));
    verify(messageInstance, atLeast(1)).getStructureInstance();
  }
}
