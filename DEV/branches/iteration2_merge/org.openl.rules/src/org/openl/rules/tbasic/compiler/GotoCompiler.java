package org.openl.rules.tbasic.compiler;

import java.util.List;

import org.openl.rules.tbasic.AlgorithmTreeNode;
import org.openl.rules.tbasic.runtime.GotoOperation;
import org.openl.rules.tbasic.runtime.RuntimeOperation;

public class GotoCompiler extends NodeCompiler{

    public GotoCompiler(List<RuntimeOperation> operations,
            AlgorithmTreeNode node) {
        super(operations, node);
    }

    @Override
    public RuntimeOperation compileNode() {
        RuntimeOperation operation = new GotoOperation(node.getAlgorithmRow().getAction().getValue());
        operation.setSourceCode(node);
        operations.add(operation);
        return operation;
    }

}
