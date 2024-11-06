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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ByteArrayRefDiffblueTest {
  @InjectMocks
  private ByteArrayRef byteArrayRef;

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef()}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
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
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef(String)}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ByteArrayRef#getBytes()}
   */
  @Test
  public void testGetBytes_givenByteArrayRef_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ByteArrayRef()).getBytes());
  }

  /**
   * Test {@link ByteArrayRef#setValue(String, byte[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ByteArrayRef} Name is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ByteArrayRef#setValue(String, byte[])}
   */
  @Test
  public void testSetValue_whenNull_thenByteArrayRefNameIsName() {
    // Arrange and Act
    byteArrayRef.setValue("Name", null);

    // Assert
    assertEquals("Name", byteArrayRef.getName());
  }

  /**
   * Test {@link ByteArrayRef#getEntity()}.
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ByteArrayRef#getEntity()}
   */
  @Test
  public void testGetEntity_givenByteArrayRef_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ByteArrayRef()).getEntity());
  }

  /**
   * Test {@link ByteArrayRef#toString()}.
   * <p>
   * Method under test: {@link ByteArrayRef#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("ByteArrayRef[id=42, name=null, entity=null]", (new ByteArrayRef("42")).toString());
  }
}
