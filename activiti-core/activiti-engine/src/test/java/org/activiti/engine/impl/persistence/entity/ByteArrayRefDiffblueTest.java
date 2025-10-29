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
   * Method under test: {@link ByteArrayRef#getBytes()}
   */
  @Test
  public void testGetBytes() {
    // Arrange, Act and Assert
    assertNull((new ByteArrayRef()).getBytes());
  }

  /**
   * Method under test: {@link ByteArrayRef#setValue(String, byte[])}
   */
  @Test
  public void testSetValue() {
    // Arrange and Act
    byteArrayRef.setValue("Name", null);

    // Assert
    assertEquals("Name", byteArrayRef.getName());
  }

  /**
   * Method under test: {@link ByteArrayRef#getEntity()}
   */
  @Test
  public void testGetEntity() {
    // Arrange, Act and Assert
    assertNull((new ByteArrayRef()).getEntity());
  }

  /**
   * Method under test: {@link ByteArrayRef#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("ByteArrayRef[id=42, name=null, entity=null]", (new ByteArrayRef("42")).toString());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef()}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link ByteArrayRef#ByteArrayRef(String)}
   *   <li>{@link ByteArrayRef#getId()}
   *   <li>{@link ByteArrayRef#getName()}
   *   <li>{@link ByteArrayRef#isDeleted()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange and Act
    ByteArrayRef actualByteArrayRef = new ByteArrayRef("42");
    String actualId = actualByteArrayRef.getId();
    String actualName = actualByteArrayRef.getName();

    // Assert
    assertEquals("42", actualId);
    assertNull(actualName);
    assertFalse(actualByteArrayRef.isDeleted());
  }
}
