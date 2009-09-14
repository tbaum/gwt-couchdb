function(keys, values) {
    var permissions = [];
    for (var i in values) {
        for (j in values[i]) {
            if (permissions.indexOf(values[i][j]) == -1) {
                permissions.push(values[i][j]);
            }
        }
    }

    return permissions;
}