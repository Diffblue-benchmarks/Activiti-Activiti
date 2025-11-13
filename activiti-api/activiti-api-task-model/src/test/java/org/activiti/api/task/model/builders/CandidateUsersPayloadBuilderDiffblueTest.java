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
package org.activiti.api.task.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.CandidateUsersPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CandidateUsersPayloadBuilderDiffblueTest {
  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withCandidateUsers(List)"
  })
  void testWithCandidateUsers_given42_whenArrayListAdd42() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult =
        addCandidateUsersResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withCandidateUsers(List)"
  })
  void testWithCandidateUsers_givenFoo_whenArrayListAddFoo() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult =
        addCandidateUsersResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withCandidateUsers(List)"
  })
  void testWithCandidateUsers_whenArrayList() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult =
        addCandidateUsersResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withCandidateUsers(List)"
  })
  void testWithCandidateUsers_whenNull() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult =
        addCandidateUsersResult.withCandidateUsers(null);

    // Assert
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUser(String)}.
   *
   * <p>Method under test: {@link CandidateUsersPayloadBuilder#withCandidateUser(String)}
   */
  @Test
  @DisplayName("Test withCandidateUser(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withCandidateUser(String)"
  })
  void testWithCandidateUser() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUserResult =
        addCandidateUsersResult.withCandidateUser("2020-03-01");

    // Assert
    assertSame(addCandidateUsersResult, actualWithCandidateUserResult);
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CandidateUsersPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link CandidateUsersPayloadBuilder}
   *   <li>{@link CandidateUsersPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CandidateUsersPayloadBuilder.<init>()",
    "CandidateUsersPayload CandidateUsersPayloadBuilder.build()",
    "CandidateUsersPayloadBuilder CandidateUsersPayloadBuilder.withTaskId(String)"
  })
  void testBuild() {
    // Arrange and Act
    CandidateUsersPayloadBuilder actualCandidateUsersPayloadBuilder =
        new CandidateUsersPayloadBuilder();
    ArrayList<String> candidateUsers = new ArrayList<>();
    CandidateUsersPayload actualCandidateUsersPayload =
        actualCandidateUsersPayloadBuilder
            .withCandidateUsers(candidateUsers)
            .withTaskId("42")
            .build();

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    List<String> candidateUsers2 = actualCandidateUsersPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateUsers, candidateUsers2);
  }
}
