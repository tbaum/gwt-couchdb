function (doc) {
    if (doc.type == 'equipment') {
        emit(doc._id, doc);

    }
}