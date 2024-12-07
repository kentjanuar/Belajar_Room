package com.implisit.belajar_room

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.implisit.belajar_room.database.daftarBelanja
import com.implisit.belajar_room.database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class adapterDaftar (private val daftarBelanja : MutableList<daftarBelanja>):
        RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private lateinit var onItemCLickCallback: OnItemClickCallback


    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){ 
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.item)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlah)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tanggal)
        var _btnStatus = itemView.findViewById<Button>(R.id.btnStatus)

        var _btnEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        var _btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]
        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit",1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemCLickCallback.delData(daftar)
        }

        holder._btnStatus.setOnClickListener{
            if(daftar.status == 0){
                daftar.status = 1
                holder._btnStatus.setText("Unfavorit")
                CoroutineScope(Dispatchers.IO).async {
                    val DB = daftarBelanjaDB.getDatabase(it.context)
                    DB.fundaftarBelanjaDAO().update(
                        daftar.tanggal ?: "",
                        daftar.item ?: "",
                        daftar.jumlah ?: "",
                        1,
                        pilihid = daftar.id
                    )
                }

                val intent = Intent(it.context,  MainActivity::class.java)
                it.context.startActivity(intent)

                Toast.makeText(it.context, "Barang di favorit", Toast.LENGTH_SHORT).show()
            } else {
                daftar.status = 0
                holder._btnStatus.setText("Favorit")
                CoroutineScope(Dispatchers.IO).async {
                    val DB = daftarBelanjaDB.getDatabase(it.context)
                    DB.fundaftarBelanjaDAO().update(
                        daftar.tanggal ?: "",
                        daftar.item ?: "",
                        daftar.jumlah ?: "",
                        0,
                        pilihid = daftar.id
                    )

                    val intent = Intent(it.context,halamanFavorit::class.java)
                    it.context.startActivity(intent)
                }
                Toast.makeText(it.context, "Barang di unfavorit", Toast.LENGTH_SHORT).show()

            }
            notifyDataSetChanged()
        }

    }

    fun isiData(daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
    return daftarBelanja.size
    }

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }



    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCLickCallback = onItemClickCallback
    }



}