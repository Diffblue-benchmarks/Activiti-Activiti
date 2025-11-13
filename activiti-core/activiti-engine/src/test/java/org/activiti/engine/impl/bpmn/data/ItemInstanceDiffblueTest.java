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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ItemInstanceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ItemInstance#ItemInstance(ItemDefinition, StructureInstance)}
   *   <li>{@link ItemInstance#getItem()}
   *   <li>{@link ItemInstance#getStructureInstance()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ItemInstance.<init>(ItemDefinition, StructureInstance)",
    "ItemDefinition ItemInstance.getItem()",
    "StructureInstance ItemInstance.getStructureInstance()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    // Act
    ItemInstance actualItemInstance = new ItemInstance(item, structureInstance);
    ItemDefinition actualItem = actualItemInstance.getItem();

    // Assert
    assertSame(structureInstance, actualItemInstance.getStructureInstance());
    assertSame(item, actualItem);
  }

  /**
   * Test {@link ItemInstance#getFieldValue(String)}.
   *
   * <ul>
   *   <li>Then calls {@link FieldBaseStructureInstance#getFieldValue(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ItemInstance#getFieldValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ItemInstance.getFieldValue(String)"})
  public void testGetFieldValue_thenCallsGetFieldValue() {
    // Arrange
    FieldBaseStructureInstance structureInstance = mock(FieldBaseStructureInstance.class);
    when(structureInstance.getFieldValue(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    ItemInstance itemInstance = new ItemInstance(item, structureInstance);

    // Act
    itemInstance.getFieldValue("Field Name");

    // Assert
    verify(structureInstance).getFieldValue("Field Name");
  }

  /**
   * Test {@link ItemInstance#getFieldValue(String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ItemInstance#getFieldValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ItemInstance.getFieldValue(String)"})
  public void testGetFieldValue_thenReturnNull() {
    // Arrange
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance itemInstance = new ItemInstance(item, structureInstance);

    // Act and Assert
    assertNull(itemInstance.getFieldValue("Field Name"));
  }

  /**
   * Test {@link ItemInstance#setFieldValue(String, Object)}.
   *
   * <p>Method under test: {@link ItemInstance#setFieldValue(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ItemInstance.setFieldValue(String, Object)"})
  public void testSetFieldValue() {
    // Arrange
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));
    FieldBaseStructureInstance structureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    ItemInstance itemInstance = new ItemInstance(item, structureInstance);
    Object object = JSONObject.NULL;

    // Act
    itemInstance.setFieldValue("Field Name", object);

    // Assert
    StructureInstance structureInstance2 = itemInstance.getStructureInstance();
    assertTrue(structureInstance2 instanceof FieldBaseStructureInstance);
    Map<String, Object> stringObjectMap =
        ((FieldBaseStructureInstance) structureInstance2).fieldValues;
    assertEquals(1, stringObjectMap.size());
    assertSame(structureInstance.fieldValues, stringObjectMap);
    assertSame(object, stringObjectMap.get("Field Name"));
  }

  /**
   * Test {@link ItemInstance#setFieldValue(String, Object)}.
   *
   * <ul>
   *   <li>Then calls {@link FieldBaseStructureInstance#setFieldValue(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link ItemInstance#setFieldValue(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ItemInstance.setFieldValue(String, Object)"})
  public void testSetFieldValue_thenCallsSetFieldValue() {
    // Arrange
    FieldBaseStructureInstance structureInstance = mock(FieldBaseStructureInstance.class);
    doNothing().when(structureInstance).setFieldValue(Mockito.<String>any(), Mockito.<Object>any());
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    ItemInstance itemInstance = new ItemInstance(item, structureInstance);

    // Act
    itemInstance.setFieldValue("Field Name", JSONObject.NULL);

    // Assert
    verify(structureInstance).setFieldValue(eq("Field Name"), isA(Object.class));
  }
}
