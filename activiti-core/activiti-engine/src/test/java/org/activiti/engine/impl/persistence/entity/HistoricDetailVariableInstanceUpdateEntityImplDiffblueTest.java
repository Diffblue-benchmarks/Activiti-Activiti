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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.JPAEntityListVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricDetailVariableInstanceUpdateEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link
   *       HistoricDetailVariableInstanceUpdateEntityImpl}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setCachedValue(Object)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setDoubleValue(Double)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setLongValue(Long)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setName(String)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setRevision(int)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setTextValue2(String)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setTextValue(String)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#setVariableType(VariableType)}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getByteArrayRef()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getCachedValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getDoubleValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getLongValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getName()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getPersistentState()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevision()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getTextValue()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getTextValue2()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableName()}
   *   <li>{@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricDetailVariableInstanceUpdateEntityImpl.<init>()",
    "ByteArrayRef HistoricDetailVariableInstanceUpdateEntityImpl.getByteArrayRef()",
    "Object HistoricDetailVariableInstanceUpdateEntityImpl.getCachedValue()",
    "Double HistoricDetailVariableInstanceUpdateEntityImpl.getDoubleValue()",
    "Long HistoricDetailVariableInstanceUpdateEntityImpl.getLongValue()",
    "String HistoricDetailVariableInstanceUpdateEntityImpl.getName()",
    "Object HistoricDetailVariableInstanceUpdateEntityImpl.getPersistentState()",
    "int HistoricDetailVariableInstanceUpdateEntityImpl.getRevision()",
    "String HistoricDetailVariableInstanceUpdateEntityImpl.getTextValue()",
    "String HistoricDetailVariableInstanceUpdateEntityImpl.getTextValue2()",
    "String HistoricDetailVariableInstanceUpdateEntityImpl.getVariableName()",
    "VariableType HistoricDetailVariableInstanceUpdateEntityImpl.getVariableType()",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setCachedValue(Object)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setDoubleValue(Double)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setLongValue(Long)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setName(String)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setRevision(int)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setTextValue(String)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setTextValue2(String)",
    "void HistoricDetailVariableInstanceUpdateEntityImpl.setVariableType(VariableType)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricDetailVariableInstanceUpdateEntityImpl
        actualHistoricDetailVariableInstanceUpdateEntityImpl =
            new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    ByteArrayRef actualByteArrayRef =
        actualHistoricDetailVariableInstanceUpdateEntityImpl.getByteArrayRef();
    Object actualCachedValue =
        actualHistoricDetailVariableInstanceUpdateEntityImpl.getCachedValue();
    Double actualDoubleValue =
        actualHistoricDetailVariableInstanceUpdateEntityImpl.getDoubleValue();
    Long actualLongValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getLongValue();
    String actualName = actualHistoricDetailVariableInstanceUpdateEntityImpl.getName();
    actualHistoricDetailVariableInstanceUpdateEntityImpl.getPersistentState();
    int actualRevision = actualHistoricDetailVariableInstanceUpdateEntityImpl.getRevision();
    String actualTextValue = actualHistoricDetailVariableInstanceUpdateEntityImpl.getTextValue();
    String actualTextValue2 = actualHistoricDetailVariableInstanceUpdateEntityImpl.getTextValue2();
    String actualVariableName =
        actualHistoricDetailVariableInstanceUpdateEntityImpl.getVariableName();
    VariableType actualVariableType =
        actualHistoricDetailVariableInstanceUpdateEntityImpl.getVariableType();

    // Assert
    assertEquals("42", actualTextValue);
    assertEquals("42", actualTextValue2);
    assertEquals("Name", actualName);
    assertEquals("Name", actualVariableName);
    assertEquals(
        "VariableUpdate", actualHistoricDetailVariableInstanceUpdateEntityImpl.getDetailType());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getId());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getActivityInstanceId());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getExecutionId());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getTaskId());
    assertNull(actualHistoricDetailVariableInstanceUpdateEntityImpl.getTime());
    assertNull(actualByteArrayRef);
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
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BooleanType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue3() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new DoubleType());

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@link JPAEntityListVariableType} (default constructor) ForceCacheable is {@code
   *       false}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue_givenJPAEntityListVariableTypeForceCacheableIsFalse_thenReturnNull() {
    // Arrange
    JPAEntityListVariableType variableType = new JPAEntityListVariableType();
    variableType.setForceCacheable(false);

    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(variableType);
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue_givenJavaLangObject_thenReturnNull() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    Class<Object> theClass = Object.class;
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(
        new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue_thenReturnFalse() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BooleanType());

    // Act and Assert
    assertFalse((Boolean) historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricDetailVariableInstanceUpdateEntityImpl.getValue()"})
  public void testGetValue_thenReturnTrue() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(1L);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BooleanType());

    // Act and Assert
    assertTrue((Boolean) historicDetailVariableInstanceUpdateEntityImpl.getValue());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}.
   *
   * <ul>
   *   <li>Then return {@code bigdecimal}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.getVariableTypeName()"})
  public void testGetVariableTypeName_thenReturnBigdecimal() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals(
        "bigdecimal", historicDetailVariableInstanceUpdateEntityImpl.getVariableTypeName());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricDetailVariableInstanceUpdateEntityImpl#getVariableTypeName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.getVariableTypeName()"})
  public void testGetVariableTypeName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailVariableInstanceUpdateEntityImpl().getVariableTypeName());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getRevisionNext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int HistoricDetailVariableInstanceUpdateEntityImpl.getRevisionNext()"})
  public void testGetRevisionNext() {
    // Arrange, Act and Assert
    assertEquals(1, new HistoricDetailVariableInstanceUpdateEntityImpl().getRevisionNext());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] HistoricDetailVariableInstanceUpdateEntityImpl.getBytes()"})
  public void testGetBytes() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertNull(historicDetailVariableInstanceUpdateEntityImpl.getBytes());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}.
   *
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#getBytes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] HistoricDetailVariableInstanceUpdateEntityImpl.getBytes()"})
  public void testGetBytes_givenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailVariableInstanceUpdateEntityImpl().getBytes());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#setBytes(byte[])}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#setBytes(byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailVariableInstanceUpdateEntityImpl.setBytes(byte[])"})
  public void testSetBytes() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Assert
    ByteArrayRef byteArrayRef = historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef();
    assertEquals("hist.detail.var-Name", byteArrayRef.getName());
    assertNull(byteArrayRef.getBytes());
    assertNull(byteArrayRef.getId());
    assertNull(byteArrayRef.getEntity());
    assertFalse(byteArrayRef.isDeleted());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#setBytes(byte[])}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#setBytes(byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailVariableInstanceUpdateEntityImpl.setBytes(byte[])"})
  public void testSetBytes2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Assert that nothing has changed
    ByteArrayRef byteArrayRef = historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef();
    assertEquals("hist.detail.var-Name", byteArrayRef.getName());
    assertFalse(byteArrayRef.isDeleted());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals(
        "HistoricDetailVariableInstanceUpdateEntity[id=null, name=null, type=null]",
        new HistoricDetailVariableInstanceUpdateEntityImpl().toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.toString()"})
  public void testToString2() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(1L);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("not empty");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("not empty");
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=bigdecimal, longValue=1, doubleValue=10.0,"
            + " textValue=not empty, textValue2=not empty]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.toString()"})
  public void testToString3() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(1L);
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("not empty");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("");
    historicDetailVariableInstanceUpdateEntityImpl.setBytes(null);

    // Act and Assert
    assertEquals(
        "HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=bigdecimal, longValue=1, doubleValue=10.0,"
            + " textValue=not empty, textValue2=]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.toString()"})
  public void testToString4() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue(
        "HistoricDetailVariableInstanceUpdateEntity[");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals(
        "HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=bigdecimal, longValue=42, doubleValue=10.0,"
            + " textValue=HistoricDetailVariableInstanceUpdateE..., textValue2=42]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }

  /**
   * Test {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Given {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       TextValue is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailVariableInstanceUpdateEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailVariableInstanceUpdateEntityImpl.toString()"})
  public void testToString_givenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
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
    historicDetailVariableInstanceUpdateEntityImpl.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals(
        "HistoricDetailVariableInstanceUpdateEntity[id=42, name=Name, type=bigdecimal, longValue=42, doubleValue=10.0,"
            + " textValue=42, textValue2=42]",
        historicDetailVariableInstanceUpdateEntityImpl.toString());
  }
}
