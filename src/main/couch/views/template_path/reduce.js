function(keys, vals, re) {
    if (re) {
        return null;
    } else {

        var name ;
        for (var i in vals) {
            if (vals[i].name) {
                name = vals[i].name;
                break;
            }
        }

        var result = {};
        for (var i in vals) {
            if (vals[i].pos) {
                result[vals[i].id] = [ vals[i].pos, name ];
            }
        }
        return result;
        
    }
}