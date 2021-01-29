package com.bytescraft.kshoppinglist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bytescraft.kshoppinglist.dataClasses.Category
import com.bytescraft.kshoppinglist.dataClasses.Item
import com.bytescraft.kshoppinglist.dataClasses.SelectedItems
import com.bytescraft.kshoppinglist.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "K_TAG"
    private val db = FirebaseFirestore.getInstance()
    private var categories: ArrayList<Category> = arrayListOf()
    private var selectedItems: ArrayList<SelectedItems> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*   binding.saveButton.setOnClickListener {
               val firstName = binding.inputFirstName.text.toString()
               val lastName = binding.inputLastName.text.toString()

               //     saveFireStore(firstName, lastName)

           }*/
        getCategories()
    }

    private fun getCategories() {
        db.collection("categories")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        Log.d(TAG, "readFireStoreData==data: ${document.data}")
                        val category: Category = document.toObject(Category::class.java)
                        categories.add(category)
                    }
                    getItems()
                }
            }
    }

    private fun getItems() {
        db.collection("items")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        Log.d(TAG, "readFireStoreData==data: ${document.data}")
                        val item: Item = document.toObject(Item::class.java)

                        item.categoryIds.forEach { id ->
                            categories.forEach { category ->
                                if (category.id == id) {
                                    category.items.add(item)
                                }
                            }
                        }
                    }
                    getSelectedItems()
                }
            }
    }


    private fun getSelectedItems() {
        db.collection("selectedItems")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        Log.d(TAG, "readFireStoreData==data: ${document.data}")
                        selectedItems.add(document.toObject(SelectedItems::class.java))
                    }
                    listCategoriesWithItems()
                }
            }
    }

    private fun listCategoriesWithItems() {
        val result = StringBuffer()
        categories.forEach { category ->
            result.append(category.name+"\n")

            category.items.forEach { item ->
                result.append("  " + item.name)

                selectedItems.forEach { selectedItem ->
                    if (selectedItem.itemId == item.id) {
                        result.append(" - ")
                            .append(selectedItem.quantity)
                    }
                }

                result.append("\n")
            }
            result.append("\n\n")
        }
        binding.textViewResult.text = result
    }
}
/* private fun readFireStoreData() {
     val db = FirebaseFirestore.getInstance()
     db.collection("items")
         .get()
         .addOnCompleteListener {

             val result: StringBuffer = StringBuffer()

             if (it.isSuccessful) {
                 for (document in it.result!!) {
                     result.append(document.data.getValue("name")).append(" ")
                         .append(document.data.getValue("quantity")).append("\n\n")
                 }
                 binding.textViewResult.text = result
             }
         }

 }*/

/*   fun saveFireStore(firstname: String, lastname: String) {
       val db = FirebaseFirestore.getInstance()
       val user: MutableMap<String, Any> = HashMap()
       user["firstName"] = firstname
       user["lastName"] = lastname

       db.collection("users")
               .add(user)
               .addOnSuccessListener {
                   Toast.makeText(this@MainActivity, "record added successfully ", Toast.LENGTH_SHORT).show()
               }
               .addOnFailureListener {
                   Toast.makeText(this@MainActivity, "record Failed to add ", Toast.LENGTH_SHORT).show()
               }
       readFireStoreData()
   }*/

