import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

/*
 * Program to visualize graphs for the graph hw8
 * @author miabelar 18fa
 */


public class GraphVisualizer {

    public static final int NODE_TIME = 1000;
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 900;

    protected static String styleSheet = "node {" + "   fill-color: grey;" + "size: 20px;"
                    + "fill-mode: dyn-plain;" + "stroke-color: black;" + "stroke-width: 1px;"
                    + "text-size: 20px;" + "}" + "edge {" + "text-size: 20px;" + "}"
                    + "node.marked {" + "   fill-color: red;" + "}" + "edge.marked {"
                    + "   fill-color: red;" + "   size: 4px;" + "}";


    static JFrame mainFrame;
    static org.graphstream.graph.implementations.SingleGraph graph;
    static HashMap<String, Integer> edgeToWeight;

    static JButton widestPathButton;
    static JButton sccButton;
    static JButton dijkstraButton;
    static JButton dfsButton;
    static JButton bfsButton;
    static JButton kruskalButton;

    static JCheckBox isDirected;

    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui.renderer",
                        "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        edgeToWeight = new HashMap<String, Integer>();

        graph = new org.graphstream.graph.implementations.SingleGraph("");
        graph.addAttribute("ui.stylesheet", styleSheet);

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        JPanel view = viewer.addDefaultView(false);
        viewer.enableAutoLayout();

        // ui construction
        mainFrame = new JFrame("Graph Visualizer ");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.add(view, BorderLayout.CENTER);

        initTopBar();
        toggleButtons();

