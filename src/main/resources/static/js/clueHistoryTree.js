simple_chart_config = {
    chart: {
        container: "#family-tree-container",
        rootOrientation: "SOUTH",
        connectors: {
            type: "bCurve",
            style: {
                "arrow-end": "diamond-wide-long"
            }
        }
    },
    nodeStructure: nodeStructure
}
new Treant(simple_chart_config, $);