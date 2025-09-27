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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class MessageImplicitDataOutputAssociationDiffblueTest {
  /**
   * Test {@link MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Source is {@code Source Ref}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MessageImplicitDataOutputAssociation.<init>(String, String)",
    "void MessageImplicitDataOutputAssociation.<init>(String, Expression)"
  })
  public void testNewMessageImplicitDataOutputAssociation_thenReturnSourceIsSourceRef() {
    // Arrange and Act
    MessageImplicitDataOutputAssociation actualMessageImplicitDataOutputAssociation =
        new MessageImplicitDataOutputAssociation("Target Ref", "Source Ref");

    // Assert
    assertEquals("Source Ref", actualMessageImplicitDataOutputAssociation.getSource());
    assertEquals("Target Ref", actualMessageImplicitDataOutputAssociation.getTarget());
    assertNull(actualMessageImplicitDataOutputAssociation.getSourceExpression());
  }

  /**
   * Test {@link MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String,
   * Expression)}.
   *
   * <ul>
   *   <li>Then SourceExpression return {@link FixedValue}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageImplicitDataOutputAssociation#MessageImplicitDataOutputAssociation(String, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MessageImplicitDataOutputAssociation.<init>(String, String)",
    "void MessageImplicitDataOutputAssociation.<init>(String, Expression)"
  })
  public void testNewMessageImplicitDataOutputAssociation_thenSourceExpressionReturnFixedValue() {
    // Arrange
    FixedValue sourceExpression = new FixedValue(JSONObject.NULL);

    // Act
    MessageImplicitDataOutputAssociation actualMessageImplicitDataOutputAssociation =
        new MessageImplicitDataOutputAssociation("Target Ref", sourceExpression);

    // Assert
    Expression sourceExpression2 = actualMessageImplicitDataOutputAssociation.getSourceExpression();
    assertTrue(sourceExpression2 instanceof FixedValue);
    assertEquals("Target Ref", actualMessageImplicitDataOutputAssociation.getTarget());
    assertNull(actualMessageImplicitDataOutputAssociation.getSource());
    assertSame(sourceExpression, sourceExpression2);
  }

  /**
   * Test {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}.
   *
   * <p>Method under test: {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataOutputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation =
        new MessageImplicitDataOutputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Source Ref");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());
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
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
    verify(execution).setVariable(eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isNull());
  }

  /**
   * Test {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ItemInstance#ItemInstance(ItemDefinition, StructureInstance)} with item is
   *       {@link ItemDefinition#ItemDefinition(String, StructureDefinition)} and structureInstance
   *       is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataOutputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_givenItemInstanceWithItemIsItemDefinitionAndStructureInstanceIsNull() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation =
        new MessageImplicitDataOutputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Source Ref");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    ItemInstance item2 = new ItemInstance(item, null);

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
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
  }

  /**
   * Test {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then calls {@link FieldBaseStructureInstance#getFieldValue(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MessageImplicitDataOutputAssociation#evaluate(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageImplicitDataOutputAssociation.evaluate(DelegateExecution)"})
  public void testEvaluate_thenCallsGetFieldValue() {
    // Arrange
    MessageImplicitDataOutputAssociation messageImplicitDataOutputAssociation =
        new MessageImplicitDataOutputAssociation(
            "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", "Source Ref");

    FieldBaseStructureInstance structureInstance = mock(FieldBaseStructureInstance.class);
    when(structureInstance.getFieldValue(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    ItemInstance item2 = new ItemInstance(item, structureInstance);
    MessageInstance messageInstance = new MessageInstance(new MessageDefinition("42"), item2);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getVariable(Mockito.<String>any())).thenReturn(messageInstance);
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal(
        "org.activiti.engine.impl.bpmn.CURRENT_MESSAGE", JSONObject.NULL);

    // Act
    messageImplicitDataOutputAssociation.evaluate(execution);

    // Assert
    verify(structureInstance).getFieldValue("Source Ref");
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getVariable("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE");
    verify(execution)
        .setTransientVariableLocal(
            eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
    verify(execution)
        .setVariable(eq("org.activiti.engine.impl.bpmn.CURRENT_MESSAGE"), isA(Object.class));
  }
}
