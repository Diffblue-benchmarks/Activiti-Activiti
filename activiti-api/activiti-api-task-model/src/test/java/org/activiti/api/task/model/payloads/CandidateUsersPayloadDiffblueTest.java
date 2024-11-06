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

class CandidateUsersPayloadDiffblueTest {
  /**
   * Test {@link CandidateUsersPayload#CandidateUsersPayload()}.
   * <p>
   * Method under test: {@link CandidateUsersPayload#CandidateUsersPayload()}
   */
  @Test
  @DisplayName("Test new CandidateUsersPayload()")
  void testNewCandidateUsersPayload() {
    // Arrange and Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload();

    // Assert
    assertNull(actualCandidateUsersPayload.getTaskId());
    assertNull(actualCandidateUsersPayload.getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateUsersPayload(String, List); given '42'; when ArrayList() add '42'")
  void testNewCandidateUsersPayload_given42_whenArrayListAdd42() {
    // Arrange
    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    assertSame(candidateUsers, actualCandidateUsersPayload.getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return CandidateUsers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateUsersPayload(String, List); given 'foo'; then return CandidateUsers is ArrayList()")
  void testNewCandidateUsersPayload_givenFoo_thenReturnCandidateUsersIsArrayList() {
    // Arrange
    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    assertSame(candidateUsers, actualCandidateUsersPayload.getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return CandidateUsers Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  @DisplayName("Test new CandidateUsersPayload(String, List); when ArrayList(); then return CandidateUsers Empty")
  void testNewCandidateUsersPayload_whenArrayList_thenReturnCandidateUsersEmpty() {
    // Arrange and Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", new ArrayList<>());

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    assertTrue(actualCandidateUsersPayload.getCandidateUsers().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateUsersPayload#setCandidateUsers(List)}
   *   <li>{@link CandidateUsersPayload#setTaskId(String)}
   *   <li>{@link CandidateUsersPayload#getCandidateUsers()}
   *   <li>{@link CandidateUsersPayload#getId()}
   *   <li>{@link CandidateUsersPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    candidateUsersPayload.setCandidateUsers(candidateUsers);
    candidateUsersPayload.setTaskId("42");
    List<String> actualCandidateUsers = candidateUsersPayload.getCandidateUsers();
    candidateUsersPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", candidateUsersPayload.getTaskId());
    assertTrue(actualCandidateUsers.isEmpty());
    assertSame(candidateUsers, actualCandidateUsers);
  }
}
