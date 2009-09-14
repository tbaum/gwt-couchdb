function(newDoc, oldDoc, userCtx) {
    // !code lib/validate.js
    log(userCtx);
    if (newDoc._deleted) {
        return true;
    }

    unchanged("type");

    if (newDoc.type == "user") {
        //    require("name", "path");
    } else

    if (newDoc.type == "question") {
        require("name", "answers");
    }
    else  if (newDoc.type == "equipment") {
        require("name", "templates");
    }
    else  if (newDoc.type == "template") {
        require("name", "path");
    }
    else {
        forbidden("unknown or missing document-type");
    }
    return true;
}

