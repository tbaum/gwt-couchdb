function(doc, req) {
    // !code vendor/couchapp/path.js
    // !code vendor/couchapp/template.js

    // we only show html
    return  req.query.f + "(" + toJSON(doc) + ")";
    //    assets : assetPath(),
    //    index : listPath('index','recent-posts',{descending:true,limit:8})
}