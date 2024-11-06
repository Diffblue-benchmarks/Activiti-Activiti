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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

public class AttachmentEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AttachmentEntityImpl}
   *   <li>{@link AttachmentEntityImpl#setContent(ByteArrayEntity)}
   *   <li>{@link AttachmentEntityImpl#setContentId(String)}
   *   <li>{@link AttachmentEntityImpl#setDescription(String)}
   *   <li>{@link AttachmentEntityImpl#setName(String)}
   *   <li>{@link AttachmentEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link AttachmentEntityImpl#setTaskId(String)}
   *   <li>{@link AttachmentEntityImpl#setTime(Date)}
   *   <li>{@link AttachmentEntityImpl#setType(String)}
   *   <li>{@link AttachmentEntityImpl#setUrl(String)}
   *   <li>{@link AttachmentEntityImpl#setUserId(String)}
   *   <li>{@link AttachmentEntityImpl#getContent()}
   *   <li>{@link AttachmentEntityImpl#getContentId()}
   *   <li>{@link AttachmentEntityImpl#getDescription()}
   *   <li>{@link AttachmentEntityImpl#getName()}
   *   <li>{@link AttachmentEntityImpl#getProcessInstanceId()}
   *   <li>{@link AttachmentEntityImpl#getTaskId()}
   *   <li>{@link AttachmentEntityImpl#getTime()}
   *   <li>{@link AttachmentEntityImpl#getType()}
   *   <li>{@link AttachmentEntityImpl#getUrl()}
   *   <li>{@link AttachmentEntityImpl#getUserId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    AttachmentEntityImpl actualAttachmentEntityImpl = new AttachmentEntityImpl();
    ByteArrayEntityImpl content = new ByteArrayEntityImpl();
    actualAttachmentEntityImpl.setContent(content);
    actualAttachmentEntityImpl.setContentId("42");
    actualAttachmentEntityImpl.setDescription("The characteristics of someone or something");
    actualAttachmentEntityImpl.setName("Name");
    actualAttachmentEntityImpl.setProcessInstanceId("42");
    actualAttachmentEntityImpl.setTaskId("42");
    Date time = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualAttachmentEntityImpl.setTime(time);
    actualAttachmentEntityImpl.setType("Type");
    actualAttachmentEntityImpl.setUrl("https://example.org/example");
    actualAttachmentEntityImpl.setUserId("42");
    ByteArrayEntity actualContent = actualAttachmentEntityImpl.getContent();
    String actualContentId = actualAttachmentEntityImpl.getContentId();
    String actualDescription = actualAttachmentEntityImpl.getDescription();
    String actualName = actualAttachmentEntityImpl.getName();
    String actualProcessInstanceId = actualAttachmentEntityImpl.getProcessInstanceId();
    String actualTaskId = actualAttachmentEntityImpl.getTaskId();
    Date actualTime = actualAttachmentEntityImpl.getTime();
    String actualType = actualAttachmentEntityImpl.getType();
    String actualUrl = actualAttachmentEntityImpl.getUrl();

    // Assert that nothing has changed
    assertEquals("42", actualContentId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualAttachmentEntityImpl.getUserId());
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals("Type", actualType);
    assertEquals("https://example.org/example", actualUrl);
    assertEquals(1, actualAttachmentEntityImpl.getRevision());
    assertFalse(actualAttachmentEntityImpl.isDeleted());
    assertFalse(actualAttachmentEntityImpl.isInserted());
    assertFalse(actualAttachmentEntityImpl.isUpdated());
    assertSame(content, actualContent);
    assertSame(time, actualTime);
  }

  /**
   * Test {@link AttachmentEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AttachmentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenAttachmentEntityImpl() {
    // Arrange and Act
    Object actualPersistentState = (new AttachmentEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
  }

  /**
   * Test {@link AttachmentEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link AttachmentEntityImpl} (default constructor) Content is
   * {@link ByteArrayEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AttachmentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenAttachmentEntityImplContentIsByteArrayEntity() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    attachmentEntityImpl.setContent(mock(ByteArrayEntity.class));

    // Act
    Object actualPersistentState = attachmentEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
  }
}
