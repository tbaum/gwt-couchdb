function(doc) {
    for (var i in doc.path) {
        emit(doc.path[i], 1);
    }
}