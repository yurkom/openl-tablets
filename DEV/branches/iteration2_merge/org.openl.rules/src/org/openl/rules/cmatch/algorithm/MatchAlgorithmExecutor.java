package org.openl.rules.cmatch.algorithm;

import org.openl.rules.cmatch.ColumnMatch;
import org.openl.rules.cmatch.MatchNode;
import org.openl.rules.cmatch.matcher.IMatcher;
import org.openl.types.IOpenClass;
import org.openl.vm.IRuntimeEnv;
import org.openl.vm.Tracer;

public class MatchAlgorithmExecutor implements IMatchAlgorithmExecutor {
    public static final Object NO_MATCH = null;

    public Object invoke(Object target, Object[] params, IRuntimeEnv env, ColumnMatch columnMatch) {
        Tracer t = Tracer.getTracer();

        MatchNode checkTree = columnMatch.getCheckTree();
        Object returnValues[] = columnMatch.getReturnValues();

        // iterate over linearized nodes
        for (MatchNode line : checkTree.getChildren()) {
            if (line.getRowIndex() >= 0) {
                throw new IllegalArgumentException("Linearized MatchNode tree expected!");
            }

            // find matching result value from left to right
            for (int resultIndex = 0; resultIndex < returnValues.length; resultIndex++) {
                boolean success = true;
                // check that all children are MATCH at resultIndex element
                for (MatchNode node : line.getChildren()) {
                    Argument arg = node.getArgument();
                    Object var = arg.extractValue(target, params, env);
                    IMatcher matcher = node.getMatcher();
                    Object checkValue = node.getCheckValues()[resultIndex];
                    if (!matcher.match(var, checkValue)) {
                        success = false;
                        break;
                    }
                }

                if (success) {
                    if (t!= null) {
                        t.push(new ColumnMatchTraceObject(columnMatch, params));
                        for (MatchNode node : line.getChildren()) {
                            t.push(new MatchTraceObject(columnMatch, node.getRowIndex(), resultIndex));
                            t.pop();
                        }
                        t.pop();
                    }
                    
                    return returnValues[resultIndex];
                }
            }
        }

        IOpenClass type = columnMatch.getHeader().getType();
        if (type.getClass().isPrimitive()) {
            throw new IllegalArgumentException("Cannot return <null> for primitive type " + type.getClass().getName());
        } else {
            return NO_MATCH;
        }
    }
}
