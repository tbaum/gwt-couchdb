function(doc) {
    if (doc.type == "group") {
        for (var i = 0; i < doc.users.length; ++i) {
            emit(doc.users[i], doc.permissions);
        }
    }
}
