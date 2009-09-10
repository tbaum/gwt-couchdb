function(keys, vals) {
    var tree = {};
    for (var i in vals) {
        var current = tree;
        for (var j in vals[i].path) {
            var child = vals[i].path[j];
            if (current[child] == undefined) {
                current[child] = {};
            }
            current = current[child];
        }
        current['_data'] = { "name":vals[i].name };
    }
    return tree;
}