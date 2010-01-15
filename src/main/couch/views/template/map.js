function (doc) {
    if (doc.type == 'template' && doc.path) {
        emit(doc.path, doc);
    }
}