function (doc) {
    if (doc.type == 'template') {
        emit(doc._id, { name:doc.name });
        for (var i in doc.path) {
            emit(doc.path[i], { id: doc._id, pos: i});
        }
    }
}