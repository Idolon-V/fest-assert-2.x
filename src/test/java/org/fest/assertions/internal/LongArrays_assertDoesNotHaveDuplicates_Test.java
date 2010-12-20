/*
 * Created on Dec 20, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.HasDuplicates.hasDuplicates;
import static org.fest.assertions.test.LongArrayFactory.*;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.unexpectedNull;
import static org.fest.assertions.util.ArrayWrapperList.wrap;
import static org.fest.util.Collections.set;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.core.WritableAssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link LongArrays#assertDoesNotHaveDuplicates(AssertionInfo, long[])}</code>.
 *
 * @author Alex Ruiz
 */
public class LongArrays_assertDoesNotHaveDuplicates_Test {

  private static WritableAssertionInfo info;

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private long[] actual;
  private LongArrays collections;

  @BeforeClass public static void setUpOnce() {
    info = new WritableAssertionInfo();
  }

  @Before public void setUp() {
    failures = spy(Failures.instance());
    actual = array(6L, 8L);
    collections = new LongArrays(failures);
  }

  @Test public void should_pass_if_actual_does_not_have_duplicates() {
    collections.assertDoesNotHaveDuplicates(info, actual);
  }

  @Test public void should_pass_if_actual_is_empty() {
    collections.assertDoesNotHaveDuplicates(info, emptyArray());
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(unexpectedNull());
    collections.assertDoesNotHaveDuplicates(info, null);
  }

  @Test public void should_fail_if_actual_contains_duplicates() {
    Collection<Long> duplicates = set(6L, 8L);
    actual = array(6L, 8L, 6L, 8L);
    try {
      collections.assertDoesNotHaveDuplicates(info, actual);
      fail();
    } catch (AssertionError e) {}
    verify(failures).failure(info, hasDuplicates(wrap(actual), duplicates));
  }
}
