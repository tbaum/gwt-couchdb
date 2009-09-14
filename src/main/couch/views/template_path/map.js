function (doc) {
    if (doc.type == 'template') {
        //      emit(doc._id, doc);
        emit(doc.path, doc.name);
    }
}