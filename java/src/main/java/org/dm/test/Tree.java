package org.dm.test;

import java.util.Map;

/**
 * Created by Mirage on 2016-08-15.
 */
public class Tree {
    private Node root;
    private int nodeCount;

    public Tree() {
        root = new Node();
        root.setData(-1);
        root.setParent(null);
    }

    public void addEntry(DataEntry dataEntry) {
        addCanal(root, dataEntry);
    }

//    private void addAgencia(Node root, DataEntry dataEntry) {
//        if (root.getChild(dataEntry.Agencia_ID) == null) {
//            Node tmpNode = new Node();
//            tmpNode.setParent(root);
//            tmpNode.setData(dataEntry.Agencia_ID);
//            root.addChild(dataEntry.Agencia_ID, tmpNode);
//        }
//        addSemana(root.getChild(dataEntry.Agencia_ID), dataEntry);
//    }

//    private void addSemana(Node node, DataEntry dataEntry) {
//        if (node.getChild(dataEntry.Semana) == null) {
//            Node tmpNode = new Node();
//            tmpNode.setParent(node);
//            tmpNode.setData(dataEntry.Semana);
//            node.addChild(dataEntry.Semana, tmpNode);
//        }
//        addCanal(node.getChild(dataEntry.Semana), dataEntry);
//    }

    private void addCanal(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Canal_ID) == null) {
            Node tmpNode = new Node();
            tmpNode.setParent(node);
            tmpNode.setData(dataEntry.Canal_ID);
            node.addChild(dataEntry.Canal_ID, tmpNode);
        }
        addRuta(node.getChild(dataEntry.Canal_ID), dataEntry);
    }

    private void addRuta(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Ruta_SAK) == null) {
            Node tmpNode = new Node();
            tmpNode.setParent(node);
            tmpNode.setData(dataEntry.Ruta_SAK);
            node.addChild(dataEntry.Ruta_SAK, tmpNode);
        }
        addCliente(node.getChild(dataEntry.Ruta_SAK), dataEntry);
    }

    private void addCliente(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Cliente_ID) == null) {
            Node tmpNode = new Node();
            tmpNode.setParent(node);
            tmpNode.setData(dataEntry.Cliente_ID);
            node.addChild(dataEntry.Cliente_ID, tmpNode);
        }
        addProducto(node.getChild(dataEntry.Cliente_ID), dataEntry);
    }

    private void addProducto(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Producto_ID) == null) {
            Node tmpNode = new Node();
            tmpNode.setParent(node);
            tmpNode.setData(dataEntry.Producto_ID);
            node.addChild(dataEntry.Producto_ID, tmpNode);
        }
        if (dataEntry.Demanda_uni_equil != -1) {
            addDemanda(node.getChild(dataEntry.Producto_ID), dataEntry);
        }
    }

    private void addDemanda(Node node, DataEntry dataEntry) {
        if (node.getChild(-16) == null) {
            Node tmpNode = new Node();
            tmpNode.setParent(node);
            tmpNode.setData(dataEntry.Demanda_uni_equil);
            node.addChild(-16, tmpNode);
        } else {
            int current = node.getChild(-16).getData();
            node.getChild(-16).setData((current + dataEntry.Demanda_uni_equil));
        }
        node.increse();
    }

    public int getPrediction(DataEntry dataEntry) {
        return getCanal(root, dataEntry);
    }


    private int getCanal(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Canal_ID) == null) {
            return 0;
        }
        return getRuta(node.getChild(dataEntry.Canal_ID), dataEntry);
    }

    private int getRuta(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Ruta_SAK) == null) {
            return 0;
        }
        return getCliente(node.getChild(dataEntry.Ruta_SAK), dataEntry);
    }


    private int getCliente(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Cliente_ID) == null) {
            return 0;
        }
        return getProducto(node.getChild(dataEntry.Cliente_ID), dataEntry);
    }

    private int getProducto(Node node, DataEntry dataEntry) {
        if (node.getChild(dataEntry.Producto_ID) == null) {
            int total= 0;
            Map<Integer, Node> map = node.getMap();
            for (Integer key : map.keySet()) {
                total+=getDemanda(node.getChild(key),null);
            }
            int avg = total/map.keySet().size();
            return avg;
        }

        return getDemanda(node.getChild(dataEntry.Producto_ID), dataEntry);

    }

    private int getDemanda(Node node, DataEntry dataEntry) {
        if (node.getChild(-16) == null) {
            return 0;
        } else {
            int pre = node.getChild(-16).getData();
            int count = node.getCount();
            return pre/count;
        }
    }


    public int getNodeCount(){
        nodeCount = 0;
        nodeCount(root,0);
        return nodeCount;
    }

    private void nodeCount(Node node, int level){
        nodeCount++;
        Map<Integer, Node> map = node.getMap();
        for (Integer key : map.keySet()) {
            nodeCount(map.get(key), level + 1);
        }
    }

    public void printTree() {
        print(root, 0);
    }

    public void print(Node node, int level) {
        switch (level) {
            case 0:
                System.out.print("ROOT");
                break;
            case 1:
                System.out.print("AGNC");
                break;
            case 2:
                System.out.print("WEEK");
                break;
            case 3:
                System.out.print("CNAL");
                break;
            case 4:
                System.out.print("RUTA");
                break;
            case 5:
                System.out.print("CLNT");
                break;
            case 6:
                System.out.print("PRDT");
                break;
            case 7:
                System.out.print("DEMD");
                break;

        }
        for (int i = 0; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(node.getData());
        Map<Integer, Node> map = node.getMap();
        for (Integer key : map.keySet()) {
            print(map.get(key), level + 1);
        }
    }

    private void add(Node node, DataEntry dataEntry, int level) {
        switch (level) {
            case 0:
                if (node.getChild(dataEntry.Agencia_ID) == null) {
                    Node tmpNode = new Node();
                    tmpNode.setParent(node);
                    tmpNode.setData(dataEntry.Agencia_ID);
                    node.addChild(dataEntry.Agencia_ID, tmpNode);
                }
                add(node.getChild(dataEntry.Agencia_ID), dataEntry, 1);
                break;
            case 1:
                if (node.getChild(dataEntry.Semana) == null) {
                    Node tmpNode = new Node();
                    tmpNode.setParent(node);
                    tmpNode.setData(dataEntry.Semana);
                    node.addChild(dataEntry.Semana, tmpNode);
                }
                add(node.getChild(dataEntry.Semana), dataEntry, 2);
                break;

        }
//        if(node.getChild(value)== null){
//            Node tmpNode = new Node();
//            tmpNode.setParent(node);
//            tmpNode.setData(value);
//            node.addChild(value,tmpNode);
//        }else {
//            node.getChild(value)
//        }
    }
}
