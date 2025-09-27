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
package org.activiti.engine.impl.scripting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import javax.script.ScriptEngine;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JuelScriptEngineFactoryDiffblueTest {
  /**
   * Test {@link JuelScriptEngineFactory#getMethodCallSyntax(String, String, String[])}.
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getMethodCallSyntax(String, String,
   * String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String JuelScriptEngineFactory.getMethodCallSyntax(String, String, String[])"
  })
  public void testGetMethodCallSyntax() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new JuelScriptEngineFactory().getMethodCallSyntax("Obj", "Method", "Arguments"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getOutputStatement(String)}.
   *
   * <ul>
   *   <li>When {@code out:print("}.
   *   <li>Then return {@code out:print("out:print(\"")}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getOutputStatement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getOutputStatement(String)"})
  public void testGetOutputStatement_whenOutPrint_thenReturnOutPrintOutPrint() {
    // Arrange, Act and Assert
    assertEquals(
        "out:print(\"out:print(\\\"\")",
        new JuelScriptEngineFactory().getOutputStatement("out:print(\""));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getOutputStatement(String)}.
   *
   * <ul>
   *   <li>When {@code To Display}.
   *   <li>Then return {@code out:print("To Display")}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getOutputStatement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getOutputStatement(String)"})
  public void testGetOutputStatement_whenToDisplay_thenReturnOutPrintToDisplay() {
    // Arrange, Act and Assert
    assertEquals(
        "out:print(\"To Display\")",
        new JuelScriptEngineFactory().getOutputStatement("To Display"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code javax.script.engine_version}.
   *   <li>Then return {@code 1.0}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenJavaxScriptEngineVersion_thenReturn10() {
    // Arrange, Act and Assert
    assertEquals("1.0", new JuelScriptEngineFactory().getParameter("javax.script.engine_version"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code javax.script.engine}.
   *   <li>Then return {@link ScriptingEngines#DEFAULT_SCRIPTING_LANGUAGE}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenJavaxScriptEngine_thenReturnDefault_scripting_language() {
    // Arrange, Act and Assert
    assertEquals(
        ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE,
        new JuelScriptEngineFactory().getParameter("javax.script.engine"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code javax.script.language_version}.
   *   <li>Then return {@code 2.1}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenJavaxScriptLanguageVersion_thenReturn21() {
    // Arrange, Act and Assert
    assertEquals(
        "2.1", new JuelScriptEngineFactory().getParameter("javax.script.language_version"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code javax.script.language}.
   *   <li>Then return {@code JSP 2.1 EL}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenJavaxScriptLanguage_thenReturnJsp21El() {
    // Arrange, Act and Assert
    assertEquals("JSP 2.1 EL", new JuelScriptEngineFactory().getParameter("javax.script.language"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code javax.script.name}.
   *   <li>Then return {@code JSP 2.1 EL}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenJavaxScriptName_thenReturnJsp21El() {
    // Arrange, Act and Assert
    assertEquals("JSP 2.1 EL", new JuelScriptEngineFactory().getParameter("javax.script.name"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JuelScriptEngineFactory().getParameter("Key"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getParameter(String)}.
   *
   * <ul>
   *   <li>When {@code THREADING}.
   *   <li>Then return {@code MULTITHREADED}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getParameter(String)"})
  public void testGetParameter_whenThreading_thenReturnMultithreaded() {
    // Arrange, Act and Assert
    assertEquals("MULTITHREADED", new JuelScriptEngineFactory().getParameter("THREADING"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getProgram(String[])}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getProgram(String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getProgram(String[])"})
  public void testGetProgram_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", new JuelScriptEngineFactory().getProgram());
  }

  /**
   * Test {@link JuelScriptEngineFactory#getProgram(String[])}.
   *
   * <ul>
   *   <li>When {@code MD}.
   *   <li>Then return {@code ${MD}}.
   * </ul>
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getProgram(String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JuelScriptEngineFactory.getProgram(String[])"})
  public void testGetProgram_whenMd_thenReturnMd() {
    // Arrange, Act and Assert
    assertEquals("${MD} ", new JuelScriptEngineFactory().getProgram("MD"));
  }

  /**
   * Test {@link JuelScriptEngineFactory#getScriptEngine()}.
   *
   * <p>Method under test: {@link JuelScriptEngineFactory#getScriptEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptEngine JuelScriptEngineFactory.getScriptEngine()"})
  public void testGetScriptEngine() {
    // Arrange
    JuelScriptEngineFactory juelScriptEngineFactory = new JuelScriptEngineFactory();

    // Act
    ScriptEngine actualScriptEngine = juelScriptEngineFactory.getScriptEngine();

    // Assert
    assertTrue(actualScriptEngine instanceof JuelScriptEngine);
    assertSame(juelScriptEngineFactory, actualScriptEngine.getFactory());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link JuelScriptEngineFactory}
   *   <li>{@link JuelScriptEngineFactory#getEngineName()}
   *   <li>{@link JuelScriptEngineFactory#getEngineVersion()}
   *   <li>{@link JuelScriptEngineFactory#getExtensions()}
   *   <li>{@link JuelScriptEngineFactory#getLanguageName()}
   *   <li>{@link JuelScriptEngineFactory#getLanguageVersion()}
   *   <li>{@link JuelScriptEngineFactory#getMimeTypes()}
   *   <li>{@link JuelScriptEngineFactory#getNames()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JuelScriptEngineFactory.<init>()",
    "String JuelScriptEngineFactory.getEngineName()",
    "String JuelScriptEngineFactory.getEngineVersion()",
    "List JuelScriptEngineFactory.getExtensions()",
    "String JuelScriptEngineFactory.getLanguageName()",
    "String JuelScriptEngineFactory.getLanguageVersion()",
    "List JuelScriptEngineFactory.getMimeTypes()",
    "List JuelScriptEngineFactory.getNames()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    JuelScriptEngineFactory actualJuelScriptEngineFactory = new JuelScriptEngineFactory();
    String actualEngineName = actualJuelScriptEngineFactory.getEngineName();
    String actualEngineVersion = actualJuelScriptEngineFactory.getEngineVersion();
    List<String> actualExtensions = actualJuelScriptEngineFactory.getExtensions();
    String actualLanguageName = actualJuelScriptEngineFactory.getLanguageName();
    String actualLanguageVersion = actualJuelScriptEngineFactory.getLanguageVersion();
    List<String> actualMimeTypes = actualJuelScriptEngineFactory.getMimeTypes();
    List<String> actualNames = actualJuelScriptEngineFactory.getNames();

    // Assert
    assertEquals("1.0", actualEngineVersion);
    assertEquals("2.1", actualLanguageVersion);
    assertEquals("JSP 2.1 EL", actualLanguageName);
    assertEquals(1, actualExtensions.size());
    assertTrue(actualMimeTypes.isEmpty());
    assertEquals(ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, actualExtensions.get(0));
    assertEquals(ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, actualEngineName);
    assertSame(actualExtensions, actualNames);
  }
}
