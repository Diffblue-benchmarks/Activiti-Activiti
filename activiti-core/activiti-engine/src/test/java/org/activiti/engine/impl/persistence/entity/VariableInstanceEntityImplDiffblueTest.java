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
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.HistoricJPAEntityListVariableType;
import org.activiti.engine.impl.variable.IntegerType;
import org.activiti.engine.impl.variable.JPAEntityListVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;

public class VariableInstanceEntityImplDiffblueTest {
  /**
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new VariableInstanceEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertTrue(((Map<Object, Object>) actualPersistentState).isEmpty());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
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
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
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
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState4() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setDoubleValue(10.0d);

    // Act
    Object actualPersistentState = variableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(1, ((Map<String, Double>) actualPersistentState).size());
    assertEquals(10.0d, ((Map<String, Double>) actualPersistentState).get("doubleValue").doubleValue(), 0.0);
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState5() {
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
   * Method under test: {@link VariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState6() {
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
   * Method under test:
   * {@link VariableInstanceEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();

    // Act
    variableInstanceEntityImpl.setExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(1, ((Map<String, Boolean>) persistentState).size());
    assertTrue(((Map<String, Boolean>) persistentState).get("forcedUpdate"));
    assertTrue(variableInstanceEntityImpl.forcedUpdate);
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes() {
    // Arrange, Act and Assert
    assertNull((new VariableInstanceEntityImpl()).getBytes());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getBytes());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue() {
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
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BooleanType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue4() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new DoubleType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue5() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    variableInstanceEntityImpl.setType(new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue6() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new JPAEntityListVariableType());

    // Act and Assert
    assertNull(variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());
    Object object = JSONObject.NULL;

    // Act
    variableInstanceEntityImpl.setValue(object);

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("bigdecimal", variableInstanceEntityImpl.typeName);
    assertEquals(1, ((Map<String, String>) persistentState).size());
    assertEquals("null", ((Map<String, String>) persistentState).get("textValue"));
    assertEquals("null", variableInstanceEntityImpl.getTextValue());
    assertNull(variableInstanceEntityImpl.getLongValue());
    assertSame(object, variableInstanceEntityImpl.getCachedValue());
    assertSame(object, variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    variableInstanceEntityImpl.setType(new CustomObjectType("Type Name", theClass));
    Object object = JSONObject.NULL;

    // Act
    variableInstanceEntityImpl.setValue(object);

    // Assert
    Object persistentState = variableInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("Type Name", variableInstanceEntityImpl.typeName);
    assertNull(variableInstanceEntityImpl.getLongValue());
    assertNull(variableInstanceEntityImpl.getTextValue());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
    assertSame(object, variableInstanceEntityImpl.getCachedValue());
    assertSame(object, variableInstanceEntityImpl.getValue());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#setValue(Object)}
   */
  @Test
  public void testSetValue3() {
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
    assertEquals(42L, variableInstanceEntityImpl.getLongValue().longValue());
    assertTrue(((Map<String, Object>) persistentState).containsKey("longValue"));
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  public void testGetTypeName() {
    // Arrange, Act and Assert
    assertNull((new VariableInstanceEntityImpl()).getTypeName());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  public void testGetTypeName2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTypeName(null);
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", variableInstanceEntityImpl.getTypeName());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#getTypeName()}
   */
  @Test
  public void testGetTypeName3() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setTypeName("foo");
    variableInstanceEntityImpl.setType(null);

    // Act and Assert
    assertEquals("foo", variableInstanceEntityImpl.getTypeName());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null]",
        (new VariableInstanceEntityImpl()).toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null]", variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString3() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2("foo");
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null, textValue2=foo]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString4() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue("foo");
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null, textValue=foo]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString5() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(10.0d);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null, doubleValue=10.0]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString6() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(1L);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue(null);
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null, longValue=1]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString7() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=bigdecimal]", variableInstanceEntityImpl.toString());
  }

  /**
   * Method under test: {@link VariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString8() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(null);
    variableInstanceEntityImpl.setLongValue(null);
    variableInstanceEntityImpl.setDoubleValue(null);
    variableInstanceEntityImpl.setTextValue("");
    variableInstanceEntityImpl.setTextValue2(null);
    variableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("VariableInstanceEntity[id=null, name=null, type=null, textValue=]",
        variableInstanceEntityImpl.toString());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link VariableInstanceEntityImpl}
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
    actualVariableInstanceEntityImpl.getByteArrayRef();
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

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualTextValue);
    assertEquals("42", actualTextValue2);
    assertEquals("Name", actualName);
    assertEquals(1, actualVariableInstanceEntityImpl.getRevision());
    assertEquals(10.0d, actualDoubleValue.doubleValue(), 0.0);
    assertEquals(42L, actualLongValue.longValue());
    assertFalse(actualVariableInstanceEntityImpl.isDeleted());
    assertFalse(actualVariableInstanceEntityImpl.isInserted());
    assertFalse(actualVariableInstanceEntityImpl.isUpdated());
    assertSame(type, actualType);
    assertSame(object, actualCachedValue);
  }
}
