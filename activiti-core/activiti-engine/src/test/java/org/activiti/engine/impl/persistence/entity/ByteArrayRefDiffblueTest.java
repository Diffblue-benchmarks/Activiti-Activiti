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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ByteArrayRefDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef()}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayRef.<init>()",
    "void ByteArrayRef.<init>(String)",
    "String ByteArrayRef.getId()",
    "String ByteArrayRef.getName()",
    "boolean ByteArrayRef.isDeleted()"
  })
  public void testGettersAndSetters_thenReturnIdIsNull() {
    // Arrange and Act
    ByteArrayRef actualByteArrayRef = new ByteArrayRef();
    String actualId = actualByteArrayRef.getId();
    String actualName = actualByteArrayRef.getName();

    // Assert
    assertNull(actualId);
    assertNull(actualName);
    assertFalse(actualByteArrayRef.isDeleted());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef(String)}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayRef.<init>()",
    "void ByteArrayRef.<init>(String)",
    "String ByteArrayRef.getId()",
    "String ByteArrayRef.getName()",
    "boolean ByteArrayRef.isDeleted()"
  })
  public void testGettersAndSetters_when42_thenReturnIdIs42() {
    // Arrange and Act
    ByteArrayRef actualByteArrayRef = new ByteArrayRef("42");
    String actualId = actualByteArrayRef.getId();
    String actualName = actualByteArrayRef.getName();

    // Assert
    assertEquals("42", actualId);
    assertNull(actualName);
    assertFalse(actualByteArrayRef.isDeleted());
  }

  /**
   * Test {@link ByteArrayRef#getBytes()}.
   *
   * <p>Method under test: {@link ByteArrayRef#getBytes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] ByteArrayRef.getBytes()"})
  public void testGetBytes() {
    // Arrange, Act and Assert
    assertNull(new ByteArrayRef().getBytes());
  }

  /**
   * Test {@link ByteArrayRef#setValue(String, byte[])}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.
   *   <li>When {@code null}.
   *   <li>Then {@link ByteArrayRef#ByteArrayRef()} Name is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRef#setValue(String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ByteArrayRef.setValue(String, byte[])"})
  public void testSetValue_givenByteArrayRef_whenNull_thenByteArrayRefNameIsName() {
    // Arrange
    ByteArrayRef byteArrayRef = new ByteArrayRef();

    // Act
    byteArrayRef.setValue("Name", null);

    // Assert
    assertEquals("Name", byteArrayRef.getName());
  }

  /**
   * Test {@link ByteArrayRef#getEntity()}.
   *
   * <p>Method under test: {@link ByteArrayRef#getEntity()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ByteArrayEntity ByteArrayRef.getEntity()"
  })
  public void testGetEntity() {
    // Arrange, Act and Assert
    assertNull(new ByteArrayRef().getEntity());
  }

  /**
   * Test {@link ByteArrayRef#toString()}.
   *
   * <p>Method under test: {@link ByteArrayRef#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ByteArrayRef.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("ByteArrayRef[id=42, name=null, entity=null]", new ByteArrayRef("42").toString());
  }
}
