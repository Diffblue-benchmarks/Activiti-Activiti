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
package org.activiti.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class ApplicationContentDiffblueTest {
  @InjectMocks private ApplicationContent applicationContent;

  @Mock private Map<String, List<FileContent>> map;

  /**
   * Test {@link ApplicationContent#add(ApplicationEntry)}.
   *
   * <ul>
   *   <li>Then calls {@link Map#computeIfAbsent(Object, Function)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContent#add(ApplicationEntry)}
   */
  @Test
  @DisplayName("Test add(ApplicationEntry); then calls computeIfAbsent(Object, Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationContent.add(ApplicationEntry)"})
  void testAdd_thenCallsComputeIfAbsent() throws UnsupportedEncodingException {
    // Arrange
    when(map.computeIfAbsent(
            Mockito.<String>any(), Mockito.<Function<String, List<FileContent>>>any()))
        .thenReturn(new ArrayList<>());
    FileContent fileContent = new FileContent("Name", "AXAXAXAX".getBytes("UTF-8"));
    ApplicationEntry entry = new ApplicationEntry("Type", fileContent);

    // Act
    applicationContent.add(entry);

    // Assert
    verify(map).computeIfAbsent(eq("Type"), isA(Function.class));
  }

  /**
   * Test {@link ApplicationContent#getFileContents(String)}.
   *
   * <p>Method under test: {@link ApplicationContent#getFileContents(String)}
   */
  @Test
  @DisplayName("Test getFileContents(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationContent.getFileContents(String)"})
  void testGetFileContents() {
    // Arrange, Act and Assert
    assertTrue(new ApplicationContent().getFileContents("Entry Type").isEmpty());
  }
}
