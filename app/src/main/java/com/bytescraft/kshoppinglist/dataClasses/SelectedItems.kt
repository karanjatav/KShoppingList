package com.bytescraft.kshoppinglist.dataClasses

import com.google.firebase.firestore.DocumentId

data class SelectedItems(
    @DocumentId
    var id: String = "",
    var itemId: String = "",
    var quantity: Int = 0
)
