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
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEntityNoRevisionDiffblueTest {
  @InjectMocks
  private CommentEntityImpl commentEntityImpl;

  /**
   * Test {@link AbstractEntityNoRevision#getId()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#getId()}
   */
  @Test
  public void testGetId_givenCommentEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new CommentEntityImpl()).getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#getId()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#getId()}
   */
  @Test
  public void testGetId_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(commentEntityImpl.getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setId(String)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setId(String)}
   */
  @Test
  public void testSetId_givenCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl2 = new CommentEntityImpl();

    // Act
    commentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", commentEntityImpl2.getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setId(String)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setId(String)}
   */
  @Test
  public void testSetId_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl2 = new CommentEntityImpl();
    commentEntityImpl2.setTime(mock(Date.class));

    // Act
    commentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", commentEntityImpl2.getId());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isInserted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Inserted is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
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
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  public void testIsInserted_givenCommentEntityImplTimeIsDate_thenReturnFalse() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isInserted());
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
  public void testIsInserted_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setInserted(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setInserted(boolean)}
   */
  @Test
  public void testSetInserted_givenCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setInserted(true);

    // Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setInserted(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setInserted(boolean)}
   */
  @Test
  public void testSetInserted_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setInserted(true);

    // Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isUpdated()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  public void testIsUpdated_givenCommentEntityImplTimeIsDate_thenReturnFalse() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isUpdated()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Updated is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
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
  public void testIsUpdated_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setUpdated(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated_givenCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setUpdated(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntityNoRevision#isDeleted()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Deleted is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
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
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  public void testIsDeleted_givenCommentEntityImplTimeIsDate_thenReturnFalse() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isDeleted());
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
  public void testIsDeleted_givenCommentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isDeleted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setDeleted(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted_givenCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(commentEntityImpl.isDeleted());
  }

  /**
   * Test {@link AbstractEntityNoRevision#setDeleted(boolean)}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityNoRevision#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(commentEntityImpl.isDeleted());
  }
}
