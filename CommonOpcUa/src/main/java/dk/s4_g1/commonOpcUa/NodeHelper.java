package dk.s4_g1.commonOpcUa;

import dk.s4_g1.common.enums.Nodes;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public class NodeHelper {
    public static NodeId createNodeId(Nodes type) {
        return new NodeId(type.namespaceIndex, type.node);
    }
}
