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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.HistoricJPAEntityListVariableType;
import org.activiti.engine.impl.variable.HistoricJPAEntityVariableType;
import org.activiti.engine.impl.variable.IntegerType;
import org.activiti.engine.impl.variable.JPAEntityListVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class VariableInstanceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link VariableInstanceEntityImpl}
   *   <li>{@link VariableInstanceEntityImpl#forceUpdate()}
   *   <li>{@link VariableInstanceEntityImpl#setCachedValue(Object)}
   *   <li>{@link VariableInstanceEntityImpl#setDoubleValue(Double)}
   *   <li>{@link VariableInstanceEntityImpl#setExecutionId(String)}
   *   <li>{@link VariableInstanceEntityImpl#setLongValue(Long)}
   *   <li>{@link VariableInstanceEntityImpl#setName(String)}
   *   <li>{@link VariableInstanceEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link VariableInstanceEntityImpl#setTaskId(String)}
   *   <li>{@link VariableInstanceEntityImpl#setTextValue2(String)}
   *   <li>{@link VariableInstanceEntityImpl#setTextValue(String)}
   *   <li>{@link VariableInstanceEntityImpl#setType(VariableType)}
   *   <li>{@link VariableInstanceEntityImpl#setTypeName(String)}
   *   <li>{@link VariableInstanceEntityImpl#getByteArrayRef()}
   *   <li>{@link VariableInstanceEntityImpl#getCachedValue()}
   *   <li>{@link VariableInstanceEntityImpl#getDoubleValue()}
   *   <li>{@link VariableInstanceEntityImpl#getExecutionId()}
   *   <li>{@link VariableInstanceEntityImpl#getLongValue()}
   *   <li>{@link VariableInstanceEntityImpl#getName()}
   *   <li>{@link VariableInstanceEntityImpl#getProcessInstanceId()}
   *   <li>{@link VariableInstanceEntityImpl#getTaskId()}
   *   <li>{@link VariableInstanceEntityImpl#getTextValue()}
   *   <li>{@link VariableInstanceEntityImpl#getTextValue2()}
   *   <li>{@link VariableInstanceEntityImpl#getType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityImpl.<init>()",
    "void VariableInstanceEntityImpl.forceUpdate()",
    "ByteArrayRef VariableInstanceEntityImpl.getByteArrayRef()",
    "Object VariableInstanceEntityImpl.getCachedValue()",
    "Double VariableInstanceEntityImpl.getDoubleValue()",
    "String VariableInstanceEntityImpl.getExecutionId()",
    "Long VariableInstanceEntityImpl.getLongValue()",
    "String VariableInstanceEntityImpl.getName()",
    "String VariableInstanceEntityImpl.getProcessInstanceId()",
    "String VariableInstanceEntityImpl.getTaskId()",
    "String VariableInstanceEntityImpl.getTextValue()",
    "String VariableInstanceEntityImpl.getTextValue2()",
    "VariableType VariableInstanceEntityImpl.getType()",
    "void VariableInstanceEntityImpl.setCachedValue(Object)",
    "void VariableInstanceEntityImpl.setDoubleValue(Double)",
    "void VariableInstanceEntityImpl.setExecutionId(String)",
    "void VariableInstanceEntityImpl.setLongValue(Long)",
    "void VariableInstanceEntityImpl.setName(String)",
    "void VariableInstanceEntityImpl.setProcessInstanceId(String)",
    "void VariableInstanceEntityImpl.setTaskId(String)",
    "void VariableInstanceEntityImpl.setTextValue(String)",
    "void VariableInstanceEntityImpl.setTextValue2(String)",
    "void VariableInstanceEntityImpl.setType(VariableType)",
    "void VariableInstanceEntityImpl.setTypeName(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    VariableInstanceEntityImpl actualVariableInstanceEntityImpl = new VariableInstanceEntityImpl();
    actualVariableInstanceEntityImpl.forceUpdate();
    Object object = JSONObject.NULL;
    actualVariableInstanceEntityImpl.setCachedValue(object);
    actualVariableInstanceEntityImpl.setDoubleValue(10.0d);
    actualVariableInstanceEntityImpl.setExecutionId("42");
    actualVariableInstanceEntityImpl.setLongValue(42L);
    actualVariableInstanceEntityImpl.setName("Name");
    actualVariableInstanceEntityImpl.setProcessInstanceId("42");
    actualVariableInstanceEntityImpl.setTaskId("42");
    actualVariableInstanceEntityImpl.setTextValue2("42");
    actualVariableInstanceEntityImpl.setTextValue("42");
    BigDecimalType type = new BigDecimalType();
    actualVariableInstanceEntityImpl.setType(type);
    actualVariableInstanceEntityImpl.setTypeName("Type Name");
    ByteArrayRef actualByteArrayRef = actualVariableInstanceEntityImpl.getByteArrayRef();
    Object actualCachedValue = actualVariableInstanceEntityImpl.getCachedValue();
    Double actualDoubleValue = actualVariableInstanceEntityImpl.getDoubleValue();
    String actualExecutionId = actualVariableInstanceEntityImpl.getExecutionId();
    Long actualLongValue = actualVariableInstanceEntityImpl.getLongValue();
    String actualName = actualVariableInstanceEntityImpl.getName();
    String actualProcessInstanceId = actualVariableInstanceEntityImpl.getProcessInstanceId();
    String actualTaskId = actualVariableInstanceEntityImpl.getTaskId();
    String actualTextValue = actualVariableInstanceEntityImpl.getTextValue();
    String actualTextValue2 = actualVariableInstanceEntityImpl.getTextValue2();
    VariableType actualType = actualVariableInstanceEntityImpl.getType();

    // Assert
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualTextValue);
    assertEquals("42", actualTextValue2);
    assertEquals("Name", actualName);
    assertNull(actualVariableInstanceEntityImpl.getId());
    assertNull(actualByteArrayRef);
    assertEquals(1, actualVariableInstanceEntityImpl.getRevision());
    assertEquals(10.0d, actualDoubleValue.doubleValue(), 0.0);
    assertEquals(42L, actualLongValue.longValue());
    assertFalse(actualVariableInstanceEntityImpl.isDeleted());
    assertFalse(actualVariableInstanceEntityImpl.isInserted());
    assertFalse(actualVariableInstanceEntityImpl.isUpdated());
    assertSame(type, actualType);
    assertSame(object, actualCachedValue);
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_givenVariableInstanceEntityImpl_thenReturnEmpty() {
    // Arrange and Act
    Object actualPersistentState = new VariableInstanceEntityImpl().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertTrue(((Map<Object, Object>) actualPersistentState).isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code doubleValue} doubleValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnDoubleValueDoubleValueIsTen() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setDoubleValue(10.0d);

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, Double>) actualPersistentState).size());
    assertEquals(
        10.0d, ((Map<String, Double>) actualPersistentState).get("doubleValue").doubleValue(), 0.0);
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code forcedUpdate}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnForcedUpdate() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.forceUpdate();

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, Boolean>) actualPersistentState).size());
    assertTrue(((Map<String, Boolean>) actualPersistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code longValue} longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnLongValueLongValueIsFortyTwo() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setLongValue(42L);

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, Long>) actualPersistentState).size());
    assertEquals(42L, ((Map<String, Long>) actualPersistentState).get("longValue").longValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code textValue2} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnTextValue2Is42() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTextValue2("42");

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("textValue2"));
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code textValue} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnTextValueIs42() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTextValue("42");

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("textValue"));
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setExecution(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) PersistentState {@link
   *       Map}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setExecution(ExecutionEntity)"})
  public void testSetExecution_thenVariableInstanceEntityImplPersistentStateMap() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();

    // Act
    variableInstanceEntityImpl.setExecution(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(1, ((Map<String, Boolean>) persistentState).size());
    assertTrue(((Map<String, Boolean>) persistentState).get("forcedUpdate"));
    assertTrue(variableInstanceEntityImpl.forcedUpdate);
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getBytes()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getBytes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] VariableInstanceEntityImpl.getBytes()"})
  public void testGetBytes_givenVariableInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull(new VariableInstanceEntityImpl().getBytes());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getBytes()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Bytes is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getBytes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] VariableInstanceEntityImpl.getBytes()"})
  public void testGetBytes_givenVariableInstanceEntityImplBytesIsNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getBytes());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setBytes(byte[])}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Bytes is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setBytes(byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setBytes(byte[])"})
  public void testSetBytes_givenVariableInstanceEntityImplBytesIsNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setBytes(null);

    // Act
    variableInstanceEntityImpl.setBytes(null);

    // Assert that nothing has changed
    assertEquals("var-null", variableInstanceEntityImpl.getByteArrayRef().getName());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setBytes(byte[])}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) ByteArrayRef Name is {@code
   *       var-null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setBytes(byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setBytes(byte[])"})
  public void testSetBytes_thenVariableInstanceEntityImplByteArrayRefNameIsVarNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();

    // Act
    variableInstanceEntityImpl.setBytes(null);

    // Assert
    assertEquals("var-null", variableInstanceEntityImpl.getByteArrayRef().getName());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link HistoricJPAEntityVariableType} (default constructor) ForceCacheable is
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenHistoricJPAEntityVariableTypeForceCacheableIsTrue() {
    // Arrange
    HistoricJPAEntityVariableType type = new HistoricJPAEntityVariableType();
    type.setForceCacheable(true);

    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTextValue("42");
    variableInstanceEntityImpl.setType(type);

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenJavaLangObject_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    variableInstanceEntityImpl.setType(new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) CachedValue is {@code
   *       null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplCachedValueIsNull_thenReturnNull() {
    // Arrange
    HistoricJPAEntityListVariableType type = new HistoricJPAEntityListVariableType();
    type.setForceCacheable(false);

    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(type);
    variableInstanceEntityImpl.setCachedValue(null);

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) LongValue is forty-two.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplLongValueIsFortyTwo_thenReturnFalse() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setLongValue(42L);
    variableInstanceEntityImpl.setType(new BooleanType());

    // Act and Assert
    assertFalse((Boolean) variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) LongValue is one.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplLongValueIsOne_thenReturnTrue() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setLongValue(1L);
    variableInstanceEntityImpl.setType(new BooleanType());

    // Act and Assert
    assertTrue((Boolean) variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Type is {@link
   *       BigDecimalType} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplTypeIsBigDecimalType_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Type is {@link
   *       BooleanType} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplTypeIsBooleanType_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BooleanType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Type is {@link DoubleType}
   *       (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplTypeIsDoubleType_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new DoubleType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Type is {@link
   *       JPAEntityListVariableType} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableInstanceEntityImpl.getValue()"})
  public void testGetValue_givenVariableInstanceEntityImplTypeIsJPAEntityListVariableType() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new JPAEntityListVariableType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setValue(Object)}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) TextValue is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setValue(Object)"})
  public void testSetValue_thenVariableInstanceEntityImplTextValueIs42() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new IntegerType());

    // Act
    variableInstanceEntityImpl.setValue(42);

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("42", variableInstanceEntityImpl.getTextValue());
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertEquals("42", ((Map<String, Object>) persistentState).get("textValue"));
    assertEquals("integer", variableInstanceEntityImpl.typeName);
    assertEquals(42, ((Integer) variableInstanceEntityImpl.getCachedValue()).intValue());
    assertEquals(42, ((Integer) variableInstanceEntityImpl.getValue()).intValue());
    assertEquals(42L, variableInstanceEntityImpl.getLongValue().longValue());
    assertEquals(
        42L, ((Long) ((Map<String, Object>) persistentState).get("longValue")).longValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setValue(Object)}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) {@link
   *       VariableInstanceEntityImpl#typeName} is {@code bigdecimal}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setValue(Object)"})
  public void testSetValue_thenVariableInstanceEntityImplTypeNameIsBigdecimal() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act
    variableInstanceEntityImpl.setValue(JSONObject.NULL);

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("bigdecimal", variableInstanceEntityImpl.typeName);
    assertEquals(1, ((Map<String, String>) persistentState).size());
    assertEquals("null", ((Map<String, String>) persistentState).get("textValue"));
    assertEquals("null", variableInstanceEntityImpl.getTextValue());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#setValue(Object)}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) {@link
   *       VariableInstanceEntityImpl#typeName} is {@code Type Name}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInstanceEntityImpl.setValue(Object)"})
  public void testSetValue_thenVariableInstanceEntityImplTypeNameIsTypeName() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    variableInstanceEntityImpl.setType(new CustomObjectType("Type Name", theClass));

    // Act
    variableInstanceEntityImpl.setValue(JSONObject.NULL);

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("Type Name", variableInstanceEntityImpl.typeName);
    assertNull(variableInstanceEntityImpl.getTextValue());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getTypeName()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) TypeName is {@code foo}.
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.getTypeName()"})
  public void testGetTypeName_givenVariableInstanceEntityImplTypeNameIsFoo_thenReturnFoo() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTypeName("foo");
    variableInstanceEntityImpl.setType(null);

    // Act and Assert
    assertEquals("foo", variableInstanceEntityImpl.getTypeName());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getTypeName()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.getTypeName()"})
  public void testGetTypeName_givenVariableInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new VariableInstanceEntityImpl().getTypeName());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#getTypeName()}.
   *
   * <ul>
   *   <li>Then return {@code bigdecimal}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.getTypeName()"})
  public void testGetTypeName_thenReturnBigdecimal() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTypeName(null);
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", variableInstanceEntityImpl.getTypeName());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2("not empty");
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null, textValue2=not empty]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue("not empty");
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null, textValue=not empty]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString3() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setDoubleValue(10.0d);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null, doubleValue=10.0]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString_givenVariableInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null]",
        new VariableInstanceEntityImpl().toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code VariableInstanceEntity[id=null, name=null, type=bigdecimal]}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString_thenReturnVariableInstanceEntityIdNullNameNullTypeBigdecimal() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=bigdecimal]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code VariableInstanceEntity[id=null, name=null, type=null]}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString_thenReturnVariableInstanceEntityIdNullNameNullTypeNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code VariableInstanceEntity[id=null, name=null, type=null, longValue=42]}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString_thenReturnVariableInstanceEntityIdNullNameNullTypeNullLongValue42() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setLongValue(42L);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null, longValue=42]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link VariableInstanceEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code VariableInstanceEntity[id=null, name=null, type=null, textValue2=]}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String VariableInstanceEntityImpl.toString()"})
  public void testToString_thenReturnVariableInstanceEntityIdNullNameNullTypeNullTextValue2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2("");
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "VariableInstanceEntity[id=null, name=null, type=null, textValue2=]",
        variableInstanceEntityImpl.toString());
  }
}
