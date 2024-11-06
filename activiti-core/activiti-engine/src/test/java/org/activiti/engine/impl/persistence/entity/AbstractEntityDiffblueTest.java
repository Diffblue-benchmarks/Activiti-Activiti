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
   * Test {@link AbstractEntity#getId()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getId()}
   */
  @Test
  public void testGetId_givenAttachmentEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new AttachmentEntityImpl()).getId());
  }

  /**
   * Test {@link AbstractEntity#getId()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getId()}
   */
  @Test
  public void testGetId_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertNull(attachmentEntityImpl.getId());
  }

  /**
   * Test {@link AbstractEntity#setId(String)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setId(String)}
   */
  @Test
  public void testSetId_givenAttachmentEntityImpl() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl2 = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", attachmentEntityImpl2.getId());
  }

  /**
   * Test {@link AbstractEntity#setId(String)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntityImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setId(String)}
   */
  @Test
  public void testSetId_givenAttachmentEntityImplContentIsByteArrayEntityImpl() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl2 = new AttachmentEntityImpl();
    attachmentEntityImpl2.setContent(mock(ByteArrayEntityImpl.class));

    // Act
    attachmentEntityImpl2.setId("42");

    // Assert
    assertEquals("42", attachmentEntityImpl2.getId());
  }

  /**
   * Test {@link AbstractEntity#getRevisionNext()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext_givenAttachmentEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(2, (new AttachmentEntityImpl()).getRevisionNext());
  }

  /**
   * Test {@link AbstractEntity#getRevisionNext()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getRevisionNext()}
   */
  @Test
  public void testGetRevisionNext_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertEquals(2, attachmentEntityImpl.getRevisionNext());
  }

  /**
   * Test {@link AbstractEntity#getRevision()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getRevision()}
   */
  @Test
  public void testGetRevision_givenAttachmentEntityImpl() {
    // Arrange, Act and Assert
    assertEquals(1, (new AttachmentEntityImpl()).getRevision());
  }

  /**
   * Test {@link AbstractEntity#getRevision()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#getRevision()}
   */
  @Test
  public void testGetRevision_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertEquals(1, attachmentEntityImpl.getRevision());
  }

  /**
   * Test {@link AbstractEntity#isInserted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
  public void testIsInserted_givenAttachmentEntityImplContentIsByteArrayEntity_thenReturnFalse() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntity#isInserted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Inserted is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isInserted()}
   */
  @Test
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
  public void testIsInserted_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isInserted());
  }

  /**
   * Test {@link AbstractEntity#setInserted(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setInserted(boolean)}
   */
  @Test
  public void testSetInserted_givenAttachmentEntityImpl() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setInserted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntity#setInserted(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setInserted(boolean)}
   */
  @Test
  public void testSetInserted_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setInserted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isInserted());
  }

  /**
   * Test {@link AbstractEntity#isUpdated()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
  public void testIsUpdated_givenAttachmentEntityImplContentIsByteArrayEntity_thenReturnFalse() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntity#isUpdated()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Updated is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isUpdated()}
   */
  @Test
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
  public void testIsUpdated_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isUpdated());
  }

  /**
   * Test {@link AbstractEntity#setUpdated(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated_givenAttachmentEntityImpl() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntity#setUpdated(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setUpdated(boolean)}
   */
  @Test
  public void testSetUpdated_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setUpdated(true);

    // Assert
    assertTrue(attachmentEntityImpl.isUpdated());
  }

  /**
   * Test {@link AbstractEntity#isDeleted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
  public void testIsDeleted_givenAttachmentEntityImplContentIsByteArrayEntity_thenReturnFalse() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act and Assert
    assertFalse(attachmentEntityImpl.isDeleted());
  }

  /**
   * Test {@link AbstractEntity#isDeleted()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Deleted is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#isDeleted()}
   */
  @Test
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
  public void testIsDeleted_givenAttachmentEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AttachmentEntityImpl()).isDeleted());
  }

  /**
   * Test {@link AbstractEntity#setDeleted(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted_givenAttachmentEntityImpl() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();

    // Act
    attachmentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }

  /**
   * Test {@link AbstractEntity#setDeleted(boolean)}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntity#setDeleted(boolean)}
   */
  @Test
  public void testSetDeleted_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    attachmentEntityImpl.setDeleted(true);

    // Assert
    assertTrue(attachmentEntityImpl.isDeleted());
  }
}
