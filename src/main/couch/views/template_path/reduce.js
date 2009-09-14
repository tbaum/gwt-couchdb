function(keys, vals, re) {
    log("==================================");
    log(keys);
    log(vals);
    log(re);
    var result = [];
    var names = {};

    for (var k1 in keys) {
        var docid1 = keys[k1][1];
        names[docid1] = vals[k1];
    }
    log(names);

    for (var k2 in keys) {
        var path = keys[k2][0];
        var docid = keys[k2][1];
        names[docid] = vals[k2];
        var name = "";
        var missing = false;
        for (var p in path) {
            if (name) name += " : ";
            log("look for " + path[p]);
            var pi = path[p];
            if (names[pi])   name += names[pi];
            else missing = true;
        }
        result[k2] = missing ? null : name;
    }

    log("we got");
    log(result);
    for (var k in result) {
        if (!result[k]) return null;
    }
    return result;
}