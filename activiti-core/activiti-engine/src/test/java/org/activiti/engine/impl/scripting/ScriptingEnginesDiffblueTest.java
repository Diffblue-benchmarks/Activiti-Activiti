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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.util.ArrayList;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ScriptingEnginesDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ScriptingEngines#ScriptingEngines(ScriptEngineManager)}
   *   <li>{@link ScriptingEngines#setCacheScriptingEngines(boolean)}
   *   <li>{@link ScriptingEngines#setScriptBindingsFactory(ScriptBindingsFactory)}
   *   <li>{@link ScriptingEngines#getScriptBindingsFactory()}
   *   <li>{@link ScriptingEngines#isCacheScriptingEngines()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ScriptingEngines.<init>(ScriptEngineManager)",
    "ScriptBindingsFactory ScriptingEngines.getScriptBindingsFactory()",
    "boolean ScriptingEngines.isCacheScriptingEngines()",
    "void ScriptingEngines.setCacheScriptingEngines(boolean)",
    "void ScriptingEngines.setScriptBindingsFactory(ScriptBindingsFactory)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    actualScriptingEngines.setCacheScriptingEngines(true);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());
    actualScriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);
    ScriptBindingsFactory actualScriptBindingsFactory =
        actualScriptingEngines.getScriptBindingsFactory();
    boolean actualIsCacheScriptingEnginesResult = actualScriptingEngines.isCacheScriptingEngines();

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualIsCacheScriptingEnginesResult);
    assertSame(scriptBindingsFactory, actualScriptBindingsFactory);
  }

  /**
   * Test {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}.
   *
   * <p>Method under test: {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptingEngines.<init>(ScriptBindingsFactory)"})
  public void testNewScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());

    // Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualScriptingEngines.isCacheScriptingEngines());
    assertSame(scriptBindingsFactory, actualScriptingEngines.getScriptBindingsFactory());
  }

  /**
   * Test {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}.
   *
   * <p>Method under test: {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ScriptingEngines ScriptingEngines.addScriptEngineFactory(ScriptEngineFactory)"
  })
  public void testAddScriptEngineFactory() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act
    ScriptingEngines actualAddScriptEngineFactoryResult =
        scriptingEngines.addScriptEngineFactory(new JuelScriptEngineFactory());

    // Assert
    assertSame(scriptingEngines, actualAddScriptEngineFactoryResult);
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, Bindings)} with {@code script}, {@code
   * language}, {@code bindings}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object ScriptingEngines.evaluate(String, String, Bindings)"})
  public void testEvaluateWithScriptLanguageBindings() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", new SimpleBindings()));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, Bindings)} with {@code script}, {@code
   * language}, {@code bindings}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object ScriptingEngines.evaluate(String, String, Bindings)"})
  public void testEvaluateWithScriptLanguageBindings2() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setCacheScriptingEngines(false);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", new SimpleBindings()));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope)} with {@code script},
   * {@code language}, {@code variableScope}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope)"})
  public void testEvaluateWithScriptLanguageVariableScope() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());
    ScriptingEngines scriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Act
    scriptingEngines.evaluate(
        "Script",
        ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE,
        NoExecutionVariableScope.getSharedInstance());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        scriptingEngines.getScriptBindingsFactory().processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, ScriptEngine> stringScriptEngineMap = scriptingEngines.cachedEngines;
    assertEquals(1, stringScriptEngineMap.size());
    ScriptEngine getResult = stringScriptEngineMap.get(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE);
    ScriptEngineFactory factory = getResult.getFactory();
    assertTrue(factory instanceof GroovyScriptEngineFactory);
    assertTrue(getResult instanceof GroovyScriptEngineImpl);
    assertTrue(factory.getScriptEngine() instanceof GroovyScriptEngineImpl);
    assertEquals("2.0", factory.getEngineVersion());
    assertEquals("3.0.19", factory.getLanguageVersion());
    assertEquals("Groovy Scripting Engine", factory.getEngineName());
    assertEquals("Groovy", factory.getLanguageName());
    assertNotNull(((GroovyScriptEngineImpl) getResult).getClassLoader());
    assertEquals(1, factory.getExtensions().size());
    assertEquals(1, factory.getMimeTypes().size());
    assertEquals(2, factory.getNames().size());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)} with {@code
   * script}, {@code language}, {@code variableScope}, {@code storeScriptVariables}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope, boolean)"
  })
  public void testEvaluateWithScriptLanguageVariableScopeStoreScriptVariables() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new ScriptingEngines(scriptBindingsFactory)
                .evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)} with {@code
   * script}, {@code language}, {@code variableScope}, {@code storeScriptVariables}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope, boolean)"
  })
  public void testEvaluateWithScriptLanguageVariableScopeStoreScriptVariables2() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(
            Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new ScriptingEngines(scriptBindingsFactory)
                .evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
    verify(resolverFactory)
        .createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)} with {@code
   * script}, {@code language}, {@code variableScope}, {@code storeScriptVariables}.
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope, boolean)"
  })
  public void testEvaluateWithScriptLanguageVariableScopeStoreScriptVariables3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());
    ScriptingEngines scriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Act
    scriptingEngines.evaluate(
        "Script",
        ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE,
        NoExecutionVariableScope.getSharedInstance(),
        true);

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        scriptingEngines.getScriptBindingsFactory().processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, ScriptEngine> stringScriptEngineMap = scriptingEngines.cachedEngines;
    assertEquals(1, stringScriptEngineMap.size());
    ScriptEngine getResult = stringScriptEngineMap.get(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE);
    ScriptEngineFactory factory = getResult.getFactory();
    assertTrue(factory instanceof GroovyScriptEngineFactory);
    assertTrue(getResult instanceof GroovyScriptEngineImpl);
    assertTrue(factory.getScriptEngine() instanceof GroovyScriptEngineImpl);
    assertEquals("2.0", factory.getEngineVersion());
    assertEquals("3.0.19", factory.getLanguageVersion());
    assertEquals("Groovy Scripting Engine", factory.getEngineName());
    assertEquals("Groovy", factory.getLanguageName());
    assertNotNull(((GroovyScriptEngineImpl) getResult).getClassLoader());
    assertEquals(1, factory.getExtensions().size());
    assertEquals(1, factory.getMimeTypes().size());
    assertEquals(2, factory.getNames().size());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope)} with {@code script},
   * {@code language}, {@code variableScope}.
   *
   * <ul>
   *   <li>Then calls {@link ResolverFactory#createResolver(ProcessEngineConfigurationImpl,
   *       VariableScope)}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope)"})
  public void testEvaluateWithScriptLanguageVariableScope_thenCallsCreateResolver() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(
            Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new ScriptingEngines(scriptBindingsFactory)
                .evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
    verify(resolverFactory)
        .createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope)} with {@code script},
   * {@code language}, {@code variableScope}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object ScriptingEngines.evaluate(String, String, VariableScope)"})
  public void testEvaluateWithScriptLanguageVariableScope_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory =
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new ScriptingEngines(scriptBindingsFactory)
                .evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link ScriptingEngines#getEngineByName(String)}.
   *
   * <p>Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptEngine ScriptingEngines.getEngineByName(String)"})
  public void testGetEngineByName() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new ScriptingEngines(new ScriptEngineManager()).getEngineByName("en"));
  }

  /**
   * Test {@link ScriptingEngines#getEngineByName(String)}.
   *
   * <p>Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptEngine ScriptingEngines.getEngineByName(String)"})
  public void testGetEngineByName2() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setCacheScriptingEngines(false);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.getEngineByName("en"));
  }

  /**
   * Test {@link ScriptingEngines#getEngineByName(String)}.
   *
   * <ul>
   *   <li>Then Factory return {@link GroovyScriptEngineFactory}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptEngine ScriptingEngines.getEngineByName(String)"})
  public void testGetEngineByName_thenFactoryReturnGroovyScriptEngineFactory() {
    // Arrange
    ScriptingEngines scriptingEngines =
        new ScriptingEngines(new ScriptEngineManager(new GroovyClassLoader()));

    // Act
    ScriptEngine actualEngineByName =
        scriptingEngines.getEngineByName(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE);

    // Assert
    ScriptEngineFactory factory = actualEngineByName.getFactory();
    assertTrue(factory instanceof GroovyScriptEngineFactory);
    assertTrue(factory.getScriptEngine() instanceof GroovyScriptEngineImpl);
    assertTrue(actualEngineByName instanceof GroovyScriptEngineImpl);
    assertEquals("2.0", factory.getEngineVersion());
    assertEquals("3.0.19", factory.getLanguageVersion());
    assertEquals("Groovy Scripting Engine", factory.getEngineName());
    assertEquals("Groovy", factory.getLanguageName());
    assertNotNull(((GroovyScriptEngineImpl) actualEngineByName).getClassLoader());
    assertEquals(1, factory.getExtensions().size());
    assertEquals(1, factory.getMimeTypes().size());
    Map<String, ScriptEngine> stringScriptEngineMap = scriptingEngines.cachedEngines;
    assertEquals(1, stringScriptEngineMap.size());
    assertEquals(2, factory.getNames().size());
    assertTrue(stringScriptEngineMap.containsKey(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE));
  }
}
