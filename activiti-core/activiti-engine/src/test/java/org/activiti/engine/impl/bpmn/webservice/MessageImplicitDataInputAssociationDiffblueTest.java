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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class MessageImplicitDataInputAssociationDiffblueTest {
  /**
   * Test {@link MessageImplicitDataInputAssociation#MessageImplicitDataInputAssociation(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * MessageImplicitDataInputAssociation#MessageImplicitDataInputAssociation(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataInputAssociation.<init>(String, String)"})
  public void testNewMessageImplicitDataInputAssociation() {
    // Arrange and Act
    MessageImplicitDataInputAssociation actualMessageImplicitDataInputAssociation =
        new MessageImplicitDataInputAssociation("Source", "Target");

    // Assert
    assertEquals("Source", actualMessageImplicitDataInputAssociation.getSource());
    assertEquals("Target", actualMessageImplicitDataInputAssociation.getTarget());
    assertNull(actualMessageImplicitDataInputAssociation.getSourceExpression());
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link MessageDefinition#MessageDefinition(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataInputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_givenMessageDefinitionWithIdIs42() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation =
        new MessageImplicitDataInputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Target");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);

    MessageInstance messageInstance = new MessageInstance(message, item2);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal(
        "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", JSONObject.NULL);

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution, atLeast(1)).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link MessageInstance} {@link MessageInstance#getStructureInstance()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataInputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_givenMessageInstanceGetStructureInstanceReturnNull() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation =
        new MessageImplicitDataInputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Target");

    MessageInstance messageInstance = mock(MessageInstance.class);
    when(messageInstance.getStructureInstance()).thenReturn(null);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal(
        "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", JSONObject.NULL);

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(messageInstance).getStructureInstance();
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution, atLeast(1)).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
  }

  /**
   * Test {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then calls {@link FieldBaseStructureInstance#setFieldValue(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link MessageImplicitDataInputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataInputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_thenCallsSetFieldValue() {
    // Arrange
    MessageImplicitDataInputAssociation messageImplicitDataInputAssociation =
        new MessageImplicitDataInputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Target");

    FieldBaseStructureInstance fieldBaseStructureInstance = mock(FieldBaseStructureInstance.class);
    doNothing()
        .when(fieldBaseStructureInstance)
        .setFieldValue(Mockito.<String>any(), Mockito.<Object>any());

    MessageInstance messageInstance = mock(MessageInstance.class);
    when(messageInstance.getStructureInstance()).thenReturn(fieldBaseStructureInstance);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal(
        "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", JSONObject.NULL);

    // Act
    messageImplicitDataInputAssociation.evaluate(execution);

    // Assert
    verify(fieldBaseStructureInstance).setFieldValue(eq("Target"), isA(Object.class));
    verify(messageInstance, atLeast(1)).getStructureInstance();
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution, atLeast(1)).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
  }
}
