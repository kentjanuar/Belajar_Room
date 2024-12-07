package com.implisit.belajar_room

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.implisit.belajar_room.database.daftarBelanja
import com.implisit.belajar_room.database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class halamanFavorit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_favorit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = daftarBelanjaDB.getDatabase(this)

        val _keHome = findViewById<Button>(R.id.keHome)

        _keHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        adapterDaftar = adapterDaftar(arDaftar)

        val _rvFavorit = findViewById<RecyclerView>(R.id.rcFavorit)

        _rvFavorit.layoutManager = LinearLayoutManager(this)
        _rvFavorit.adapter = adapterDaftar

        CoroutineScope(Dispatchers.IO).async{
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectALLFav()
            arDaftar.addAll(daftarBelanja)
            adapterDaftar.notifyDataSetChanged()

        }
    }

    private  lateinit var  DB : daftarBelanjaDB
    private lateinit var  adapterDaftar: adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()
}