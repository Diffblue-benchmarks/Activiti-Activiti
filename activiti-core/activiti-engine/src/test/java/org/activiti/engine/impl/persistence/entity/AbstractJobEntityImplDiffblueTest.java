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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractJobEntityImplDiffblueTest {
  /**
   * Test {@link AbstractJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>Then return size is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object AbstractJobEntityImpl.getPersistentState()"})
  public void testGetPersistentState_givenDeadLetterJobEntityImpl_thenReturnSizeIsThree() {
    // Arrange and Act
    Object actualPersistentState = (new DeadLetterJobEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionMessage"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object AbstractJobEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnSizeIsFour() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExceptionStacktrace(null);

    // Act
    Object actualPersistentState = deadLetterJobEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(4, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionByteArrayId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionMessage"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getDuedate()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getDuedate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Date AbstractJobEntityImpl.getDuedate()"})
  public void testGetDuedate() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getDuedate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setDuedate(Date)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setDuedate(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setDuedate(Date)"})
  public void testSetDuedate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    deadLetterJobEntityImpl.setDuedate(duedate);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals(0, ((Integer) ((Map<String, Object>) persistentState).get("retries")).intValue());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
    assertSame(duedate, deadLetterJobEntityImpl.getDuedate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExecutionId()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExecutionId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getExecutionId()"})
  public void testGetExecutionId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExecutionId(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setExecutionId(String)"})
  public void testSetExecutionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setExecutionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl.getExecutionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRetries()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRetries()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int AbstractJobEntityImpl.getRetries()"})
  public void testGetRetries() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getRetries());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRetries(int)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRetries(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setRetries(int)"})
  public void testSetRetries() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setRetries(1);

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertEquals(1, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(1, deadLetterJobEntityImpl.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#getProcessInstanceId()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessInstanceId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getProcessInstanceId()"})
  public void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setProcessInstanceId(String)"})
  public void testSetProcessInstanceId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#isExclusive()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Exclusive is {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractJobEntityImpl.isExclusive()"})
  public void testIsExclusive_givenDeadLetterJobEntityImplExclusiveIsFalse_thenReturnFalse() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExclusive(false);

    // Act and Assert
    assertFalse(deadLetterJobEntityImpl.isExclusive());
  }

  /**
   * Test {@link AbstractJobEntityImpl#isExclusive()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#isExclusive()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractJobEntityImpl.isExclusive()"})
  public void testIsExclusive_givenDeadLetterJobEntityImpl_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new DeadLetterJobEntityImpl()).isExclusive());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getProcessDefinitionId()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getProcessDefinitionId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getProcessDefinitionId()"})
  public void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setProcessDefinitionId(String)"})
  public void testSetProcessDefinitionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getRepeat()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getRepeat()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getRepeat()"})
  public void testGetRepeat() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setRepeat(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setRepeat(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setRepeat(String)"})
  public void testSetRepeat() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setRepeat("Repeat");

    // Assert
    assertEquals("Repeat", deadLetterJobEntityImpl.getRepeat());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getEndDate()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getEndDate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Date AbstractJobEntityImpl.getEndDate()"})
  public void testGetEndDate() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getEndDate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setEndDate(Date)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setEndDate(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setEndDate(Date)"})
  public void testSetEndDate() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    Date endDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    deadLetterJobEntityImpl.setEndDate(endDate);

    // Assert
    assertSame(endDate, deadLetterJobEntityImpl.getEndDate());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getMaxIterations()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getMaxIterations()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int AbstractJobEntityImpl.getMaxIterations()"})
  public void testGetMaxIterations() {
    // Arrange, Act and Assert
    assertEquals(0, (new DeadLetterJobEntityImpl()).getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setMaxIterations(int)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setMaxIterations(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setMaxIterations(int)"})
  public void testSetMaxIterations() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setMaxIterations(3);

    // Assert
    assertEquals(3, deadLetterJobEntityImpl.getMaxIterations());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerType()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getJobHandlerType()"})
  public void testGetJobHandlerType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerType(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerType(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setJobHandlerType(String)"})
  public void testSetJobHandlerType() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");

    // Assert
    assertEquals("Job Handler Type", deadLetterJobEntityImpl.getJobHandlerType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobHandlerConfiguration()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getJobHandlerConfiguration()"})
  public void testGetJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobHandlerConfiguration(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setJobHandlerConfiguration(String)"})
  public void testSetJobHandlerConfiguration() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");

    // Assert
    assertEquals("Job Handler Configuration", deadLetterJobEntityImpl.getJobHandlerConfiguration());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getJobType()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getJobType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getJobType()"})
  public void testGetJobType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setJobType(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setJobType(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setJobType(String)"})
  public void testSetJobType() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setJobType("Job Type");

    // Assert
    assertEquals("Job Type", deadLetterJobEntityImpl.getJobType());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getTenantId()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getTenantId()"})
  public void testGetTenantId() {
    // Arrange, Act and Assert
    assertEquals("", (new DeadLetterJobEntityImpl()).getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setTenantId(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setTenantId(String)"})
  public void testSetTenantId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobEntityImpl.getTenantId());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionStacktrace()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getExceptionStacktrace()"})
  public void testGetExceptionStacktrace_givenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionStacktrace());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionStacktrace()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) ExceptionStacktrace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionStacktrace()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getExceptionStacktrace()"})
  public void testGetExceptionStacktrace_givenDeadLetterJobEntityImplExceptionStacktraceIsNull() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setExceptionStacktrace(null);

    // Act and Assert
    assertNull(deadLetterJobEntityImpl.getExceptionStacktrace());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionMessage()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.getExceptionMessage()"})
  public void testGetExceptionMessage() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionMessage());
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setExceptionMessage(String)"})
  public void testSetExceptionMessage() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("An error occurred", deadLetterJobEntityImpl.getExceptionMessage());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals(0, ((Integer) ((Map<String, Object>) persistentState).get("retries")).intValue());
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) ExceptionMessage is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setExceptionMessage(String)"})
  public void testSetExceptionMessage_thenDeadLetterJobEntityImplExceptionMessageIsEmptyString() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setExceptionMessage("");

    // Assert
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("", deadLetterJobEntityImpl.getExceptionMessage());
    assertEquals(3, ((Map<String, Object>) persistentState).size());
    assertEquals("", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals(0, ((Integer) ((Map<String, Object>) persistentState).get("retries")).intValue());
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#setExceptionMessage(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#setExceptionMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractJobEntityImpl.setExceptionMessage(String)"})
  public void testSetExceptionMessage_whenNull() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityImpl.setExceptionMessage(null);

    // Assert that nothing has changed
    Object persistentState = deadLetterJobEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
  }

  /**
   * Test {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.persistence.entity.ByteArrayRef AbstractJobEntityImpl.getExceptionByteArrayRef()"})
  public void testGetExceptionByteArrayRef() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getExceptionByteArrayRef());
  }

  /**
   * Test {@link AbstractJobEntityImpl#getUtf8Bytes(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"byte[] AbstractJobEntityImpl.getUtf8Bytes(String)"})
  public void testGetUtf8Bytes_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobEntityImpl()).getUtf8Bytes(null));
  }

  /**
   * Test {@link AbstractJobEntityImpl#getUtf8Bytes(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code Str} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#getUtf8Bytes(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"byte[] AbstractJobEntityImpl.getUtf8Bytes(String)"})
  public void testGetUtf8Bytes_whenStr_thenReturnStrBytesIsUtf8() throws UnsupportedEncodingException {
    // Arrange and Act
    byte[] actualUtf8Bytes = (new DeadLetterJobEntityImpl()).getUtf8Bytes("Str");

    // Assert
    assertArrayEquals("Str".getBytes("UTF-8"), actualUtf8Bytes);
  }

  /**
   * Test {@link AbstractJobEntityImpl#toString()}.
   * <p>
   * Method under test: {@link AbstractJobEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractJobEntityImpl.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("DeadLetterJobEntity [id=null]", (new DeadLetterJobEntityImpl()).toString());
  }
}
