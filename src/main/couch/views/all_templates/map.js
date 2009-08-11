function (doc) {
    if (doc.type == 'template' && doc.path && doc.path.length == 1) {
        emit(doc.path, doc);
    }
}