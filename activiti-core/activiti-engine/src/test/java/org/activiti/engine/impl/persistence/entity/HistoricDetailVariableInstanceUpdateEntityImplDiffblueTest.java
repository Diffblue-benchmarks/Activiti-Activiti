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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.HistoricJPAEntityListVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;

public class HistoricDetailVariableInstanceUpdateEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#setCachedValue(Object)}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#setDoubleValue(Double)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setLongValue(Long)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setName(String)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setRevision(int)}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#setTextValue2(String)}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#setTextValue(String)}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#setVariableType(VariableType)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getByteArrayRef()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getCachedValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getDoubleValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getLongValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getName()}
   *   <li>
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getPersistentState()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevision()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getTextValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getTextValue2()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableName()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricDetailVariableInstanceUpdateEntityImpl actualHistoricDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    Object object = JSONObject.NULL;
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setCachedValue(object);
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setName("Name");
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    BigDecimalType variableType = new BigDecimalType();
    actualHistoricDetailVariableInstanceUpdateEntityImpl.setVariableType(variableType);
    actualHistoricDetailVariableInstanceUpdateEntityImpl.getByteArrayRef();
    Object actualCachedValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getCachedValue();
    Double actualDoubleValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getDoubleValue();
    Long actualLongValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getLongValue();
    String actualName = actualHistoricDetailVariableInstanceUpdateEntityImpl.getName();
    actualHistoricDetailVariableInstanceUpdateEntityImpl.getPersistentState();
    int actualRevision = actualHistoricDetailVariableInstanceUpdateEntityImpl.getRevision();
    String actualTextValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getTextValue();
    String actualTextValue2 = actualHistoricDetailVariableInstanceUpdateEntityImpl.getTextValue2();
    String actualVariableName = actualHistoricDetailVariableInstanceUpdateEntityImpl.getVariableName();
    VariableType actualVariableType = actualHistoricDetailVariableInstanceUpdateEntityImpl.getVariableType();

    // Assert that nothing has changed
    assertEquals("42", actualTextValue);
    assertEquals("42", actualTextValue2);
    assertEquals("Name", actualName);
    assertEquals("Name", actualVariableName);
    assertEquals("VariableUpdate", actualHistoricDetailVariableInstanceUpdateEntityImpl.getDetailType());
    assertEquals(1, actualRevision);
    assertEquals(10.0d, actualDoubleValue.doubleValue(), 0.0);
    assertEquals(42L, actualLongValue.longValue());
    assertFalse(actualHistoricDetailVariableInstanceUpdateEntityImpl.isDeleted());
    assertFalse(actualHistoricDetailVariableInstanceUpdateEntityImpl.isInserted());
    assertFalse(actualHistoricDetailVariableInstanceUpdateEntityImpl.isUpdated());
    assertSame(variableType, actualVariableType);
    assertSame(object, actualCachedValue);
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue() {
    // Arrange
    HistoricJPAEntityListVariableType variableType = new HistoricJPAEntityListVariableType();
    variableType.setForceCacheable(false);

    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(variableType);
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(null);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BooleanType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue4() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new DoubleType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) Time is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenHistoricDetailVariableInstanceUpdateEntityImplTimeIsDate() {
    // Arrange
    HistoricJPAEntityListVariableType variableType = new HistoricJPAEntityListVariableType();
    variableType.setForceCacheable(false);

    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTime(mock(java.sql.Date.class));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(variableType);
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(null);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  public void testGetValue_givenJavaLangObject_thenReturnNull() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    Class<Object> theClass = Object.class;
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", historicDetailVariableInstanceUpdateEntityImpl.getVariableTypeName());
  }

  /**
   * Test
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTime(mock(java.sql.Date.class));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("bigdecimal", historicDetailVariableInstanceUpdateEntityImpl.getVariableTypeName());
  }

  /**
   * Test
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}
   */
  @Test
  public void testGetVariableTypeName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailVariableInstanceUpdateEntityImpl()).getVariableTypeName());
  }

  /**
   * Test
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setTime(mock(java.sql.Date.class));

    // Act and Assert
    assertEquals(1, historicDetailVariableInstanceUpdateEntityImpl.getRevisionNext());
  }

  /**
   * Test
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}.
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext_givenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(1, (new HistoricDetailVariableInstanceUpdateEntityImpl()).getRevisionNext());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getBytes());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}.
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes_givenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailVariableInstanceUpdateEntityImpl()).getBytes());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}.
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) Time is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}
   */
  @Test
  public void testGetBytes_givenHistoricDetailVariableInstanceUpdateEntityImplTimeIsDate() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTime(mock(java.sql.Date.class));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getBytes());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=null, name=null, type=null]",
        (new HistoricDetailVariableInstanceUpdateEntityImpl()).toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(null);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2(null);
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=null]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString3() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(null);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("foo");
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=null, textValue2=foo]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString4() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(null);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("foo");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2(null);
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=null, textValue=foo]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString5() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(null);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2(null);
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=null, doubleValue=10.0]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString6() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(null);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(1L);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue(null);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2(null);
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=null, longValue=1]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   * <p>
   * Method under test:
   * {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  public void testToString7() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("HistoricDetailVariableInstanceUpdateEntity[id=null, name=null, type=bigdecimal]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }
}
