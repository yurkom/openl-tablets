package org.openl.binding.impl;

import org.openl.binding.IBoundMethodNode;
import org.openl.binding.IBoundNode;
import org.openl.types.IOpenCast;
import org.openl.types.IOpenClass;

public class MethodCastNode extends CastNode implements IBoundMethodNode {

    public MethodCastNode(IBoundNode bnode, IOpenCast cast, IOpenClass castedType) {
        super(null, bnode, cast, castedType);
    }

    public int getLocalFrameSize() {
        return ((IBoundMethodNode) getChildren()[0]).getLocalFrameSize();
    }

    public int getParametersSize() {
        return ((IBoundMethodNode) getChildren()[0]).getParametersSize();
    }
}
