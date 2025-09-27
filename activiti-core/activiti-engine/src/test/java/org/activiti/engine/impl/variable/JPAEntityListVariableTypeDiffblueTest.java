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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JPAEntityListVariableTypeDiffblueTest {
  /**
   * Test new {@link JPAEntityListVariableType} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link JPAEntityListVariableType}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JPAEntityListVariableType.<init>()"})
  public void testNewJPAEntityListVariableType() {
    // Arrange and Act
    JPAEntityListVariableType actualJpaEntityListVariableType = new JPAEntityListVariableType();

    // Assert
    assertFalse(actualJpaEntityListVariableType.isCachable());
    assertEquals(
        JPAEntityListVariableType.TYPE_NAME, actualJpaEntityListVariableType.getTypeName());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JPAEntityListVariableType#setForceCacheable(boolean)}
   *   <li>{@link JPAEntityListVariableType#getTypeName()}
   *   <li>{@link JPAEntityListVariableType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String JPAEntityListVariableType.getTypeName()",
    "boolean JPAEntityListVariableType.isCachable()",
    "void JPAEntityListVariableType.setForceCacheable(boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act
    jpaEntityListVariableType.setForceCacheable(true);
    String actualTypeName = jpaEntityListVariableType.getTypeName();

    // Assert
    assertTrue(jpaEntityListVariableType.isCachable());
    assertEquals(JPAEntityListVariableType.TYPE_NAME, actualTypeName);
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityListVariableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenNull_whenArrayListAddNull_thenReturnFalse() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertFalse(jpaEntityListVariableType.isAbleToStore(objectList));
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityListVariableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenArrayList_thenReturnFalse() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertFalse(jpaEntityListVariableType.isAbleToStore(new ArrayList<>()));
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityListVariableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JPAEntityListVariableType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code AXAXAXAX} Bytes is {@code UTF-8}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityListVariableType.getValue(ValueFields)"})
  public void testGetValue_givenAxaxaxaxBytesIsUtf8_thenThrowActivitiException()
      throws UnsupportedEncodingException {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getTextValue()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityListVariableType.getValue(valueFields));
    verify(valueFields).getBytes();
    verify(valueFields, atLeast(1)).getTextValue();
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@link JPAEntityListVariableType} (default constructor) ForceCacheable is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityListVariableType.getValue(ValueFields)"})
  public void testGetValue_givenJPAEntityListVariableTypeForceCacheableIsTrue() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();
    jpaEntityListVariableType.setForceCacheable(true);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue2("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");

    // Act and Assert
    assertNull(jpaEntityListVariableType.getValue(valueFields));
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityListVariableType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertNull(
        jpaEntityListVariableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] JPAEntityListVariableType.serializeIds(List)"})
  public void testSerializeIds_given42_whenArrayListAdd42() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("42");
    ids.add("foo");

    // Act and Assert
    assertArrayEquals(
        new byte[] {
          -84, -19, 0, 5, 'u', 'r', 0, 19, '[', 'L', 'j', 'a', 'v', 'a', '.', 'l', 'a', 'n', 'g',
          '.', 'S', 't', 'r', 'i', 'n', 'g', ';', -83, -46, 'V', -25, -23, 29, '{', 'G', 2, 0, 0,
          'x', 'p', 0, 0, 0, 2, 't', 0, 2, '4', '2', 't', 0, 3, 'f', 'o', 'o'
        },
        jpaEntityListVariableType.serializeIds(ids));
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] JPAEntityListVariableType.serializeIds(List)"})
  public void testSerializeIds_givenFoo_whenArrayListAddFoo() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("foo");

    // Act and Assert
    assertArrayEquals(
        new byte[] {
          -84, -19, 0, 5, 'u', 'r', 0, 19, '[', 'L', 'j', 'a', 'v', 'a', '.', 'l', 'a', 'n', 'g',
          '.', 'S', 't', 'r', 'i', 'n', 'g', ';', -83, -46, 'V', -25, -23, 29, '{', 'G', 2, 0, 0,
          'x', 'p', 0, 0, 0, 1, 't', 0, 3, 'f', 'o', 'o'
        },
        jpaEntityListVariableType.serializeIds(ids));
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] JPAEntityListVariableType.serializeIds(List)"})
  public void testSerializeIds_whenArrayList() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertArrayEquals(
        new byte[] {
          -84, -19, 0, 5, 'u', 'r', 0, 19, '[', 'L', 'j', 'a', 'v', 'a', '.', 'l', 'a', 'n', 'g',
          '.', 'S', 't', 'r', 'i', 'n', 'g', ';', -83, -46, 'V', -25, -23, 29, '{', 'G', 2, 0, 0,
          'x', 'p', 0, 0, 0, 0
        },
        jpaEntityListVariableType.serializeIds(new ArrayList<>()));
  }

  /**
   * Test {@link JPAEntityListVariableType#deserializeIds(byte[])}.
   *
   * <p>Method under test: {@link JPAEntityListVariableType#deserializeIds(byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JPAEntityListVariableType.deserializeIds(byte[])"})
  public void testDeserializeIds() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new JPAEntityListVariableType().deserializeIds("AXAXAXAX".getBytes("UTF-8")));
  }
}
