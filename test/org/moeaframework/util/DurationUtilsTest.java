/* Copyright 2009-2024 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.util;

import java.time.Duration;

import org.junit.Test;
import org.moeaframework.Assert;

public class DurationUtilsTest {
	
	@Test
	public void testToMilliseconds() {
		Assert.assertEquals(5000, DurationUtils.toMilliseconds(Duration.ofSeconds(5)));
		Assert.assertEquals(500, DurationUtils.toMilliseconds(Duration.ofMillis(500)));
		Assert.assertEquals(300000, DurationUtils.toMilliseconds(Duration.ofMinutes(5)));
		Assert.assertEquals(0, DurationUtils.toMilliseconds(Duration.ofNanos(5)));
	}
	
	@Test
	public void testToNanoseconds() {
		Assert.assertEquals(5000000000L, DurationUtils.toNanoseconds(Duration.ofSeconds(5)));
		Assert.assertEquals(500000000L, DurationUtils.toNanoseconds(Duration.ofMillis(500)));
		Assert.assertEquals(300000000000L, DurationUtils.toNanoseconds(Duration.ofMinutes(5)));
		Assert.assertEquals(5L, DurationUtils.toNanoseconds(Duration.ofNanos(5)));
	}
	
}
