function (doc) {
    if (doc.type == 'statusgroup') {
        emit(doc._id, doc);
    }
}