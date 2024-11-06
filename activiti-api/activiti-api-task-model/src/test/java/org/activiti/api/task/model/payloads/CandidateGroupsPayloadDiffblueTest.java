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
package org.activiti.api.task.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CandidateGroupsPayloadDiffblueTest {
  /**
   * Test {@link CandidateGroupsPayload#CandidateGroupsPayload()}.
   * <p>
   * Method under test: {@link CandidateGroupsPayload#CandidateGroupsPayload()}
   */
  @Test
  @DisplayName("Test new CandidateGroupsPayload()")
  void testNewCandidateGroupsPayload() {
    // Arrange and Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload();

    // Assert
    assertNull(actualCandidateGroupsPayload.getTaskId());
    assertNull(actualCandidateGroupsPayload.getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateGroupsPayload(String, List); given '42'; when ArrayList() add '42'")
  void testNewCandidateGroupsPayload_given42_whenArrayListAdd42() {
    // Arrange
    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", candidateGroups);

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    assertSame(candidateGroups, actualCandidateGroupsPayload.getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return CandidateGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateGroupsPayload(String, List); given 'foo'; then return CandidateGroups is ArrayList()")
  void testNewCandidateGroupsPayload_givenFoo_thenReturnCandidateGroupsIsArrayList() {
    // Arrange
    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", candidateGroups);

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    assertSame(candidateGroups, actualCandidateGroupsPayload.getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return CandidateGroups Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateGroupsPayload(String, List); when ArrayList(); then return CandidateGroups Empty")
  void testNewCandidateGroupsPayload_whenArrayList_thenReturnCandidateGroupsEmpty() {
    // Arrange and Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", new ArrayList<>());

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    assertTrue(actualCandidateGroupsPayload.getCandidateGroups().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateGroupsPayload#setCandidateGroups(List)}
   *   <li>{@link CandidateGroupsPayload#setTaskId(String)}
   *   <li>{@link CandidateGroupsPayload#getCandidateGroups()}
   *   <li>{@link CandidateGroupsPayload#getId()}
   *   <li>{@link CandidateGroupsPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    candidateGroupsPayload.setCandidateGroups(candidateGroups);
    candidateGroupsPayload.setTaskId("42");
    List<String> actualCandidateGroups = candidateGroupsPayload.getCandidateGroups();
    candidateGroupsPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", candidateGroupsPayload.getTaskId());
    assertTrue(actualCandidateGroups.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
  }
}
