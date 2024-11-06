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
package org.activiti.engine.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class BpmnErrorDiffblueTest {
  /**
   * Test {@link BpmnError#BpmnError(String, String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return ErrorCode is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#BpmnError(String, String)}
   */
  @Test
  public void testNewBpmnError_whenAnErrorOccurred_thenReturnErrorCodeIsAnErrorOccurred() {
    // Arrange and Act
    BpmnError actualBpmnError = new BpmnError("An error occurred", "Not all who wander are lost");

    // Assert
    assertEquals("An error occurred", actualBpmnError.getErrorCode());
    assertEquals("Not all who wander are lost", actualBpmnError.getLocalizedMessage());
    assertEquals("Not all who wander are lost", actualBpmnError.getMessage());
    assertNull(actualBpmnError.getCause());
    assertEquals(0, actualBpmnError.getSuppressed().length);
  }

  /**
   * Test {@link BpmnError#BpmnError(String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return LocalizedMessage is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  public void testNewBpmnError_whenAnErrorOccurred_thenReturnLocalizedMessageIsEmptyString() {
    // Arrange and Act
    BpmnError actualBpmnError = new BpmnError("An error occurred");

    // Assert
    assertEquals("", actualBpmnError.getLocalizedMessage());
    assertEquals("", actualBpmnError.getMessage());
    assertEquals("An error occurred", actualBpmnError.getErrorCode());
    assertNull(actualBpmnError.getCause());
    assertEquals(0, actualBpmnError.getSuppressed().length);
  }

  /**
   * Test {@link BpmnError#BpmnError(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  public void testNewBpmnError_whenEmptyString_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError(""));
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError("", "Not all who wander are lost"));
  }

  /**
   * Test {@link BpmnError#BpmnError(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  public void testNewBpmnError_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError(null));
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError(null, "Not all who wander are lost"));
  }

  /**
   * Test {@link BpmnError#setErrorCode(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#setErrorCode(String)}
   */
  @Test
  public void testSetErrorCode_whenEmptyString_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new BpmnError("An error occurred")).setErrorCode(""));
  }

  /**
   * Test {@link BpmnError#setErrorCode(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnError#setErrorCode(String)}
   */
  @Test
  public void testSetErrorCode_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new BpmnError("An error occurred")).setErrorCode(null));
  }

  /**
   * Test {@link BpmnError#getErrorCode()}.
   * <p>
   * Method under test: {@link BpmnError#getErrorCode()}
   */
  @Test
  public void testGetErrorCode() {
    // Arrange, Act and Assert
    assertEquals("An error occurred", (new BpmnError("An error occurred")).getErrorCode());
  }
}
