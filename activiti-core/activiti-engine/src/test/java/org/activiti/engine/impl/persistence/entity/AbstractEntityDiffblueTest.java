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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEntityDiffblueTest {
  @InjectMocks
  private AttachmentEntityImpl attachmentEntityImpl;

  /**
   * Method under test: {@link AbstractEntity#getId()}
   */
  @Test
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new AttachmentEntityImpl()).getId());
  }

  /**
   * Method under test: {@link AbstractEntity#getId()}
   */
  @Test
  public void testGetId2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertNull(attachmentEntityImpl.getId());
  }

  /**
   * Method under test: {@link AbstractEntity#setId(String)}
   */
  @Test
  public void testSetId() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl2 = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", attachmentEntityImpl2.getId());
  }

  /**
   * Method under test: {@link AbstractEntity#setId(String)}
   */
  @Test
  public void testSetId2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl2 = new AttachmentEntityImpl();
    attachmentEntityImpl2.setContent(mock(ByteArrayEntityImpl.class));

    // Act
    attachmentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", attachmentEntityImpl2.getId());
  }

  /**
   * Method under test: {@link AbstractEntity#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext() {
    // Arrange, Act and Assert
    assertEquals(2, (new AttachmentEntityImpl()).getRevisionNext());
  }

  /**
   * Method under test: {@link AbstractEntity#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertEquals(2, attachmentEntityImpl.getRevisionNext());
  }

  /**
   * Method under test: {@link AbstractEntity#getRevision()}
   */
  @Test
  public void testGetRevision() {
    // Arrange, Act and Assert
    assertEquals(1, (new AttachmentEntityImpl()).getRevision());
  }

  /**
   * Method under test: {@link AbstractEntity#getRevision()}
   */
  @Test
  public void testGetRevision2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertEquals(1, attachmentEntityImpl.getRevision());
  }

  /**
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  public void testIsInserted() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isInserted());
  }

  /**
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  public void testIsInserted2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setInserted(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  public void testIsInserted3() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntity#setInserted(boolean)}
   */
  @Test
  public void testSetInserted() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setInserted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntity#setInserted(boolean)}
   */
  @Test
  public void testSetInserted2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setInserted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  public void testIsUpdated() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  public void testIsUpdated2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setUpdated(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  public void testIsUpdated3() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntity#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntity#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  public void testIsDeleted() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  public void testIsDeleted2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setDeleted(true);

    // Act and Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  public void testIsDeleted3() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntity#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }

  /**
   * Method under test: {@link AbstractEntity#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted2() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }
}
