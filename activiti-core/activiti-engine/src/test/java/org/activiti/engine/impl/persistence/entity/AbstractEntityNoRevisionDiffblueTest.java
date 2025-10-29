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
   * Method under test: {@link AbstractEntityNoRevision#getId()}
   */
  @Test
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new CommentEntityImpl()).getId());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#getId()}
   */
  @Test
  public void testGetId2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(commentEntityImpl.getId());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setId(String)}
   */
  @Test
  public void testSetId() {
    // Arrange
    CommentEntityImpl commentEntityImpl2 = new CommentEntityImpl();

    // Act
    commentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", commentEntityImpl2.getId());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setId(String)}
   */
  @Test
  public void testSetId2() {
    // Arrange
    CommentEntityImpl commentEntityImpl2 = new CommentEntityImpl();
    commentEntityImpl2.setTime(mock(Date.class));

    // Act
    commentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", commentEntityImpl2.getId());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  public void testIsInserted() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isInserted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  public void testIsInserted2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setInserted(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isInserted()}
   */
  @Test
  public void testIsInserted3() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setInserted(boolean)}
   */
  @Test
  public void testSetInserted() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setInserted(true);

    // Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setInserted(boolean)}
   */
  @Test
  public void testSetInserted2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setInserted(true);

    // Assert
    assertTrue(commentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  public void testIsUpdated() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  public void testIsUpdated2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setUpdated(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isUpdated()}
   */
  @Test
  public void testIsUpdated3() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(commentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  public void testIsDeleted() {
    // Arrange, Act and Assert
    assertFalse((new CommentEntityImpl()).isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  public void testIsDeleted2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setDeleted(true);

    // Act and Assert
    assertTrue(commentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#isDeleted()}
   */
  @Test
  public void testIsDeleted3() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertFalse(commentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(commentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntityNoRevision#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted2() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(Date.class));

    // Act
    commentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(commentEntityImpl.isDeleted());
  }
}
