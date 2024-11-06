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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import org.junit.Test;

public class CommentEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CommentEntityImpl}
   *   <li>{@link CommentEntityImpl#setAction(String)}
   *   <li>{@link CommentEntityImpl#setFullMessage(String)}
   *   <li>{@link CommentEntityImpl#setMessage(String)}
   *   <li>{@link CommentEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link CommentEntityImpl#setTaskId(String)}
   *   <li>{@link CommentEntityImpl#setTime(Date)}
   *   <li>{@link CommentEntityImpl#setType(String)}
   *   <li>{@link CommentEntityImpl#setUserId(String)}
   *   <li>{@link CommentEntityImpl#getAction()}
   *   <li>{@link CommentEntityImpl#getFullMessage()}
   *   <li>{@link CommentEntityImpl#getMessage()}
   *   <li>{@link CommentEntityImpl#getPersistentState()}
   *   <li>{@link CommentEntityImpl#getProcessInstanceId()}
   *   <li>{@link CommentEntityImpl#getTaskId()}
   *   <li>{@link CommentEntityImpl#getTime()}
   *   <li>{@link CommentEntityImpl#getType()}
   *   <li>{@link CommentEntityImpl#getUserId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CommentEntityImpl actualCommentEntityImpl = new CommentEntityImpl();
    actualCommentEntityImpl.setAction("Action");
    actualCommentEntityImpl.setFullMessage("Full Message");
    actualCommentEntityImpl.setMessage("Not all who wander are lost");
    actualCommentEntityImpl.setProcessInstanceId("42");
    actualCommentEntityImpl.setTaskId("42");
    Date time = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualCommentEntityImpl.setTime(time);
    actualCommentEntityImpl.setType("Type");
    actualCommentEntityImpl.setUserId("42");
    String actualAction = actualCommentEntityImpl.getAction();
    String actualFullMessage = actualCommentEntityImpl.getFullMessage();
    String actualMessage = actualCommentEntityImpl.getMessage();
    actualCommentEntityImpl.getPersistentState();
    String actualProcessInstanceId = actualCommentEntityImpl.getProcessInstanceId();
    String actualTaskId = actualCommentEntityImpl.getTaskId();
    Date actualTime = actualCommentEntityImpl.getTime();
    String actualType = actualCommentEntityImpl.getType();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualCommentEntityImpl.getUserId());
    assertEquals("Action", actualAction);
    assertEquals("Full Message", actualFullMessage);
    assertEquals("Not all who wander are lost", actualMessage);
    assertEquals("Type", actualType);
    assertFalse(actualCommentEntityImpl.isDeleted());
    assertFalse(actualCommentEntityImpl.isInserted());
    assertFalse(actualCommentEntityImpl.isUpdated());
    assertSame(time, actualTime);
  }

  /**
   * Test {@link CommentEntityImpl#getFullMessageBytes()}.
   * <p>
   * Method under test: {@link CommentEntityImpl#getFullMessageBytes()}
   */
  @Test
  public void testGetFullMessageBytes() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setMessage("Not all who wander are lost");
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualFullMessageBytes = commentEntityImpl.getFullMessageBytes();

    // Assert
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualFullMessageBytes);
  }

  /**
   * Test {@link CommentEntityImpl#getFullMessageBytes()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getFullMessageBytes()}
   */
  @Test
  public void testGetFullMessageBytes_givenCommentEntityImplTimeIsDate() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setMessage("Not all who wander are lost");
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(mock(java.sql.Date.class));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualFullMessageBytes = commentEntityImpl.getFullMessageBytes();

    // Assert
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualFullMessageBytes);
  }

  /**
   * Test {@link CommentEntityImpl#getFullMessageBytes()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getFullMessageBytes()}
   */
  @Test
  public void testGetFullMessageBytes_givenCommentEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new CommentEntityImpl()).getFullMessageBytes());
  }

  /**
   * Test {@link CommentEntityImpl#setFullMessageBytes(byte[])}.
   * <p>
   * Method under test: {@link CommentEntityImpl#setFullMessageBytes(byte[])}
   */
  @Test
  public void testSetFullMessageBytes() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setMessage("Not all who wander are lost");
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");

    // Act
    commentEntityImpl.setFullMessageBytes(null);

    // Assert
    assertNull(commentEntityImpl.getFullMessageBytes());
    assertNull(commentEntityImpl.getFullMessage());
  }

  /**
   * Test {@link CommentEntityImpl#setFullMessageBytes(byte[])}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#setFullMessageBytes(byte[])}
   */
  @Test
  public void testSetFullMessageBytes_givenCommentEntityImplTimeIsDate() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setMessage("Not all who wander are lost");
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(mock(java.sql.Date.class));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");

    // Act
    commentEntityImpl.setFullMessageBytes(null);

    // Assert
    assertNull(commentEntityImpl.getFullMessageBytes());
    assertNull(commentEntityImpl.getFullMessage());
  }

  /**
   * Test {@link CommentEntityImpl#setFullMessageBytes(byte[])}.
   * <ul>
   *   <li>Then {@link CommentEntityImpl} (default constructor) FullMessage is
   * {@code AXAXAXAX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#setFullMessageBytes(byte[])}
   */
  @Test
  public void testSetFullMessageBytes_thenCommentEntityImplFullMessageIsAxaxaxax() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("AXAXAXAX", commentEntityImpl.getFullMessage());
    byte[] expectedFullMessageBytes = "AXAXAXAX".getBytes("UTF-8");
    assertArrayEquals(expectedFullMessageBytes, commentEntityImpl.getFullMessageBytes());
  }

  /**
   * Test {@link CommentEntityImpl#setMessage(String[])} with
   * {@code messageParts}.
   * <p>
   * Method under test: {@link CommentEntityImpl#setMessage(String[])}
   */
  @Test
  public void testSetMessageWithMessageParts() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setMessage(new String[]{"Message Parts"});

    // Assert
    List<String> messageParts = commentEntityImpl.getMessageParts();
    assertEquals(1, messageParts.size());
    assertEquals("Message Parts", messageParts.get(0));
    assertEquals("Message Parts", commentEntityImpl.getMessage());
  }

  /**
   * Test {@link CommentEntityImpl#setMessage(String[])} with
   * {@code messageParts}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#setMessage(String[])}
   */
  @Test
  public void testSetMessageWithMessageParts_givenCommentEntityImplTimeIsDate() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setTime(mock(java.sql.Date.class));

    // Act
    commentEntityImpl.setMessage(new String[]{"Message Parts"});

    // Assert
    List<String> messageParts = commentEntityImpl.getMessageParts();
    assertEquals(1, messageParts.size());
    assertEquals("Message Parts", messageParts.get(0));
    assertEquals("Message Parts", commentEntityImpl.getMessage());
  }

  /**
   * Test {@link CommentEntityImpl#setMessage(String[])} with
   * {@code messageParts}.
   * <ul>
   *   <li>Then {@link CommentEntityImpl} (default constructor) Message is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#setMessage(String[])}
   */
  @Test
  public void testSetMessageWithMessageParts_thenCommentEntityImplMessageIsNull() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();

    // Act
    commentEntityImpl.setMessage(new String[]{null});

    // Assert
    assertEquals("null", commentEntityImpl.getMessage());
    List<String> messageParts = commentEntityImpl.getMessageParts();
    assertEquals(1, messageParts.size());
    assertNull(messageParts.get(0));
  }

  /**
   * Test {@link CommentEntityImpl#getMessageParts()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor) Time is
   * {@link Date}.</li>
   *   <li>Then return first is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getMessageParts()}
   */
  @Test
  public void testGetMessageParts_givenCommentEntityImplTimeIsDate_thenReturnFirstIsFoo()
      throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(mock(java.sql.Date.class));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");
    commentEntityImpl.setMessage(new String[]{"foo"});

    // Act
    List<String> actualMessageParts = commentEntityImpl.getMessageParts();

    // Assert
    assertEquals(1, actualMessageParts.size());
    assertEquals("foo", actualMessageParts.get(0));
  }

  /**
   * Test {@link CommentEntityImpl#getMessageParts()}.
   * <ul>
   *   <li>Given {@link CommentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getMessageParts()}
   */
  @Test
  public void testGetMessageParts_givenCommentEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new CommentEntityImpl()).getMessageParts());
  }

  /**
   * Test {@link CommentEntityImpl#getMessageParts()}.
   * <ul>
   *   <li>Then return first is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getMessageParts()}
   */
  @Test
  public void testGetMessageParts_thenReturnFirstIsFoo() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");
    commentEntityImpl.setMessage(new String[]{"foo"});

    // Act
    List<String> actualMessageParts = commentEntityImpl.getMessageParts();

    // Assert
    assertEquals(1, actualMessageParts.size());
    assertEquals("foo", actualMessageParts.get(0));
  }

  /**
   * Test {@link CommentEntityImpl#getMessageParts()}.
   * <ul>
   *   <li>Then return first is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityImpl#getMessageParts()}
   */
  @Test
  public void testGetMessageParts_thenReturnFirstIsNull() throws UnsupportedEncodingException {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    commentEntityImpl.setAction("Action");
    commentEntityImpl.setDeleted(true);
    commentEntityImpl.setFullMessage("Full Message");
    commentEntityImpl.setFullMessageBytes("AXAXAXAX".getBytes("UTF-8"));
    commentEntityImpl.setId("42");
    commentEntityImpl.setInserted(true);
    commentEntityImpl.setProcessInstanceId("42");
    commentEntityImpl.setTaskId("42");
    commentEntityImpl.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    commentEntityImpl.setType("Type");
    commentEntityImpl.setUpdated(true);
    commentEntityImpl.setUserId("42");
    commentEntityImpl.setMessage(new String[]{"null"});

    // Act
    List<String> actualMessageParts = commentEntityImpl.getMessageParts();

    // Assert
    assertEquals(1, actualMessageParts.size());
    assertNull(actualMessageParts.get(0));
  }
}
