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
import java.util.List;
import javax.script.ScriptEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JuelScriptEngineFactoryDiffblueTest {
  @InjectMocks
  private JuelScriptEngineFactory juelScriptEngineFactory;

  /**
   * Method under test:
   * {@link JuelScriptEngineFactory#getMethodCallSyntax(String, String, String[])}
   */
  @Test
  public void testGetMethodCallSyntax() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> juelScriptEngineFactory.getMethodCallSyntax("Obj", "Method", "Arguments"));
  }

  /**
   * Method under test: {@link JuelScriptEngineFactory#getOutputStatement(String)}
   */
  @Test
  public void testGetOutputStatement() {
    // Arrange, Act and Assert
    assertEquals("out:print(\"To Display\")", juelScriptEngineFactory.getOutputStatement("To Display"));
    assertEquals("out:print(\"out:print(\\\"\")", juelScriptEngineFactory.getOutputStatement("out:print(\""));
  }

  /**
   * Method under test: {@link JuelScriptEngineFactory#getParameter(String)}
   */
  @Test
  public void testGetParameter() {
    // Arrange, Act and Assert
    assertNull(juelScriptEngineFactory.getParameter("Key"));
    assertEquals("JSP 2.1 EL", juelScriptEngineFactory.getParameter("javax.script.name"));
    assertEquals(ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE,
        juelScriptEngineFactory.getParameter("javax.script.engine"));
    assertEquals("1.0", juelScriptEngineFactory.getParameter("javax.script.engine_version"));
    assertEquals("JSP 2.1 EL", juelScriptEngineFactory.getParameter("javax.script.language"));
    assertEquals("2.1", juelScriptEngineFactory.getParameter("javax.script.language_version"));
    assertEquals("MULTITHREADED", juelScriptEngineFactory.getParameter("THREADING"));
  }

  /**
   * Method under test: {@link JuelScriptEngineFactory#getProgram(String[])}
   */
  @Test
  public void testGetProgram() {
    // Arrange, Act and Assert
    assertEquals("${MD} ", (new JuelScriptEngineFactory()).getProgram("MD"));
    assertEquals("", (new JuelScriptEngineFactory()).getProgram());
  }

  /**
   * Method under test: {@link JuelScriptEngineFactory#getScriptEngine()}
   */
  @Test
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
   * Methods under test:
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
