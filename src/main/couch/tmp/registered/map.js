function (doc) {
    if (( doc.type == 'user' ) && (doc.confirmed === true )) {
        emit(doc._id, doc._id);
    }
}
