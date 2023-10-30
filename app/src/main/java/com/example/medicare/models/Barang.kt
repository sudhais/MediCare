package com.example.medicare.models

class Barang {

    var id: Int? = null
    var title: String? = null
    var author: String? = null
    var category: String? = null
    var content: String? = null
    var date: String? = null
    var image: ByteArray? = null

    constructor(id: Int, title: String, author: String, category: String, content: String, date: String, image: ByteArray){
        this.id = id
        this.title = title
        this.author = author
        this.category = category
        this.content = content
        this.date = date
        this.image = image
    }
}