        mainFrame.setVisible(true);
    }

    static void drawGraph() {
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
    }

    static void toggleButtons() {
        if (isDirected.isSelected()) {
            sccButton.setEnabled(true);
            dfsButton.setEnabled(true);
            dijkstraButton.setEnabled(true);
            bfsButton.setEnabled(false);
            kruskalButton.setEnabled(false);
            widestPathButton.setEnabled(false);
        } else {
            sccButton.setEnabled(false);
            dfsButton.setEnabled(false);
            dijkstraButton.setEnabled(false);
            bfsButton.setEnabled(true);
            kruskalButton.setEnabled(true);
            widestPathButton.setEnabled(true);

        }
    }

    static void addNode(String name) {
        if (name.length() == 0) {
            showErrorMessage("Node must have name");
        } else if (graph.getNode(name) != null) {
            showErrorMessage("Node already in graph");
        } else {
            graph.addNode(name);
        }
    }

    static void addEdge(String start, String end, String weightStr) {
        if (graph.getNode(start) != null && graph.getNode(end) != null) {
            String edgeName = start + end;
            boolean proceed = true;
            if (!isDirected.isSelected()) {
                if (graph.getEdge(end + start) != null) {
                    proceed = false;
                }
            }
            if (graph.getEdge(edgeName) != null || !proceed) {
                showErrorMessage("Edge already in graph");
            } else {
                graph.addEdge(edgeName, start, end, isDirected.isSelected());
                int weight;
                if (weightStr.length() > 0) {
                    try {
                        weight = Integer.parseInt(weightStr);
                    } catch (NumberFormatException e) {
                        weight = 1;
                    }
                } else {
                    weight = 1;
                }
                edgeToWeight.put(edgeName, weight);
                graph.getEdge(edgeName).addAttribute("ui.label", Integer.toString(weight));

            }
        } else {
            showErrorMessage("Nodes do not exist");
        }
    }

    static void runBFS(String start, String end) {
        if (graph.getNode(start) != null && graph.getNode(end) != null) {
            try {
                HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

                // create reverse of map
                HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
                for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                    intToNode.put(entry.getValue(), entry.getKey());
                }

                Graph g = serializeGraph(graph, nodeToInt);

                // run and color
                List<Integer> bfsResults =
                                BFS.getShortestPath(g, nodeToInt.get(start), nodeToInt.get(end));
                int lengthOfResults = bfsResults.size();

                int i = 0;
                markNodes(i, lengthOfResults, intToNode, bfsResults, null, true);
            } catch (Exception e) {
                showErrorMessage("Exception thrown during execution");
            }

        } else {
            showErrorMessage("Nodes do not exist");
        }
    }

    static void runWidestPath(String start, String end) {
        if (graph.getNode(start) != null && graph.getNode(end) != null) {
            try {
                HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

                // create reverse of map
                HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
                for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                    intToNode.put(entry.getValue(), entry.getKey());
                }

                Graph g = serializeGraph(graph, nodeToInt);

                // run and color
                List<Integer> widestPathResults = WidestPath.getWidestPath(g, nodeToInt.get(start),
                                nodeToInt.get(end));
                int lengthOfResults = widestPathResults.size();

                int i = 0;
                markNodes(i, lengthOfResults, intToNode, widestPathResults, null, true);
            } catch (Exception e) {
                showErrorMessage("Exception thrown during execution");
            }

        } else {
            showErrorMessage("Nodes do not exist");
        }
    }

    static void runDFS(String start) {
        if (graph.getNode(start) != null) {
            try {
                HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

                // create reverse of map
                HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
                for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                    intToNode.put(entry.getValue(), entry.getKey());
                }
                WDGraph g = serializeWDGraph(graph, nodeToInt);

                // run and color
                List<Integer> dfsResults = DFS.dfsReverseFinishingTime(g, nodeToInt.get(start));
                int lengthOfResults = dfsResults.size();

                int i = 0;
                markNodes(i, lengthOfResults, intToNode, dfsResults, null, false);
            } catch (Exception e) {
                showErrorMessage("Exception thrown during execution");
            }

        } else {
            showErrorMessage("Nodes do not exist");
        }
    }

    static void runDijkstra(String start, String end) {
        if (graph.getNode(start) != null && graph.getNode(end) != null) {
            try {
                HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

                // create reverse of map
                HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
                for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                    intToNode.put(entry.getValue(), entry.getKey());
                }

                WDGraph g = serializeWDGraph(graph, nodeToInt);

                // run and color
                Iterable<Integer> dijkstraResults = Dijkstra.getShortestPath(g,
                                nodeToInt.get(start), nodeToInt.get(end));
                Iterator<Integer> dijkstraIterator = dijkstraResults.iterator();
                List<Integer> dijkstraList = new ArrayList<Integer>();
                // populate dijkstraList
                while (dijkstraIterator.hasNext()) {
                    int vertex = dijkstraIterator.next();
                    dijkstraList.add(vertex);
                }
                int lengthOfResults = dijkstraList.size();

                int i = 0;
                markNodes(i, lengthOfResults, intToNode, dijkstraList, null, false);
            } catch (Exception e) {
                showErrorMessage("Exception thrown during execution");
            }

        } else {
            showErrorMessage("Nodes do not exist");
        }
    }

    static void runKruskal() {
        try {
            HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

            // create reverse of map
            HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
            for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                intToNode.put(entry.getValue(), entry.getKey());
            }

            Graph g = serializeGraph(graph, nodeToInt);

            // run and color
            Graph mst = Kruskal.getMST(g);
            // color the vertices
            for (Node n : graph.getNodeSet()) {
                n.setAttribute("ui.class", "marked");
            }
            // color the edges
            for (Kruskal.Edge e : Kruskal.getEdges(mst)) {
                String startNode = intToNode.get(e.u);
                String endNode = intToNode.get(e.v);
                // can be either combination of the strings
                String edgePossibilityOne = startNode + endNode;
                String edgePossibilityTwo = endNode + startNode;
                org.graphstream.graph.Edge edge = graph.getEdge(edgePossibilityOne);
                if (edge == null) {
                    edge = graph.getEdge(edgePossibilityTwo);
                }
                edge.setAttribute("ui.class", "marked");
            }
        } catch (Exception e) {
            showErrorMessage("Exception thrown during execution");
        }

    }

    static void runSCC() {
        try {
            HashMap<String, Integer> nodeToInt = setUpHashMap(graph);

            // create reverse of map
            HashMap<Integer, String> intToNode = new HashMap<Integer, String>();
            for (Map.Entry<String, Integer> entry : nodeToInt.entrySet()) {
                intToNode.put(entry.getValue(), entry.getKey());
            }

            WDGraph g = serializeWDGraph(graph, nodeToInt);

            // run and color
            Set<Set<Integer>> connectedComponents =
                            ConnectedComponents.stronglyConnectedComponents(g);
            for (Set<Integer> set : connectedComponents) {
                List<Integer> vertexList = new ArrayList<Integer>();
                vertexList.addAll(set);
                // generate random color for CC
                Random rand = new Random();
                float red = rand.nextFloat();
                float green = rand.nextFloat();
                float blue = rand.nextFloat();
                Color randomColor = new Color(red, green, blue);
                // color the nodes
                Node prevNode = null;
                for (int v : vertexList) {
                    String nodeId = intToNode.get(v);
                    if (nodeId == null) {
                        showErrorMessage("Invalid node trying to be reached");
                    } else {
                        Node n = graph.getNode(nodeId);
                        n.addAttribute("ui.color", randomColor);
                        if (prevNode != null) {
                            String edgeIdOne = prevNode.toString() + n.toString();
                            org.graphstream.graph.Edge edge = graph.getEdge(edgeIdOne);
                            if (edge != null) {
                                edge.addAttribute("ui.color", randomColor);
                            }
                        }
                        prevNode = n;

                    }
                }
            }
        } catch (Exception e) {
            showErrorMessage("Exception thrown during execution");
        }

    }


    static void markNodes(int i, int lengthOfResults, HashMap<Integer, String> intToNode,
                    List<Integer> results, Node prevNode, boolean markEdges) {
        if (i < lengthOfResults) {
            String nodeId = intToNode.get(results.get(i));
            if (nodeId == null) {
                showErrorMessage("Invalid node trying to be reached");
            } else {
                Node n = graph.getNode(nodeId);
                n.setAttribute("ui.class", "marked");
                n.setAttribute("visited");
                if (markEdges) {
                    if (prevNode != null) {
                        String edgeIdOne = prevNode.toString() + n.toString();
                        String edgeIdTwo = n.toString() + prevNode.toString();
                        org.graphstream.graph.Edge edge = graph.getEdge(edgeIdOne);
                        if (edge == null) {
                            if (!isDirected.isSelected()) {
                                edge = graph.getEdge(edgeIdTwo);
                                if (edge != null) {
                                    edge.setAttribute("ui.class", "marked");
                                }
                            }
                        } else {
                            edge.setAttribute("ui.class", "marked");
                        }

                    }
                }
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        markNodes(i + 1, lengthOfResults, intToNode, results, n, markEdges);
                    }
                }, NODE_TIME);
            }
        }
    }

    static HashMap<String, Integer> setUpHashMap(
                    org.graphstream.graph.implementations.SingleGraph graph) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int nodeCounter = 0;
        for (Node node : graph) {
            map.put(node.getId(), nodeCounter);
            nodeCounter = nodeCounter + 1;
        }

        return map;
    }

    static String jsonSerializerGraph() {
        // nodes
        StringBuilder nodesBuilder = new StringBuilder("[");
        for (Node node : graph) {
            nodesBuilder.append(node.getId() + ",");
        }
        // remove the last comma
        nodesBuilder.setLength(nodesBuilder.length() - 1);
        nodesBuilder.append("]");
        String nodes = nodesBuilder.toString();

        // edges
        StringBuilder edgesBuilder = new StringBuilder("[");
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            Node startVertex = edge.getNode0();
            Node endVertex = edge.getNode1();
            edgesBuilder.append("(" + startVertex.getId() + "," + endVertex.getId() + ":"
                            + edgeToWeight.get(startVertex.getId() + endVertex.getId()).toString()
                            + "),");
        }
        // remove the last comma
        edgesBuilder.setLength(edgesBuilder.length() - 1);
        edgesBuilder.append("]");
        String edges = edgesBuilder.toString();

        String returnString = "{\n nodes: " + nodes + ",\n edges: " + edges + "\n}";
        return returnString;
    }

    static void createGraphFromJSON(String json) {
        if (json.length() == 0) {
            showErrorMessage("JSON is empty");
        } else {
            graph.clear();
            edgeToWeight.clear();
            graph.addAttribute("ui.stylesheet", styleSheet);

            // add nodes
            // get the point where we have nodes
            json = json.substring(json.indexOf("["));
            char currChar = json.charAt(1);
            int parseCounter = 1;
            String currNodeName = "";
            while (currChar != ']') {
                currChar = json.charAt(parseCounter);
                if (currChar != ',' && currChar != ']') {
                    currNodeName = currNodeName + currChar;
                } else {
                    if (currNodeName.length() > 0) {
                        addNode(currNodeName);
                    }
                    currNodeName = "";
                }
                parseCounter = parseCounter + 1;
            }

            json = json.substring(parseCounter);
            json = json.substring(json.indexOf("["));


            // add edges
            currChar = json.charAt(1);
            parseCounter = 1;
            String nodeStart = "";
            String nodeEnd = "";
            String currEdgeWeight = "";
            // 1 for start, 2 for end, 3 for weight
            int parsingSection = 1;
            boolean shouldEdit = true;
            while (currChar != ']') {
                currChar = json.charAt(parseCounter);
                if (currChar == '(') {
                    parsingSection = 1;
                    shouldEdit = true;
                } else if (currChar == ',' && parsingSection == 1) {
                    parsingSection = 2;
                } else if (currChar == ':' && parsingSection == 2) {
                    parsingSection = 3;
                } else if (currChar == ')') {
                    shouldEdit = false;
                    // clear and add
                    addEdge(nodeStart, nodeEnd, currEdgeWeight);
                    nodeStart = "";
                    nodeEnd = "";
                    currEdgeWeight = "";
                } else {
                    if (parsingSection == 1 && shouldEdit) {
                        nodeStart = nodeStart + currChar;
                    } else if (parsingSection == 2 && shouldEdit) {
                        nodeEnd = nodeEnd + currChar;
                    } else if (parsingSection == 3 && shouldEdit) {
                        currEdgeWeight = currEdgeWeight + currChar;
                    }
                }
                parseCounter = parseCounter + 1;
            }

            drawGraph();
        }
    }

    static Graph serializeGraph(org.graphstream.graph.implementations.SingleGraph graph,
                    HashMap<String, Integer> map) {

        int numNodes = 0;
        for (@SuppressWarnings("unused") Node node : graph) {
            numNodes = numNodes + 1;
        }

        Graph hwGraph = new Graph(numNodes);

        // now add edges
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            // get the two endpoints
            Node startVertex = edge.getNode0();
            Node endVertex = edge.getNode1();
            int startId = map.get(startVertex.getId());
            int endId = map.get(endVertex.getId());
            hwGraph.addEdge(startId, endId,
                            edgeToWeight.get(startVertex.getId() + endVertex.getId()));
        }

        return hwGraph;
    }

    static WDGraph serializeWDGraph(org.graphstream.graph.implementations.SingleGraph graph,
                    HashMap<String, Integer> map) {

        int numNodes = 0;
        for (@SuppressWarnings("unused") Node node : graph) {
            numNodes = numNodes + 1;
        }

        WDGraph hwGraph = new WDGraph(numNodes);

        // now add edges
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            // get the two endpoints
            Node startVertex = edge.getNode0();
            Node endVertex = edge.getNode1();
            int startId = map.get(startVertex.getId());
            int endId = map.get(endVertex.getId());
            hwGraph.addEdge(startId, endId,
                            (double) edgeToWeight.get(startVertex.getId() + endVertex.getId()));
        }

        return hwGraph;
    }

    static void clearGraph() {
        graph.clear();
        edgeToWeight.clear();
        graph.addAttribute("ui.stylesheet", styleSheet);
        drawGraph();
    }

    static void clearColors() {
        for (Node node : graph) {
            node.removeAttribute("ui.class");
            node.removeAttribute("visited");
            node.removeAttribute("ui.color");
        }
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            edge.removeAttribute("ui.class");
        }
        drawGraph();
    }

    static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // ui methods for get and put JSON
    static void showTextDialog(String content) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                | UnsupportedLookAndFeelException ex) {
                }

                JTextArea ta = new JTextArea(20, 20);
                ta.setText(content);
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                ta.setCaretPosition(0);
                ta.setEditable(false);

                Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

                JOptionPane.showMessageDialog(null, new JScrollPane(ta),
                                "JSON Graph Representation", JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(image));

            }
        });
    }

    static void showInputTextDialog() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                | UnsupportedLookAndFeelException ex) {
                }

                JTextArea ta = new JTextArea(30, 30);
                ta.setText("");
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                ta.setCaretPosition(0);
                ta.setEditable(true);

                int result = JOptionPane.showConfirmDialog(null, new JScrollPane(ta),
                                "JSON Graph Representation", JOptionPane.DEFAULT_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    createGraphFromJSON(ta.getText());
                }

            }
        });
    }

    static void convertToDirectedGraph() {
        ArrayList<org.graphstream.graph.Edge> edgeList =
                        new ArrayList<org.graphstream.graph.Edge>();
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            edgeList.add(edge);
        }
        // clear all edges
        for (Node n : graph) {
            Iterator<org.graphstream.graph.Edge> it = n.getEdgeIterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
        // readd edges
        for (org.graphstream.graph.Edge edge : edgeList) {
            graph.addEdge(edge.getId(), edge.getNode0().getId(), edge.getNode1().getId(), true);
            graph.getEdge(edge.getId()).addAttribute("ui.label",
                            Integer.toString(edgeToWeight.get(edge.getId())));
        }
        edgeList.clear();
        drawGraph();

    }

    static void convertToUndirectedGraph() {
        ArrayList<org.graphstream.graph.Edge> edgeList =
                        new ArrayList<org.graphstream.graph.Edge>();
        for (org.graphstream.graph.Edge edge : graph.getEdgeSet()) {
            edgeList.add(edge);
        }
        // clear all edges
        for (Node n : graph) {
            Iterator<org.graphstream.graph.Edge> it = n.getEdgeIterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
        // readd edges
        for (org.graphstream.graph.Edge edge : edgeList) {
            graph.addEdge(edge.getId(), edge.getNode0().getId(), edge.getNode1().getId(), false);
            graph.getEdge(edge.getId()).addAttribute("ui.label",
                            Integer.toString(edgeToWeight.get(edge.getId())));
        }
        edgeList.clear();
        drawGraph();
    }

    @SuppressWarnings("serial")
    static void initTopBar() {
        JButton nodeAddButton = new JButton("Add Node");
        JTextField nodeNameField = new HintTextField("Node name");

        nodeNameField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNode(nodeNameField.getText());
                drawGraph();
                nodeNameField.setText("");
                nodeNameField.requestFocus();
            }
        });

        JButton edgeAddButton = new JButton("Add Edge");
        JTextField edgeStartVertexField = new HintTextField("Starting vertex");
        JTextField edgeEndVertexField = new HintTextField("Ending vertex");
        JTextField edgeWeightField = new HintTextField("Edge Weight");

        edgeEndVertexField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdge(edgeStartVertexField.getText(), edgeEndVertexField.getText(),
                                edgeWeightField.getText());
                drawGraph();
                edgeStartVertexField.setText("");
                edgeEndVertexField.setText("");
                edgeWeightField.setText("");
                edgeStartVertexField.requestFocus();
            }
        });

        edgeWeightField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdge(edgeStartVertexField.getText(), edgeEndVertexField.getText(),
                                edgeWeightField.getText());
                drawGraph();
                edgeStartVertexField.setText("");
                edgeEndVertexField.setText("");
                edgeWeightField.setText("");
                edgeStartVertexField.requestFocus();
            }
        });

        bfsButton = new JButton("BFS Shortest Path");
        JTextField bfsStart = new HintTextField("Start Node");
        JTextField bfsEnd = new HintTextField("End Node");

        bfsEnd.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runBFS(bfsStart.getText(), bfsEnd.getText());
                bfsStart.setText("");
                bfsEnd.setText("");
            }
        });

        bfsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runBFS(bfsStart.getText(), bfsEnd.getText());
                bfsStart.setText("");
                bfsEnd.setText("");
            }
        });

        nodeAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNode(nodeNameField.getText());
                drawGraph();
                nodeNameField.setText("");
                nodeNameField.requestFocus();
            }
        });

        edgeAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEdge(edgeStartVertexField.getText(), edgeEndVertexField.getText(),
                                edgeWeightField.getText());
                drawGraph();
                edgeStartVertexField.setText("");
                edgeEndVertexField.setText("");
                edgeWeightField.setText("");
                edgeStartVertexField.requestFocus();
            }
        });

        JButton clearGraph = new JButton("Clear Graph");

        clearGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearGraph();
            }
        });


        JButton clearColors = new JButton("Clear Colors");

        clearColors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearColors();
            }
        });

        JButton getJsonRep = new JButton("Get JSON");

        getJsonRep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jsonRep = jsonSerializerGraph();
                // make sure JSON not empty
                if (jsonRep.length() > 24) {
                    showTextDialog(jsonRep);
                } else {
                    showErrorMessage("Graph is empty");
                }
            }
        });

        JButton putJsonRep = new JButton("Put JSON");

        putJsonRep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInputTextDialog();
            }
        });


        kruskalButton = new JButton("Run Kruskal");

        kruskalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runKruskal();
            }
        });

        isDirected = new JCheckBox("Directed");
        isDirected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isDirected.isSelected()) {
                    // convert to directed graph
                    convertToDirectedGraph();
                } else {
                    // convert to undirected graph
                    convertToUndirectedGraph();
                }
                toggleButtons();
            }
        });

        dfsButton = new JButton("Run DFS");
        JTextField dfsField = new HintTextField("Start Node");

        dfsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runDFS(dfsField.getText());
                dfsField.setText("");
            }
        });

        dijkstraButton = new JButton("Run Dijkstra");
        dijkstraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runDijkstra(bfsStart.getText(), bfsEnd.getText());
                bfsStart.setText("");
                bfsEnd.setText("");
            }
        });

        sccButton = new JButton("Run SCC");
        sccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runSCC();
            }
        });

        widestPathButton = new JButton("Run WidestPath");
        widestPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runWidestPath(bfsStart.getText(), bfsEnd.getText());
                bfsStart.setText("");
                bfsEnd.setText("");
            }
        });



        JPanel topBar = new JPanel(new GridLayout(2, 1));

        JPanel graphComponents = new JPanel();

        JPanel bfsFields = new JPanel();

        JPanel twoFieldsPanel = new JPanel();

        JPanel bfsDijkstras = new JPanel();
        JPanel threeAlg = new JPanel();

        JPanel clearGraphButtons = new JPanel();
        JPanel jsonGraphButtons = new JPanel();

        JPanel jsonAndDirectedPanel = new JPanel();

        JPanel graphButtons = new JPanel();

        JPanel graphRunOptions = new JPanel();

        JPanel nodePanel = new JPanel();
        JPanel edgePanel = new JPanel();
        JPanel edgeAndNodePanel = new JPanel();

        JPanel textFieldEdgesNodes = new JPanel();
        JPanel textFieldEdges = new JPanel();

        JPanel dfsPanel = new JPanel();
        JPanel sccKurskal = new JPanel();
        JPanel dfsSccKruskal = new JPanel();

        textFieldEdgesNodes.add(edgeStartVertexField, BorderLayout.WEST);
        textFieldEdgesNodes.add(edgeEndVertexField, BorderLayout.EAST);
        textFieldEdges.add(textFieldEdgesNodes, BorderLayout.WEST);
        textFieldEdges.add(edgeWeightField, BorderLayout.EAST);
        edgePanel.add(textFieldEdges, BorderLayout.WEST);
        edgePanel.add(edgeAddButton, BorderLayout.EAST);
        nodePanel.add(nodeNameField, BorderLayout.WEST);
        nodePanel.add(nodeAddButton, BorderLayout.EAST);
        edgeAndNodePanel.add(nodePanel, BorderLayout.WEST);
        edgeAndNodePanel.add(edgePanel, BorderLayout.EAST);
        clearGraphButtons.add(clearColors, BorderLayout.WEST);
        clearGraphButtons.add(clearGraph, BorderLayout.EAST);
        jsonGraphButtons.add(getJsonRep, BorderLayout.WEST);
        jsonGraphButtons.add(putJsonRep, BorderLayout.EAST);
        jsonAndDirectedPanel.add(jsonGraphButtons, BorderLayout.WEST);
        jsonAndDirectedPanel.add(isDirected, BorderLayout.EAST);
        graphButtons.add(clearGraphButtons, BorderLayout.WEST);
        graphButtons.add(jsonAndDirectedPanel, BorderLayout.EAST);
        graphComponents.add(edgeAndNodePanel, BorderLayout.WEST);
        graphComponents.add(graphButtons, BorderLayout.EAST);

        bfsFields.add(bfsStart, BorderLayout.WEST);
        bfsFields.add(bfsEnd, BorderLayout.EAST);
        bfsDijkstras.add(bfsButton, BorderLayout.WEST);
        bfsDijkstras.add(dijkstraButton, BorderLayout.EAST);
        threeAlg.add(bfsDijkstras, BorderLayout.WEST);
        threeAlg.add(widestPathButton, BorderLayout.EAST);
        twoFieldsPanel.add(bfsFields, BorderLayout.WEST);
        twoFieldsPanel.add(threeAlg, BorderLayout.EAST);
        dfsPanel.add(dfsField, BorderLayout.WEST);
        dfsPanel.add(dfsButton, BorderLayout.EAST);

        sccKurskal.add(sccButton, BorderLayout.WEST);
        sccKurskal.add(kruskalButton, BorderLayout.EAST);

        dfsSccKruskal.add(dfsPanel, BorderLayout.WEST);
        dfsSccKruskal.add(sccKurskal, BorderLayout.EAST);

        graphRunOptions.add(twoFieldsPanel, BorderLayout.WEST);
        graphRunOptions.add(dfsSccKruskal, BorderLayout.EAST);

        topBar.add(graphComponents, BorderLayout.NORTH);
        topBar.add(graphRunOptions, BorderLayout.SOUTH);

        twoFieldsPanel.setBackground(Color.LIGHT_GRAY);
        dfsPanel.setBackground(Color.LIGHT_GRAY);
        nodePanel.setBackground(Color.LIGHT_GRAY);
        edgePanel.setBackground(Color.LIGHT_GRAY);


        mainFrame.add(topBar, BorderLayout.NORTH);
    }
}


// class for hint text

@SuppressWarnings("serial")
class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}
