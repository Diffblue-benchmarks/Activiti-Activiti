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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BpmnErrorDiffblueTest {
  /**
   * Test {@link BpmnError#BpmnError(String, String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return ErrorCode is {@code An error occurred}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String, String)"})
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
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return LocalizedMessage is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String)"})
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
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String)"})
  public void testNewBpmnError_whenEmptyString_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError(""));
  }

  /**
   * Test {@link BpmnError#BpmnError(String, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String, String)"})
  public void testNewBpmnError_whenEmptyString_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new BpmnError("", "Not all who wander are lost"));
  }

  /**
   * Test {@link BpmnError#BpmnError(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String)"})
  public void testNewBpmnError_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new BpmnError(null));
  }

  /**
   * Test {@link BpmnError#BpmnError(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#BpmnError(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.<init>(String, String)"})
  public void testNewBpmnError_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new BpmnError(null, "Not all who wander are lost"));
  }

  /**
   * Test {@link BpmnError#setErrorCode(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#setErrorCode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.setErrorCode(String)"})
  public void testSetErrorCode_whenEmptyString_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new BpmnError("An error occurred").setErrorCode(""));
  }

  /**
   * Test {@link BpmnError#setErrorCode(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnError#setErrorCode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnError.setErrorCode(String)"})
  public void testSetErrorCode_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new BpmnError("An error occurred").setErrorCode(null));
  }

  /**
   * Test {@link BpmnError#getErrorCode()}.
   *
   * <p>Method under test: {@link BpmnError#getErrorCode()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnError.getErrorCode()"})
  public void testGetErrorCode() {
    // Arrange, Act and Assert
    assertEquals("An error occurred", new BpmnError("An error occurred").getErrorCode());
  }
}
