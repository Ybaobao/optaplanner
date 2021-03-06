/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.score.buildin.hardmediumsoftlong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.config.score.trend.InitializingScoreTrendLevel;
import org.optaplanner.core.impl.score.trend.InitializingScoreTrend;

public class HardMediumSoftLongScoreDefinitionTest {

    @Test
    public void getZeroScore() {
        HardMediumSoftLongScore score = new HardMediumSoftLongScoreDefinition().getZeroScore();
        assertThat(score).isEqualTo(HardMediumSoftLongScore.ZERO);
    }

    @Test
    public void getSoftestOneScore() {
        HardMediumSoftLongScore score = new HardMediumSoftLongScoreDefinition().getOneSoftestScore();
        assertThat(score).isEqualTo(HardMediumSoftLongScore.ONE_SOFT);
    }

    @Test
    public void getLevelsSize() {
        assertEquals(3, new HardMediumSoftLongScoreDefinition().getLevelsSize());
    }

    @Test
    public void getLevelLabels() {
        assertArrayEquals(new String[] { "hard score", "medium score", "soft score" },
                new HardMediumSoftLongScoreDefinition().getLevelLabels());
    }

    @Test
    public void getFeasibleLevelsSize() {
        assertEquals(1, new HardMediumSoftLongScoreDefinition().getFeasibleLevelsSize());
    }

    @Test
    public void buildOptimisticBoundOnlyUp() {
        HardMediumSoftLongScoreDefinition scoreDefinition = new HardMediumSoftLongScoreDefinition();
        HardMediumSoftLongScore optimisticBound = scoreDefinition.buildOptimisticBound(
                InitializingScoreTrend.buildUniformTrend(InitializingScoreTrendLevel.ONLY_UP, 3),
                HardMediumSoftLongScore.of(-1L, -2L, -3L));
        assertEquals(0, optimisticBound.getInitScore());
        assertEquals(Long.MAX_VALUE, optimisticBound.getHardScore());
        assertEquals(Long.MAX_VALUE, optimisticBound.getMediumScore());
        assertEquals(Long.MAX_VALUE, optimisticBound.getSoftScore());
    }

    @Test
    public void buildOptimisticBoundOnlyDown() {
        HardMediumSoftLongScoreDefinition scoreDefinition = new HardMediumSoftLongScoreDefinition();
        HardMediumSoftLongScore optimisticBound = scoreDefinition.buildOptimisticBound(
                InitializingScoreTrend.buildUniformTrend(InitializingScoreTrendLevel.ONLY_DOWN, 3),
                HardMediumSoftLongScore.of(-1L, -2L, -3L));
        assertEquals(0, optimisticBound.getInitScore());
        assertEquals(-1L, optimisticBound.getHardScore());
        assertEquals(-2L, optimisticBound.getMediumScore());
        assertEquals(-3L, optimisticBound.getSoftScore());
    }

    @Test
    public void buildPessimisticBoundOnlyUp() {
        HardMediumSoftLongScoreDefinition scoreDefinition = new HardMediumSoftLongScoreDefinition();
        HardMediumSoftLongScore pessimisticBound = scoreDefinition.buildPessimisticBound(
                InitializingScoreTrend.buildUniformTrend(InitializingScoreTrendLevel.ONLY_UP, 3),
                HardMediumSoftLongScore.of(-1L, -2L, -3L));
        assertEquals(0, pessimisticBound.getInitScore());
        assertEquals(-1L, pessimisticBound.getHardScore());
        assertEquals(-2L, pessimisticBound.getMediumScore());
        assertEquals(-3L, pessimisticBound.getSoftScore());
    }

    @Test
    public void buildPessimisticBoundOnlyDown() {
        HardMediumSoftLongScoreDefinition scoreDefinition = new HardMediumSoftLongScoreDefinition();
        HardMediumSoftLongScore pessimisticBound = scoreDefinition.buildPessimisticBound(
                InitializingScoreTrend.buildUniformTrend(InitializingScoreTrendLevel.ONLY_DOWN, 3),
                HardMediumSoftLongScore.of(-1L, -2L, -3L));
        assertEquals(0, pessimisticBound.getInitScore());
        assertEquals(Long.MIN_VALUE, pessimisticBound.getHardScore());
        assertEquals(Long.MIN_VALUE, pessimisticBound.getMediumScore());
        assertEquals(Long.MIN_VALUE, pessimisticBound.getSoftScore());
    }

    @Test
    public void divideBySanitizedDivisor() {
        HardMediumSoftLongScoreDefinition scoreDefinition = new HardMediumSoftLongScoreDefinition();
        HardMediumSoftLongScore dividend = scoreDefinition.fromLevelNumbers(2, new Number[] { 0L, 1L, 10L });
        HardMediumSoftLongScore zeroDivisor = scoreDefinition.getZeroScore();
        assertThat(scoreDefinition.divideBySanitizedDivisor(dividend, zeroDivisor))
                .isEqualTo(dividend);
        HardMediumSoftLongScore oneDivisor = scoreDefinition.getOneSoftestScore();
        assertThat(scoreDefinition.divideBySanitizedDivisor(dividend, oneDivisor))
                .isEqualTo(dividend);
        HardMediumSoftLongScore tenDivisor = scoreDefinition.fromLevelNumbers(10, new Number[] { 10L, 10L, 10L });
        assertThat(scoreDefinition.divideBySanitizedDivisor(dividend, tenDivisor))
                .isEqualTo(scoreDefinition.fromLevelNumbers(0, new Number[] { 0L, 0L, 1L }));
    }

}
