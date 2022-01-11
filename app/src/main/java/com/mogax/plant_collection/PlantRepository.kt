package com.mogax.plant_collection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mogax.plant_collection.PlantRepository.Singleton.databaseRef
import com.mogax.plant_collection.PlantRepository.Singleton.plantList

class PlantRepository {

    object Singleton{
        val databaseRef = FirebaseDatabase.getInstance().getReference("plant")

        val plantList = arrayListOf<PlantModele>()
    }

    fun updateData(callback: () -> Unit){
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                plantList.clear()
                for (ds in p0.children){
                    val plant = ds.getValue(PlantModele::class.java)
                    if(plant!=null){
                        plantList.add(plant)
                    }
                }
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }

}