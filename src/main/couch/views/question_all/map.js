function (doc) {
    if (doc.type == 'question') {
        emit(doc._id, doc);
    }
}