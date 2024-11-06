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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.Reader;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import org.junit.Test;

public class JuelScriptEngineDiffblueTest {
  /**
   * Test {@link JuelScriptEngine#JuelScriptEngine(ScriptEngineFactory)}.
   * <p>
   * Method under test:
   * {@link JuelScriptEngine#JuelScriptEngine(ScriptEngineFactory)}
   */
  @Test
  public void testNewJuelScriptEngine() {
    // Arrange
    JuelScriptEngineFactory scriptEngineFactory = new JuelScriptEngineFactory();

    // Act and Assert
    assertSame(scriptEngineFactory, (new JuelScriptEngine(scriptEngineFactory)).getFactory());
  }

  /**
   * Test {@link JuelScriptEngine#compile(Reader)} with {@code reader}.
   * <ul>
   *   <li>Then throw {@link ScriptException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelScriptEngine#compile(Reader)}
   */
  @Test
  public void testCompileWithReader_thenThrowScriptException() throws ScriptException {
    // Arrange
    JuelScriptEngine juelScriptEngine = new JuelScriptEngine(new JuelScriptEngineFactory());

    // Act and Assert
    assertThrows(ScriptException.class, () -> juelScriptEngine.compile(new FileReader(new FileDescriptor())));
  }

  /**
   * Test {@link JuelScriptEngine#createBindings()}.
   * <p>
   * Method under test: {@link JuelScriptEngine#createBindings()}
   */
  @Test
  public void testCreateBindings() {
    // Arrange, Act and Assert
    assertTrue((new JuelScriptEngine()).createBindings().isEmpty());
  }
}
