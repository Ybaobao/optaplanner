/*
 * Copyright 2015 JBoss Inc
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

package org.optaplanner.examples.investmentallocation.solver.move;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.investmentallocation.domain.AssetClassAllocation;

public class InvestmentQuantityTransferMove extends AbstractMove {

    private final AssetClassAllocation fromAssetClassAllocation;
    private final AssetClassAllocation toAssetClassAllocation;
    private final long transferMicros;

    public InvestmentQuantityTransferMove(AssetClassAllocation fromAssetClassAllocation, AssetClassAllocation toAssetClassAllocation, long transferMicros) {
        this.fromAssetClassAllocation = fromAssetClassAllocation;
        this.toAssetClassAllocation = toAssetClassAllocation;
        this.transferMicros = transferMicros;
    }

    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    public Move createUndoMove(ScoreDirector scoreDirector) {
        return new InvestmentQuantityTransferMove(toAssetClassAllocation, fromAssetClassAllocation, transferMicros);
    }

    public void doMove(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(fromAssetClassAllocation, "quantityMicros");
        fromAssetClassAllocation.setQuantityMicros(fromAssetClassAllocation.getQuantityMicros() - transferMicros);
        scoreDirector.afterVariableChanged(fromAssetClassAllocation, "quantityMicros");
        scoreDirector.beforeVariableChanged(toAssetClassAllocation, "quantityMicros");
        toAssetClassAllocation.setQuantityMicros(toAssetClassAllocation.getQuantityMicros() + transferMicros);
        scoreDirector.afterVariableChanged(toAssetClassAllocation, "quantityMicros");
    }

    public Collection<? extends Object> getPlanningEntities() {
        return Arrays.asList(fromAssetClassAllocation, toAssetClassAllocation);
    }

    public Collection<? extends Object> getPlanningValues() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        long fromQuantity = fromAssetClassAllocation.getQuantityMicros();
        long toQuantity = toAssetClassAllocation.getQuantityMicros();
        return "[" + fromAssetClassAllocation + " {" + fromQuantity + "->" + (fromQuantity - transferMicros) + "}, "
                + toAssetClassAllocation + " {" + toQuantity + "->" + (toQuantity + transferMicros) + "}]";
    }

}