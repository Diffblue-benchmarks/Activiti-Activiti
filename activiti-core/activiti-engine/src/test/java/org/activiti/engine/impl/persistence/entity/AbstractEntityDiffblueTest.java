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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractEntityDiffblueTest {
  /**
   * Test {@link AbstractEntity#getId()}.
   * <p>
   * Method under test: {@link AbstractEntity#getId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractEntity.getId()"})
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new AttachmentEntityImpl()).getId());
  }

  /**
   * Test {@link AbstractEntity#setId(String)}.
   * <p>
   * Method under test: {@link AbstractEntity#setId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntity.setId(String)"})
  public void testSetId() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setId("42");

    // Assert
    assertEquals("42", attachmentEntityImpl.getId());
  }

  /**
   * Test {@link AbstractEntity#getRevisionNext()}.
   * <p>
   * Method under test: {@link AbstractEntity#getRevisionNext()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int AbstractEntity.getRevisionNext()"})
  public void testGetRevisionNext() {
    // Arrange, Act and Assert
    assertEquals(2, (new AttachmentEntityImpl()).getRevisionNext());
  }

  /**
   * Test {@link AbstractEntity#getRevision()}.
   * <p>
   * Method under test: {@link AbstractEntity#getRevision()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int AbstractEntity.getRevision()"})
  public void testGetRevision() {
    // Arrange, Act and Assert
    assertEquals(1, (new AttachmentEntityImpl()).getRevision());
  }

  /**
   * Test {@link AbstractEntity#isInserted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Inserted is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isInserted()"})
  public void testIsInserted_givenAttachmentEntityImplInsertedIsTrue_thenReturnTrue() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setInserted(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntity#isInserted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isInserted()"})
  public void testIsInserted_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isInserted());
  }

  /**
   * Test {@link AbstractEntity#setInserted(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntity#setInserted(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntity.setInserted(boolean)"})
  public void testSetInserted() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setInserted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntity#isUpdated()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Updated is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isUpdated()"})
  public void testIsUpdated_givenAttachmentEntityImplUpdatedIsTrue_thenReturnTrue() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setUpdated(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntity#isUpdated()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isUpdated()"})
  public void testIsUpdated_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isUpdated());
  }

  /**
   * Test {@link AbstractEntity#setUpdated(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntity#setUpdated(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntity.setUpdated(boolean)"})
  public void testSetUpdated() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntity#isDeleted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Deleted is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isDeleted()"})
  public void testIsDeleted_givenAttachmentEntityImplDeletedIsTrue_thenReturnTrue() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setDeleted(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }

  /**
   * Test {@link AbstractEntity#isDeleted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntity.isDeleted()"})
  public void testIsDeleted_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isDeleted());
  }

  /**
   * Test {@link AbstractEntity#setDeleted(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntity#setDeleted(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntity.setDeleted(boolean)"})
  public void testSetDeleted() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }
}
