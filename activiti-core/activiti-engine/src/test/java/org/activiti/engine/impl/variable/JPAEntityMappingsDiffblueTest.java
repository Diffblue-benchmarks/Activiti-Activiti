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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JPAEntityMappingsDiffblueTest {
  /**
   * Test {@link JPAEntityMappings#isJPAEntity(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#isJPAEntity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityMappings.isJPAEntity(Object)"})
  public void testIsJPAEntity_whenNull() {
    // Arrange, Act and Assert
    assertFalse(new JPAEntityMappings().isJPAEntity(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityMappings#isJPAEntity(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#isJPAEntity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityMappings.isJPAEntity(Object)"})
  public void testIsJPAEntity_whenNull2() {
    // Arrange, Act and Assert
    assertFalse(new JPAEntityMappings().isJPAEntity(null));
  }

  /**
   * Test {@link JPAEntityMappings#getEntityMetaData(Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return EntityClass is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EntityMetaData JPAEntityMappings.getEntityMetaData(Class)"})
  public void testGetEntityMetaData_whenJavaLangByte_thenReturnEntityClassIsNull() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    Class<Byte> clazz = Byte.class;

    // Act
    EntityMetaData actualEntityMetaData = jpaEntityMappings.getEntityMetaData(clazz);

    // Assert
    assertNull(actualEntityMetaData.getEntityClass());
    assertNull(actualEntityMetaData.getIdType());
    assertNull(actualEntityMetaData.getIdField());
    assertNull(actualEntityMetaData.getIdMethod());
    assertFalse(actualEntityMetaData.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityMappings#getEntityMetaData(Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return EntityClass is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EntityMetaData JPAEntityMappings.getEntityMetaData(Class)"})
  public void testGetEntityMetaData_whenJavaLangObject_thenReturnEntityClassIsNull() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    Class<Object> clazz = Object.class;

    // Act
    EntityMetaData actualEntityMetaData = jpaEntityMappings.getEntityMetaData(clazz);

    // Assert
    assertNull(actualEntityMetaData.getEntityClass());
    assertNull(actualEntityMetaData.getIdType());
    assertNull(actualEntityMetaData.getIdField());
    assertNull(actualEntityMetaData.getIdMethod());
    assertFalse(actualEntityMetaData.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityMappings#getJPAClassString(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getJPAClassString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getJPAClassString(Object)"})
  public void testGetJPAClassString_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new JPAEntityMappings().getJPAClassString(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityMappings#getJPAClassString(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getJPAClassString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getJPAClassString(Object)"})
  public void testGetJPAClassString_whenNull2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new JPAEntityMappings().getJPAClassString(null));
  }

  /**
   * Test {@link JPAEntityMappings#getJPAIdString(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getJPAIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getJPAIdString(Object)"})
  public void testGetJPAIdString_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new JPAEntityMappings().getJPAIdString(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityMappings#getIdValue(Object, EntityMetaData)}.
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdValue(Object, EntityMetaData)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityMappings.getIdValue(Object, EntityMetaData)"})
  public void testGetIdValue() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();

    EntityMetaData metaData = new EntityMetaData();
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> jpaEntityMappings.getIdValue(JSONObject.NULL, metaData));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>Then return {@code 0}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_thenReturn0() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();

    // Act and Assert
    assertEquals(
        "0",
        jpaEntityMappings.getIdString(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", new JPAEntityMappings().getIdString("42"));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return {@code 65}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenA_thenReturn65() {
    // Arrange, Act and Assert
    assertEquals("65", new JPAEntityMappings().getIdString((byte) 'A'));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenA_thenReturnA() {
    // Arrange, Act and Assert
    assertEquals("A", new JPAEntityMappings().getIdString('A'));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", new JPAEntityMappings().getIdString(42L));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenFortyTwo_thenReturn422() {
    // Arrange, Act and Assert
    assertEquals("42", new JPAEntityMappings().getIdString(42));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new JPAEntityMappings().getIdString(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new JPAEntityMappings().getIdString(null));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenOne_thenReturn1() {
    // Arrange, Act and Assert
    assertEquals("1", new JPAEntityMappings().getIdString((short) 1));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenTen_thenReturn100() {
    // Arrange, Act and Assert
    assertEquals("10.0", new JPAEntityMappings().getIdString(10.0f));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JPAEntityMappings.getIdString(Object)"})
  public void testGetIdString_whenTen_thenReturn1002() {
    // Arrange, Act and Assert
    assertEquals("10.0", new JPAEntityMappings().getIdString(10.0d));
  }
}
