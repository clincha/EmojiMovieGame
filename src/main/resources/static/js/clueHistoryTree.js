simple_chart_config = {
    chart: {
        container: "#family-tree-container"
    },
    nodeStructure: nodeStructure
}
new Treant(simple_chart_config, function () {
    alert('Tree Loaded')
}, $);