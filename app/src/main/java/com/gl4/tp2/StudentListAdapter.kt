package com.gl4.tp2
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*


import kotlin.collections.ArrayList


class StudentListAdapter(private val data:ArrayList<MainActivity.Student>): RecyclerView.Adapter<StudentListAdapter.ViewHolder>(),Filterable {



    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView
        val textView: TextView
        val checkBox:CheckBox

        init {
            imageView= itemView.findViewById(R.id.imageView)
            textView = itemView.findViewById(R.id.textView)
            checkBox=itemView.findViewById((R.id.checkBox))
            checkBox.setOnClickListener{v->
                val isChecked =(v as CheckBox).isChecked
                dataFilterList[adapterPosition].isPresent=isChecked

                notifyDataSetChanged()


            }
        }

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: StudentListAdapter.ViewHolder, position: Int) {



        if (dataFilterList[position].gender == "Homme") {
            holder.imageView.setImageResource(R.drawable.man)
        }
        else  {
            holder.imageView.setImageResource(R.drawable.woman)
        }

        holder.textView.text = dataFilterList[position].firstName.toString() +" "+dataFilterList[position].lastName.toString()+"\nMatière: "+dataFilterList[position].matiere.toString()+"\n"+
              "Present: "+ dataFilterList[position].isPresent.toString()

        holder.checkBox.isChecked = dataFilterList[position].isPresent



    }


    override fun getItemCount(): Int {
        return dataFilterList.size
    }


    var dataFilterList = ArrayList<MainActivity.Student>()
    init {
        // associer le tableau des données initiales
        dataFilterList = data
    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val splited=constraint.toString().split(",")

                if(splited[0].equals("0")) {
                    dataFilterList = data
                    val charSearch = splited[1]
                    val resultList = ArrayList<MainActivity.Student>()
                        for (student in data) {
                            if (student.matiere.equals(charSearch))
                             {
                                resultList.add(student)

                            }
                        }
                        dataFilterList = resultList



                }
                else if(splited[0].equals("1")) {

                    val charSearch = splited[1]
                    if (charSearch.isEmpty()) {
                        dataFilterList = data
                    } else {
                        val resultList = ArrayList<MainActivity.Student>()
                        for (student in data) {
                            if (student.firstName.lowercase(Locale.ROOT)
                                    .contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(student)

                            }
                        }
                        dataFilterList = resultList
                    }
                }

                else if(splited[0].equals("2")) {
                    dataFilterList = data
                    val charSearch = splited[1]
                    val resultList = ArrayList<MainActivity.Student>()

                    if(charSearch.equals("Présent"))
                    {
                        for (student in data) {
                            if (student.isPresent==true)
                            {
                                resultList.add(student)

                            }
                        }

                    }

                    else if(charSearch.equals("Absent"))
                    {
                        for (student in data) {
                            if (student.isPresent==false)
                            {
                                resultList.add(student)

                            }
                        }

                    }

                    dataFilterList = resultList



                }


                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<MainActivity.Student>

                notifyDataSetChanged()
            }

        }



    }







}