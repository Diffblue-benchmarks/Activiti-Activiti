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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.HistoricJPAEntityListVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;

public class HistoricVariableInstanceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link HistoricVariableInstanceEntityImpl}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setCachedValue(Object)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setCreateTime(Date)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setDoubleValue(Double)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setExecutionId(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setLastUpdatedTime(Date)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setLongValue(Long)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setName(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setTaskId(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setTextValue2(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setTextValue(String)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#setVariableType(VariableType)}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getByteArrayRef()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getCachedValue()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getCreateTime()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getDoubleValue()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getExecutionId()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getLastUpdatedTime()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getLongValue()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getName()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getProcessInstanceId()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getTaskId()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getTextValue()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getTextValue2()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getVariableName()}
   *   <li>{@link HistoricVariableInstanceEntityImpl#getVariableType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricVariableInstanceEntityImpl actualHistoricVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    Object object = JSONObject.NULL;
    actualHistoricVariableInstanceEntityImpl.setCachedValue(object);
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricVariableInstanceEntityImpl.setCreateTime(createTime);
    actualHistoricVariableInstanceEntityImpl.setDoubleValue(10.0d);
    actualHistoricVariableInstanceEntityImpl.setExecutionId("42");
    Date lastUpdatedTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricVariableInstanceEntityImpl.setLastUpdatedTime(lastUpdatedTime);
    actualHistoricVariableInstanceEntityImpl.setLongValue(42L);
    actualHistoricVariableInstanceEntityImpl.setName("Name");
    actualHistoricVariableInstanceEntityImpl.setProcessInstanceId("42");
    actualHistoricVariableInstanceEntityImpl.setTaskId("42");
    actualHistoricVariableInstanceEntityImpl.setTextValue2("42");
    actualHistoricVariableInstanceEntityImpl.setTextValue("42");
    BigDecimalType variableType = new BigDecimalType();
    actualHistoricVariableInstanceEntityImpl.setVariableType(variableType);
    actualHistoricVariableInstanceEntityImpl.getByteArrayRef();
    Object actualCachedValue = actualHistoricVariableInstanceEntityImpl.getCachedValue();
    Date actualCreateTime = actualHistoricVariableInstanceEntityImpl.getCreateTime();
    Double actualDoubleValue = actualHistoricVariableInstanceEntityImpl.getDoubleValue();
    String actualExecutionId = actualHistoricVariableInstanceEntityImpl.getExecutionId();
    Date actualLastUpdatedTime = actualHistoricVariableInstanceEntityImpl.getLastUpdatedTime();
    Long actualLongValue = actualHistoricVariableInstanceEntityImpl.getLongValue();
    String actualName = actualHistoricVariableInstanceEntityImpl.getName();
    String actualProcessInstanceId = actualHistoricVariableInstanceEntityImpl.getProcessInstanceId();
    String actualTaskId = actualHistoricVariableInstanceEntityImpl.getTaskId();
    String actualTextValue = actualHistoricVariableInstanceEntityImpl.getTextValue();
    String actualTextValue2 = actualHistoricVariableInstanceEntityImpl.getTextValue2();
    String actualVariableName = actualHistoricVariableInstanceEntityImpl.getVariableName();
    VariableType actualVariableType = actualHistoricVariableInstanceEntityImpl.getVariableType();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualTextValue);
    assertEquals("42", actualTextValue2);
    assertEquals("Name", actualName);
    assertEquals("Name", actualVariableName);
    assertEquals(1, actualHistoricVariableInstanceEntityImpl.getRevision());
    assertEquals(10.0d, actualDoubleValue.doubleValue(), 0.0);
    assertEquals(42L, actualLongValue.longValue());
    assertFalse(actualHistoricVariableInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricVariableInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricVariableInstanceEntityImpl.isUpdated());
    assertSame(variableType, actualVariableType);
    assertSame(createTime, actualCreateTime);
    assertSame(lastUpdatedTime, actualLastUpdatedTime);
    assertSame(object, actualCachedValue);
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getPersistentState()}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicVariableInstanceEntityImpl.setCreateTime(createTime);
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    Date lastUpdatedTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicVariableInstanceEntityImpl.setLastUpdatedTime(lastUpdatedTime);
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act
    Object actualPersistentState = historicVariableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(7, ((Map<String, Object>) actualPersistentState).size());
    assertEquals("42", ((Map<String, Object>) actualPersistentState).get("textValue"));
    assertEquals("42", ((Map<String, Object>) actualPersistentState).get("textValue2"));
    assertNull(((Map<String, Object>) actualPersistentState).get("byteArrayRef"));
    assertEquals(10.0d, ((Double) ((Map<String, Object>) actualPersistentState).get("doubleValue")).doubleValue(), 0.0);
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("longValue"));
    assertSame(createTime, ((Map<String, Object>) actualPersistentState).get("createTime"));
    assertSame(lastUpdatedTime, ((Map<String, Object>) actualPersistentState).get("lastUpdatedTime"));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return containsKey {@code createTime}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnContainsKeyCreateTime() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(mock(java.sql.Date.class));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    java.util.Date lastUpdatedTime = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicVariableInstanceEntityImpl.setLastUpdatedTime(lastUpdatedTime);
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act
    Object actualPersistentState = historicVariableInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(7, ((Map<String, Object>) actualPersistentState).size());
    assertEquals("42", ((Map<String, Object>) actualPersistentState).get("textValue"));
    assertEquals("42", ((Map<String, Object>) actualPersistentState).get("textValue2"));
    assertNull(((Map<String, Object>) actualPersistentState).get("byteArrayRef"));
    assertEquals(10.0d, ((Double) ((Map<String, Object>) actualPersistentState).get("doubleValue")).doubleValue(), 0.0);
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("longValue"));
    assertSame(lastUpdatedTime, ((Map<String, Object>) actualPersistentState).get("lastUpdatedTime"));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is six.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnSizeIsSix() {
    // Arrange and Act
    Object actualPersistentState = (new HistoricVariableInstanceEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(6, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("createTime"));
    assertNull(((Map<String, Object>) actualPersistentState).get("doubleValue"));
    assertNull(((Map<String, Object>) actualPersistentState).get("lastUpdatedTime"));
    assertNull(((Map<String, Object>) actualPersistentState).get("longValue"));
    assertNull(((Map<String, Object>) actualPersistentState).get("textValue"));
    assertNull(((Map<String, Object>) actualPersistentState).get("textValue2"));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue() {
    // Arrange
    HistoricJPAEntityListVariableType variableType = new HistoricJPAEntityListVariableType();
    variableType.setForceCacheable(false);

    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(variableType);
    historicVariableInstanceEntityImpl.setCachedValue(null);

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CreateTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenHistoricVariableInstanceEntityImplCreateTimeIsDate() {
    // Arrange
    HistoricJPAEntityListVariableType variableType = new HistoricJPAEntityListVariableType();
    variableType.setForceCacheable(false);

    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCreateTime(mock(java.sql.Date.class));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(variableType);
    historicVariableInstanceEntityImpl.setCachedValue(null);

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * VariableType is {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenHistoricVariableInstanceEntityImplVariableTypeIsBigDecimalType() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * VariableType is {@link BooleanType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenHistoricVariableInstanceEntityImplVariableTypeIsBooleanType() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setVariableType(new BooleanType());

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * VariableType is {@link DoubleType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenHistoricVariableInstanceEntityImplVariableTypeIsDoubleType() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setVariableType(new DoubleType());

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenJavaLangObject_thenReturnNull() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    historicVariableInstanceEntityImpl.setVariableType(new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getBytes()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getBytes());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getBytes()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes_givenHistoricVariableInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricVariableInstanceEntityImpl()).getBytes());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getBytes()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CreateTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes_givenHistoricVariableInstanceEntityImplCreateTimeIsDate() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(mock(java.sql.Date.class));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(historicVariableInstanceEntityImpl.getBytes());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", historicVariableInstanceEntityImpl.getVariableTypeName());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CreateTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName_givenHistoricVariableInstanceEntityImplCreateTimeIsDate() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(mock(java.sql.Date.class));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", historicVariableInstanceEntityImpl.getVariableTypeName());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName_givenHistoricVariableInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricVariableInstanceEntityImpl()).getVariableTypeName());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#getTime()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime_givenHistoricVariableInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricVariableInstanceEntityImpl()).getTime());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=null, name=null, revision=1, type=null]",
        (new HistoricVariableInstanceEntityImpl()).toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(null);
    historicVariableInstanceEntityImpl.setDoubleValue(null);
    historicVariableInstanceEntityImpl.setTextValue(null);
    historicVariableInstanceEntityImpl.setTextValue2(null);
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString3() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(null);
    historicVariableInstanceEntityImpl.setDoubleValue(null);
    historicVariableInstanceEntityImpl.setTextValue(null);
    historicVariableInstanceEntityImpl.setTextValue2("foo");
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null, textValue2=foo]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString4() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(null);
    historicVariableInstanceEntityImpl.setDoubleValue(null);
    historicVariableInstanceEntityImpl.setTextValue("foo");
    historicVariableInstanceEntityImpl.setTextValue2(null);
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null, textValue=foo]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString5() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(null);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setTextValue(null);
    historicVariableInstanceEntityImpl.setTextValue2(null);
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null, doubleValue=10.0]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString6() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(1L);
    historicVariableInstanceEntityImpl.setDoubleValue(null);
    historicVariableInstanceEntityImpl.setTextValue(null);
    historicVariableInstanceEntityImpl.setTextValue2(null);
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null, longValue=1]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString7() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=null, name=null, revision=1, type=bigdecimal]",
        historicVariableInstanceEntityImpl.toString());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityImpl#toString()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CreateTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceEntityImpl#toString()}
   */
  @Test
  public void testToString_givenHistoricVariableInstanceEntityImplCreateTimeIsDate() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(mock(java.sql.Date.class));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTaskId("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(null);
    historicVariableInstanceEntityImpl.setLongValue(null);
    historicVariableInstanceEntityImpl.setDoubleValue(null);
    historicVariableInstanceEntityImpl.setTextValue(null);
    historicVariableInstanceEntityImpl.setTextValue2(null);
    historicVariableInstanceEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricVariableInstanceEntity[id=42, name=Name, revision=1, type=null]",
        historicVariableInstanceEntityImpl.toString());
  }
}
