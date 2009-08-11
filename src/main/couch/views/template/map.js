function (doc) {
    if (doc.type == 'template') {
        emit(doc.path, doc);
    }
}