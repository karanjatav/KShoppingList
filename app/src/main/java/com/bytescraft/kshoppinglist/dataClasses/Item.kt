package com.bytescraft.kshoppinglist.dataClasses

import com.google.firebase.firestore.DocumentId

data class Item(
    @DocumentId
    var id: String = "",
    var categoryIds: List<String> = arrayListOf(),
    var name: String = "",
    var quantityUnit: String = ""
)