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

public class AbstractEntityNoRevisionDiffblueTest {
  /**
   * Test {@link AbstractEntityNoRevision#getId()}.
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#getId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractEntityNoRevision.getId()"})
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new CommentEntityImpl()).getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setId(String)}.
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntityNoRevision.setId(String)"})
  public void testSetId() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setId("42");

    // Assert
    assertEquals("42", commentEntityImpl.getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isInserted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Inserted is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isInserted()"})
  public void testIsInserted_givenCommentEntityImplInsertedIsTrue_thenReturnTrue() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setInserted(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isInserted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isInserted()"})
  public void testIsInserted_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setInserted(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setInserted(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntityNoRevision.setInserted(boolean)"})
  public void testSetInserted() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setInserted(true);

    // Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isUpdated()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Updated is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isUpdated()"})
  public void testIsUpdated_givenCommentEntityImplUpdatedIsTrue_thenReturnTrue() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setUpdated(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isUpdated()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isUpdated()"})
  public void testIsUpdated_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setUpdated(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setUpdated(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntityNoRevision.setUpdated(boolean)"})
  public void testSetUpdated() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isDeleted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Deleted is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isDeleted()"})
  public void testIsDeleted_givenCommentEntityImplDeletedIsTrue_thenReturnTrue() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setDeleted(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isDeleted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isDeleted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEntityNoRevision.isDeleted()"})
  public void testIsDeleted_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isDeleted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setDeleted(boolean)}.
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setDeleted(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEntityNoRevision.setDeleted(boolean)"})
  public void testSetDeleted() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(commentEntityImpl.isDeleted());
  }
}
