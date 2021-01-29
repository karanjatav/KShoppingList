package com.bytescraft.kshoppinglist.dataClasses

import com.google.firebase.firestore.DocumentId

data class Category(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var items: ArrayList<Item> = arrayListOf()
)
