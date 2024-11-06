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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import groovy.lang.GroovyClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScriptingEnginesDiffblueTest {
  @InjectMocks
  private ScriptingEngines scriptingEngines;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ScriptingEngines#ScriptingEngines(ScriptEngineManager)}
   *   <li>{@link ScriptingEngines#setCacheScriptingEngines(boolean)}
   *   <li>{@link ScriptingEngines#setScriptBindingsFactory(ScriptBindingsFactory)}
   *   <li>{@link ScriptingEngines#getScriptBindingsFactory()}
   *   <li>{@link ScriptingEngines#isCacheScriptingEngines()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    actualScriptingEngines.setCacheScriptingEngines(true);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    actualScriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);
    ScriptBindingsFactory actualScriptBindingsFactory = actualScriptingEngines.getScriptBindingsFactory();
    boolean actualIsCacheScriptingEnginesResult = actualScriptingEngines.isCacheScriptingEngines();

    // Assert that nothing has changed
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualIsCacheScriptingEnginesResult);
    assertSame(scriptBindingsFactory, actualScriptBindingsFactory);
  }

  /**
   * Test {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}
   */
  @Test
  public void testNewScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    // Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualScriptingEngines.isCacheScriptingEngines());
    assertSame(scriptBindingsFactory, actualScriptingEngines.getScriptBindingsFactory());
  }

  /**
   * Test {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}
   */
  @Test
  public void testNewScriptingEngines_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    // Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualScriptingEngines.isCacheScriptingEngines());
    assertSame(scriptBindingsFactory, actualScriptingEngines.getScriptBindingsFactory());
  }

  /**
   * Test {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}
   */
  @Test
  public void testAddScriptEngineFactory() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertSame(scriptingEngines, scriptingEngines.addScriptEngineFactory(new JuelScriptEngineFactory()));
  }

  /**
   * Test {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}
   */
  @Test
  public void testAddScriptEngineFactory2() throws MalformedURLException {
    // Arrange
    URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);
    when(urlStreamHandlerFactory.createURLStreamHandler(Mockito.<String>any())).thenReturn(null);
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager(
        new URLClassLoader(new URL[]{Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()},
            new GroovyClassLoader(), urlStreamHandlerFactory)));

    // Act
    ScriptingEngines actualAddScriptEngineFactoryResult = scriptingEngines
        .addScriptEngineFactory(new JuelScriptEngineFactory());

    // Assert
    verify(urlStreamHandlerFactory).createURLStreamHandler(eq("jar"));
    assertSame(scriptingEngines, actualAddScriptEngineFactoryResult);
  }

  /**
   * Test {@link ScriptingEngines#setScriptEngineFactories(List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link COWArrayList#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptingEngines#setScriptEngineFactories(List)}
   */
  @Test
  public void testSetScriptEngineFactories_givenArrayListIterator_thenCallsIterator() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    COWArrayList<ScriptEngineFactory> scriptEngineFactories = mock(COWArrayList.class);

    ArrayList<ScriptEngineFactory> scriptEngineFactoryList = new ArrayList<>();
    when(scriptEngineFactories.iterator()).thenReturn(scriptEngineFactoryList.iterator());

    // Act
    scriptingEngines.setScriptEngineFactories(scriptEngineFactories);

    // Assert that nothing has changed
    verify(scriptEngineFactories).iterator();
  }

  /**
   * Test {@link ScriptingEngines#setScriptEngineFactories(List)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptingEngines#setScriptEngineFactories(List)}
   */
  @Test
  public void testSetScriptEngineFactories_thenThrowActivitiException() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    COWArrayList<ScriptEngineFactory> scriptEngineFactories = mock(COWArrayList.class);
    when(scriptEngineFactories.iterator()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.setScriptEngineFactories(scriptEngineFactories));
    verify(scriptEngineFactories).iterator();
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, Bindings)} with
   * {@code script}, {@code language}, {@code bindings}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  public void testEvaluateWithScriptLanguageBindings() {
    // Arrange and Act
    scriptingEngines.evaluate("Script", ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, new SimpleBindings());

    // Assert
    Map<String, ScriptEngine> stringScriptEngineMap = scriptingEngines.cachedEngines;
    assertEquals(1, stringScriptEngineMap.size());
    ScriptEngine getResult = stringScriptEngineMap.get(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE);
    ScriptEngineFactory factory = getResult.getFactory();
    assertTrue(factory instanceof GroovyScriptEngineFactory);
    assertTrue(getResult instanceof GroovyScriptEngineImpl);
    ScriptEngine scriptEngine = factory.getScriptEngine();
    assertTrue(scriptEngine instanceof GroovyScriptEngineImpl);
    assertEquals("2.0", factory.getEngineVersion());
    assertEquals("3.0.19", factory.getLanguageVersion());
    assertEquals("Groovy Scripting Engine", factory.getEngineName());
    List<String> names = factory.getNames();
    assertEquals(2, names.size());
    assertEquals("Groovy", names.get(1));
    assertEquals("Groovy", factory.getLanguageName());
    List<String> mimeTypes = factory.getMimeTypes();
    assertEquals(1, mimeTypes.size());
    assertEquals("application/x-groovy", mimeTypes.get(0));
    assertNotNull(((GroovyScriptEngineImpl) getResult).getClassLoader());
    assertNotNull(((GroovyScriptEngineImpl) scriptEngine).getClassLoader());
    List<String> extensions = factory.getExtensions();
    assertEquals(1, extensions.size());
    assertEquals(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, extensions.get(0));
    assertEquals(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, names.get(0));
    assertSame(factory, scriptEngine.getFactory());
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, Bindings)} with
   * {@code script}, {@code language}, {@code bindings}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  public void testEvaluateWithScriptLanguageBindings_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.evaluate(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE,
        ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, new SimpleBindings()));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, Bindings)} with
   * {@code script}, {@code language}, {@code bindings}.
   * <ul>
   *   <li>When {@code en}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  public void testEvaluateWithScriptLanguageBindings_whenEn_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.evaluate("Script", "en", new SimpleBindings()));
  }

  /**
   * Test
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   * with {@code script}, {@code language}, {@code variableScope},
   * {@code storeScriptVariables}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  public void testEvaluateWithScriptLanguageVariableScopeStoreScriptVariables() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>()));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
  }

  /**
   * Test
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   * with {@code script}, {@code language}, {@code variableScope},
   * {@code storeScriptVariables}.
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  public void testEvaluateWithScriptLanguageVariableScopeStoreScriptVariables2() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
    verify(resolverFactory).createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope)} with
   * {@code script}, {@code language}, {@code variableScope}.
   * <ul>
   *   <li>Then calls
   * {@link ResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  public void testEvaluateWithScriptLanguageVariableScope_thenCallsCreateResolver() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
    verify(resolverFactory).createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Test {@link ScriptingEngines#evaluate(String, String, VariableScope)} with
   * {@code script}, {@code language}, {@code variableScope}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  public void testEvaluateWithScriptLanguageVariableScope_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>()));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link ScriptingEngines#getEngineByName(String)}.
   * <ul>
   *   <li>Then Factory return {@link GroovyScriptEngineFactory}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  public void testGetEngineByName_thenFactoryReturnGroovyScriptEngineFactory() {
    // Arrange and Act
    ScriptEngine actualEngineByName = scriptingEngines.getEngineByName(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE);

    // Assert
    ScriptEngineFactory factory = actualEngineByName.getFactory();
    assertTrue(factory instanceof GroovyScriptEngineFactory);
    ScriptEngine scriptEngine = factory.getScriptEngine();
    assertTrue(scriptEngine instanceof GroovyScriptEngineImpl);
    assertTrue(actualEngineByName instanceof GroovyScriptEngineImpl);
    assertEquals("2.0", factory.getEngineVersion());
    assertEquals("3.0.19", factory.getLanguageVersion());
    assertEquals("Groovy Scripting Engine", factory.getEngineName());
    List<String> names = factory.getNames();
    assertEquals(2, names.size());
    assertEquals("Groovy", names.get(1));
    assertEquals("Groovy", factory.getLanguageName());
    List<String> mimeTypes = factory.getMimeTypes();
    assertEquals(1, mimeTypes.size());
    assertEquals("application/x-groovy", mimeTypes.get(0));
    assertNotNull(((GroovyScriptEngineImpl) scriptEngine).getClassLoader());
    assertNotNull(((GroovyScriptEngineImpl) actualEngineByName).getClassLoader());
    List<String> extensions = factory.getExtensions();
    assertEquals(1, extensions.size());
    Map<String, ScriptEngine> stringScriptEngineMap = scriptingEngines.cachedEngines;
    assertEquals(1, stringScriptEngineMap.size());
    assertTrue(stringScriptEngineMap.containsKey(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE));
    assertEquals(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, extensions.get(0));
    assertEquals(ScriptingEngines.GROOVY_SCRIPTING_LANGUAGE, names.get(0));
    assertSame(factory, scriptEngine.getFactory());
  }

  /**
   * Test {@link ScriptingEngines#getEngineByName(String)}.
   * <ul>
   *   <li>When {@code en}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  public void testGetEngineByName_whenEn_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.getEngineByName("en"));
  }

  /**
   * Test {@link ScriptingEngines#createBindings(VariableScope, boolean)} with
   * {@code variableScope}, {@code storeScriptVariables}.
   * <ul>
   *   <li>Then return {@link SimpleBindings#SimpleBindings()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptingEngines#createBindings(VariableScope, boolean)}
   */
  @Test
  public void testCreateBindingsWithVariableScopeStoreScriptVariables_thenReturnSimpleBindings() {
    // Arrange
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));
    ScriptBindingsFactory scriptBindingsFactory = mock(ScriptBindingsFactory.class);
    SimpleBindings simpleBindings = new SimpleBindings();
    when(scriptBindingsFactory.createBindings(Mockito.<VariableScope>any(), anyBoolean())).thenReturn(simpleBindings);
    doNothing().when(scriptBindingsFactory).setResolverFactories(Mockito.<List<ResolverFactory>>any());
    scriptBindingsFactory.setResolverFactories(resolverFactories);

    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);

    // Act
    Bindings actualCreateBindingsResult = scriptingEngines.createBindings(NoExecutionVariableScope.getSharedInstance(),
        true);

    // Assert
    verify(scriptBindingsFactory).createBindings(isA(VariableScope.class), eq(true));
    verify(scriptBindingsFactory).setResolverFactories(isA(List.class));
    assertTrue(actualCreateBindingsResult.isEmpty());
    assertSame(simpleBindings, actualCreateBindingsResult);
  }

  /**
   * Test {@link ScriptingEngines#createBindings(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Then return {@link SimpleBindings#SimpleBindings()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptingEngines#createBindings(VariableScope)}
   */
  @Test
  public void testCreateBindingsWithVariableScope_thenReturnSimpleBindings() {
    // Arrange
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));
    ScriptBindingsFactory scriptBindingsFactory = mock(ScriptBindingsFactory.class);
    SimpleBindings simpleBindings = new SimpleBindings();
    when(scriptBindingsFactory.createBindings(Mockito.<VariableScope>any())).thenReturn(simpleBindings);
    doNothing().when(scriptBindingsFactory).setResolverFactories(Mockito.<List<ResolverFactory>>any());
    scriptBindingsFactory.setResolverFactories(resolverFactories);

    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);

    // Act
    Bindings actualCreateBindingsResult = scriptingEngines.createBindings(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(scriptBindingsFactory).createBindings(isA(VariableScope.class));
    verify(scriptBindingsFactory).setResolverFactories(isA(List.class));
    assertTrue(actualCreateBindingsResult.isEmpty());
    assertSame(simpleBindings, actualCreateBindingsResult);
  }
}
